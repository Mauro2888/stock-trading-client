package com.stock.trading.client.inbound;


import com.stock.trading.client.domain.StockTradingFindSymbolService;
import com.stock.trading.client.domain.model.Symbol;
import com.stock.trading.client.inbound.mapper.StockTradingMapper;
import com.stock.trading.client.vm.StockResponse;
import com.stock.trading.client.vm.api.StockTradingFind;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Logger;

import static com.stock.trading.client.inbound.Delegate.Type.REST;

@Delegate(type = REST)
public class StockTradingFindDelegate implements StockTradingFind {

    private final Logger log = Logger.getLogger(getClass().getName());
    private final StockTradingFindSymbolService stockTradingFindSymbolService;
    private final StockTradingMapper stockTradingMapper;

    @Autowired
    public StockTradingFindDelegate(StockTradingFindSymbolService stockTradingFindSymbolService,
                                    StockTradingMapper stockTradingMapper) {
        this.stockTradingFindSymbolService = stockTradingFindSymbolService;
        this.stockTradingMapper = stockTradingMapper;
    }

    @Override
    public StockResponse find(String symbol) {
        log.info(() -> "Finding stock with symbol: %s".formatted(symbol));
        var res = stockTradingFindSymbolService.find(Symbol.valueOf(symbol));
        return stockTradingMapper.apply(res);
    }
}

