package com.cemiltokatli.jurl;

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
     * @param protocol Protocol
     */
    TelnetURL(String protocol){
        super(protocol);
    }

    /**
     * Returns the username.
     *
     * @return Username
     */
    public String getUsername(){
        return username;
    }

    /**
     * Returns the password.
     *
     * @return Password
     */
    public String getPassword(){
        return password;
    }

    /**
     * Returns the host name.
     *
     * @return Host name
     */
    public String getHost(){
        return host;
    }

    /**
     * Returns the port.
     *
     * @return Port
     */
    public int getPort(){
        return port;
    }

    /**
     * Sets the given argument as the username.
     *
     * @param username
     * @return TelnetURL object
     */
    public TelnetURL setUsername(String username){
        this.username = username;
        return this;
    }

    /**
     * Sets the given argument as the password.
     *
     * @param password
     * @return TelnetURL object
     */
    public TelnetURL setPassword(String password){
        this.password = password;
        return this;
    }

    /**
     * Sets the given argument as the host name.
     *
     * @param host
     * @return TelnetURL object
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
     * @param port
     * @return TelnetURL object
     */
    public TelnetURL setPort(int port){
        this.port = port;
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

        //Protocol
        url.append(super.getProtocol());

        //Username
        String usernamePrepared = username;

        if(encode)
            usernamePrepared = encode(usernamePrepared);

        url.append(usernamePrepared);


        //Password
        String passwordPrepared = password;

        if(encode)
            passwordPrepared = encode(passwordPrepared);

        url.append(":").append(passwordPrepared);

        //Host
        String hostPrepared = host.replaceFirst("^telnet://|telnet:","");
        url.append("@").append(hostPrepared);

        //Port
        if(port > -1){
            url.append(":").append(port);
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
