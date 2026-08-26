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

@Service
@RequiredArgsConstructor
public class RewardsServiceImpl implements RewardsService {

    private final CustomerRepository customerRepository;
    private final PurchaseRepository purchaseRepository;

    @Override
    @Transactional(readOnly = true)
    public RewardsResponse getRewards(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        // Enforce the 3-month requirement
        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);

        // Update repository to: findByCustomerIdAndPurchaseDateAfter(...)
        Map<YearMonth, Integer> pointsByMonth = purchaseRepository
                .findByCustomerIdAndPurchaseDateAfterOrderByPurchaseDate(customerId, threeMonthsAgo)
                .stream()
                .collect(Collectors.groupingBy(
                        purchase -> YearMonth.from(purchase.getPurchaseDate()),
                        Collectors.summingInt(purchase -> calculatePoints(purchase.getAmount()))));

        List<MonthlyReward> monthlyRewards = pointsByMonth.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> new MonthlyReward(entry.getKey().toString(), entry.getValue()))
                .toList();
        int totalPoints = monthlyRewards.stream().mapToInt(MonthlyReward::points).sum();
        return new RewardsResponse(customer.getId(), customer.getName(), monthlyRewards, totalPoints);
    }

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