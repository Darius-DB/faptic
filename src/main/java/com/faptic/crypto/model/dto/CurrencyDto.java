package com.faptic.crypto.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class CurrencyDto {

    private Integer id;
    @NotBlank
    private LocalDate timestamp;
    @NotBlank
    private String symbol;
    @NotNull
    private Double price;
    private Double normalizedRange;
}
