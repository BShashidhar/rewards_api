package com.charter.rewards.repository;

import com.charter.rewards.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

/** Provides persistence operations for customers. */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
