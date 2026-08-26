package com.charter.rewards.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** A recorded customer purchase used to calculate reward points. */
@Entity
@Table(name = "purchases")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
