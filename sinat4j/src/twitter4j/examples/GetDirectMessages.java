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

import twitter4j.DirectMessage;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.io.IOException;
import java.util.List;

/**
 * Example application that gets recent direct messages from specified account.<br>
 * Usage: java twitter4j.examples.GetDirectMessages ID Password
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public class GetDirectMessages {
    /**
     * Usage: java twitter4j.examples.GetDirectMessages ID Password
     * @param args String[]
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("No TwitterID/Password specified.");
            System.out.println(
                "Usage: java twitter4j.examples.GetDirectMessages ID Password");
           // System.exit( -1);
        }
        Twitter twitter = new Twitter("tearblue78@gmail.com", "springqi");
        try {
            List<DirectMessage> messages = twitter.getDirectMessages();
            for (DirectMessage message : messages) {
                System.out.println("Sender:" + message.getSenderScreenName());
                System.out.println("Text:" + message.getText() + "\n");
                System.out.println("user sender name:" + message.getSender().getName() + "\n");
            }
            System.exit(0);
        } catch (TwitterException te) {
            System.out.println("Failed to get messages: " + te.getMessage());
            System.exit( -1);
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
