package com.cemiltokatli.jurl.test;

import com.cemiltokatli.jurl.JURL;
import com.cemiltokatli.jurl.Protocol;
import com.cemiltokatli.jurl.TelnetURL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.io.File;

/**
 * This class is designed for testing the TelnetURL class under the "com.cemiltokatli.jurl" package.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TelnetURLTest {
    private List<URL> testData;

    @BeforeAll
    public void initAll(){
        testData = new ArrayList<>();
        readTestData("TelnetURLTest", testData);
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

                //Host
                url.host = item.getString("host");

                //Port
                if(!item.isNull("port") && item.getInt("port") > -1)
                    url.port = item.getInt("port");

                //Username
                url.username = item.getString("username");

                //Password
                url.password = item.getString("password");

                //Excepted
                url.builtURL = item.getString("builtURL");

                source.add(url);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Tests the toString methods of the TelnetURL class with and without encoding the URL.
     */
    @Test
    @DisplayName("Test URL Building")
    @SuppressWarnings("unchecked")
    public void testURLBuild(){
        if(testData == null)
            initAll();

        for(URL url : testData){
            TelnetURL testURL = JURL.build(Protocol.TELNET);
            testURL.setHost(url.host);
            testURL.setPort(url.port);
            testURL.setUsername(url.username);
            testURL.setPassword(url.password);

            assertEquals(url.builtURL,testURL.toString(), "URL building error without encoding.");
        }
    }

    /**
     * An object of this class represents a single Telnet URL for testing purposes.
     */
    private class URL{
        String username;
        String password;
        String host;
        int port;
        String builtURL;

        URL(){
            this.port = -1;
        }
    }
}
