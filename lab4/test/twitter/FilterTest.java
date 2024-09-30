
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    private static final Instant TIME1 = Instant.parse("2016-01-17T10:00:00Z");
    private static final Instant TIME2 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant TIME3 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant TIME4 = Instant.parse("2016-02-17T12:00:00Z");
    private static final Instant TIME5 = Instant.parse("2017-02-17T10:00:00Z");
    private static final Instant TIME6 = Instant.parse("2017-02-17T10:30:00Z");
    private static final Instant TIME7 = Instant.parse("2017-02-17T11:00:00Z");
    private static final Instant TIME8 = Instant.parse("2017-02-17T15:00:00Z");

    private static final Tweet TWEET_A = new Tweet(1, "alice", "Is it valid to discuss cryptography extensively?", TIME2);
    private static final Tweet TWEET_B = new Tweet(2, "bob", "Cryptography chat in 30 minutes #excited", TIME3);
    private static final Tweet TWEET_C = new Tweet(3, "sameUser", "Awareness is a blend of realities .@.!*@& ", TIME5);
    private static final Tweet TWEET_C_SHORT = new Tweet(8, "sameUser", "Awareness", TIME5);
    private static final Tweet TWEET_C_UPPER = new Tweet(4, "sameUser", "Awareness is a BLEND of realities", TIME5);
    private static final Tweet TWEET_D = new Tweet(5, "sameUser", "Despite ongoing negative attention, it’s fine", TIME7);
    private static final Tweet TWEET_E = new Tweet(6, "sameUser", "Due to the infrequent positive focus, it’s good", TIME7);
    private static final Tweet TWEET_F = new Tweet(7, "sameUser", "A tweet just to ensure the test suite runs", TIME7);


    /*
     * Testing writtenBy() 
     * No results expected
     */
    @Test
    public void writtenBySingleTweetNoResults() {
        List<Tweet> result = Filter.writtenBy(Arrays.asList(TWEET_A), "nonexistent_user");
        assertTrue("Expected no results", result.isEmpty());
    }

    @Test
    public void writtenByMultipleTweetsNoResults() {
        List<Tweet> result = Filter.writtenBy(
                Arrays.asList(TWEET_A, TWEET_B, TWEET_C, TWEET_D, TWEET_E),
                "nonexistent_user"
        );
        assertTrue("Expected no results", result.isEmpty());
    }

    /*
     * One result expected
     */
    @Test
    public void writtenBySingleTweetOneResult() {
        List<Tweet> result = Filter.writtenBy(Arrays.asList(TWEET_A), "alice");
        assertEquals("Expected one tweet in the result", 1, result.size());
        assertTrue("Expected result to contain the tweet", result.contains(TWEET_A));
    }

    @Test
    public void writtenByMultipleTweetsOneResult() {
        List<Tweet> result = Filter.writtenBy(
                Arrays.asList(TWEET_A, TWEET_B, TWEET_C, TWEET_D, TWEET_E),
                "alice"
        );
        assertEquals("Expected one tweet in the result", 1, result.size());
        assertTrue("Expected result to contain the tweet", result.contains(TWEET_A));
    }

    /*
     * Multiple results expected (0 < size < input size)
     */
    @Test
    public void writtenByMultipleTweetsMultipleResults() {
        List<Tweet> result = Filter.writtenBy(
                Arrays.asList(TWEET_A, TWEET_D, TWEET_C, TWEET_B),
                "sameUser"
        );
        assertFalse("Expected non-empty result", result.isEmpty());
        assertTrue("Expected result to contain tweets", 
                result.containsAll(Arrays.asList(TWEET_D, TWEET_C)));
        assertEquals("Expected tweets in original order", 0, result.indexOf(TWEET_D));
    }

    /*
     * All results expected
     */
    @Test
    public void writtenByMultipleTweetsAllResults() {
        List<Tweet> result = Filter.writtenBy(
                Arrays.asList(TWEET_E, TWEET_D, TWEET_C, TWEET_F),
                "sameUser"
        );
        assertFalse("Expected non-empty result", result.isEmpty());
        assertTrue("Expected result to contain tweets", 
                result.containsAll(Arrays.asList(TWEET_E, TWEET_D, TWEET_C, TWEET_F)));
        assertEquals("Expected tweets in original order", 0, result.indexOf(TWEET_E));
        assertEquals("Expected tweets in original order", 1, result.indexOf(TWEET_D));
        assertEquals("Expected tweets in original order", 2, result.indexOf(TWEET_C));
    }

    /*
     * Testing inTimespan()
     * No results expected for one tweet
     */
    @Test 
    public void inTimespanOneTweetNoResult() {
        List<Tweet> result = Filter.inTimespan(
                Arrays.asList(TWEET_A),
                new Timespan(TIME3, TIME5)
        );
        assertTrue("Expected no results", result.isEmpty());
    }

    /*
     * One result expected for one tweet
     */
    @Test
    public void inTimespanOneTweetOneResult() {
        List<Tweet> result = Filter.inTimespan(
                Arrays.asList(TWEET_B),
                new Timespan(TIME2, TIME4)
        );
        assertEquals("Expected one tweet in the result", 1, result.size());
        assertEquals("Expected result to contain the tweet", TWEET_B, result.get(0));
    }

    /*
     * No results expected for multiple tweets
     */
    @Test
    public void inTimespanMultipleTweetsNoResult() {
        List<Tweet> result = Filter.inTimespan(
                Arrays.asList(TWEET_C, TWEET_D, TWEET_E, TWEET_F),
                new Timespan(TIME2, TIME4)
        );
        assertTrue("Expected no results", result.isEmpty());
    }

    /*
     * Some results expected for multiple tweets
     */
    @Test
    public void inTimespanMultipleTweetsSomeResults() {
        List<Tweet> result = Filter.inTimespan(
                Arrays.asList(TWEET_C, TWEET_B, TWEET_D, TWEET_E, TWEET_F),
                new Timespan(TIME2, TIME6)
        );
        assertEquals("Expected two tweets in the result", 2, result.size());
        assertTrue("Expected result to contain tweets", 
                result.containsAll(Arrays.asList(TWEET_C, TWEET_B)));
        assertEquals("Expected tweets in original order", 0, result.indexOf(TWEET_C));
    }

    /*
     * All results expected for multiple tweets
     */
    @Test 
    public void inTimespanMultipleTweetsAllResults() {
        List<Tweet> expectedTweets = Arrays.asList(
                TWEET_C, TWEET_B, TWEET_E, TWEET_D, TWEET_F, TWEET_A
        ); 
        List<Tweet> result = Filter.inTimespan(
                expectedTweets,             
                new Timespan(TIME1, TIME8)
        );
        assertEquals("Expected six tweets in the result", 6, result.size());
        assertTrue("Expected result to contain tweets", result.containsAll(expectedTweets));
        for (int i = 0; i < expectedTweets.size(); ++i) {
            assertEquals("Expected tweets in original order", 
                    i, result.indexOf(expectedTweets.get(i)));
        }
    }

    /*
     * Testing the case where the tweet matches the timespan exactly
     */
    @Test
    public void inTimespanOneTweetAtExactInstant() {
        List<Tweet> result = Filter.inTimespan(
                Arrays.asList(TWEET_A),
                new Timespan(TIME2, TIME2)
        );
        assertEquals("Expected one tweet in the result", 1, result.size());
        assertEquals("Expected result to contain the tweet", TWEET_A, result.get(0));
    }

    /*
     * Testing boundaries of timespan
     */
    @Test
    public void inTimespanOneTweetAtMinBoundary() {
        List<Tweet> result = Filter.inTimespan(
                Arrays.asList(TWEET_A),
                new Timespan(TIME2, TIME3)
        );
        assertEquals("Expected one tweet in the result", 1, result.size());
        assertEquals("Expected result to contain the tweet", TWEET_A, result.get(0));
    }

    @Test
    public void inTimespanOneTweetAtMaxBoundary() {
        List<Tweet> result = Filter.inTimespan(
                Arrays.asList(TWEET_B),
                new Timespan(TIME2, TIME4)
        );
        assertEquals("Expected one tweet in the result", 1, result.size());
        assertEquals("Expected result to contain the tweet", TWEET_B, result.get(0));
    }
}
