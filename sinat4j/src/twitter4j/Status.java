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
package twitter4j;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import twitter4j.http.Response;
import twitter4j.org.json.JSONObject;
import twitter4j.org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.lang.*;

/**
 * A data class representing one single status of a user.
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public class Status extends TwitterResponse implements java.io.Serializable {

    private Date createdAt;
    private long id;
    private String text;
    private String source;
    private boolean isTruncated;
    private long inReplyToStatusId;
    private int inReplyToUserId;
    private boolean isFavorited;
    private String inReplyToScreenName;
    private double latitude = -1;
    private double longitude = -1;  
    private String  thumbnail_pic = null;//louise add.
    private String  bmiddle_pic = null;//louise add.
    private String  original_pic = null;//louise add.
    
    private boolean isRetweet = false;
    
    private RetweetDetails retweetDetails;
    private static final long serialVersionUID = 1608000492860584608L;

    /*package*/Status(Response res, Twitter twitter) throws TwitterException {
        super(res);
        Element elem = res.asDocument().getDocumentElement();
        init(res, elem, twitter);
    }

    /*package*/Status(Response res, Element elem, Twitter twitter) throws
            TwitterException {
        super(res);
        init(res, elem, twitter);
    }

    public Status(String str) throws TwitterException, JSONException {
        // StatusStream uses this constructor
        super();    	
        JSONObject json = new JSONObject(str);
        id = json.getLong("id");
        text = json.getString("text");
        source = json.getString("source");
        createdAt = parseDate(json.getString("created_at"), "EEE MMM dd HH:mm:ss z yyyy");

        inReplyToStatusId = getLong("in_reply_to_status_id", json);
        inReplyToUserId = getInt("in_reply_to_user_id", json);
        isFavorited = getBoolean("favorited", json);
        user = new User(json.getJSONObject("user"));
        
        if (!json.isNull("retweeted_status"))
        	isRetweet = true;
        
        retweetStatus = new RetweetStatus(json.getJSONObject("retweeted_status"));
        
        /*Louise add */
        thumbnail_pic = json.getString("thumbnail_pic");
        bmiddle_pic = json.getString("bmiddle_pic");
        original_pic = json.getString("original_pic");
        
    }

    private void init(Response res, Element elem, Twitter twitter) throws
            TwitterException {
        ensureRootNodeNameIs("status", elem);
        user = new User(res, (Element) elem.getElementsByTagName("user").item(0)
                , twitter);
        
        if((Element) elem.getElementsByTagName("retweeted_status").item(0) != null)        	
        	isRetweet = true;
        
     
        if(isRetweet)
        	retweetStatus = new RetweetStatus(res, (Element) elem.getElementsByTagName("retweeted_status").item(0)
                , twitter);
        /*        
        if(retweetStatus != null)
        	isRetweet = true;
       */ 
        id = getChildLong("id", elem);
        text = getChildText("text", elem);
        source = getChildText("source", elem);
        createdAt = getChildDate("created_at", elem);
        isTruncated = getChildBoolean("truncated", elem);
        inReplyToStatusId = getChildLong("in_reply_to_status_id", elem);
        inReplyToUserId = getChildInt("in_reply_to_user_id", elem);
        isFavorited = getChildBoolean("favorited", elem);
        inReplyToScreenName = getChildText("in_reply_to_screen_name", elem);
        NodeList georssPoint = elem.getElementsByTagName("georss:point");
        if(1 == georssPoint.getLength()){
            String[] point = georssPoint.item(0).getFirstChild().getNodeValue().split(" ");
           // System.out.println("geo point:" + point[0]+ " " + point[1]);
            if(point[0]!= null)
            	latitude = Double.parseDouble(point[0]);
            if(point[1].equals("null"))
            {//louise add. for geo point (0,null)
            	// System.out.println("geo point[1]:" + point[1]);
            }
            else
            	longitude = Double.parseDouble(point[1]);
        }
        NodeList retweetDetailsNode = elem.getElementsByTagName("retweet_details");
        if(1 == retweetDetailsNode.getLength()){
            retweetDetails = new RetweetDetails(res,(Element)retweetDetailsNode.item(0),twitter);
        }
    
        /*Louise add */
        thumbnail_pic = getChildText("thumbnail_pic", elem);
        bmiddle_pic = getChildText("bmiddle_pic", elem);
        original_pic = getChildText("original_pic", elem);
    }

    /**
     * Return the created_at
     *
     * @return created_at
     * @since Twitter4J 1.1.0
     */

    public Date getCreatedAt() {
        return this.createdAt;
    }

    /**
     * Returns the id of the status
     *
     * @return the id
     */
    public long getId() {
        return this.id;
    }

    /**
     * Returns the text of the status
     *
     * @return the text
     */
    public String getText() {
        return this.text;
    }

    /**
     * Returns the source
     *
     * @return the source
     * @since Twitter4J 1.0.4
     */
    public String getSource() {
        return this.source;
    }


    /**
     * Test if the status is truncated
     *
     * @return true if truncated
     * @since Twitter4J 1.0.4
     */
    public boolean isTruncated() {
        return isTruncated;
    }

    /**
     * Returns the in_reply_tostatus_id
     *
     * @return the in_reply_tostatus_id
     * @since Twitter4J 1.0.4
     */
    public long getInReplyToStatusId() {
        return inReplyToStatusId;
    }

    /**
     * Returns the in_reply_user_id
     *
     * @return the in_reply_tostatus_id
     * @since Twitter4J 1.0.4
     */
    public int getInReplyToUserId() {
        return inReplyToUserId;
    }

    /**
     * Returns the in_reply_to_screen_name
     *
     * @return the in_in_reply_to_screen_name
     * @since Twitter4J 2.0.4
     */
    public String getInReplyToScreenName() {
        return inReplyToScreenName;
    }

    /**
     * returns The location's latitude that this tweet refers to.
     *
     * @since Twitter4J 2.0.10
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * returns The location's longitude that this tweet refers to.
     *
     * @since Twitter4J 2.0.10
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Test if the status is favorited
     *
     * @return true if favorited
     * @since Twitter4J 1.0.4
     */
    public boolean isFavorited() {
        return isFavorited;
    }


    private User user = null;

    /**
     * Return the user
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }
    

    private RetweetStatus retweetStatus = null;

    /**
     * Return the user
     *
     * @return the user
     */
    public RetweetStatus getRetweetStatus() {
        return retweetStatus;
    }
    
    /**
    *
    * @since Twitter4J 2.0.10
    */
   public boolean isHasRetweet(){
       return isRetweet;
   }


    /**
     *
     * @since Twitter4J 2.0.10
     */
    public boolean isRetweet(){
        return null != retweetDetails;
    }

    /**
     *
     * @since Twitter4J 2.0.10
     */
    public RetweetDetails getRetweetDetails() {
        return retweetDetails;
    }
    
    
    public String getThumbnialPic() {
        return this.thumbnail_pic;
    }
   
    public String getBMPic() {
        return this.bmiddle_pic;
    }
    
    public String getOriginalPic() {
        return this.original_pic;
    }
    /*package*/
    static List<Status> constructStatuses(Response res,
                                          Twitter twitter) throws TwitterException {
        Document doc = res.asDocument();
        if (isRootNodeNilClasses(doc)) {
            return new ArrayList<Status>(0);
        } else {
            try {
                ensureRootNodeNameIs("statuses", doc);
                NodeList list = doc.getDocumentElement().getElementsByTagName(
                        "status");
                int size = list.getLength();
                List<Status> statuses = new ArrayList<Status>(size);
                for (int i = 0; i < size; i++) {
                    Element status = (Element) list.item(i);
                    statuses.add(new Status(res, status, twitter));
                }
                return statuses;
            } catch (TwitterException te) {
                ensureRootNodeNameIs("nil-classes", doc);
                return new ArrayList<Status>(0);
            }
        }
    }

    @Override
    public int hashCode() {
        return (int) id;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return obj instanceof Status && ((Status) obj).id == this.id;
    }

    @Override
    public String toString() {
        return "Status{" +
                "createdAt=" + createdAt +
                ", id=" + id +
                ", text='" + text + '\'' +
                ", source='" + source + '\'' +
                ", isTruncated=" + isTruncated +
                ", inReplyToStatusId=" + inReplyToStatusId +
                ", inReplyToUserId=" + inReplyToUserId +
                ", isFavorited=" + isFavorited +
                ", inReplyToScreenName='" + inReplyToScreenName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", retweetDetails=" + retweetDetails +
                ", thumbnail_pic=" + thumbnail_pic + '\''+
                ", bmiddle_pic=" + bmiddle_pic + '\''+
                ", original_pic=" + original_pic + '\''+
                ", user=" + user +
                ", retweeted_status=" + retweetStatus +
                '}';
    }
}
