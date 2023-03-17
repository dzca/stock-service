package com.demo.stocks.services.impl;

import com.demo.stocks.controller.StockController;
import com.demo.stocks.domain.Stock;
import com.demo.stocks.services.CsvParser;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvParserOpenCsvImp implements CsvParser {

    private static final Logger log = LoggerFactory.getLogger(CsvParserOpenCsvImp.class);

    @Override
    public List<Stock> readStocks(Path path) {

        try (Reader reader = Files.newBufferedReader(path)) {
            List<Stock> stocksList = new CsvToBeanBuilder(reader)
                    .withType(Stock.class)
                    .build()
                    .parse();


            return stocksList;
        } catch (IOException ioe){
            log.info("Failed to read stock file; {}, error:{}", path.getFileName(),ioe.getMessage());
            return new ArrayList<>();
        }
    }
}
