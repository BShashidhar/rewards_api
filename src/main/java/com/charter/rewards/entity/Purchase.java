package com.charter.rewards.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a recorded customer purchase transaction.
 * <p>
 * This entity maps to the "purchases" table in the database and is used to
 * track transaction amounts and dates for the calculation of reward points.
 * </p>
 */
@Entity
@Table(name = "purchases")
@Getter
@NoArgsConstructor
public class Purchase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "customer_id", nullable = false)
	private Long customerId;

	@Column(name = "purchase_date", nullable = false)
	private LocalDate purchaseDate;

	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal amount;

	/** Creates a purchase record. */
	public Purchase(Long customerId, LocalDate purchaseDate, BigDecimal amount) {
		if (customerId == null || purchaseDate == null || amount == null || amount.signum() < 0) {
			throw new IllegalArgumentException("Purchase must have a customer, date, and non-negative amount");
		}
		this.customerId = customerId;
		this.purchaseDate = purchaseDate;
		this.amount = amount;
	}
}
