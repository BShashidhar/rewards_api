package com.charter.rewards.repository;

import com.charter.rewards.entity.Purchase;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/** Provides persistence operations for purchase records. */
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    /** Finds all purchases belonging to a customer in date order. */
    List<Purchase> findByCustomerIdOrderByPurchaseDate(Long customerId);
}
