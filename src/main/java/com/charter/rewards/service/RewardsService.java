package com.charter.rewards.service;

import com.charter.rewards.dto.RewardsResponse;
import java.math.BigDecimal;

/**
 * Service interface defining the operations for calculating and retrieving
 * customer reward points.
 * <p>
 * Implementations of this interface handle the business logic required to
 * process purchase transactions and convert them into reward points based on
 * the program's defined rules.
 * </p>
 */
public interface RewardsService {

	/**
	 * Retrieves the accumulated monthly and total reward points for a specific
	 * customer.
	 *
	 * @param customerId the unique identifier of the customer
	 * @return a {@link RewardsResponse} containing the points grouped by month as
	 *         well as the overall total points
	 */
	RewardsResponse getRewards(Long customerId);

	/**
	 * Calculates the reward points earned for a single purchase transaction.
	 * <p>
	 * Applies the specific reward program calculation rules to the given monetary
	 * amount to determine the correct number of points awarded.
	 * </p>
	 *
	 * @param amount the total monetary amount of the purchase
	 * @return the calculated reward points for the given amount
	 */
	int calculatePoints(BigDecimal amount);
}