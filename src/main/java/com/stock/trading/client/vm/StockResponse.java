package com.stock.trading.client.vm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Stock response")
public record StockResponse(
        @Schema(example = "Apple") String name,
        @Schema(example = "AAPL") String symbol,
        BigDecimal price) {

    public static class Builder {
        private String name;
        private String symbol;
        private BigDecimal price;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withSymbol(String symbol) {
            this.symbol = symbol;
            return this;
        }

        public Builder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public StockResponse build() {
            return new StockResponse(name, symbol, price);
        }
    }

    @JsonIgnore
    public static Builder builder() {
        return new Builder();
    }
}
