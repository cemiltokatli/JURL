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
 * This class is designed for testing the HttpURL class under the "com.cemiltokatli.jurl" package.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HttpURLTest {
    private List<URL> testURLBuildData;
    private List<URL> testURLBuildParseData;

    @BeforeAll
    public void initAll(){
        testURLBuildData = new ArrayList<>();
        testURLBuildParseData = new ArrayList<>();
        readTestData("HttpURLTest_build", testURLBuildData);
        readTestData("HttpURLTest_buildparse", testURLBuildParseData);
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
                if(item.getString("protocol").equals("https://"))
                    url.protocol = Protocol.HTTPS;
                else
                    url.protocol = Protocol.HTTP;

                //Host
                url.host = item.getString("host");

                //Port
                url.port = item.getInt("port");

                //Show WWW
                url.shownWWW = item.getBoolean("shownWWW");

                //Route Parameters
                if(item.has("routeParameters") && !item.isNull("routeParameters")){
                    url.routeParameters = new LinkedList<>();

                    JSONArray routeParameters = item.getJSONArray("routeParameters");
                    for(int j = 0; j < routeParameters.length(); j++){
                        url.routeParameters.add(routeParameters.getString(j));
                    }
                }

                //Query Fields
                if(item.has("queryFields") && !item.isNull("queryFields")){
                    url.queryFields = new LinkedHashMap<>();

                    JSONArray queryFields = item.getJSONArray("queryFields");
                    for(int j = 1; j < queryFields.length(); j+=2){
                        url.queryFields.put(queryFields.getString(j-1),queryFields.getString(j));
                    }
                }

                //Fragment
                if(item.has("fragment") && !item.isNull("fragment"))
                    url.fragment = item.getString("fragment");

                //Excepted
                url.builtURL = item.getString("builtURL");

                //Structure
                if(item.has("structure") && !item.isNull("structure")) {
                    url.structure = item.getString("structure");
                }

                //Route Parameter Aliases
                if(item.has("routeParametersAliases") && !item.isNull("routeParametersAliases")){
                    url.routeParametersAliases = new LinkedList<>();

                    JSONArray routeParameters = item.getJSONArray("routeParametersAliases");
                    for(int j = 0; j < routeParameters.length(); j++){
                        url.routeParametersAliases.add(routeParameters.getString(j));
                    }
                }

                source.add(url);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Tests the toString methods of the HttpURL class without encoding the URL.
     */
    @Test
    @DisplayName("Test URL Building")
    @SuppressWarnings("unchecked")
    public void testURLBuild(){
        for(URL url : testURLBuildData){
            HttpURL testURL = JURL.build(url.protocol);
            testURL.setHost(url.host);
            testURL.setPort(url.port);
            testURL.showWWW(url.shownWWW);

            for (String routeParam : url.routeParameters)
                testURL.addRouteParam(routeParam);

            for (Map.Entry<String, String> queryField : url.queryFields.entrySet()) {
                testURL.addQueryField(queryField.getKey(), queryField.getValue());
            }

            testURL.setFragment(url.fragment);

            assertEquals(url.builtURL,testURL.toString(), "URL building error without encoding."); }
    }

    /**
     * Tests the fromString, setRouteParam and toString methods together.
     */
    @Test
    @DisplayName("Test URL Parsing and Building")
    public void testURLBuildWithParse() throws MalformedHttpURLException{
        for(URL url : testURLBuildParseData){
            HttpURL testURL = JURL.build(url.protocol).setString(url.structure);

            if(url.port > -1)
                testURL.setPort(url.port);

            if(url.shownWWW)
                testURL.showWWW(true);

            for (int i = 1; i < url.routeParametersAliases.size(); i+=2){
                testURL.setRouteParam(url.routeParametersAliases.get(i-1), url.routeParametersAliases.get(i));
            }

            for (String routeParam : url.routeParameters)
                testURL.addRouteParam(routeParam);

            for (Map.Entry<String, String> queryField : url.queryFields.entrySet()) {
                testURL.addQueryField(queryField.getKey(), queryField.getValue());
            }

            if(url.fragment != null)
                testURL.setFragment(url.fragment);

            assertEquals(url.builtURL,testURL.toString(), "URL building error without encoding.");
        }
    }

    /**
     * An object of this class represents a single Http URL for testing purposes.
     */
    private class URL{
        Protocol<HttpURL> protocol;
        String host;
        int port;
        boolean shownWWW;
        LinkedList<String> routeParameters;
        LinkedList<String> routeParametersAliases;
        Map<String, String> queryFields;
        String fragment;
        String structure;
        String builtURL;

        URL(){
            this.port = -1;
            this.routeParameters = new LinkedList<>();
            this.routeParametersAliases = new LinkedList<>();
            this.queryFields = new LinkedHashMap<>();
        }
    }
}
