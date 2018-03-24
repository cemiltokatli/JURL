package com.cemiltokatli.jurl.test;

import com.cemiltokatli.jurl.DataURL;
import com.cemiltokatli.jurl.FileURL;
import com.cemiltokatli.jurl.JURL;
import com.cemiltokatli.jurl.Protocol;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is designed for testing the DataURL class under the "com.cemiltokatli.jurl" package.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DataURLTest {
    private List<URL> testData;

    @BeforeAll
    public void initAll(){
        testData = new ArrayList<>();
        readTestData("DataURLTest", testData);
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

                //Media-Type
                if(!item.isNull("mediaType"))
                    url.mediaType = item.getString("mediaType");

                //Base64
                if(!item.isNull("base64") && item.getBoolean("base64"))
                    url.base64 = item.getBoolean("base64");

                //Data
                url.data = item.getString("data");

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
     * Tests the toString methods of the DataURL class with and without encoding the URL.
     */
    @Test
    @DisplayName("Test URL Building")
    @SuppressWarnings("unchecked")
    public void testURLBuild(){
        for(URL url : testData){
            DataURL testURL = JURL.build(Protocol.DATA);

            if(url.mediaType != null)
                testURL.setMediaType(url.mediaType);

            if(url.base64)
                testURL.setBase64(true);

            testURL.setData(url.data);

            assertEquals(url.builtURL,testURL.toString(), "URL building error without encoding.");
        }
    }

    /**
     * An object of this class represents a single Data URL for testing purposes.
     */
    private class URL{
        String mediaType;
        boolean base64;
        String data;
        String builtURL;
    }
}
