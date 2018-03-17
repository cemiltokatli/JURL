package com.cemiltokatli.jurl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a URL with file, ftp, ftps or sftp protocol.
 * An object of this class can only be instantiated by the JURL.build method.
 */
public class FileURL extends URL {
    private String username;
    private String password;
    private String host;
    private int port;
    private List<String> pathSegments;

    /**
     * Creates a new HttpURL object with the given protocol.
     *
     * @param protocol Protocol
     */
    FileURL(String protocol){
        super(protocol);
        this.port = -1;
        this.pathSegments = new ArrayList<>();
    }

    /**
     * Returns the user name
     *
     * @return User name
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password
     *
     * @return Password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the host name
     *
     * @return Host name
     */
    public String getHost() {
        return host;
    }

    /**
     * Returns the port number
     *
     * @return Port number
     */
    public int getPort() {
        return port;
    }

    /**
     * Sets the given value as the username of the URL.
     *
     * @param username
     * @return FileURL object
     */
    public FileURL setUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * Sets the given value as the password of the URL.
     *
     * @param password
     * @return FileURL object
     */
    public FileURL setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * Sets the given value as the host name of the URL.
     *
     * @param host
     * @return FileURL object
     */
    public FileURL setHost(String host) {
        if(host != null){
            this.host = host;
        }
        return this;
    }

    /**
     * Sets the given value as the port of the URL.
     *
     * @param port
     * @return FileURL object
     */
    public FileURL setPort(int port) {
        this.port = port;
        return this;
    }

    /**
     * Adds a new segment to the path field of the URL.
     *
     * @param segment
     * @return FileURL object
     */
    public FileURL addPathSegment(String segment){
        if(segment.contains("/")){
            String[] pieces = segment.replaceFirst("^/", "").split("/");
            for(String piece : pieces){
                if(!piece.isEmpty()){
                    this.pathSegments.add(piece.trim());
                }
            }
        }
        else
            this.pathSegments.add(segment);

        return this;
    }

    /**
     * Removes the given segment from the path field of the URL.
     *
     * @param segment
     * @return FileURL object
     */
    public FileURL removePathSegment(String segment){
        this.pathSegments.remove(segment);
        return this;
    }

    /**
     * Builds the URL and returns it as a String.
     * If the encode argument is true, it also encodes the URL.
     *
     * @param encode
     * @return The built URL
     */
    public String toString(boolean encode){
        StringBuilder url = new StringBuilder();

        //Protocol
        url.append(super.getProtocol());

        //Username
        if(username != null){
            if(encode)
                username = encode(username);

            url.append(username);
        }

        //Password
        if(password != null){
            if(username != null)
                url.append(":");

            if(encode)
                password = encode(password);

            url.append(password);
        }

        //Host
        host = host.replaceFirst("^ftp://|ftps://|sftp://|file://","");

        if(username != null){
            url.append("@");
        }
        url.append(host);

        //Port
        if(port > -1){
            url.append(":").append(port);
        }

        //Path
        if(!pathSegments.isEmpty()) {
            String segment = "";
            url.append("/");

            for (int i = 0; i < pathSegments.size(); i++) {
                segment = pathSegments.get(i);

                if (encode)
                    segment = encode(segment);

                if (i > 0)
                    url.append("/");

                url.append(segment);
            }

            //# If the latest param is a directory, add a slash (/) at the end
            if (!segment.contains(".")) {
                url.append("/");
            }
        }

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
