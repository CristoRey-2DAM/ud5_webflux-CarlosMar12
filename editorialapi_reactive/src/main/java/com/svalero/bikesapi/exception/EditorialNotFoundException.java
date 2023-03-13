package com.svalero.bikesapi.exception;

public class EditorialNotFoundException extends Exception {

    private static final String DEFAULT_ERROR_MESSAGE = "Editorial not found";

    public EditorialNotFoundException(String message) {
        super(message);
    }

    public EditorialNotFoundException() {
        super(DEFAULT_ERROR_MESSAGE);
    }
}
