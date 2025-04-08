package com.stock.trading.client.domain.repository;

import com.stock.trading.client.domain.model.Stock;
import com.stock.trading.client.domain.model.Symbol;
import com.stock.trading.client.vm.StockResponse;

public interface StockTradingFindSymbolRepository {
    Stock find(Symbol symbol);
}
