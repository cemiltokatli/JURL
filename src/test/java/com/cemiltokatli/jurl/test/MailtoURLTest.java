package com.cemiltokatli.jurl.test;

import com.cemiltokatli.jurl.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is designed for testing the MailtoURL class under the "com.cemiltokatli.jurl" package.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MailtoURLTest {
    private List<URL> testData;

    @BeforeAll
    public void initAll(){
        testData = new ArrayList<>();
        readTestData("MailtoURLTest", testData);
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

                //E-Mail Address
                url.emailAddress = item.getString("mail");

                //Subject
                if(!item.isNull("subject") && !item.getString("subject").isEmpty())
                    url.subject = item.getString("subject");

                //Content
                if(!item.isNull("content") && !item.getString("content").isEmpty())
                    url.content = item.getString("content");

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
     * Tests the toString methods of the MailtoURL class with and without encoding the URL.
     */
    @Test
    @DisplayName("Test URL Building")
    @SuppressWarnings("unchecked")
    public void testURLBuild(){
        for(URL url : testData){
            MailtoURL testURL = JURL.build(Protocol.MAILTO);
            testURL.setEmailAddress(url.emailAddress);
            testURL.setSubject(url.subject);
            testURL.setContent(url.content);

            assertEquals(url.builtURL,testURL.toString(), "URL building error without encoding.");
            assertEquals(url.builtURLEncoded, testURL.toString(true),"URL building error with encoding");
        }
    }

    /**
     * An object of this class represents a single Mailto URL for testing purposes.
     */
    private class URL{
        String emailAddress;
        String subject;
        String content;
        String builtURL;
        String builtURLEncoded;
    }
}
