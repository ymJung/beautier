package com.metalbird.beautier.connector.model;

public class CustomConnectorException extends Exception {
    public CustomConnectorException(CustomException custom) {
        super(custom.name());
    }
}
