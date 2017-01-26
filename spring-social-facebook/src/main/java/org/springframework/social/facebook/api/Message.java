package org.springframework.social.facebook.api;

import java.util.Date;
import java.util.List;

/**
 * Model class representing a message of page conversation.
 *
 * @author Gurkan Vural
 */
public class Message extends FacebookObject {
    private String id;
    private Date createdTime;
    private Reference from;
    private String message;
    private String subject;
    private List<Reference> to;

    public String getId() {
        return id;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public Reference getFrom() {
        return from;
    }

    public String getMessage() {
        return message;
    }

    public String getSubject() {
        return subject;
    }

    public List<Reference> getTo() {
        return to;
    }
}
