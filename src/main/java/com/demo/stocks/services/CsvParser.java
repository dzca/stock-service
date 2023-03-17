package com.demo.stocks.services;

import com.demo.stocks.domain.Stock;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface CsvParser {

    List<Stock> readStocks(Path path);
}
