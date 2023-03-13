package com.svalero.bikesapi.exception;

public class MangaNotFoundException extends Exception {

    private static final String DEFAULT_ERROR_MESSAGE = "Route not found";

    public MangaNotFoundException(String message) {
        super(message);
    }

    public MangaNotFoundException() {
        super(DEFAULT_ERROR_MESSAGE);
    }
}
