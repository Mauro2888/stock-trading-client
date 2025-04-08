package com.stock.trading.client.inbound;

import com.stock.trading.client.vm.StockResponse;
import com.stock.trading.client.vm.api.StockTradingFind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import static com.stock.trading.client.inbound.Delegate.Type.REST;

@RestController
@RequestMapping("/api/v1/stock")
@RequestScope
public class StockTradingFindResourceImpl implements StockTradingFind {

    private final StockTradingFind delegate;

    @Autowired
    public StockTradingFindResourceImpl(@Delegate(type = REST) StockTradingFind delegate) {
        this.delegate = delegate;
    }

    @GetMapping
    @Override
    public StockResponse find(String symbol) {
        return delegate.find(symbol);
    }
}
