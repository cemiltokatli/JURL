package com.cemiltokatli.jurl;

/**
 * The library is usually controlled via this class.
 * The "build" method of this class is the first step to start building a URL.
 * It returns a new URL object according to the given protocol.
 */
abstract public class JURL {
    /**
     * Creates and returns a new object that represents the given protocol.
     * The returning object is always an object of a class that is derived from the URL class.
     *
     * This method is the first step to build a URL and only way to initialize a valid URL object.
     *
     * @param protocol the protocol of the URL.
     * @param <T> type
     * @return Returns the URL object.
     */
    @SuppressWarnings("unchecked")
    public static <T extends URL> T build(Protocol<T> protocol){

        if(protocol.getType() == HttpURL.class){
            return (T)new HttpURL(protocol.getProtocol());
        }
        else if(protocol.getType() == FileURL.class){
            return (T)new FileURL(protocol.getProtocol());
        }
        else if(protocol.getType() == DataURL.class){
            return (T)new DataURL(protocol.getProtocol());
        }
        else if(protocol.getType() == TelnetURL.class){
            return (T)new TelnetURL(protocol.getProtocol());
        }
        else{
            return (T)new MailtoURL(protocol.getProtocol());
        }
    }
}
