package com.demo.stocks.services;

import com.demo.stocks.domain.Stock;

import java.util.List;

public interface StockService {

    void batchSave(List<Stock> stocks);
}
