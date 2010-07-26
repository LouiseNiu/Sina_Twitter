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
public class Counts extends TwitterResponse implements java.io.Serializable {

    private long id;
    private int cmtCount; //count of Comments
    private int rtCount; //count of Retweets
    private int mentnCount; //count of Mentions
    private int dmCount; //count of direct messages
    private int flwerCount; //count of Followers
 
    private static final long serialVersionUID = 1608000492860584608L;

    public Counts(Response res, Twitter twitter) throws TwitterException {
        super(res);
        Element elem = res.asDocument().getDocumentElement();
        init(res, elem, twitter);
    }

    /*package*/Counts(Response res, Element elem, Twitter twitter) throws
            TwitterException {
        super(res);
        init(res, elem, twitter);
    }

    public Counts(String str) throws TwitterException, JSONException {
        // StatusStream uses this constructor
        super();
        JSONObject json = new JSONObject(str);
        id = json.getLong("id");
        cmtCount = json.getInt("comments");
        rtCount = json.getInt("rt");
        mentnCount = json.getInt("mentions");
        dmCount = json.getInt("dm");
        flwerCount = json.getInt("followers");  
    }

    private void init(Response res, Element elem, Twitter twitter) throws
            TwitterException {
        ensureRootNodeNameIs("count", elem);      
        id = getChildLong("id", elem);
        cmtCount = getChildInt("comments", elem);
        rtCount = getChildInt("rt", elem); 
        mentnCount = getChildInt("mentions", elem);
        dmCount = getChildInt("dm", elem); 
        flwerCount = getChildInt("followers", elem);   
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
     * Returns the name
     *
     * @return the name
     */
    public int getCommentsCount() {
        return cmtCount;
    }

    /**
     * Returns the full_name
     *
     * @return the full name
     * @since Twitter4J 1.0.4
     */
    public int getRetweetsCount() {
        return rtCount;
    }
    /**
     * Returns the full_name
     *
     * @return the full name
     * @since Twitter4J 1.0.4
     */
    public int getMentionsCount() {
        return mentnCount;
    }
    /**
     * Returns the full_name
     *
     * @return the full name
     * @since Twitter4J 1.0.4
     */
    public int getDMCount() {
        return dmCount;
    }
    /**
     * Returns the full_name
     *
     * @return the full name
     * @since Twitter4J 1.0.4
     */
    public int getFollowersCount() {
        return flwerCount;
    }
       
    /*package*/
    static List<Counts> constructCounts(Response res,
                                          Twitter twitter) throws TwitterException {
        Document doc = res.asDocument();
        if (isRootNodeNilClasses(doc)) {
            return new ArrayList<Counts>(0);
        } else {
            try {
               // ensureRootNodeNameIs("counts", doc);
                NodeList list = doc.getDocumentElement().getElementsByTagName(
                        "count");
                int size = list.getLength();
                List<Counts> counts = new ArrayList<Counts>(size);
                for (int i = 0; i < size; i++) {
                    Element count = (Element) list.item(i);
                    counts.add(new Counts(res, count, twitter));
                }
                return counts;
            } catch (TwitterException te) {
                ensureRootNodeNameIs("nil-classes", doc);
                return new ArrayList<Counts>(0);
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
        return obj instanceof Counts && ((Counts) obj).id == this.id;
    }

    @Override
    public String toString() {
        return "Counts{" +
                "id=" + id +
                ", comments='" + cmtCount + '\'' +
                ", rt='" + rtCount + '\'' +
                ", mentions='" + mentnCount + '\'' +
                ", dm='" + dmCount + '\'' +
                ", followers='" + flwerCount +                 
                '}';
    }
}
