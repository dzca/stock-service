package com.demo.stocks.services.impl;

import com.demo.stocks.domain.Stock;
import com.demo.stocks.repository.StockRepository;
import com.demo.stocks.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;

    @Override
    public void batchSave(List<Stock> stocks) {
        for (Stock stock : stocks) {
            stockRepository.saveStock(stock);
        }
    }
}
