package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import org.junit.Test;

public class SocialNetworkTest {

	Instant i = Instant.parse("2017-02-17T15:00:00Z");
	Tweet AfB = new Tweet(1, "A", "@B", i);
	Tweet AfC = new Tweet(2, "A", "@C", i);
	
	Tweet BfA = new Tweet(3, "B", "@A", i);
	Tweet BfC = new Tweet(4, "B", "@C", i);
	Tweet CfA = new Tweet(5, "C", "@A", i);
	Tweet CfB = new Tweet(6, "C", "@B", i);
	Tweet A = new Tweet(7, "A", "void", i);
	Tweet B = new Tweet(8, "B", "void", i);
	Tweet C = new Tweet(9, "C", "void", i);
	Tweet AfBC = new Tweet(10, "A", "@B,@C", i);
	
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    /*
     * Tests for guessFollows
     * NB the weak postcondition merely requires that C not contain 
     */
    
    @Test
    public void testGraphEmpty() {
        Map<String, Set<String>> followsGraph = 
        		SocialNetwork.guessFollowsGraph(new ArrayList<>());
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }
    
    @Test
    public void testTweetsWithoutMentions() {
    	List<Tweet> tweets = Arrays.asList(A, B, C);
    	Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
    	assertTrue("expected empty graph", followsGraph.isEmpty());
    }
    
    @Test
    public void testSingleMention() {
    	List<Tweet> tweets = Arrays.asList(AfB, BfA);
    	Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
    	
    	assertFalse("expected non-empty graph", followsGraph.isEmpty());
    	
    	Set<String> A_follows = new HashSet<String>(followsGraph.get("A"));
    	Set<String> B_follows = new HashSet<String>(followsGraph.get("B"));
    			
    	// Expect A={B}, B={A}
    	assertTrue("expected A to follow B", setEq(A_follows, new HashSet<>(Arrays.asList("B"))));
    	assertTrue("expected B to follow A", setEq(B_follows, new HashSet<>(Arrays.asList("A"))));
    	assertTrue("expected C not to follow anybody", isNullOrEmpty(followsGraph.get("C"))); 
    }
    
    @Test
    public void testMultipleMentions() {
    	List<Tweet> tweets = Arrays.asList(AfBC);
    	Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
    	
    	Set<String> A_follows = new HashSet<>(followsGraph.get("A"));
    	
    	// Expect A={B,C}
    	assertFalse("expected non-empty graph", followsGraph.isEmpty());
    	assertTrue("expected A to follow B and C", setEq(A_follows, new HashSet<>(Arrays.asList("B", "C"))));
    	}
    
    @Test
    public void testMultipleTweets() {
    	List<Tweet> tweets = Arrays.asList(AfB, AfC);
    	Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
    	
    	assertFalse("expected non-empty graph", followsGraph.isEmpty());
    	
    	// Expect A={B,C} only
    	Set<String> A_follows = new HashSet<>(followsGraph.get("A"));
    	
    	assertTrue("expected A to follow B and C", setEq(A_follows, new HashSet<>(Arrays.asList("B", "C"))));
    }
    
    
    
 
    
    
    
    
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
    }
    
    @Test
    public void testInfluencersEmptySet() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("A", new HashSet<>());
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertEquals("expected list of size 1", influencers.size(), 1);
        assertTrue("expected A to be in list", influencers.contains("A"));
    }
    
    @Test
    public void testSingleInfluencer() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("A", new HashSet<>(Arrays.asList("B")));
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        // Expect [A, B] or [B, A]
        assertEquals("expected list of size 2", influencers.size(), 2);
        assertTrue("expected A to be in list", influencers.contains("A"));
        assertTrue("expected B to be in list", influencers.contains("B"));
    }
    
    @Test
    public void testMultipleInfluencers() {
    	Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("A", new HashSet<>(Arrays.asList("B")));
        followsGraph.put("C", new HashSet<>(Arrays.asList("B")));
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        // Expect [B,C,A] or [A,C,B]
        assertEquals("expected list of size 3", influencers.size(), 3);
        assertEquals("expected B to be first in list", influencers.get(0), "B");
        assertTrue("expected A to be in list", influencers.contains("A"));
        assertTrue("expected C to be in list", influencers.contains("C"));
    }
    
    @Test 
    public void testInfluencersTied() {
    	Map<String, Set<String>> followsGraph = new HashMap<>();
    	followsGraph.put("B", new HashSet<>(Arrays.asList("A", "C")));
    	followsGraph.put("C", new HashSet<>(Arrays.asList("A","B")));
    	followsGraph.put("A", new HashSet<>(Arrays.asList("B","C")));
    	
    	List<String> influencers = SocialNetwork.influencers(followsGraph);
    	
    	// Expect [A,C,B] or [A,C,B]
    	assertEquals("expected list of size 3", influencers.size(), 3);
    	assertTrue("expected A to be in list", influencers.contains("A"));
    	assertTrue("expected B to be in list", influencers.contains("B"));
    	assertTrue("expected C to be in list", influencers.contains("C"));
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */
    
    /**
     * Helper method which asserts a set is null or empty
     * @param items A set of any type
     */
    private <T> boolean isNullOrEmpty(Set<T> items) {
    	return items == null || items.size() == 0;
    }
    
    private <T> boolean setEq(final Set<T> setA, final Set<T> setB) {
    	// Use copies 
    	Set<T> a = new HashSet<T>(setA);
    	Set<T> b = new HashSet<T>(setB);
    	for(T t: a)
    	{
    		if (!b.contains(t))
    			return false;
    		b.remove(t);
    	}
    	// Popped everything from B as we found it, so
    	// if the sets were equal it should now be empty
    	return b.isEmpty();
    }

}
