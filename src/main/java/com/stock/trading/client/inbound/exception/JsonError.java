package com.stock.trading.client.inbound.exception;

import java.util.Map;

public record JsonError(
        String code,
        String message,
        Map<String, String> details
) {
    public JsonError(String code, String message) {
        this(code, message, Map.of());
    }

    public JsonError(String code, String message, String field, String error) {
        this(code, message, Map.of(field, error));
    }
}