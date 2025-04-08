package com.stock.trading.client.domain.model;

import java.math.BigDecimal;

public record Stock(String name, BigDecimal price, Symbol symbol) {
}
