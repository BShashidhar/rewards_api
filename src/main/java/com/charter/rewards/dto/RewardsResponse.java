package com.charter.rewards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/**
 * REST response containing the calculated monthly and total reward points for a
 * customer.
 * 
 * @param customerId     the unique identifier of the customer
 * @param customerName   the name of the customer
 * @param monthlyRewards a list of reward points grouped by transaction month
 * @param totalPoints    the overall total reward points accumulated by the
 *                       customer
 */
@Schema(description = "Customer reward points grouped by month")
public record RewardsResponse(Long customerId, String customerName, List<MonthlyReward> monthlyRewards,
		int totalPoints) {
}
