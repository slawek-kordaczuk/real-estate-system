package com.real.estate.priceservice.domain.exception;

public class ExternalApiClientException extends RuntimeException {
    public ExternalApiClientException(String message) {
        super(message);
    }
}
