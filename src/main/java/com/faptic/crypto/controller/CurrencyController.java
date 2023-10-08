package com.faptic.crypto.controller;

import com.faptic.crypto.model.dto.CurrencyDto;
import com.faptic.crypto.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/crypto")
public class CurrencyController {

    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/{crypto}")
    public ResponseEntity<CurrencyDto> getSpecifiedCrypto(
            @PathVariable String crypto,
            @RequestParam String criteria) {
        return new ResponseEntity<>(currencyService.getSpecifiedCrypto(crypto, criteria), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<CurrencyDto>> getAllCurrencies() {
        return new ResponseEntity<>(currencyService.getAllCurrencies(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CurrencyDto> getCurrencyByDateAndSymbol(
            @RequestParam String date,
            @RequestParam(required = false) String symbol) {
        return new ResponseEntity<>(currencyService.getCurrencyByDateAndSymbol(date, symbol), HttpStatus.OK);
    }
}
