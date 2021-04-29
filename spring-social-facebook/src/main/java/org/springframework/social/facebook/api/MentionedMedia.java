package org.springframework.social.facebook.api;

public class MentionedMedia extends FacebookObject {

    private String id;

    private InstagramMedia mentionedMedia;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public InstagramMedia getMentionedMedia() {
        return mentionedMedia;
    }

    public void setMentionedMedia(InstagramMedia mentionedMedia) {
        this.mentionedMedia = mentionedMedia;
    }
}
