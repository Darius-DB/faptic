package com.faptic.crypto.service;

import com.faptic.crypto.model.Currency;
import com.faptic.crypto.model.dto.CurrencyDto;
import com.faptic.crypto.model.dto.CurrencyMapper;
import com.faptic.crypto.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public CurrencyDto getSpecifiedCrypto(String crypto, String criteria) {
//       //TODO replace RuntimeException with Custom Exception
        Currency currencyToFind = new Currency();
        switch (criteria) {
            case "newest":
                currencyToFind = currencyRepository.findCurrencyBySymbolIgnoreCaseOrderByTimestampAsc(crypto).get(0);
                break;
            case "oldest":
                int size = currencyRepository.findCurrencyBySymbolIgnoreCaseOrderByTimestampAsc(crypto).size();
                currencyToFind = currencyRepository.findCurrencyBySymbolIgnoreCaseOrderByTimestampAsc(crypto).get(size-1);
                break;
            case "min":
                currencyToFind = currencyRepository.findBySymbolIgnoreCaseOrderByPriceAsc(crypto).get(0);
               break;
            case "max":
                int length = currencyRepository.findBySymbolIgnoreCaseOrderByPriceAsc(crypto).size();
                currencyToFind = currencyRepository.findBySymbolIgnoreCaseOrderByPriceAsc(crypto).get(length-1);
                break;
//            default:
//                throw new IllegalArgumentException("You might try to search for an invalid criteria or a currency that we do not support yet");

        }

        return CurrencyMapper.toCurrencyDto(currencyToFind);
    }

    public List<CurrencyDto> getAllCurrencies() {
        return currencyRepository.findAllByOrderByNormalizedRangeDescSymbol()
                .stream()
                .map(CurrencyMapper::toCurrencyDto)
                .collect(Collectors.toList());
    }

    public CurrencyDto getCurrencyByDateAndSymbol(String date, String symbol) {
        CurrencyDto currencyDto = new CurrencyDto();
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (Objects.nonNull(symbol)) {
            int size = currencyRepository
                    .findByTimestampAndSymbolIgnoreCaseOrderByNormalizedRange(localDate, symbol).size();
            Currency currencyToFind = currencyRepository.findByTimestampAndSymbolIgnoreCaseOrderByNormalizedRange(localDate, symbol)
                    .get(size - 1);
            currencyDto = CurrencyMapper.toCurrencyDto(currencyToFind);
        } else {
            int size = currencyRepository.findByTimestampOrderByNormalizedRange(localDate).size();
            Currency currencyToFind = currencyRepository.findByTimestampOrderByNormalizedRange(localDate)
                    .get(size - 1);
            currencyDto = CurrencyMapper.toCurrencyDto(currencyToFind);
        }
        return currencyDto;
    }
}
