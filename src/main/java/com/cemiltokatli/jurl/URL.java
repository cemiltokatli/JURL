package com.cemiltokatli.jurl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Ancestor of the all classes that represent a URL.
 */
abstract class URL {
    private String protocol;

    /**
     * Creates a new URL object with the given protocol.
     *
     * @param protocol protocol of the URL.
     */
    URL(String protocol){
        this.protocol = protocol;
    }

    /**
     * Returns the protocol.
     *
     * @return protocol of the URL.
     */
    public String getProtocol(){
        return protocol;
    }

    /**
     * Encodes the given value with UTF-8 and returns it.
     *
     * @param value the value to be encoded.
     * @return the encoded value.
     */
    String encode(String value){
        return encode(value, false);
    }

    /**
     * Encodes the given value with UTF-8 and returns it.
     * If the forHttp argument is true, this method produces exactly the same result with the
     * javascript's encodeURIComponent function.
     *
     * @param value the value to be encoded
     * @param forHttp true for Http encoding
     * @return the encoded value
     */
    String encode(String value, boolean forHttp){
        try{
            String encodedValue = URLEncoder.encode(value, "utf-8");

            if(forHttp){
                Map<String, String> nonEncodedChars = Map.of("~", "%7E", "!","%21","(","%28",")","%29","\'","%27");
                for(Map.Entry<String, String> c : nonEncodedChars.entrySet()){
                    encodedValue = encodedValue.replaceAll(c.getValue(), c.getKey());
                }
            }
            encodedValue = encodedValue.replaceAll("\\+","%20");

            return encodedValue;
        }
        catch(UnsupportedEncodingException e){
            return value;
        }
    }
}
