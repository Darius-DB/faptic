package com.faptic.crypto.repository;

import com.faptic.crypto.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    Optional<Currency> findCurrencyBySymbolIgnoreCase(String symbol);
    List<Currency> findCurrencyBySymbolIgnoreCaseOrderByTimestampAsc(String symbol);

    List<Currency> findBySymbolIgnoreCaseOrderByPriceAsc(String symbol);

    List<Currency> findAllByOrderByNormalizedRangeDescSymbol();

    List<Currency> findByTimestampAndSymbolIgnoreCaseOrderByNormalizedRange(LocalDate date, String symbol);

    List<Currency> findByTimestampOrderByNormalizedRange(LocalDate date);

}
