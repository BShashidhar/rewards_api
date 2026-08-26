package com.charter.rewards.service_impl;

import com.charter.rewards.dto.MonthlyReward;
import com.charter.rewards.dto.RewardsResponse;
import com.charter.rewards.entity.Customer;
import com.charter.rewards.exception.CustomerNotFoundException;
import com.charter.rewards.repository.CustomerRepository;
import com.charter.rewards.repository.PurchaseRepository;
import com.charter.rewards.service.RewardsService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the {@link RewardsService} that calculates customer reward
 * points.
 * <p>
 * This service encapsulates the core business logic of the rewards program. It
 * fetches customer purchase history and applies the specific point calculation
 * rules to generate monthly and total reward summaries.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class RewardsServiceImpl implements RewardsService {

	private final CustomerRepository customerRepository;
	private final PurchaseRepository purchaseRepository;

	/**
	 * Calculates and retrieves the monthly and total reward points for a customer
	 * based on their purchases over the last three months.
	 * <p>
	 * The method fetches all purchases made by the customer strictly within a
	 * three-month rolling window from the current date. It groups these purchases
	 * by calendar month, calculates the points for each individual transaction, and
	 * aggregates the totals.
	 * </p>
	 *
	 * @param customerId the unique identifier of the customer
	 * @return a {@link RewardsResponse} containing the points grouped by month and
	 *         the total
	 * @throws CustomerNotFoundException if no customer is found with the provided
	 *                                   ID
	 */
	@Override
	@Transactional(readOnly = true)
	public RewardsResponse getRewards(Long customerId) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException(customerId));

		// Enforce the 3-month requirement
		LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);

		// Update repository to: findByCustomerIdAndPurchaseDateAfter(...)
		Map<YearMonth, Integer> pointsByMonth = purchaseRepository
				.findByCustomerIdAndPurchaseDateAfterOrderByPurchaseDate(customerId, threeMonthsAgo).stream()
				.collect(Collectors.groupingBy(purchase -> YearMonth.from(purchase.getPurchaseDate()),
						Collectors.summingInt(purchase -> calculatePoints(purchase.getAmount()))));

		List<MonthlyReward> monthlyRewards = pointsByMonth.entrySet().stream().sorted(Map.Entry.comparingByKey())
				.map(entry -> new MonthlyReward(entry.getKey().toString(), entry.getValue())).toList();

		int totalPoints = monthlyRewards.stream().mapToInt(MonthlyReward::points).sum();

		return new RewardsResponse(customer.getId(), customer.getName(), monthlyRewards, totalPoints);
	}

	/**
	 * Calculates the reward points for a single purchase amount based on the
	 * program rules.
	 * <p>
	 * Calculation rules:
	 * <ul>
	 * <li>A customer receives 2 points for every dollar spent over $100 in each
	 * transaction.</li>
	 * <li>A customer receives 1 point for every dollar spent between $50 and $100
	 * in each transaction.</li>
	 * <li>Fractions of a dollar are truncated prior to calculation.</li>
	 * </ul>
	 * For example, a $120 purchase = 2x$20 + 1x$50 = 90 points.
	 * </p>
	 *
	 * @param amount the total monetary amount of the transaction
	 * @return the total points earned for the transaction
	 * @throws IllegalArgumentException if the purchase amount is null or negative
	 */
	@Override
	public int calculatePoints(BigDecimal amount) {
		if (amount == null || amount.signum() < 0) {
			throw new IllegalArgumentException("Purchase amount must be non-negative");
		}

		// Truncate decimals first to strictly honor "every whole dollar spent"
		int wholeDollars = amount.intValue();

		if (wholeDollars <= 50) {
			return 0;
		}

		if (wholeDollars <= 100) {
			return wholeDollars - 50;
		}

		// 50 points for the $50-$100 tier, plus 2 points for every dollar over 100
		return 50 + ((wholeDollars - 100) * 2);
	}
}