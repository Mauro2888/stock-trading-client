package com.stock.trading.client.domain;


import com.stock.trading.client.domain.model.Stock;
import com.stock.trading.client.domain.model.Symbol;
import com.stock.trading.client.vm.StockResponse;

public interface StockTradingFindSymbolService {
    Stock find(Symbol symbol);
}
