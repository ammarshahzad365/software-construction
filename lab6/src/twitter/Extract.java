/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.List;
import java.time.Instant;
import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;
import java.lang.StringBuilder;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
	public static Timespan getTimespan(List<Tweet> tweets) {
        Instant min = tweets.get(0).getTimestamp();
        Instant max = tweets.get(0).getTimestamp();
        return getTimespan(tweets, min, max);
    }
    
	/**
	 * Recursive utility method for finding the time period
	 * spanned by tweets.
	 * 
	 * @param tweets 
	 * 			list of tweets with distinct ids, not modified
	 * @param min
	 * 			Instant object which keeps track of the minimum time
	 * @param max
	 * 			Instant object which keeps track of the maximum time
	 * @return a minimum-length time interval that contains the timestamp of
	 * 		every tweet in the list
	 */
    private static Timespan getTimespan(final List<Tweet> tweets, Instant min, Instant max) {
    	if (tweets.isEmpty())
    		return new Timespan(min, max);
    	Tweet t = tweets.get(0);
    	if (min.compareTo(t.getTimestamp()) >= 0)
    		min = t.getTimestamp();
    	if (max.compareTo(t.getTimestamp()) <= 0)
    		max = t.getTimestamp();
    	return getTimespan(tweets.subList(1, tweets.size()), min, max);
    			
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        // Lower-case record, avoids duplication by case difference
    	HashSet<String> unames = new HashSet<>();
    	
    	// the set to return, with names in original case
        HashSet<String> results = new HashSet<>();
        
        for (Tweet t : tweets)
        {
        	String txt = t.getText();
        	int i = 0; // iterator
        	while (i < txt.length())
        	{
        		char c = txt.charAt(i);
        		if (c == '@' 
        			&& 
        		   (i == 0 || !isIdentifier(txt.charAt(i-1))) 
        		   	&&
        		   isIdentifier(txt.charAt(i+1)))
        		{
    				// Start of a mention
    				StringBuilder sb = new StringBuilder(txt.length()-i);
    				while (++i < txt.length() && 
    						isIdentifier(txt.charAt(i)))
    				{
    					sb.append(txt.charAt(i));
    				}
    				String result = sb.toString(); // Original case
    				String uname = result.toLowerCase();
    				if (!unames.contains(uname))
    				{
    					unames.add(uname); // don't go over it twice
    					results.add(result);
    				}
        		}
        		else
        			i++;
        	}
        }
        return results;
    }
    
    /**
     * Get usernames mentioned in a tweet tweet.
     * 
     * @param tweet
     *            a tweet not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(Tweet t) {
    	return getMentionedUsers(Arrays.asList(t));
    }
    
    /**
     * Determines whether a char is a valid identifier or not
     * @param c  the character to test
     * @return   whether or not the character is a member of the 
     * 			 charset [a-zA-Z0-9_-]
     */
    private static boolean isIdentifier(char c) {
    	return     c >= 'A' && c <='Z'
    			|| c >= 'a' && c <= 'z'
    			|| c >= '0' && c <= '9'
    			|| c == '_'
    			|| c == '-';
    }

}