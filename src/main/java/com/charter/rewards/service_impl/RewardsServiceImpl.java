package com.charter.rewards.service_impl;

import com.charter.rewards.dto.MonthlyReward;
import com.charter.rewards.dto.RewardsResponse;
import com.charter.rewards.entity.Customer;
import com.charter.rewards.exception.CustomerNotFoundException;
import com.charter.rewards.repository.CustomerRepository;
import com.charter.rewards.repository.PurchaseRepository;
import com.charter.rewards.service.RewardsService;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Calculates customer reward points from persisted purchase records. */
@Service
public class RewardsServiceImpl implements RewardsService {

    private static final BigDecimal FIFTY = BigDecimal.valueOf(50);
    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

    private final CustomerRepository customerRepository;
    private final PurchaseRepository purchaseRepository;

    /** Creates a rewards service with its persistence dependencies. */
    public RewardsServiceImpl(CustomerRepository customerRepository, PurchaseRepository purchaseRepository) {
        this.customerRepository = customerRepository;
        this.purchaseRepository = purchaseRepository;
    }

    /** Returns dynamically discovered monthly points and the customer's total. */
    @Override
    @Transactional(readOnly = true)
    public RewardsResponse getRewards(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        Map<YearMonth, Integer> pointsByMonth = purchaseRepository.findByCustomerIdOrderByPurchaseDate(customerId)
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

    /** Calculates points for one purchase using the program's progressive tiers. */
    @Override
    public int calculatePoints(BigDecimal amount) {
        if (amount == null || amount.signum() < 0) {
            throw new IllegalArgumentException("Purchase amount must be non-negative");
        }
        if (amount.compareTo(FIFTY) <= 0) {
            return 0;
        }
        if (amount.compareTo(ONE_HUNDRED) <= 0) {
            return amount.subtract(FIFTY).intValue();
        }
        return 50 + amount.subtract(ONE_HUNDRED).multiply(BigDecimal.valueOf(2)).intValue();
    }
}
