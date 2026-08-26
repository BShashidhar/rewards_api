package com.charter.rewards.repository;

import com.charter.rewards.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link Customer} entities.
 * <p>
 * This interface extends {@link JpaRepository} to inherit standard CRUD
 * (Create, Read, Update, Delete) operations and JPA-specific database
 * interactions for the customer records, without requiring manual
 * implementation.
 * </p>
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}