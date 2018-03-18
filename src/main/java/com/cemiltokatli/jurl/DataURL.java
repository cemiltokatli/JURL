package com.cemiltokatli.jurl;

import java.util.Base64;

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

    /**
     * Returns the media-type.
     *
     * @return Media-Type
     */
    public String getMediaType(){
        return mediaType;
    }

    /**
     * Returns the base64 status.
     *
     * @return Base64 active or inactive
     */
    public boolean isBase64Active(){
        return base64;
    }

    /**
     * Returns the data
     *
     * @return Data
     */
    public String getData(){
        return data;
    }

    /**
     * Sets the given argument as the media-type.
     *
     * @param mediaType
     * @return DataURL object
     */
    public DataURL setMediaType(String mediaType){
        this.mediaType = mediaType;
        return this;
    }

    /**
     * Changes the status of the base64 attribute.
     *
     * @param base64
     * @return DataURL object
     */
    public DataURL setBase64(boolean base64){
        this.base64 = base64;
        return this;
    }

    /**
     * Sets the given argument as the data.
     *
     * @param data
     * @return DataURL object
     */
    public DataURL setData(String data){
        if(data != null){
            this.data = data;
        }
        return this;
    }

    /**
     * Builds the URL and returns it as a String.
     * If the encode argument is true, it also encodes the URL.
     *
     * @param encode
     * @return The built URL
     */
    public String toString(boolean encode) {
        StringBuilder url = new StringBuilder();
        boolean addSlash = true;

        //Protocol
        url.append(super.getProtocol());

        //Media-Type
        if(mediaType != null){
            url.append(mediaType);
        }

        //Base64
        if(base64){
            url.append(";base64");
        }

        //Data
        String dataPrepared = data;

        if(base64)
            dataPrepared = Base64.getEncoder().encodeToString(dataPrepared.getBytes());

        if(encode && !base64)
            dataPrepared = encode(dataPrepared);

        url.append(",").append(dataPrepared);

        return url.toString();
    }

    /**
     * Builds the URL and returns it as a String.
     *
     * @return The built URL
     */
    @Override
    public String toString(){
        return toString(false);
    }

    /**
     * Builds the URL and returns it as a java.net.URL object.
     * If the encode argument is true, it also encodes the URL.
     *
     * @param encode Encoding type
     * @return The built URL
     * @throws java.net.MalformedURLException
     */
    public java.net.URL toURL(boolean encode) throws java.net.MalformedURLException{
        return new java.net.URL(toString(encode));
    }

    /**
     * Builds the URL and returns it as a java.net.URL object.
     *
     * @return The build URL
     * @throws java.net.MalformedURLException
     */
    public java.net.URL toURL() throws java.net.MalformedURLException{
        return new java.net.URL(toString(false));
    }
}
