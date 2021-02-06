package assignment4;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * TweetReader contains method used to return tweets from method
 * Do not change the method header
 */
public class TweetReader {
    private static int textMaxLength = 140;
    /**
     * Find tweets written by a particular user.
     *
     * @param url
     * url used to query a GET Request from the server
     * @return return list of tweets from the server
     */

    private final static CloseableHttpClient httpClient = HttpClients.createDefault();

    static List<Tweets> readTweetsFromWeb(String url) throws Exception {
        List<Tweets> tweetList;
        HttpGet request = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity);
        tweetList = jsonToJava(result);
        System.out.println(tweetList.size());
        response.close();
        return tweetList;
    }


    private static List<Tweets> jsonToJava(String jsonResponse) {
        JSONArray tweets = new JSONArray(jsonResponse);
        ObjectMapper mapper = new ObjectMapper();
        List<Tweets> tweetsList = new ArrayList<>();
        for(int i=0; i < tweets.length(); i++) {
            JSONObject temp = tweets.getJSONObject(i);
            String tweetToTurn = temp.toString();
            long idNumber = temp.getLong("Id");
            if(idNumber < Integer.MAX_VALUE && idNumber > 0) {
                try {
                    Tweets tweet = mapper.readValue(tweetToTurn, Tweets.class);
                    if(checkName(tweet.getName()) || textLength(tweet.getText())
                    || !isValid(tweet.getDate())) {
                        continue;
                    }
                    tweetsList.add(tweet);
                } catch (Exception | JsonMappingException e) {
                    System.out.println("Error parsing json object into Tweets object!");
                }
            }
        }
        return tweetsList;
    }


    private static boolean checkName(String name) {
        if(name == null) return true;
        Pattern p = Pattern.compile("[^a-z0-9_]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(name);
        return m.find();
    }


    private static boolean textLength(String text) {
        return (text == null || text.length() > textMaxLength);
    }


    private static boolean isValid(String dateStr) {
        if (dateStr == null) return false;
            try {
                Instant.parse(dateStr);
            } catch (Exception e2) {
                return false;
            }
        return true;
    }
}