package com.stock.trading.client.vm;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

public record StockResponse(String name, String symbol, BigDecimal price) {

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
