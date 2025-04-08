package com.stock.trading.client;

import com.stock.trading.service.inbound.StockProtoRequest;
import com.stock.trading.service.inbound.StockProtoResponse;
import com.stock.trading.service.inbound.StockTradingFindServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class StockClientService {

    @GrpcClient("stockService")
    private StockTradingFindServiceGrpc.StockTradingFindServiceBlockingStub stub;

    public StockProtoResponse getStockProtoRequest() {
        StockProtoRequest stockProtoRequest = StockProtoRequest.newBuilder()
                .setSymbol("AMZN")
                .build();
        return stub.find(stockProtoRequest);
    }
}
