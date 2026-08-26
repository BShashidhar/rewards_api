package com.charter.rewards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/** REST response containing monthly and total reward points. */
@Schema(description = "Customer reward points grouped by month")
public record RewardsResponse(Long customerId, String customerName, List<MonthlyReward> monthlyRewards,
                              int totalPoints) {
}
