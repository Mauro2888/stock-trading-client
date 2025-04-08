package com.stock.trading.client.outbound.rpc;

import com.stock.trading.service.inbound.StockTradingFindServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.grpc.Channel;

@Configuration
public class GrpcClientConfig {

    @GrpcClient("stockService")
    private Channel stockServiceChannel;
    
    @Bean
    public StockTradingFindServiceGrpc.StockTradingFindServiceBlockingStub stockTradingFindServiceBlockingStub() {
        return StockTradingFindServiceGrpc.newBlockingStub(stockServiceChannel);
    }
}