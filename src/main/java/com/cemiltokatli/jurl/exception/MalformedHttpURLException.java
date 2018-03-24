package com.cemiltokatli.jurl.exception;

/**
 * Thrown to indicate that a malformed URL has occurred. Either no legal protocol could be found in a specification string or
 * the string could not be parsed.
 */
public class MalformedHttpURLException extends Exception {
    /**
     * Creates a new MalformedHttpURLException with the specified detail message.
     *
     * @param message   the detail message
     */
    public MalformedHttpURLException(String message){
        super(message);
    }
}
