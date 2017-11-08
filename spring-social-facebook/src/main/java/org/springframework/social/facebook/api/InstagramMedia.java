package org.springframework.social.facebook.api;

import java.util.Date;
import java.util.List;

public class InstagramMedia extends FacebookObject {

    private String id;

    private String igId;

    private String caption;

    private String mediaType;

    private String mediaUrl;

    private InstagramUser owner;

    private String permalink;

    private String shortcode;

    private String thumbnailUrl;

    private Date timestamp;

    private Integer commentsCount;

    private Integer likeCount;

    private boolean isCommentEnabled;

    private List<InstagramMedia> children;

    private List<Comment> comments;

    public String getId() {
        return id;
    }

    public String getIgId() {
        return igId;
    }

    public String getCaption() {
        return caption;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public InstagramUser getOwner() {
        return owner;
    }

    public String getPermalink() {
        return permalink;
    }

    public String getShortcode() {
        return shortcode;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Integer getCommentsCount() {
        return commentsCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public List<InstagramMedia> getChildren() {
        return children;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public boolean isCommentEnabled() {
        return isCommentEnabled;
    }
}