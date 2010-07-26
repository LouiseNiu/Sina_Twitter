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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.http.ImageItem;


/**
 * Example application that uses OAuth method to acquire access to your account.<br>
 * This application illustrates how to use OAuth method with Twitter4J.<br>
 * Usage: java -Dtwitter4j.oauth.consumerKey=[consumer key] -Dtwitter4j.oauth.consumerSecret=[consumer secret] twitter4j.examples.OAuthUpdate [message]
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public class OAuthUpdateTwo {
    /**
     * Usage: java -Dtwitter4j.oauth.consumerKey=[consumer key] -Dtwitter4j.oauth.consumerSecret=[consumer secret] twitter4j.examples.OAuthUpdate [message]
     * @param args message
     */
    public static void main(String[] args) {
        try {
        //	System.setProperty("twitter4j.oauth.consumerKey", Twitter.CONSUMER_KEY);
        //	System.setProperty("twitter4j.oauth.consumerSecret", Twitter.CONSUMER_SECRET);
        	
            Twitter twitter = new Twitter();
            
            twitter.setToken("19b4e7c5a55ce0665100f32be0a366dc", "5e3dec9a5920be3ac774dc4b70326cdc");
            
           // Status status = twitter.updateStatus("test again~~");
            
          //  System.out.println("input :");  
		  //  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		  //  String filename = br.readLine();
		 //	byte[] content=readFileImage(filename);
		 //	System.out.println("content length:"+content.length);
		 	ImageItem pic=new ImageItem("D:/printservice.JPG");
			int result = twitter.uploadStatus("abctesttesttest",pic);
            
            System.out.println("Successfully updated the status to [" + result + "].");
            System.exit(0);
        } catch (TwitterException te) {
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit( -1);
        } catch (Exception ioe) {
            System.out.println("Failed to read the system input.");
            System.exit( -1);
        }
    }
    
    public static byte[] readFileImage(String filename) throws IOException {
        BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream(filename));
        int len=bufferedInputStream.available();
        byte[] bytes=new byte[len];
        int r=bufferedInputStream.read(bytes);
        if(len!=r)
        {
          bytes=null;
          throw new IOException("testtest");
        }
        bufferedInputStream.close();
        return bytes;
    }
}
