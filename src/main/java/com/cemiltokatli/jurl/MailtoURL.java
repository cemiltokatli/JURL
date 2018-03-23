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
     * @param protocol Protocol
     */
    MailtoURL(String protocol){
        super(protocol);
    }

    /**
     * Returns the e-mail address.
     *
     * @return E-mail address
     */
    public String getEmailAddress(){
        return emailAddress;
    }

    /**
     * Returns the subject.
     *
     * @return Subject
     */
    public String getSubject(){
        return subject;
    }

    /**
     * Returns the content.
     *
     * @return Content
     */
    public String getContent(){
        return content;
    }

    /**
     * Sets the given argument as the e-mail address.
     *
     * @param emailAddress
     * @return MailToURL object
     */
    public MailtoURL setEmailAddress(String emailAddress){
        this.emailAddress = emailAddress;
        return this;
    }

    /**
     * Sets the given argument as the subject.
     *
     * @param subject
     * @return MailToURL object
     */
    public MailtoURL setSubject(String subject){
        this.subject = subject;
        return this;
    }

    /**
     * Sets the given argument as the content.
     *
     * @param content
     * @return MailToURL object
     */
    public MailtoURL setContent(String content){
        this.content = content;
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
     * Builds the URL and returns it as a String.
     *
     * @return The built URL
     */
    @Override
    public String toString(){
        return toString(false);
    }
}
