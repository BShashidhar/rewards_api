package com.charter.rewards.exception;

/**
 * Exception thrown when a requested customer cannot be found in the system.
 * <p>
 * This is a {@link RuntimeException} that indicates a failure to locate a
 * customer by their unique identifier, typically resulting in an HTTP 404 Not
 * Found response.
 * </p>
 */
public class CustomerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new {@code CustomerNotFoundException} with a detailed message
	 * containing the missing customer's identifier.
	 *
	 * @param customerId the unique identifier of the customer that could not be
	 *                   found
	 */
	public CustomerNotFoundException(Long customerId) {
		super("Customer not found: " + customerId);
	}
}