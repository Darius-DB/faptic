package com.faptic.crypto.model.dto;

import com.faptic.crypto.model.Currency;

public class CurrencyMapper {

    public static Currency toCurrencyEntity(CurrencyDto currencyDto) {
        return Currency.builder()
                .timestamp(currencyDto.getTimestamp())
                .symbol(currencyDto.getSymbol())
                .price(currencyDto.getPrice())
                .build();
    }

    public static CurrencyDto toCurrencyDto(Currency currency) {
        return CurrencyDto.builder()
                .id(currency.getId())
                .timestamp(currency.getTimestamp())
                .symbol(currency.getSymbol())
                .price(currency.getPrice())
                .normalizedRange(currency.getNormalizedRange())
                .build();
    }
}
