package com.stock.trading.client.domain.impl;

import com.stock.trading.client.domain.StockTradingFindSymbolService;
import com.stock.trading.client.domain.model.Stock;
import com.stock.trading.client.domain.model.Symbol;
import com.stock.trading.client.domain.repository.StockTradingFindSymbolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StockTradingFindSymbolImpl implements StockTradingFindSymbolService {

    private final StockTradingFindSymbolRepository stockTradingFindSymbolRepository;

    @Autowired
    public StockTradingFindSymbolImpl(StockTradingFindSymbolRepository stockTradingFindSymbolRepository) {
        this.stockTradingFindSymbolRepository = stockTradingFindSymbolRepository;
    }

    @Override
    public Stock find(Symbol symbol) {
        return stockTradingFindSymbolRepository.find(symbol);
    }
}
