package com.stock.trading.client.outbound.rpc;

import com.stock.trading.client.domain.model.Stock;
import com.stock.trading.client.domain.model.Symbol;
import com.stock.trading.client.domain.repository.StockTradingFindSymbolRepository;
import com.stock.trading.service.inbound.StockProtoRequest;
import com.stock.trading.service.inbound.StockTradingFindServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class StockTradingFindSymbolRepositoryRpc implements StockTradingFindSymbolRepository {

    @GrpcClient("stockService")
    private StockTradingFindServiceGrpc.StockTradingFindServiceBlockingStub stockTradingFindServiceBlockingStub;

    @Override
    public Stock find(Symbol symbol) {
        var stockProtoRequest = StockProtoRequest.newBuilder()
                .setSymbol(symbol.getName())
                .build();
        var res = stockTradingFindServiceBlockingStub.find(stockProtoRequest);
        return new Stock(res.getName(),
                BigDecimal.valueOf(res.getPrice()),
                Symbol.valueOf(res.getSymbol()));
    }
}
