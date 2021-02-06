package assignment4;

import java.util.ArrayList;
import java.util.List;

public class Main {
    final static String URLEndpoint = "http://kevinstwitterclient2.azurewebsites.net/api/products";

    /**
     * We will not use your Main class to test your code
     * Feel free to modify this as much as you want
     * Here is an example of how you might test the code from main
     * for Problem 1 and Problem 2
     */
    public static void main(String[] args) throws Exception {

        // Problem 1: Returning Tweets from Server
        TweetReader reader = new TweetReader();
//        long i = 2147483647L;
        double i = 2147483648L;
        long z = 4294967290L;

        int j = (int)i;
        System.out.println(j);
        List<Tweets> tweetsList = reader.readTweetsFromWeb(URLEndpoint);
       List<String> alreadyViewed = new ArrayList<>();
//        for(Tweets tweet : tweetsList) {
//            if(alreadyViewed.contains(tweet.getName().toLowerCase()))
//                continue;
//            System.out.println(tweet.getName());
//            alreadyViewed.add(tweet.getName().toLowerCase());
//        }
//         //Problem 2:
//        // Filter Tweets by Username
//        Filter filter = new Filter();
//        List<Tweets> filteredUser = filter.writtenBy(tweetsList,"____");
//        System.out.println();
//        System.out.println(filteredUser.size() + " Tweets by the username!");
//        System.out.println("-------------------");
//        System.out.println(filteredUser);
//
//        // Filter by Timespan
//        Instant testStart = Instant.parse("2017-11-11T00:00:00Z");
//        Instant testEnd = Instant.parse("2017-11-11T03:00:00Z");
//        Timespan timespan = new Timespan(testStart,testEnd);
//        List<Tweets> filteredTimeSpan = filter.inTimespan(tweetsList,timespan);
//        System.out.println("-------------------");
//        System.out.println();
//        System.out.println(filteredTimeSpan.size() + " Tweets in that timeSpan!");
//        System.out.println("-------------------");
//        System.out.println(filteredTimeSpan);
//        System.out.println("-------------------");
//
//
//        //Filter by words containinng
//        List<Tweets> filteredWords = filter.containing(tweetsList, Arrays.asList("testing","sorry", "testcase", "googling"));
//        System.out.println();
//        System.out.println(filteredWords.size() + " Tweets that contain these words!");
//        System.out.println("-------------------");
//        System.out.println(filteredWords);
//        System.out.println("-------------------");


        SocialNetwork friends = new SocialNetwork();
//        List<Set<String>> friendsList = friends.findCliques(tweetsList);
//        System.out.println(friendsList.size());
//        for(Set<String> item : friendsList) {
//            System.out.println(item);
//        }
       List<String> tweeters = assignment4.SocialNetwork.findKMostFollower(tweetsList, 10);
//        System.out.println(tweeters.size());
//       for (String user : tweeters) {
//           System.out.println(user + " ");
//       }

    }
}

