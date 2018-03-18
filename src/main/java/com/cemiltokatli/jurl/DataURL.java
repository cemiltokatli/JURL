package com.cemiltokatli.jurl;

/**
 * Represents a data URL.
 * An object of this class can only be instantiated by the JURL.build method.
 */
public class DataURL extends URL {
    private String mediaType;
    private boolean base64;
    private String data;

    /**
     * Creates a new DataURL object with the given protocol.
     *
     * @param protocol Protocol
     */
    DataURL(String protocol){
        super(protocol);
    }
}
