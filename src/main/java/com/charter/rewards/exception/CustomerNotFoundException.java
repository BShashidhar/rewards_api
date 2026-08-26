package com.charter.rewards.exception;

/** Indicates that a requested customer does not exist. */
public class CustomerNotFoundException extends RuntimeException {

    /** Creates an exception for a missing customer identifier. */
    public CustomerNotFoundException(Long customerId) {
        super("Customer not found: " + customerId);
    }
}
