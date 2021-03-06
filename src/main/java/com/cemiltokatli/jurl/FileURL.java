package com.cemiltokatli.jurl;

import com.cemiltokatli.jurl.exception.URLBuildException;

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
     * @param protocol Protocol of the URL.
     */
    FileURL(String protocol){
        super(protocol);
        this.port = -1;
        this.pathSegments = new ArrayList<>();
    }

    /**
     * Returns the user name.
     *
     * @return the user name.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password
     *
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the host name
     *
     * @return the host name.
     */
    public String getHost() {
        return host;
    }

    /**
     * Returns the port number
     *
     * @return the port number.
     */
    public int getPort() {
        return port;
    }

    /**
     * Sets the given value as the username of the URL.
     *
     * @param username the user name of the URL.
     * @return the FileURL object
     */
    public FileURL setUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * Sets the given value as the password of the URL.
     *
     * @param password the password of the URL.
     * @return the FileURL object
     */
    public FileURL setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * Sets the given value as the host name of the URL.
     *
     * @param host the host name of the URL.
     * @return the FileURL object
     */
    public FileURL setHost(String host) {
        if(host != null){
            this.host = host.replaceAll("/","");
        }
        return this;
    }

    /**
     * Sets the given value as the port number of the URL.
     *
     * @param port the port number of the URL.
     * @return the FileURL object
     */
    public FileURL setPort(int port) {
        this.port = port;
        return this;
    }

    /**
     * Adds a new segment to the path field of the URL.
     *
     * @param segment the segment to be added.
     * @return the FileURL object
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
     * @param segment the segment to be removed.
     * @return the FileURL object
     */
    public FileURL removePathSegment(String segment){
        this.pathSegments.remove(segment);
        return this;
    }

    /**
     * Builds the URL and returns it as a string.
     * If the encode argument is true, it also encodes the URL.
     *
     * @param encode true to encode the URL.
     * @return the built URL
     */
    public String toString(boolean encode){
        StringBuilder url = new StringBuilder();

        //Protocol
        url.append(super.getProtocol());

        //# Throw an error if the host name is null or empty
        if(host == null || host.isEmpty())
            throw new URLBuildException("Host name is null or empty. A host name must be set to build a file url.");

        //Username
        if(username != null){
            String usernamePrepared = username;

            if(encode)
                usernamePrepared = encode(usernamePrepared);

            url.append(usernamePrepared);
        }

        //Password
        if(password != null){
            String passwordPrepared = password;

            if(username != null)
                url.append(":");

            if(encode)
                passwordPrepared = encode(passwordPrepared);

            url.append(passwordPrepared);
        }

        //Host
        String hostPrepared = host.replaceFirst("^ftp://|ftps://|sftp://|file://|ftp:|ftps:|file:|sftp:","");

        if(username != null){
            url.append("@");
        }
        url.append(hostPrepared);

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
     * Builds the URL and returns it as a string.
     *
     * @return the built URL
     */
    @Override
    public String toString(){
        return toString(false);
    }
}
