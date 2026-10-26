package com.asin.PayementPorto.dto;

public record ErrorResponse(
        Integer status,
        String error
) {
}
