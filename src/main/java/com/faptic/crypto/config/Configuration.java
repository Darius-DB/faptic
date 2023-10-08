package com.faptic.crypto.config;

import com.faptic.crypto.model.Currency;
import com.faptic.crypto.repository.CurrencyRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class Configuration {

    private final CurrencyRepository currencyRepository;

    List<String> csvFiles = new ArrayList<>(Arrays.asList(
            "BTC_values.csv", "DOGE_values.csv", "ETH_values.csv", "LTC_values.csv", "XRP_values.csv"
    ));

    List<String> cryptos = new ArrayList<>(Arrays.asList(
            "BTC", "DOGE", "ETH", "LTC", "XRP"
    ));

    public Configuration(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Order(1)
    public void populateDB() {
        for (String csvFile : csvFiles) {
            try {
                File file = new File("src/main/resources/prices/" + csvFile);
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String headers;
                String line = "";
                String[] tempArr;
                headers = br.readLine();
                while((line = br.readLine()) != null) {
                    tempArr = line.split(",");
                     currencyRepository.save(
                             new Currency(
                                     Instant.ofEpochMilli(Long.parseLong(tempArr[0])).atZone(ZoneId.systemDefault()).toLocalDate(),
                                     tempArr[1],
                                     Double.parseDouble(tempArr[2]))
                     );

                }
                br.close();
            } catch(IOException ioe) {
                ioe.printStackTrace();
            }
      }
    }

    @EventListener(ApplicationReadyEvent.class)
    @Order(2)
    public void calculateRange() {

        for (String crypto : cryptos) {
            List<Currency> currencies = currencyRepository.findBySymbolIgnoreCaseOrderByPriceAsc(crypto);
            int length = currencies.size();
            for (int i = 0; i < currencies.size(); i++) {
                Currency currencyToUpdate = currencies.get(i);
                currencyToUpdate.setNormalizedRange(
                        (currencies.get(i).getPrice() - currencies.get(0).getPrice()) /
                                (currencies.get(length - 1).getPrice() - currencies.get(0).getPrice())
                );
                currencyRepository.save(currencyToUpdate);
            }

        }
    }


}
