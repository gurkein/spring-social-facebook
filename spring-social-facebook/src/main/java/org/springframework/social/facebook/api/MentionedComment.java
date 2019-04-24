package org.springframework.social.facebook.api;

public class MentionedComment extends FacebookObject {

    private String id;

    private InstagramComment mentionedComment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public InstagramComment getMentionedComment() {
        return mentionedComment;
    }

    public void setMentionedComment(InstagramComment mentionedComment) {
        this.mentionedComment = mentionedComment;
    }
}
