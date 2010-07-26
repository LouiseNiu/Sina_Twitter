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
public class TList extends TwitterResponse implements java.io.Serializable {

    private long id;
    private String name;
    private String full_name;
    private String slug;
    private String description;
    private long subscriber_count;
    private long member_count;
    private String uri;
    private String mode;    
    private static final long serialVersionUID = 1608000492860584608L;

    /*package*/TList(Response res, Twitter twitter) throws TwitterException {
        super(res);
        Element elem = res.asDocument().getDocumentElement();
        init(res, elem, twitter);
    }

    /*package*/TList(Response res, Element elem, Twitter twitter) throws
            TwitterException {
        super(res);
        init(res, elem, twitter);
    }

    public TList(String str) throws TwitterException, JSONException {
        // StatusStream uses this constructor
        super();
        JSONObject json = new JSONObject(str);
        id = json.getLong("id");
        name = json.getString("name");
        full_name = json.getString("full_name");
        slug = json.getString("slug");
        description = json.getString("description");
        subscriber_count = getLong("in_reply_to_status_id", json);
        member_count = getInt("in_reply_to_user_id", json);
        uri = json.getString("uri");
        mode = json.getString("mode");
        
        user = new User(json.getJSONObject("user"));
        
    }

    private void init(Response res, Element elem, Twitter twitter) throws
            TwitterException {
        ensureRootNodeNameIs("list", elem);
        user = new User(res, (Element) elem.getElementsByTagName("user").item(0)
                , twitter);
        id = getChildLong("id", elem);
        name = getChildText("name", elem);
        full_name = getChildText("full_name", elem);
        slug = getChildText("slug", elem);
        description = getChildText("description", elem);
      
        subscriber_count = getChildLong("subscriber_count", elem);
        member_count = getChildInt("menber_count", elem);
        
        uri = getChildText("uri", elem);
        mode = getChildText("mode", elem);
        
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
    public String getName() {
        return this.name;
    }

    /**
     * Returns the full_name
     *
     * @return the full name
     * @since Twitter4J 1.0.4
     */
    public String getFullName() {
        return this.full_name;
    }

    /**
     * Returns the subscriber_count
     *
     * @return the subscriber_count
     * @since Twitter4J 1.0.4
     */
    public long getSubscriberCount() {
        return subscriber_count;
    }

    /**
     * Returns the member_count
     *
     * @return the member_count
     * @since Twitter4J 1.0.4
     */
    public long getMemberCount() {
        return member_count;
    }

    /**
     * Returns the description
     *
     * @return the description
     * @since Twitter4J 2.0.4
     */
    public String getDescription() {
        return description;
    }

    /**
     * returns slug.
     *
     * @since Twitter4J 2.0.10
     */
    public String getSlug() {
        return slug;
    }

    /**
     * returns The location's longitude that this tweet refers to.
     *
     * @since Twitter4J 2.0.10
     */
    public String getMode() {
        return mode;
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
    
    /*package*/
    static List<TList> constructTLists(Response res,
                                          Twitter twitter) throws TwitterException {
        Document doc = res.asDocument();
        if (isRootNodeNilClasses(doc)) {
            return new ArrayList<TList>(0);
        } else {
            try {
                ensureRootNodeNameIs("lists_list", doc);
                NodeList list = doc.getDocumentElement().getElementsByTagName(
                        "list");
                int size = list.getLength();
                List<TList> statuses = new ArrayList<TList>(size);
                for (int i = 0; i < size; i++) {
                    Element status = (Element) list.item(i);
                    statuses.add(new TList(res, status, twitter));
                }
                return statuses;
            } catch (TwitterException te) {
                ensureRootNodeNameIs("nil-classes", doc);
                return new ArrayList<TList>(0);
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
        return obj instanceof TList && ((TList) obj).id == this.id;
    }

    @Override
    public String toString() {
        return "List{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", full_name='" + full_name + '\'' +
                ", slug='" + slug +
                ", description='" + description +
                ", subscriber_count=" + subscriber_count +
                ", member_count=" + member_count +
                ", uri='" + uri + '\'' +
                ", mode='" + mode +
                ", user=" + user +
                '}';
    }
}
