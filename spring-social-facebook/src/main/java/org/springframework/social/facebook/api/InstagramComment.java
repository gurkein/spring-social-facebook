package org.springframework.social.facebook.api;

import java.util.Date;

public class InstagramComment extends FacebookObject {

    private String id;

    private boolean hidden;

    private InstagramMedia media;

    private String text;

    private Date timestamp;

    private InstagramUser user;

    private String username;

    private long likeCount;

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

    public InstagramUser getUser() {
        return user;
    }

    public String getUsername() {
        return username;
    }

    public long getLikeCount() {
        return likeCount;
    }
}
