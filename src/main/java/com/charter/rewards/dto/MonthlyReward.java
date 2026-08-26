package com.charter.rewards.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** Reward points earned by a customer during one calendar month. */
@Schema(description = "Reward points earned in one calendar month")
public record MonthlyReward(String month, int points) {
}
