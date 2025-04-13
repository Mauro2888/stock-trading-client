package com.stock.trading.client.inbound;

import com.stock.trading.client.inbound.rate.RateLimiter;
import com.stock.trading.client.vm.StockResponse;
import com.stock.trading.client.vm.api.StockTradingFind;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @RateLimiter(requests = 2, duration = 10)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    @ApiResponse(
            responseCode = "200",
            description = "Find stock by symbol",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StockResponse.class))})
    @ApiResponse(
            responseCode = "404",
            description = "Stock not found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = "{ \"message\": \"Stock not found\" }"))})
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = "{ \"message\": \"Internal server error\" }"))})
    @ApiResponse(
            responseCode = "429",
            description = "Too many requests",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(example = "{ \"message\": \"Too many requests\" }"))})
    public StockResponse find(String symbol) {
        return delegate.find(symbol);
    }
}
