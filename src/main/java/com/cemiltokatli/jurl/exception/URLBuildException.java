package com.cemiltokatli.jurl.exception;

/**
 * Thrown to indicate that one of mandatory fields, like a host name for a Http(s) URL,
 * could not be found.
 */
public class URLBuildException extends RuntimeException {
    /**
     * Creates a new URLBuildException with the specified detail message.
     *
     * @param message   the detail message
     */
    public URLBuildException(String message){
        super(message);
    }
}
