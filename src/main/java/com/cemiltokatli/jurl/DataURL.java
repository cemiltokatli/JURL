package com.cemiltokatli.jurl;

import com.cemiltokatli.jurl.exception.URLBuildException;

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
     * @param protocol Protocol to be set
     */
    DataURL(String protocol){
        super(protocol);
    }

    /**
     * Returns the media-type.
     *
     * @return the media-type of the URL.
     */
    public String getMediaType(){
        return mediaType;
    }

    /**
     * Returns the base64 status.
     *
     * @return a boolean indicating whether or not base64 encoding is enabled.
     */
    public boolean isBase64Active(){
        return base64;
    }

    /**
     * Returns the data
     *
     * @return the data
     */
    public String getData(){
        return data;
    }

    /**
     * Sets the given argument as the media-type.
     *
     * @param mediaType Media-type of the URL.
     * @return the DataURL object
     */
    public DataURL setMediaType(String mediaType){
        this.mediaType = mediaType;
        return this;
    }

    /**
     * Changes the status of the base64 attribute.
     *
     * @param base64 true to enable the base64 encoding option, false to disable
     * @return the DataURL object
     */
    public DataURL setBase64(boolean base64){
        this.base64 = base64;
        return this;
    }

    /**
     * Sets the given argument as the data.
     *
     * @param data the data of the URL.
     * @return the DataURL object
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
     * @param encode true to encode the URL
     * @return the built URL
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
            //# Throw an error if the data is null or empty
            if(data == null || data.isEmpty())
                throw new URLBuildException("Data is null or empty. Data must be set to build a data url.");

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
     * @return the built URL
     */
    @Override
    public String toString(){
        return toString(false);
    }
}
