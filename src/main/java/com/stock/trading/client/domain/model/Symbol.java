package com.stock.trading.client.domain.model;

public enum Symbol {
    AAPL("AAPL"),
    MSFT("MSFT"),
    GOOGL("GOOGL"),
    AMZN("AMZN"),
    TSLA("TSLA"),
    META("META"),
    NFLX("NFLX"),
    NVDA("NVDA"),
    AMD("AMD"),
    INTEL("INTC");

    private final String name;

    Symbol(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
