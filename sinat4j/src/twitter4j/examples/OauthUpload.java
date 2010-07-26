package twitter4j.examples;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.http.ImageItem;

public class OauthUpload {
    /**
     * Usage: java -Dtwitter4j.oauth.consumerKey=[consumer key] -Dtwitter4j.oauth.consumerSecret=[consumer secret] twitter4j.examples.OAuthUpload [accessToken] [accessSecret] [imageFilePath]
     * @param args message
     */
    public static void main(String[] args) {
        try {
        //	System.setProperty("twitter4j.oauth.consumerKey", Twitter.CONSUMER_KEY);
        //	System.setProperty("twitter4j.oauth.consumerSecret", Twitter.CONSUMER_SECRET);
        	
        	     	
        	
            Twitter twitter = new Twitter("tearblue78@gmail.com", "springqi");
            
           // twitter.setToken(args[0], args[1]);
  
			 try {
				    System.out.println("input :");  
				    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				    String filename = br.readLine();
				 	//byte[] content=readFileImage(filename);
				 	//System.out.println("content length:"+content.length);
				 	ImageItem pic=new ImageItem(filename);
					int result = twitter.uploadStatus("abc",pic);
					
					System.out.println("Successfully upload the status to [" + result + "].");
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