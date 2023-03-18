package com.demo.stocks.repository.impl;

import com.demo.stocks.controller.StockController;
import com.demo.stocks.domain.Stock;
import com.demo.stocks.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public class StockRepositoryImpl implements StockRepository {

    private static final Logger log = LoggerFactory.getLogger(StockRepositoryImpl.class);
    @Value("${app.redis.key}")
    private String REIDS_KEY_STOCK;

    @Value("${app.redis.idx}")
    private String REIDS_IDX;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Stock> searchStocksByTicker(String ticker) {
        String idxTicker = REIDS_IDX + "-" + ticker;
        Set<?> keySet = redisTemplate.opsForSet().members(idxTicker);

        log.debug("search idx-ticker={}, key set size={}", idxTicker, keySet.size());
        if(keySet==null || keySet.isEmpty()){
            return new ArrayList<>();
        }
        return redisTemplate.opsForValue().multiGet(keySet);
    }

    @Override
    public void saveStock(Stock stock) {
        String key_template = "%s:%s:%s:%s";
        String key = String.format(key_template, REIDS_KEY_STOCK,stock.getQuarter(), stock.getTicker(), stock.getDate());
        redisTemplate.opsForValue().set(key, stock);

        String idxTicker = REIDS_IDX + "-" + stock.getTicker();
        redisTemplate.opsForSet().add(idxTicker, key);
    }
}
