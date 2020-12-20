package org.example.backend.exception;

public class ItemPackNotFoundException extends RuntimeException {
    public ItemPackNotFoundException(String message) {
        super(message);
    }
}
