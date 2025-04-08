package com.stock.trading.stocktradingclient;

import com.stock.trading.service.inbound.StockProtoRequest;
import com.stock.trading.service.inbound.StockTradingFindServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StockTradingClientApplication implements CommandLineRunner {

    public StockTradingClientApplication(StockClientService service) {
        this.service = service;
    }

    private StockClientService service;

    public static void main(String[] args) {
        SpringApplication.run(StockTradingClientApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println(service.getStockProtoRequest());
    }
}
