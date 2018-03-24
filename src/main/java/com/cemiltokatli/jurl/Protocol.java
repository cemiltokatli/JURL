package com.cemiltokatli.jurl;

/**
 * An object of this class represents a file protocol.
 * This class also has static fields that are used by the build method of the JURL class for creating
 * a new URL object.
 *
 * @param <T> the type
 */
public class Protocol<T> {
    /**
     * Used for creating a URL with "http" scheme.
     */
    public static final Protocol<HttpURL> HTTP = new Protocol<>(HttpURL.class, "http://");
    /**
     * Used for creating a URL with "https" scheme.
     */
    public static final Protocol<HttpURL> HTTPS = new Protocol<>(HttpURL.class, "https://");
    /**
     * Used for creating a URL with "file" scheme.
     */
    public static final Protocol<FileURL> FILE = new Protocol<>(FileURL.class, "file://");
    /**
     * Used for creating a URL with "ftp" scheme.
     */
    public static final Protocol<FileURL> FTP = new Protocol<>(FileURL.class, "ftp://");
    /**
     * Used for creating a URL with "ftps" scheme.
     */
    public static final Protocol<FileURL> FTPS = new Protocol<>(FileURL.class, "ftps://");
    /**
     * Used for creating a URL with "sftp" scheme.
     */
    public static final Protocol<FileURL> SFTP = new Protocol<>(FileURL.class, "sftp://");
    /**
     * Used for creating a URL with "data" scheme.
     */
    public static final Protocol<DataURL> DATA = new Protocol<>(DataURL.class, "data:");
    /**
     * Used for creating a URL with "telnet" scheme.
     */
    public static final Protocol<TelnetURL> TELNET = new Protocol<>(TelnetURL.class, "telnet://");
    /**
     * Used for creating a URL with "mailto" scheme.
     */
    public static final Protocol<MailtoURL> MAILTO = new Protocol<>(MailtoURL.class, "mailto:");

    private Class<T> type;
    private String protocol;

    /**
     * Creates a new protocol object with the given type and protocol.
     *
     * @param type the class type
     * @param protocol the protocol
     */
    Protocol(Class<T> type, String protocol){
        this.type = type;
        this.protocol = protocol;
    }

    /**
     * Returns the protocol.
     *
     * @return the protocol.
     */
    String getProtocol(){
        return protocol;
    }

    /**
     * Returns the class type.
     *
     * @return the class type.
     */
    Class<T> getType(){
        return type;
    }
}
