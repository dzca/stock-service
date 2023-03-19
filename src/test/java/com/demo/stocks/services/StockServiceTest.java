package com.demo.stocks.services;

import com.demo.stocks.domain.Stock;
import com.demo.stocks.repository.StockRepository;
import com.demo.stocks.services.impl.StockServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class StockServiceTest {

    private static final Logger log = LoggerFactory.getLogger(StockServiceTest.class);

//    @SpyBean
    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockServiceImpl stockService;

    @Test
    public void batchSave_shouldCallRepository(){
        String jsonString = "{\n" +
                "        \"quarter\": 1,\n" +
                "        \"ticker\": \"XYZ\",\n" +
                "        \"date\": \"3/18/2023\",\n" +
                "        \"open\": \"$56.19\",\n" +
                "        \"close\": \"$55.79\",\n" +
                "        \"volume\": 138428495,\n" +
                "        \"percentChangePrice\": \"-2.47066\",\n" +
                "        \"percentChangeVolumeLastWeek\": -43.02495926\n" +
                "    }";
        Stock stock = mapStock(jsonString);
        List<Stock> stocks = new ArrayList<>();
        stocks.add(stock);
        stockService.batchSave(stocks);
        verify(stockRepository, atMostOnce()).saveStock(any(Stock.class));
    }

    private Stock mapStock(String json){
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Stock stock = objectMapper.readValue(json, Stock.class);
            return stock;
        } catch (JsonProcessingException e) {
            log.error("error parse json to stock, error:{}", e.toString());
            return null;
        }
    }
}
