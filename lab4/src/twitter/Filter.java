package twitter;

import java.util.List;
import java.util.regex.Pattern;
import java.time.Instant;
import java.util.ArrayList;

/**
 * The Filter class provides methods to sift through a collection of tweets
 * based on specific criteria.
 * 
 * Do not modify the method signatures and specifications. You may implement 
 * the method bodies and can add any new public or private methods or classes if desired.
 */
public class Filter {

    /**
     * Retrieves tweets authored by a specified user.
     * 
     * @param tweets
     *            a list of distinct tweets, which will not be altered by this method.
     * @param username
     *            the Twitter username, which must comply with the specifications
     *            defined in Tweet.getAuthor().
     * @return a list of tweets from the input that were authored by the specified
     *         username, maintaining the same order as in the input list.
     */
    public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
        List<Tweet> result = new ArrayList<>();
        for (Tweet tweet : tweets) {
            if (tweet.getAuthor().equals(username)) {
                result.add(tweet);
            }
        }
        return result;
    }

    /**
     * Filters tweets sent within a specified time period.
     * 
     * @param tweets
     *            a list of distinct tweets, which will not be modified by this method.
     * @param timespan
     *            the period during which to filter the tweets.
     * @return a list of tweets from the input that were sent within the given
     *         timespan, preserving the original order from the input.
     */
    public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
        Instant startTime = timespan.getStart();
        Instant endTime = timespan.getEnd();
        List<Tweet> filteredTweets = new ArrayList<>();
        for (Tweet tweet : tweets) {
            Instant tweetTime = tweet.getTimestamp();
            if (!tweetTime.isBefore(startTime) && !tweetTime.isAfter(endTime)) {
                filteredTweets.add(tweet);
            }
        }
        return filteredTweets;
    }

    /**
     * Finds tweets that include specific words.
     * 
     * @param tweets
     *            a list of distinct tweets, which will not be altered by this method.
     * @param words
     *            a list of words to search for within the tweets. 
     *            Each word is defined as a sequence of non-space characters.
     * @return a list of tweets from the input that contain at least one of the
     *         specified words. The comparison is case-insensitive, meaning "Obama"
     *         is treated the same as "obama". The returned tweets are in the same
     *         order as they appear in the input list.
     */
    public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
        List<Tweet> matchedTweets = new ArrayList<>();
        for (Tweet tweet : tweets) {
            String tweetText = tweet.getText();
            for (String word : words) {
                Pattern pattern = Pattern.compile("\\b" + Pattern.quote(word) + "\\b", Pattern.CASE_INSENSITIVE);
                if (pattern.matcher(tweetText).find()) {
                    matchedTweets.add(tweet);
                    break; // Only one match is necessary
                }
            }
        }
        return matchedTweets;
    }
}
