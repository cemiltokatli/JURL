package com.cemiltokatli.jurl;

import com.cemiltokatli.jurl.exception.URLBuildException;

/**
 * Represents a mail-to URL.
 * An object of this class can only be instantiated by the JURL.build method.
 */
public class MailtoURL extends URL {
    private String emailAddress;
    private String subject;
    private String content;


    /**
     * Creates a new Mail-to object with the given protocol.
     *
     * @param protocol the protocol of the URL.
     */
    MailtoURL(String protocol){
        super(protocol);
    }

    /**
     * Returns the e-mail address of the URL.
     *
     * @return the receiver e-mail.
     */
    public String getEmailAddress(){
        return emailAddress;
    }

    /**
     * Returns the subject.
     *
     * @return the subject of the e-mail to be sent.
     */
    public String getSubject(){
        return subject;
    }

    /**
     * Returns the content.
     *
     * @return the content of the e-mail to be sent.
     */
    public String getContent(){
        return content;
    }

    /**
     * Sets the given argument as the e-mail address of the URL.
     *
     * @param emailAddress the receiver e-mail
     * @return the MailToURL object
     */
    public MailtoURL setEmailAddress(String emailAddress){
        this.emailAddress = emailAddress;
        return this;
    }

    /**
     * Sets the given argument as the subject.
     *
     * @param subject the subject of the e-mail to be sent.
     * @return the MailToURL object
     */
    public MailtoURL setSubject(String subject){
        this.subject = subject;
        return this;
    }

    /**
     * Sets the given argument as the content.
     *
     * @param content the content of the e-mail to be sent.
     * @return the MailToURL object
     */
    public MailtoURL setContent(String content){
        this.content = content;
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

        //E-Mail address
            //# Throw an error if the e-mail address is null or empty
            if(emailAddress == null || emailAddress.isEmpty()){
                throw new URLBuildException("E-mail address is null or empty. An e-mail address must be set to build a mail-to url.");
            }

        url.append(emailAddress);

        //Subject
        if(subject != null) {
            if(encode)
                subject = encode(subject);

            url.append("?subject=").append(subject);
        }

        //Content
        if(content != null) {
            if (subject != null)
                url.append("&");
            else
                url.append("?");

            if(encode)
                content = encode(content);

            url.append("body=").append(content);
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
