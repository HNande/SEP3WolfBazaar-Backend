package org.example.application.exception;

public class UpstreamException extends RuntimeException {
    public UpstreamException(String message, Throwable cause) {
        super(message, cause);
    }
}
