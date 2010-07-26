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
import twitter4j.Configuration;
import twitter4j.Counts;
import twitter4j.DirectMessage;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.http.AccessToken;
import twitter4j.http.AuthorizeToken;
import twitter4j.http.RequestToken;
/*
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Example application that uses OAuth method to acquire access to your account.<br>
 * This application illustrates how to use OAuth method with Twitter4J.<br>
 * Usage: java -Dtwitter4j.oauth.consumerKey=[consumer key] -Dtwitter4j.oauth.consumerSecret=[consumer secret] twitter4j.examples.OAuthUpdate [message]
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public class OAuthUpdate {
	public static boolean bForSina = true;
	
	public static String  SinaBaseURL = "http://api.t.sina.com.cn:80/";		
	public static String  TwtBaseURL = "http://api.twitter.com/";
	//public static String  TwtBaseURL = "http://64.62.216.123/s/trunk/";

	public static final String SINA_CONSUMER_KEY = "666117474";
	public static final String SINA_CONSUMER_SECRET = "dc62617f011611dd27080cd4697bc490";
	
	public static final String TWT_CONSUMER_KEY = "sYGnpZd5aa5iTlpCDIjA";
	public static final String TWT_CONSUMER_SECRET = "XMpC5oOEmViCaaXBLWUrgfGX5iKHF53OH4k1h4Ip7E4";
	
    
    public static final String SINA_REUEST_URL = "http://api.t.sina.com.cn:80/oauth/request_token";
    public static final String SINA_AUTHORIZE_URL = "http://api.t.sina.com.cn:80/oauth/authorize";
    public static final String SINA_ACCESS_URL = "http://api.t.sina.com.cn:80/oauth/access_token";
          
    public static final String TWT_REUEST_URL = "http://api.twitter.com/oauth/request_token";
    public static final String TWT_AUTHORIZE_URL = "http://api.twitter.com/oauth/authorize";
    public static final String TWT_ACCESS_URL = "http://api.twitter.com/oauth/access_token";
     
    /**
     * Usage: java -Dtwitter4j.oauth.consumerKey=[consumer key] -Dtwitter4j.oauth.consumerSecret=[consumer secret] twitter4j.examples.OAuthUpdate [message]
     * @param args message
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        try {
        	 Twitter twitter = new Twitter();
        	/*
        	Configuration.getOAuthConsumerKey(TWT_CONSUMER_KEY);
        	Configuration.getOAuthConsumerSecret(TWT_CONSUMER_SECRET);
        	Configuration.getSource(TWT_CONSUMER_KEY);
        	Configuration.getOAuthRequestUrl(TWT_REUEST_URL);
        	Configuration.getOAuthAuthorizeUrl(TWT_AUTHORIZE_URL);
        	Configuration.getOAuthAccessUrl(TWT_ACCESS_URL);
        	*/
        	if(bForSina){
        		twitter.setOAuthConsumer(SINA_CONSUMER_KEY, SINA_CONSUMER_SECRET);
    			twitter.setSource(SINA_CONSUMER_KEY);
        		//System.setProperty("twitter4j.oauth.consumerKey", SINA_CONSUMER_KEY);
        		//System.setProperty("twitter4j.oauth.consumerSecret", SINA_CONSUMER_SECRET);
            	//System.setProperty("twitter4j.source", SINA_CONSUMER_KEY);
            	System.setProperty("twitter4j.oauth.requesturl", SINA_REUEST_URL);
            	System.setProperty("twitter4j.oauth.authorizeurl", SINA_AUTHORIZE_URL);
            	System.setProperty("twitter4j.oauth.accessurl", SINA_ACCESS_URL);
        		}
        	else{
        		System.setProperty("twitter4j.oauth.consumerKey", TWT_CONSUMER_KEY);
        		System.setProperty("twitter4j.oauth.consumerSecret", TWT_CONSUMER_SECRET);
            	System.setProperty("twitter4j.source", "");
            	System.setProperty("twitter4j.oauth.requesturl", TWT_REUEST_URL);
            	System.setProperty("twitter4j.oauth.authorizeurl", TWT_AUTHORIZE_URL);
            	System.setProperty("twitter4j.oauth.accessurl", TWT_ACCESS_URL);
        	}
        	
        	
           // Twitter twitter = new Twitter();
            
            if(bForSina)
            	twitter.setBaseURL(SinaBaseURL);
            else
            {
            	twitter.setBaseURL(TwtBaseURL);            
            	twitter.setHttpProxy("10.85.40.139", 8000);
            }
            if(bForSina)
        	{
            	/*
            	 RequestToken requestToken = twitter.getOAuthRequestToken();
                 System.out.println("Got request token.");
                 System.out.println("Request token: "+ requestToken.getToken());
                 System.out.println("Request token secret: "+ requestToken.getTokenSecret());
                 //get authorized tocken
                 AuthorizeToken authorizedToken = twitter.getAuthorizedToken("kelly.m.shi@gmail.com","Bluesky2001");
                 //get access tocken
                 String pin = authorizedToken.getVerify();
                 //System.out.println("Got token:" + authorizedToken.getToken());
                 System.out.println("Got pin:" + pin);
                 AccessToken accessToken = requestToken.getAccessToken(pin);
                 twitter.setOAuthAccessToken(accessToken.getToken(), accessToken.getTokenSecret());
            	
            	/*
            RequestToken requestToken = twitter.getOAuthRequestToken();
            System.out.println("Got request token.");
            System.out.println("Request token: "+ requestToken.getToken());
            System.out.println("Request token secret: "+ requestToken.getTokenSecret());
            AccessToken accessToken = null;
           

            String authUrl = requestToken.getAuthorizationURL();
            
            login(authUrl, "qisda", "9822@7023v");	//Jacky
            
            String pin = null;
            
             accessToken = requestToken.getAccessToken(pin);
             
           
            	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            	
	            while (null == accessToken) {
	                System.out.println("Open the following URL and grant access to your account:");
	                System.out.println(requestToken.getAuthorizationURL());
	                System.out.print("Hit enter when it's done.[Enter]:");
	                
	                String pin = br.readLine();
	                System.out.println("pin: " + br.toString());
	                try{
	                    accessToken = requestToken.getAccessToken(pin);
	                } catch (TwitterException te) {
	                    if(401 == te.getStatusCode()){
	                        System.out.println("Unable to get the access token.");
	                    }else{
	                        te.printStackTrace();
	                    }
	                }
	            }
            
            System.out.println("Got access token.");
            System.out.println("Access token: "+ accessToken.getToken());
            System.out.println("Access token secret: "+ accessToken.getTokenSecret());
           */
            	twitter.setOAuthAccessToken("8ec8d6275a8069f56f7ba5cd343f45d0", "c9ab1eaf02ea636ca6d8ab9eb9cfa0a9");
            }else            	
            	twitter.setOAuthAccessToken("72226595-eN3DlcrXZIO9yGlNgzl4s5dR82G9h4A0wyNHyMKgc", "hwrFlltQ6leiB7xdsdgwymA5NmpiHoRxthsvAFWfA");
            
            int count = 10;
            int page = 1;
            int i =1;
            String ids = null;
            
          // do  {
            Paging paging= new Paging();
            paging.setCount(count);   
            List<Status> statuses = twitter.getHomeTimeline(paging);
            for (Status status : statuses) {
               System.out.println(status.getUser().getName() + ":" +status.getUser().getId() + ":" +
                                   status.getText());
                System.out.println("has retweeted status ?" + ":" +
                		status.isHasRetweet());
                 if(status.isHasRetweet())
                	 System.out.println("retweet text£º" + ":" +
                               		status.getRetweetStatus().getText());              
                               
            }
          /*
            List<User> list= twitter.getFriendsStatuses(); 
            int size = list.size();
            System.out.println("friend size is : " + size);
            for (User friend : list){
            	 System.out.println(friend.getName()+ ":" +friend.getId());
                 
            	
            }
           */ 
            DirectMessage message = twitter.sendDirectMessage("1761000590", "test direct message ~~");
            
            
            	   
              //  {
                	//Status sts = twitter.destroyFavorite(Long.parseLong("15948639376"));
                	//Status sts = twitter.createFavorite(Long.parseLong("35435312"));
              //  }
                
           // }
            
            if(bForSina){  
                /* 	
                 List<Counts> counts = twitter.getStatusCounts("35435312,");
                 for (int len =0; len < counts.size(); len ++ ){
                 	System.out.println("status has "  +
                     		counts.get(len).getCommentsCount() + " comments!!");
                 }*/
                 
                  /*
                     List<Counts> counts = twitter.getStatusCounts("35435312,654435292,662160190");
                     for (int len =0; len < counts.size(); len ++ ){
                     	System.out.println("status has "  +
                         		counts.get(len).getCommentsCount() + " comments!!");
                     }
                 */
            	//Status sts = twitter.retweetStatus(Long.parseLong("679553486"));
            	
                 }
            User me = twitter.verifyCredentials();		
            System.out.println("my uid is: "+ String.valueOf(me.getId()));
            
            /*
            try {
				Status status = twitter.updateStatus("test test test 11fdsfasdf11");
				System.out.println("Successfully updated the status to [" + status.getText() + "].");
				
				if(bForSina){
				  List<Comment> comentes = twitter.getComments(String.valueOf(status.getId()));
	                for (Comment com : comentes) {
	                	System.out.println(com.getUser().getName() + " comment:" +
	                            com.getText());
	                }
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
            try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 try {
					Status status = twitter.updateStatus("fsdfsafdasdfsafs");
					System.out.println("Successfully updated the status to [" + status.getText() + "].");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            System.exit(0);
             */
        } catch (TwitterException te) {
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit( -1);
        }/* catch (IOException ioe) {
            System.out.println("Failed to read the system input.");
            System.exit( -1);
        }*/
       
    }
    
    /*
    private static boolean login(String authUrl, String username, String password) throws ClientProtocolException, IOException {	//Jacky
        HttpResponse response;
        HttpEntity entity;
        HttpClient client = new DefaultHttpClient();
        String htmlStr = null;
		
        //get auth html
		HttpGet httpget = new HttpGet(authUrl);
		response = client.execute(httpget);
		entity = response.getEntity();
		if(entity==null) return false;
		htmlStr = EntityUtils.toString(entity);
		
		//Log.e("jakky1", "=== now print auth html content from: " + authUrl);
		//Log.i("jakky1", "htmlStr = " + htmlStr);
		
		String form_str = subStringByBound(htmlStr, "<form action=", "</form>");
		if(form_str==null) return false;
		//Log.e("jakky1", "=== now print form content");
		//Log.i("jakky1", "form_str = " + form_str);
		entity.consumeContent();
		
		List<NameValuePair> nvp = getNameValuePairsInForm(form_str);
		removePairByKey(nvp, "session[username_or_email]");
		removePairByKey(nvp, "session[password]");
		removePairByKey(nvp, "cancel");
		nvp.add( new BasicNameValuePair("session[username_or_email]", username) );
		nvp.add( new BasicNameValuePair("session[password]", password) );
		for(NameValuePair p : nvp) { 
			System.out.println("nvp: name=" + p.getName() + ", value=" + p.getValue());
			}
		
		//post username & password
		HttpPost httppost = new HttpPost("http://api.twitter.com/oauth/authorize");
        httppost.setEntity(new UrlEncodedFormEntity(nvp, HTTP.UTF_8));
        httppost.addHeader("Referer", authUrl);
        httppost.removeHeaders("Expect");
        httppost.getParams().setBooleanParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE,false);
        response = client.execute(httppost);
        entity = response.getEntity();
        if(entity==null) return false;
		htmlStr = EntityUtils.toString(entity);

		System.out.println("=== now print login html content");
		System.out.println("htmlStr = " + htmlStr);
		
		String loginDoneURL = subStringByBound(htmlStr, "yourapp://twitt?", "\">");
		if(loginDoneURL==null) return false;
		System.out.println("loginDoneURL = " + loginDoneURL);		
		entity.consumeContent();
		
		return true;
	}
	
	private static final List<NameValuePair> getNameValuePairsInForm(String form_string) {
		List<NameValuePair> nvp = new ArrayList<NameValuePair>();
		int start = 0, end = -1;
		while(true) {
			start = end + 1;
			start = form_string.indexOf("<input", start);
			if(start==-1) break;
			end = form_string.indexOf(">", start+1);
			
			String input_string = form_string.substring(start, end);
			System.out.println( "input ==> " + input_string);
			
			String name = subStringByBound(input_string, "name=\"", "\"");
			if(name==null) continue;
			name = name.substring("name=\"".length());

			String value = subStringByBound(input_string, "value=\"", "\"");
			if(value==null) continue;
			value = value.substring("value=\"".length());
			System.out.println(String.format("<%s> : <%s>", name, value));			
			if(value.length() > 0) {
				nvp.add(new BasicNameValuePair(name, value));
			}
			
		}
		return nvp;
	}
	
	private static boolean removePairByKey(List<NameValuePair> nvp, String key) {
		Iterator<NameValuePair> it = nvp.iterator();
		do {
			NameValuePair p = it.next();
			if(p.getName().equals(key)) {
				it.remove();
				return true;
			}
		} while(it.hasNext());
		return false;
	}
	
	private static final String subStringByBound(String src, String prefix, String postfix) {
		int start = src.indexOf(prefix);
		if(start < 0) return null;
		int end = src.indexOf(postfix, start + prefix.length());
		if(end < 0) return null;
		return src.substring(start, end);
	}
	//Jacky }
	
	 */

}
