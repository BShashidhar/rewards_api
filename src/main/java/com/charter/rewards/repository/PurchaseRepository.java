package com.charter.rewards.repository;

import com.charter.rewards.entity.Purchase;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link Purchase} entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide standard CRUD
 * operations and custom query methods for accessing customer purchase records
 * in the database.
 * </p>
 */
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

	/**
	 * Retrieves a chronological list of all purchases made by a specific customer.
	 *
	 * @param customerId the unique identifier of the customer
	 * @return a {@link List} of {@link Purchase} entities ordered by their purchase
	 *         date (ascending)
	 */
	List<Purchase> findByCustomerIdOrderByPurchaseDate(Long customerId);

	/**
	 * Retrieves a chronological list of recent purchases made by a specific
	 * customer occurring strictly after a given cutoff date.
	 *
	 * @param customerId the unique identifier of the customer
	 * @param cutoffDate the date after which purchases should be retrieved
	 *                   (exclusive)
	 * @return a {@link List} of {@link Purchase} entities ordered by their purchase
	 *         date (ascending)
	 */
	List<Purchase> findByCustomerIdAndPurchaseDateAfterOrderByPurchaseDate(Long customerId, LocalDate cutoffDate);
}