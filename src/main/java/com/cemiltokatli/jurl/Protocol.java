package com.cemiltokatli.jurl;

/**
 * An object of this class represents a file protocol.
 * This class also has static fields that are used by build method of the JURL class for creating
 * a new URL object.
 *
 * @param <T>
 */
public class Protocol<T> {
    public static final Protocol<HttpURL> HTTP = new Protocol<>(HttpURL.class, "http://");
    public static final Protocol<HttpURL> HTTPS = new Protocol<>(HttpURL.class, "https://");
    public static final Protocol<FileURL> FILE = new Protocol<>(FileURL.class, "file://");
    public static final Protocol<FileURL> FTP = new Protocol<>(FileURL.class, "ftp://");
    public static final Protocol<FileURL> FTPS = new Protocol<>(FileURL.class, "ftps://");
    public static final Protocol<FileURL> SFTP = new Protocol<>(FileURL.class, "sftp://");
    public static final Protocol<DataURL> DATA = new Protocol<>(DataURL.class, "data:");
    public static final Protocol<TelnetURL> TELNET = new Protocol<>(TelnetURL.class, "telnet://");
    public static final Protocol<MailtoURL> MAILTO = new Protocol<>(MailtoURL.class, "mailto:");

    private Class<T> type;
    private String protocol;

    /**
     * Creates a new protocol object with the given type and protocol
     * @param type
     * @param protocol
     */
    Protocol(Class<T> type, String protocol){
        this.type = type;
        this.protocol = protocol;
    }

    /**
     * Returns the protocol.
     *
     * @return Protocol
     */
    String getProtocol(){
        return protocol;
    }

    /**
     * Returns the class type.
     *
     * @return Type
     */
    Class<T> getType(){
        return type;
    }
}
