package com.demo.stocks.repository;

import com.demo.stocks.domain.Stock;

import java.util.List;

public interface StockRepository {
    List<Stock> searchStocksByTicker(String ticker);

    void saveStock(Stock stock);
}
