package com.demo.stocks.domain;

import com.opencsv.bean.CsvBindByName;

import java.io.Serializable;
import java.math.BigDecimal;

public class Stock implements Serializable {

    @CsvBindByName
    private int quarter;

    @CsvBindByName(column = "stock")
    private String ticker;

    @CsvBindByName
    private String date;

    @CsvBindByName
    private String open;

    @CsvBindByName
    private String close;

    @CsvBindByName
    private Long volume;

    @CsvBindByName(column = "percent_change_price")
    private String percentChangePrice;

    @CsvBindByName(column = "percent_change_volume_over_last_wk")
    private BigDecimal percentChangeVolumeLastWeek;


    public String toString(){
        return "" + quarter + ", " + ticker + ", " + date + ", " + open;
    }

    public BigDecimal getPercentChangeVolumeLastWeek() {
        return percentChangeVolumeLastWeek;
    }

    public void setPercentChangeVolumeLastWeek(BigDecimal percentChangeVolumeLastWeek) {
        this.percentChangeVolumeLastWeek = percentChangeVolumeLastWeek;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getPercentChangePrice() {
        return percentChangePrice;
    }

    public void setPercentChangePrice(String percentChangePrice) {
        this.percentChangePrice = percentChangePrice;
    }
}
