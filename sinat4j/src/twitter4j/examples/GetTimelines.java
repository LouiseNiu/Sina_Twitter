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

import twitter4j.Comment;
import twitter4j.DirectMessage;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.org.json.JSONException;

import java.io.IOException;
import java.util.List;



/**
 * Example application that gets public, user and friend timeline using specified account.<br>
 * Usage: java twitter4j.examples.GetTimelines ID Password
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public class GetTimelines {
    /**
     * Usage: java twitter4j.examples.GetTimelines ID Password
     * @param args String[]
     * 
     */
	public static String  SinaBaseURL = "http://api.t.sina.com.cn:80/";	
	public static final String SINA_CONSUMER_KEY = "666117474";
	public static final String SINA_CONSUMER_SECRET = "dc62617f011611dd27080cd4697bc490";
    public static void main(String[] args) {
        System.out.println("Showing public timeline.");
        try {
        	/*
            if (args.length < 2) {
                System.out.println(
                    "You need to specify TwitterID/Password combination to show UserTimelines.");
                System.out.println(
                    "Usage: java twitter4j.examples.GetTimelines ID Password");
                System.exit(0);
            }
             */
            // Other methods require authentication
    		System.setProperty("twitter4j.oauth.consumerKey", SINA_CONSUMER_KEY);
    		System.setProperty("twitter4j.oauth.consumerSecret", SINA_CONSUMER_SECRET);
        	System.setProperty("twitter4j.source", SINA_CONSUMER_KEY);
            Twitter twitter = new Twitter("tearblue78@gmail.com", "springqi");
            twitter.setBaseURL(SinaBaseURL);
           // twitter.setSource(Twitter.CONSUMER_KEY);
            List<Status> statuses = null;
            /*
            List<Status> statuses = twitter.getPublicTimeline();
            for (Status status : statuses) {
                System.out.println(status.getUser().getName() + ":" +
                                   status.getText());
            }

            statuses = twitter.getFriendsTimeline();
            System.out.println("------------------------------");
            System.out.println("Showing " + args[0] + "'s friends timeline.");
            for (Status status : statuses) {
                System.out.println(status.getUser().getName() + ":" +
                                   status.getText());
            }
            */
          
           
            int count = 100;
            int page = 1;
            int i =1;
          // do  {
            Paging paging= new Paging();
            paging.setCount(count);
           // paging.setPage(page);
           // paging.setSinceId(206864173);
            statuses = twitter.getHomeTimeline(paging);
            System.out.println("page " + i + " size is " + statuses.size());
          //  if(statuses.size() > 0)
         //   	i++;
          //  else
           // 	break;
          //  }while (statuses.size()>0);
            System.out.println("------------------------------");
           // System.out.println("Showing " + args[0] + "'s timeline.");
            for (Status status : statuses) {
                System.out.println(status.getUser().getName() + ":" +
                                   status.getText());
                System.out.println("has retweeted status ?" + ":" +
                		status.isHasRetweet());
              
                if(status.isHasRetweet() == true)
                {                
                	 System.out.println(status.getUser().getName() + " ret text:" +
                             status.getRetweetStatus().getText());
                }
                
                
                System.out.println(status.getUser().getName() + " domain:" +
                        status.getUser().getDomain()); 
                
                Paging pag= new Paging();
                pag.setCount(count);
                List<Comment> comentes = twitter.getComments(String.valueOf(status.getId()),pag);
                for (Comment com : comentes) {
                	System.out.println(com.getUser().getName() + " comment:" +
                            com.getText());
            }
            }
            
           // List<DirectMessage> DMList = twitter.getDirectMessages();
           // Status status = twitter.show(81642112l);
           // System.out.println("------------------------------");
            //System.out.println("Showing " + status.getUser().getName() +
            //                   "'s status updated at " + status.getCreatedAt());
          //  System.out.println(status.getText());
            System.exit(0);
        } catch (TwitterException te) {
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit( -1);
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
