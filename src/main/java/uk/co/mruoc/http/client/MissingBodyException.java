package uk.co.mruoc.http.client;

public class MissingBodyException extends RuntimeException {

    public MissingBodyException(String message, Throwable cause) {
        super(message, cause);
    }

}
