/*
Copyright (c) 2007-2009, Yusuke Yamamoto
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of the Yusuke Yamamoto nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY Yusuke Yamamoto ``AS IS'' AND ANY
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL Yusuke Yamamoto BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package twitter4j.examples;

import java.util.List;

import twitter4j.Comment;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.http.AccessToken;
import twitter4j.http.AuthorizeToken;
import twitter4j.http.RequestToken;

/**
 * Example application that uses OAuth method to acquire access to your account.<br>
 * This application illustrates how to use OAuth method with Twitter4J.<br>
 * Usage: java -Dtwitter4j.oauth.consumerKey=[consumer key] -Dtwitter4j.oauth.consumerSecret=[consumer secret] twitter4j.examples.OAuthUpdate [message]
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public class OAuthSetTokenUpdate {
    /**
     * Usage: java -Dtwitter4j.oauth.consumerKey=[consumer key] -Dtwitter4j.oauth.consumerSecret=[consumer secret] twitter4j.examples.OAuthUpdate [message]
     * @param args message
     */
    public static void main(String[] args) {
        try {
        	/*
			if (args.length < 3) {
				System.out.println(
					"Usage: java twitter4j.examples.Update token tokenSecret text");
				System.exit( -1);
			}
			*/
        //	System.setProperty("twitter4j.oauth.consumerKey", Twitter.CONSUMER_KEY);
        //	System.setProperty("twitter4j.oauth.consumerSecret", Twitter.CONSUMER_SECRET);        	
            Twitter twitter = new Twitter();
            //get request tocken
            RequestToken requestToken = twitter.getOAuthRequestToken();
            System.out.println("Got request token.");
            System.out.println("Request token: "+ requestToken.getToken());
            System.out.println("Request token secret: "+ requestToken.getTokenSecret());
            //get authorized tocken
            AuthorizeToken authorizedToken = twitter.getAuthorizedToken("tearblue78@gmail.com","springqi");
            //get access tocken
            String pin = authorizedToken.getVerify();
            //System.out.println("Got token:" + authorizedToken.getToken());
            System.out.println("Got pin:" + pin);
            AccessToken accessToken = requestToken.getAccessToken(pin);
            twitter.setOAuthAccessToken(accessToken.getToken(), accessToken.getTokenSecret());
			
            Status status = twitter.updateStatus("test Oauth login");
           // System.out.println("Successfully updated the status to [" + status.getText() + "].");
            
            String id = String.valueOf(status.getId());
            Comment comment = twitter.sendComment(id, "comment test fdsf  ~~");
            
            List<Comment> comentes = twitter.getComments(String.valueOf(status.getId()));
            for (Comment com : comentes) {
            	System.out.println(com.getUser().getName() + " comment:" +
                        com.getText());
            }
            
            System.exit(0);
        } catch (TwitterException te) {
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit( -1);
        } catch (Exception ioe) {
            System.out.println("Failed to read the system input.");
            System.exit( -1);
        }
    }
}
