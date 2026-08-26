package com.charter.rewards.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Represents the reward points earned by a customer during a specific calendar
 * month.
 * 
 * @param month  the name or identifier of the calendar month (e.g., "JANUARY",
 *               "2023-01")
 * @param points the total number of reward points accumulated during this month
 */
@Schema(description = "Reward points earned in one calendar month")
public record MonthlyReward(String month, int points) {
}
