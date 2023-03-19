package com.demo.stocks.repository;

import com.demo.stocks.config.RedisTestConfig;
import com.demo.stocks.config.ScheduledTestConfig;
import com.demo.stocks.domain.Stock;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisTestConfig.class)
@ActiveProfiles("test")
public class StockRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(StockRepositoryTest.class);

    @Autowired
    private StockRepository stockRepository;

    @Test
    public void shouldSaveStock_toRedis() {

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
        assertNotNull(stock);

        stockRepository.saveStock(stock);
        List<Stock> stocks = stockRepository.searchStocksByTicker("XYZ");
        assertEquals(1, stocks.size());

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
