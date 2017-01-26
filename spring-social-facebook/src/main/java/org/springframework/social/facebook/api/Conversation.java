package org.springframework.social.facebook.api;

import java.util.Date;
import java.util.List;

/**
 * Model class representing a page conversation.
 * @author Gurkan Vural
 */
public class Conversation extends FacebookObject {
    private String id;
    private String snippet;
    private Date updatedTime;
    private int messageCount;
    private int unreadCount;
    private List<Reference> participants;
    private List<Reference> senders;
    private boolean canReply;
    private boolean isSubscribed;
    private String link;

    public String getId() {
        return id;
    }

    public String getSnippet() {
        return snippet;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public List<Reference> getParticipants() {
        return participants;
    }

    public List<Reference> getSenders() {
        return senders;
    }

    public boolean isCanReply() {
        return canReply;
    }

    public boolean isSubscribed() {
        return isSubscribed;
    }

    public String getLink() {
        return link;
    }
}
