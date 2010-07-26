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
public class Comment extends TwitterResponse implements java.io.Serializable {

    private Date createdAt;
    private long id;
    private String text;
    private String source;
    private boolean isTruncated;
    private boolean isFavorited;    
 
    private static final long serialVersionUID = 1608000492860584608L;

    /*package*/Comment(Response res, Twitter twitter) throws TwitterException {
        super(res);
        Element elem = res.asDocument().getDocumentElement();
        init(res, elem, twitter);
    }

    /*package*/Comment(Response res, Element elem, Twitter twitter) throws
            TwitterException {
        super(res);
        init(res, elem, twitter);
    }

    public Comment(String str) throws TwitterException, JSONException {
        // StatusStream uses this constructor
        super();
        JSONObject json = new JSONObject(str);
        id = json.getLong("id");
        text = json.getString("text");
        source = json.getString("source");
        createdAt = parseDate(json.getString("created_at"), "EEE MMM dd HH:mm:ss z yyyy");

        isFavorited = getBoolean("favorited", json);
        user = new User(json.getJSONObject("user"));
        if (!json.isNull("status")) {
            JSONObject status = json.getJSONObject("status");
        }
        
        if (!json.isNull("reply_comment")) {
            JSONObject reply_comments = json.getJSONObject("reply_comment");
        }
    }

    private void init(Response res, Element elem, Twitter twitter) throws
            TwitterException {
        ensureRootNodeNameIs("comment", elem);
        user = new User(res, (Element) elem.getElementsByTagName("user").item(0)
                , twitter);
        id = getChildLong("id", elem);
        text = getChildText("text", elem);
        source = getChildText("source", elem);
        createdAt = getChildDate("created_at", elem);
        isTruncated = getChildBoolean("truncated", elem);

        isFavorited = getChildBoolean("favorited", elem);
       
        NodeList statuses = elem.getElementsByTagName("status");
        if (statuses.getLength() != 0) {
            Element status = (Element) statuses.item(0);
        }
        
        NodeList reply_comments = elem.getElementsByTagName("reply_comment");
        if (reply_comments.getLength() != 0) {
            Element reply_comment = (Element) reply_comments.item(0);
        }
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
    
    private Status status = null;
    
   
    
    public Status getStatus()
    {
    	return status;
    }
    
    private Comment reply_comment;
    
    public Comment getReplyComment()
    {
    	return reply_comment;
    }

    /*package*/
    static List<Comment> constructComments(Response res,
                                          Twitter twitter) throws TwitterException {
        Document doc = res.asDocument();
        if (isRootNodeNilClasses(doc)) {
            return new ArrayList<Comment>(0);
        } else {
            try {
                ensureRootNodeNameIs("comments", doc);
                NodeList list = doc.getDocumentElement().getElementsByTagName(
                        "comment");
                int size = list.getLength();
                List<Comment> statuses = new ArrayList<Comment>(size);
                for (int i = 0; i < size; i++) {
                    Element status = (Element) list.item(i);
                    statuses.add(new Comment(res, status, twitter));
                }
                return statuses;
            } catch (TwitterException te) {
                ensureRootNodeNameIs("nil-classes", doc);
                return new ArrayList<Comment>(0);
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
        return obj instanceof Comment && ((Comment) obj).id == this.id;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "createdAt=" + createdAt +
                ", id=" + id +
                ", text='" + text + '\'' +
                ", source='" + source + '\'' +
                ", isTruncated=" + isTruncated +
                ", isFavorited=" + isFavorited +
                ", user=" + user +
                ", status=" + status + 
                ", reply_comment=" + reply_comment + 
                '}';
    }
}
