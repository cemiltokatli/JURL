package com.cemiltokatli.jurl;

import com.cemiltokatli.jurl.exception.URLBuildException;

/**
 * Represents a telnet URL.
 * An object of this class can only be instantiated by the JURL.build method.
 */
public class TelnetURL extends URL {
    private String username;
    private String password;
    private String host;
    private int port;

    /**
     * Creates a new TelnetURL object with the given protocol.
     *
     * @param protocol the protocol of the URL.
     */
    TelnetURL(String protocol){
        super(protocol);
        this.port = -1;
    }

    /**
     * Returns the username.
     *
     * @return the user name of the URL.
     */
    public String getUsername(){
        return username;
    }

    /**
     * Returns the password.
     *
     * @return the password of the URL.
     */
    public String getPassword(){
        return password;
    }

    /**
     * Returns the host name.
     *
     * @return the host name of the URL.
     */
    public String getHost(){
        return host;
    }

    /**
     * Returns the port number.
     *
     * @return the port number of the URL.
     */
    public int getPort(){
        return port;
    }

    /**
     * Sets the given argument as the username.
     *
     * @param username the user name of the URL.
     * @return the TelnetURL object
     */
    public TelnetURL setUsername(String username){
        this.username = username;
        return this;
    }

    /**
     * Sets the given argument as the password.
     *
     * @param password the password of the URL.
     * @return the TelnetURL object
     */
    public TelnetURL setPassword(String password){
        this.password = password;
        return this;
    }

    /**
     * Sets the given argument as the host name.
     *
     * @param host the host name of the URL.
     * @return the TelnetURL object
     */
    public TelnetURL setHost(String host){
        if(host != null){
            this.host = host.replaceAll("/","");
        }
        return this;
    }

    /**
     * Sets the given argument as the port number.
     *
     * @param port the port number of the URL.
     * @return the TelnetURL object
     */
    public TelnetURL setPort(int port){
        this.port = port;
        return this;
    }

    /**
     * Builds the URL and returns it as a string.
     * If the encode argument is true, it also encodes the URL.
     *
     * @param encode true to encode the URL.
     * @return the built URL
     */
    public String toString(boolean encode) {
        StringBuilder url = new StringBuilder();

        //Protocol
        url.append(super.getProtocol());

        //# Throw an error if the host name is null or empty
        if(host == null || host.isEmpty())
            throw new URLBuildException("Host name is null or empty. A host name must be set to build a telnet url.");

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
        String hostPrepared = host.replaceFirst("^telnet://|telnet:","");

        if(username != null){
            url.append("@");
        }
        url.append(hostPrepared);

        //Port
        if(port > -1){
            url.append(":").append(port);
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
