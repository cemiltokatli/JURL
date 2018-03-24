package com.cemiltokatli.jurl.exception;

/**
 * Thrown to indicate that the Http(s) URL string cannot be built because its building
 * has already been started or it has been created by parsing a string.
 */
public class HttpURLParseError extends Error {
    /**
     * Creates a new HttpURLParseError object with the specified detail message.
     *
     * @param message   the detail message
     */
    public HttpURLParseError(String message){
        super(message);
    }
}
