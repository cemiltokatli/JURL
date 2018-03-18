package com.cemiltokatli.jurl.test;

import com.cemiltokatli.jurl.FileURL;
import com.cemiltokatli.jurl.JURL;
import com.cemiltokatli.jurl.Protocol;
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

    @BeforeAll
    public void initAll(){
        testData = new ArrayList<>();
        readTestData("FileURLTest", testData);
    }

    /**
     * Reads the test data from the given file and assign it to the given source array
     *
     * @param fileName
     * @param source
     */
    private void readTestData(String fileName, List<URL> source){
        //Read the test data
        try{
            //Read file
            ClassLoader classLoader = getClass().getClassLoader();
            File testFile = new File(classLoader.getResource(fileName+".json").getFile());
            byte[] byteData = Files.readAllBytes(testFile.toPath());
            String data = new String(byteData, "utf-8");

            //Parse JSON
            JSONObject parsedData = new JSONObject(data);
            JSONArray items = parsedData.getJSONArray("data");
            JSONObject item;
            URL url;

            //Set data
            for(int i = 0; i < items.length(); i++){
                item = items.getJSONObject(i);
                url = new URL();

                //Protocol
                if(item.getString("protocol").equals("file://"))
                    url.protocol = Protocol.FILE;
                else if(item.getString("protocol").equals("ftp://"))
                    url.protocol = Protocol.FTP;
                else if(item.getString("protocol").equals("sftp://"))
                    url.protocol = Protocol.SFTP;
                else if(item.getString("protocol").equals("ftps://"))
                    url.protocol = Protocol.FTPS;

                //Host
                url.host = item.getString("host");

                //Port
                if(!item.isNull("port") && item.getInt("port") > -1)
                    url.port = item.getInt("port");

                //Username
                if(!item.isNull("username"))
                    url.username = item.getString("username");

                //Password
                if(!item.isNull("password"))
                    url.password = item.getString("password");

                //Path Segment
                JSONArray pathSegments = item.getJSONArray("path");
                if(pathSegments.length() > 0){
                    for(int j = 0; j < pathSegments.length(); j++){
                        url.pathSegments.add(pathSegments.getString(j));
                    }
                }

                //Excepted
                url.builtURL = item.getString("builtURL");
                url.builtURLEncoded = item.getString("builtURLEncoded");

                source.add(url);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Tests the toString methods of the FileURL class with and without encoding the URL.
     */
    @Test
    @DisplayName("Test URL Building")
    @SuppressWarnings("unchecked")
    public void testURLBuild(){
        for(URL url : testData){
            FileURL testURL = JURL.build(url.protocol);
            testURL.setHost(url.host);
            testURL.setPort(url.port);
            testURL.setUsername(url.username);
            testURL.setPassword(url.password);

            for (String segment : url.pathSegments)
                testURL.addPathSegment(segment);

            assertEquals(url.builtURL,testURL.toString(), "URL building error without encoding.");
            assertEquals(url.builtURLEncoded, testURL.toString(true),"URL building error with encoding");
        }
    }


    /**
     * An object of this class represents a single File URL for testing purposes.
     */
    private class URL{
        Protocol<FileURL> protocol;
        String username;
        String password;
        String host;
        int port;
        List<String> pathSegments;
        String builtURL;
        String builtURLEncoded;

        URL(){
            this.port = -1;
            this.pathSegments = new ArrayList<>();
        }
    }
}
