package twitter4j.examples;

import java.util.List;

import twitter4j.Twitter;
import twitter4j.User;

public class GetFriends {

	    /**
	     * Usage: java -Dtwitter4j.oauth.consumerKey=[consumer key] -Dtwitter4j.oauth.consumerSecret=[consumer secret] twitter4j.examples.GetFriends [accessToken] [accessSecret]
	     * @param args message
	     */
	    public static void main(String[] args) {
	        try {
	        //	System.setProperty("twitter4j.oauth.consumerKey", Twitter.CONSUMER_KEY);
	        //	System.setProperty("twitter4j.oauth.consumerSecret", Twitter.CONSUMER_SECRET);
	        	
	            Twitter twitter = new Twitter();
	            
	            twitter.setToken(args[0], args[1]);
	  
				 try {

						List<User> list= twitter.getFriendsStatuses();
						
						System.out.println("Successfully get Friends to [" + list + "].");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            System.exit(0);
	        } catch (Exception ioe) {
	            System.out.println("Failed to read the system input.");
	            System.exit( -1);
	        }
	    }
}
