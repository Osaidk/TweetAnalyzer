package assignment4;

import java.util.*;

/**
 * Social Network consists of methods that filter users matching a
 * condition.
 *
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {

    /**
     * Get K most followed Users.
     *
     * @param tweets list of tweets with distinct ids, not modified by this method.
     * @param k      integer of most popular followers to return
     * @return the set of usernames who are most mentioned in the text of the tweets.
     * A username-mention is "@" followed by a Twitter username (as
     * defined by Tweet.getName()'s spec).
     * The username-mention cannot be immediately preceded or followed by any
     * character valid in a Twitter username.
     * For this reason, an email address like ethomaz@utexas.edu does NOT
     * contain a mention of the username.
     * Twitter usernames are case-insensitive, and the returned set may
     * include a username at most once.
     */

    public static List<String> findKMostFollower(List<Tweets> tweets, int k) {
        int initialCount = 0;
        Filter filter = new Filter();
        List<String> mostFollowers = new ArrayList<>();
        List<String> visitedUser = new ArrayList<>();
        HashMap<String, Integer> visitedTweeter = new HashMap<>();
        for(Tweets tweet : tweets) {
            visitedTweeter.put(tweet.getName().toLowerCase(), initialCount);
            for(Tweets tweet1 : tweets) {
                if(visitedUser.contains(tweet1.getName().toLowerCase()))
                    continue;
                if(tweet.getName().toLowerCase().equals(tweet1.getName().toLowerCase()))
                    continue;
                visitedUser.add(tweet1.getName().toLowerCase());
                List<Tweets> filteredByUser = filter.writtenBy(tweets, tweet1.getName().toLowerCase());
                for(Tweets byUser : filteredByUser) {
                    List<String> wordsList = Arrays.asList(byUser.getText().toLowerCase().split(" "));
                    if(wordsList.contains("@" + tweet.getName().toLowerCase())) {
                        visitedTweeter.replace(tweet.getName().toLowerCase(), initialCount++);
                        break;
                    }
                }
            }
            initialCount = 0;
            visitedUser.clear();
        }
        visitedTweeter = sortMap(visitedTweeter);
        String []users = new String[visitedTweeter.size()];
        users = visitedTweeter.keySet().toArray(users);
        for (int i = users.length - 1; i >= users.length - k; --i) {
            mostFollowers.add(users[i]);
        }
        return mostFollowers;
    }


    private static HashMap<String, Integer> sortMap(HashMap<String, Integer> originalMap) {
        List<Map.Entry<String, Integer>> tempLinkedList =
                new LinkedList<>(originalMap.entrySet());                 // Create a tempLinkedList from elements of HashMap
        tempLinkedList.sort(Comparator.comparing(Map.Entry::getValue));  // Sort the tempLinkedList
        HashMap<String, Integer> sortedMap = new LinkedHashMap<>();     // put data from sorted tempLinkedList to hashmap
        for (Map.Entry<String, Integer> users : tempLinkedList) {
            sortedMap.put(users.getKey(), users.getValue());
        }
        return sortedMap;
    }


    /**
     * Find all cliques in the social network.
     *
     * @param tweets list of tweets with distinct ids, not modified by this method.
     * @return list of set of all cliques in the graph
     */
    List<Set<String>> findCliques(List<Tweets> tweets) {
        List<Set<String>> result = new ArrayList<>();
        HashMap<String, List<Tweets>> tweetsByUser = tweetsByUser(tweets);
        Set<String> set;
        for(String name : tweetsByUser.keySet()) {
            set = new LinkedHashSet<>();
            for(String name2 : tweetsByUser.keySet()) {
                if (name.equals(name2))
                    continue;
                set.add(name);
                List<Tweets> userTweets = tweetsByUser.get(name);
                for (Tweets userTweet : userTweets) {
                    List<String> wordsList = Arrays.asList(userTweet.getText().toLowerCase().split(" "));
                    if (wordsList.contains("@" + name2) && mentionsBack(tweetsByUser.get(name2), name) &&
                            mentionsSetUsers(name2, set, tweetsByUser) && setUsersMentionIt(name2, set, tweetsByUser))
                        set.add(name2);
                }
            }
            if(set.size() > 1 && !result.contains(set)) {
                result.add(set);
            }
        }
        return result;
    }


    private boolean mentionsBack(List<Tweets> tweets, String user) {
        for(Tweets tweet : tweets) {
            List<String> wordsList = Arrays.asList(tweet.getText().toLowerCase().split(" "));
            if(wordsList.contains("@" + user))
                return true;
        }
        return false;
    }


    private boolean mentionsSetUsers(String user, Set<String> users, HashMap<String, List<Tweets>> tweetsByuser) {
        List<Tweets> byUser = tweetsByuser.get(user);
        int count = 0;
        for(String setUser : users) {
            for(Tweets tweet : byUser) {
                List<String> wordsList = Arrays.asList(tweet.getText().toLowerCase().split(" "));
                if(wordsList.contains("@" + setUser)) {
                    count++;
                    break;
                }
            }
        }
        return count == (users.size());
    }


    private boolean setUsersMentionIt(String user, Set<String> users, HashMap<String, List<Tweets>> tweetsByUser) {
        int count = 0;
        for(String setUser : users) {
            List<Tweets> byUser = tweetsByUser.get(setUser);
            for(Tweets tweet : byUser) {
                List<String> wordsList = Arrays.asList(tweet.getText().toLowerCase().split(" "));
                if(wordsList.contains("@" + user)) {
                    count++;
                    break;
                }
            }

        }
        return count == users.size();
    }


    private HashMap<String, List<Tweets>> tweetsByUser(List<Tweets> tweets) {
        HashMap<String, List<Tweets>> userTweets = new HashMap<>();
        Filter filter = new Filter();
        for(Tweets tweet : tweets) {
            if(!userTweets.containsKey(tweet.getName().toLowerCase()))
                userTweets.put(tweet.getName().toLowerCase(), filter.writtenBy(tweets, tweet.getName().toLowerCase()));
        }
        return userTweets;
    }




}

