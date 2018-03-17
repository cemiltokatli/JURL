package com.cemiltokatli.jurl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Ancestor of the all classes that represent a URL.
 */
abstract class URL {
    private String protocol;

    /**
     * Creates a new URL object with the given protocol.
     *
     * @param protocol Protocol
     */
    URL(String protocol){
        this.protocol = protocol;
    }

    /**
     * Returns the protocol.
     *
     * @return Protocol
     */
    public String getProtocol(){
        return protocol;
    }

    /**
     * Encodes the given value with UTF-8 and returns it
     *
     * @param value
     * @return Encoded value
     */
    String encode(String value){
        try{
            return URLEncoder.encode(value, "utf-8")
                    .replaceAll("\\+", "%20")
                    .replaceAll("\\%26", "&")
                    .replaceAll("\\%21", "!")
                    .replaceAll("\\%27", "'")
                    .replaceAll("\\%28", "(")
                    .replaceAll("\\%29", ")")
                    .replaceAll("\\%5B", "[")
                    .replaceAll("\\%5D", "]")
                    .replaceAll("\\%7E", "~");
        }
        catch(UnsupportedEncodingException e){
            return value;
        }
    }
}
