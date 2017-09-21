package org.springframework.social.facebook.api;

import java.util.Date;

public class InstagramComment extends FacebookObject {

    private String id;

    private boolean hidden;

    private InstagramMedia media;

    private String text;

    private Date timestamp;

    private InstagramBusinessAccount user;

    public String getId() {
        return id;
    }

    public boolean isHidden() {
        return hidden;
    }

    public InstagramMedia getMedia() {
        return media;
    }

    public String getText() {
        return text;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public InstagramBusinessAccount getUser() {
        return user;
    }
}
