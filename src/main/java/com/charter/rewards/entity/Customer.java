package com.charter.rewards.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a customer enrolled in the rewards program.
 * <p>
 * This entity maps to the "customers" table in the database and holds the basic
 * information required to identify a customer and process their transactions.
 * </p>
 */
@Entity
@Table(name = "customers")
@Getter
@NoArgsConstructor
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 100)
	private String name;

	/** Creates a customer with a database-generated identifier. */
	public Customer(String name) {
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("Customer name is required");
		}
		this.name = name;
	}

}
