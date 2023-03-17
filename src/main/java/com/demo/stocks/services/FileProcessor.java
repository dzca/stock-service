package com.demo.stocks.services;

import com.demo.stocks.domain.Stock;

import java.util.List;

/**
 * parse data file
 */
public interface FileProcessor {

    List<Stock> readStocks(String fileName);
}
