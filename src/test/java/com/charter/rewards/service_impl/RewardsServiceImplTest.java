package com.charter.rewards.service_impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.charter.rewards.dto.MonthlyReward;
import com.charter.rewards.dto.RewardsResponse;
import com.charter.rewards.entity.Customer;
import com.charter.rewards.entity.Purchase;
import com.charter.rewards.repository.CustomerRepository;
import com.charter.rewards.repository.PurchaseRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Unit tests for reward point calculation and monthly aggregation. */
@ExtendWith(MockitoExtension.class)
class RewardsServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PurchaseRepository purchaseRepository;

    @InjectMocks
    private RewardsServiceImpl rewardsService;

    @Test
    void calculatesProgressiveTiers() {
        assertThat(rewardsService.calculatePoints(new BigDecimal("50.00"))).isZero();
        assertThat(rewardsService.calculatePoints(new BigDecimal("75.00"))).isEqualTo(25);
        assertThat(rewardsService.calculatePoints(new BigDecimal("120.00"))).isEqualTo(90);
    }

    @Test
    void rejectsNegativeAmount() {
        assertThatThrownBy(() -> rewardsService.calculatePoints(new BigDecimal("-1.00")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsMissingAmount() {
        assertThatThrownBy(() -> rewardsService.calculatePoints(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void aggregatesMultipleTransactionsAcrossDynamicMonths() {
        Customer customer = new Customer("Test Customer");
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(purchaseRepository.findByCustomerIdOrderByPurchaseDate(1L)).thenReturn(List.of(
                new Purchase(1L, LocalDate.of(2026, 3, 1), new BigDecimal("120")),
                new Purchase(1L, LocalDate.of(2026, 1, 1), new BigDecimal("75")),
                new Purchase(1L, LocalDate.of(2026, 1, 2), new BigDecimal("200"))));

        RewardsResponse response = rewardsService.getRewards(1L);

        assertThat(response.monthlyRewards()).containsExactly(
                new MonthlyReward("2026-01", 225), new MonthlyReward("2026-03", 90));
        assertThat(response.totalPoints()).isEqualTo(315);
    }
}
