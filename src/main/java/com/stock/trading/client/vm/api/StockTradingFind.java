package com.stock.trading.client.vm.api;


import com.stock.trading.client.vm.StockResponse;

public interface StockTradingFind {
    StockResponse find(String symbol);
}
