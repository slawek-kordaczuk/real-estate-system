package com.real.estate.priceservice.domain.exception;

public class ExternalApiServerException extends RuntimeException {
    public ExternalApiServerException(String message) {
        super(message);
    }
}
