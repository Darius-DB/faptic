package com.faptic.crypto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor @AllArgsConstructor
@Entity(name = "cryptos")
@Builder
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private LocalDate timestamp;
    @Column(nullable = false)
    private String symbol;
    @Column(nullable = false)
    private Double price;

    private Double normalizedRange;

    public Currency(LocalDate timestamp, String symbol, Double price) {
        this.timestamp = timestamp;
        this.symbol = symbol;
        this.price = price;
    }
}
