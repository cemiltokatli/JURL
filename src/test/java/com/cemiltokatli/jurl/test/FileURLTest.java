package com.cemiltokatli.jurl.test;

import com.cemiltokatli.jurl.HttpURL;
import com.cemiltokatli.jurl.JURL;
import com.cemiltokatli.jurl.Protocol;
import com.cemiltokatli.jurl.exception.MalformedHttpURLException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.io.File;

/**
 * This class is designed for testing the FileURL class under the "com.cemiltokatli.jurl" package.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FileURLTest {
    private List<URL> testData;




    /**
     * An object of this class represents a single File URL for testing purposes.
     */
    private class URL{
        String username;
        String password;
        String host;
        int port;
        String[] pathSegments;

        URL(String username, String password, String host, int port, String[] pathSegments){
            this.username = username;
            this.password = password;
            this.host = host;
            this.port = port;
            this.pathSegments = pathSegments;
        }
    }
}
