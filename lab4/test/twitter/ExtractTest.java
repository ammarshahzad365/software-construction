/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

	private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2017-02-17T10:00:00Z");
    private static final Instant d4 = Instant.parse("2017-02-17T11:00:00Z");
    
    
    // Testing getTimespan()
    private static final Tweet tweet1 = new Tweet(
    		1, "rehman", "watched GOT finale today", d1);
    private static final Tweet tweet2 = new Tweet(
    		2, "ammar", "GOT finale sucked", d2);
    private static final Tweet tweet3 = new Tweet(
    		3, "ahmed", "What's the point of everything?", d3);
    private static final Tweet tweet4 = new Tweet(
    		4, "usman", "no point at all", d4);
    private static final Tweet tweet5 = new Tweet(
    		5, "ayyan", "haha", d4);
    
    // Tweets with user mentioned
    private static final Tweet tweet6 = new Tweet(
    		6, "ammar", "@usman mention at start of tweet", d1);
    private static final Tweet tweet7 = new Tweet(
    		7, "ammar", "mention @usman during tweet", d1);
    private static final Tweet tweet8 = new Tweet(
    		8, "ammar", "mention !@usman with non-tweet char beforehand", d1);
    private static final Tweet tweet9 = new Tweet(
    		9, "ammar", "not a mention a@b not a mention", d1);
    private static final Tweet tweet10 = new Tweet(
    		10, "ahmed", "@covfefe @covfefe @covfefe @covfefe @covfefe - only 1 mention", d1);
    private static final Tweet tweet11 = new Tweet(
    		11, "ahmed", "@Covfefe @covFEFE @CovFeFe @covFefe @COVFEFE - only 1 mention", d1);
    private static final Tweet tweet12 = new Tweet(
    		12, "ammar", "@mention1 @mention2 @mention3 @mention4 @mention5", d1);
    
    
    /*
     * case 1: two tweets at the same time
     */
    @Test
    public void testGetTimespanSimultaneousTweets() {
    	Timespan timespan = Extract.getTimespan(Arrays.asList(tweet4, tweet5));
    	assertEquals("expected start", d4, timespan.getStart());
    	assertEquals("expected end", d4, timespan.getEnd());
    }
    
    /*
     * case 2: three tweets with different times
     */
    @Test
    public void testGetTimespanThreeTweets() {
    	Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet3, tweet5));
    	assertEquals("expected start", d1, timespan.getStart());
    	assertEquals("expected end", d4, timespan.getEnd());
    }
    
    /*
     * case 3: three tweets, two of which were at the same time
     */
    @Test 
    public void testGetTimespanThreeTweetsIncSimultaneous() {
    	Timespan timespan = Extract.getTimespan(Arrays.asList(tweet3, tweet4, tweet5));
    	assertEquals("expected start", d3, timespan.getStart());
    	assertEquals("expected end", d4, timespan.getEnd());
    }
    
        
    /*
     * getMentionedUsers tests
     * case 1: tweets with no mentioned users
     */
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(
        		tweet1, tweet2, tweet4, tweet5));
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    
    /*
     * case 2: tweet with mention at the start
     */
    @Test
    public void testGetMentionedUsersMentionAtStart() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet6));
        assertEquals(1, mentionedUsers.size());
        assertTrue("expected set to contain 'usman'", mentionedUsers.contains("usman"));
    }
    
    /*
     * case 3: tweet with mention in the middle
     */
    @Test
    public void testGetMentionedUsersMentionInMiddle() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet7));
        assertEquals(1, mentionedUsers.size());
        assertTrue("expected set to contain 'usman'", mentionedUsers.contains("usman"));
    }
    
    /*
     * case 4: tweet with non-tweet char before mention
     */
    @Test
    public void testGetMentionedUsersMentionWithPrecedingChars() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet8));
        assertEquals(1, mentionedUsers.size());
        assertTrue("expected set to contain 'usman'", mentionedUsers.contains("usman"));
    }
    
    /*
     * case 5: tweets with duplicate mentions of same username
     */
    @Test 
    public void testGetMentionedUsersDuplicateMentions() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet10));
        assertEquals(1, mentionedUsers.size());
        assertTrue("expected set to contain 'covfefe'", mentionedUsers.contains("covfefe"));
    }
    
    @Test
    public void testGetMentionedUsersDuplicateMentions2() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet11));
        assertEquals(1, mentionedUsers.size());
        
        boolean containsCovfefe = false;
        for (String user: mentionedUsers)
        {
        	containsCovfefe = user.toLowerCase().equals("covfefe");
        	if (containsCovfefe)
        		break;
        }
        assertTrue("expected set to contain 'covfefe'", containsCovfefe);
    }
    
    /*
     * case 6: tweets with multiple mentions
     */
    @Test 
    public void testGetMentionedUsersMultipleMentions() {
    	Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet12));
    	assertEquals(5, mentionedUsers.size());
    	for (String s : Arrays.asList("mention1", "mention2", "mention3", "mention4", "mention5"))
    		assertTrue("expected set to contain " + s, mentionedUsers.contains(s));
    }

    
}
