package com.springprojects.banking_application.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="transactions")
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String transactionalId;

    private String transactionalType;

    private BigDecimal amount;

    private String accountNumber;

    private String status;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private LocalDateTime dateTime;


}
