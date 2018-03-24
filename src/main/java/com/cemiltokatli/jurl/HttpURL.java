package com.cemiltokatli.jurl;

import com.cemiltokatli.jurl.exception.HttpURLParseError;
import com.cemiltokatli.jurl.exception.MalformedHttpURLException;
import com.cemiltokatli.jurl.exception.URLBuildException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a URL with http or https protocol.
 * An object of this class can only be instantiated by the JURL.build method.
 */
public class HttpURL extends URL {
    private String host;
    private int port;
    private boolean shownWWW;
    private LinkedList<String> routeParameters;
    private LinkedHashMap<String, String> queryFields;
    private String fragment;
    private String urlString;

    /**
     * Creates a new HttpURL object with the given protocol.
     *
     * @param protocol Protocol of the URL.
     */
    HttpURL(String protocol){
        super(protocol);
        this.port = -1;
        this.routeParameters = new LinkedList<>();
        this.queryFields = new LinkedHashMap<>();
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
     * Returns a boolean value indicating that if the host name starts with "www".
     *
     * @return a boolean indicating that if the host name starts with "www".
     */
    public boolean isWWWShown(){
        return shownWWW;
    }

    /**
     * Returns the segments of the path field (route parameters) of the url.
     *
     * @return the route parameters of the URL.
     */
    public List<String> getRouteParams(){
        return routeParameters;
    }

    /**
     * Returns the query fields.
     *
     * @return the query fields of the URL.
     */
    public LinkedHashMap<String, String> getQueryFields(){
        return queryFields;
    }

    /**
     * Returns the fragment (hash).
     *
     * @return the fragment of the URL.
     */
    public String getFragment(){
        return fragment;
    }


    /**
     * Sets the given value as the host name of the URL.
     *
     * @param host the host name
     * @return the HttpURL object
     */
    public HttpURL setHost(String host){
        if(host != null){
            this.host = host.replaceAll("/","");
        }

        return this;
    }

    /**
     * Sets the given value as the port number of the URL.
     *
     * @param port the port number
     * @return the HttpURL object
     */
    public HttpURL setPort(int port){
        this.port = port;
        return this;
    }

    /**
     * Shows or hides WWW in the beginning of the host name.
     *
     * @param status true to show, false to hide
     * @return the HttpURL object
     */
    public HttpURL showWWW(boolean status){
        this.shownWWW = status;
        return this;
    }

    /**
     * Adds a new route parameter.
     *
     * @param param parameter to be added.
     * @return the HttpURL object
     */
    public HttpURL addRouteParam(String param){
        if(param.contains("/")){
            String[] pieces = param.replaceFirst("^/", "").split("/");
            for(String piece : pieces){
                if(!piece.isEmpty()){
                    this.routeParameters.add(piece.trim());
                }
            }
        }
        else
            this.routeParameters.add(param);

        return this;
    }

    /**
     * Removes the given route parameter.
     *
     * @param param parameter to be removed.
     * @return the HttpURL object
     */
    public HttpURL removeRouteParam(String param){
        this.routeParameters.remove(param);
        return this;
    }

    /**
     * It is possible to add placeholders in the URL that you send to the "setString" method and
     * replace these placeholders with real values later. This approach allows you to add route parameters dynamically
     * even if you are building the URL on a pre-defined structure.
     * This method allows you to replace those placeholders with real values.
     *
     * @param name name of the placeholder
     * @param value value to be replaced with the name
     * @return the HttpURL object
     */
    public HttpURL setRouteParam(String name, String value){
        for(int i = 0; i < routeParameters.size(); i++){
            if(routeParameters.get(i).equals("{"+name+"}")){
                routeParameters.set(i, value);
            }
        }

        return this;
    }

    /**
     * Adds a new query field.
     *
     * @param name the name of the field.
     * @param value the value of the field.
     * @return the HttpURL object
     */
    public HttpURL addQueryField(String name, String value){
        this.queryFields.put(name, value);
        return this;
    }

    /**
     * Removes the query field whose name is given.
     *
     * @param name the name of the field to be removed.
     * @return the HttpURL object
     */
    public HttpURL removeQueryField(String name){
        this.queryFields.remove(name);
        return this;
    }

    /**
     * Sets the given value as the fragment of the URL.
     *
     * @param fragment the fragment of the URL.
     * @return the HttpURL object
     */
    public HttpURL setFragment(String fragment){
        this.fragment = fragment;
        return this;
    }

    /**
     * Removes the fragment of the URL.
     *
     * @return the HttpURL object
     */
    public HttpURL clearFragment(){
        this.fragment = null;
        return this;
    }

    /**
     * Parses the given url.
     * This method must be called before all of the others in this class.
     *
     * @param url url to be parsed
     * @return the HttpURL object
     * @throws MalformedHttpURLException If the given URL is malformed.
     * @throws HttpURLParseError If the building of the URL has already been started.
     */
    public HttpURL setString(String url) throws MalformedHttpURLException, HttpURLParseError{
        //Check if the building of the URL has been started before this method is called
        if(host != null || port >= 0 || routeParameters.size() > 0 || queryFields.size() > 0 || fragment != null || urlString != null){
            throw new HttpURLParseError("URL cannot be parsed if it is already being built");
        }

        this.urlString = url;
        String cleanURL = url;

        //Clean protocol
        cleanURL = cleanURL.replaceFirst("^https://|http://","");

        //Extraction
        java.net.URL urlObj;

        try{
            urlObj = new java.net.URL("http://"+cleanURL);
        }
        catch(java.net.MalformedURLException e){
            throw new MalformedHttpURLException(e.getMessage());
        }

        //Extract Host
        host = urlObj.getHost();

        //Extract port
        port = urlObj.getPort();

        //Extract WWW Status
        String[] hostPieces = host.split("\\.");
        if(hostPieces.length >= 3 && host.startsWith("www")) {
            shownWWW = true;
            host = host.replaceFirst("www\\.","");
        }

        //Extract Route Parameters
        if(urlObj.getPath() != null) {
            String[] pieces = urlObj.getPath().replaceFirst("^/", "").split("/");
            for(String piece : pieces){
                if(!piece.trim().isEmpty())
                    routeParameters.add(piece);
            }
        }

        //Extract Query Fields
        if(urlObj.getQuery() != null) {
            queryFields = new LinkedHashMap<>();
            String[] variables = urlObj.getQuery().split("&");
            for(String variable : variables){
                String[] pieces = variable.split("=");
                queryFields.put(pieces[0], pieces[1]);
            }
        }

        //Extract Fragment
        Pattern fragmentPattern = Pattern.compile("#.+");
        Matcher fragmentMatcher = fragmentPattern.matcher(cleanURL);
        if(fragmentMatcher.find()){
            fragment = fragmentMatcher.group(0).replaceFirst("#", "");
        }

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
        boolean addSlash = true;

        //Protocol
        url.append(super.getProtocol());

        //Host
            //# Throw an error if the host name is null or empty
            if(host == null || host.isEmpty())
                throw new URLBuildException("Host name is null or empty. A host name must be set to build a http or https url.");

        String hostPrepared = host.replaceFirst("^https://|http://|https:|http:","");

        if(shownWWW)
            url.append("www.");

        if(shownWWW && hostPrepared.split("\\.").length >= 3 && hostPrepared.startsWith("www"))
            hostPrepared = hostPrepared.replaceFirst("www\\.","");

        url.append(hostPrepared);

        //Port
        if(port > -1){
            url.append(":").append(port);
        }

        //Route Parameters
        if(routeParameters.size() > 0) {
            url.append("/");

            String param="";
            for(int i = 0; i < routeParameters.size(); i++){
                param = routeParameters.get(i);

                if (encode)
                    param = encode(param, true);


                if(i > 0)
                    url.append("/");

                url.append(param);
            }

            //# If the latest param is a directory, add a slash (/) at the end
            if(!param.contains(".")) {
                url.append("/");
            }

            addSlash = false;
        }

        //Query Fields
        if(queryFields.size() > 0) {
            if(addSlash) {
                url.append("/");
                addSlash = false;
            }

            url.append("?");

            boolean addDelimiter = false;
            String value;
            for (String key : queryFields.keySet()) {
                if (addDelimiter)
                    url.append("&");
                else
                    addDelimiter = true;

                value = queryFields.get(key);

                if(encode){
                    key = encode(key, true);
                    value = encode(value, true);
                }

                url.append(key).append("=").append(value);
            }
        }

        //Fragment
        if(fragment != null) {
            String fragmentPrepared = fragment;

            if(encode)
                fragmentPrepared = encode(fragmentPrepared, true);

            if(addSlash)
                url.append("/");

            url.append("#").append(fragmentPrepared);
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

    /**
     * Builds the URL and returns it as a java.net.URL object.
     * If the encode argument is true, it also encodes the URL.
     *
     * @param encode true to encode the URL.
     * @return The built URL
     * @throws java.net.MalformedURLException If the built URL is malformed
     */
    public java.net.URL toURL(boolean encode) throws java.net.MalformedURLException{
        return new java.net.URL(toString(encode));
    }

    /**
     * Builds the URL and returns it as a java.net.URL object.
     *
     * @return the build URL
     * @throws java.net.MalformedURLException  If the built URL is malformed
     */
    public java.net.URL toURL() throws java.net.MalformedURLException{
        return new java.net.URL(toString(false));
    }
}