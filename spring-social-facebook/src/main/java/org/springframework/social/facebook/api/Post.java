/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.facebook.api;

import java.util.*;


/**
 * Model class representing an entry in a feed.
 *
 * @author Craig Walls
 */
public class Post extends FacebookObject {

    private String id;

    private List<Action> actions;

    private AdminCreator adminCreator;

    private Reference application;

    @Deprecated
    // attachments{title}
    private String caption;

    private Date createdTime;

    @Deprecated
    // attachments{description}
    private String description;

    private Reference from;

    private String icon;

    private boolean isHidden;

    private boolean isPublished;

    @Deprecated
    // attachments{unshimmed_url}
    private String link;

    @Deprecated
    private String message;

    private Map<Integer, List<MessageTag>> messageTags;

    @Deprecated
    // attachments{title}
    private String name;

    @Deprecated
    // attachments{target{id}}
    private String objectId;

    private String picture;

    private Page place;

    private Privacy privacy;

    private List<PostProperty> properties = new ArrayList<PostProperty>();

    private int sharesCount;

    @Deprecated
    // attachments{media{source}}
    private String source;

    private StatusType statusType;

    private String story;

    private List<Reference> to;

    @Deprecated
    // attachments{media_type} If there is no attachments or media_type=link, the value is the same as
    // attachments{type=status}.
    private PostType type = PostType.UNKNOWN;

    private Date updatedTime;

    private List<Reference> withTags;

    private List<StoryAttachment> attachments;

    public String getId() {
        return id;
    }

    public List<Action> getActions() {
        return actions;
    }

    public AdminCreator getAdminCreator() {
        return adminCreator;
    }

    public Reference getApplication() {
        return application;
    }

    // TODO: public ? getCallToAction() { ... }

    public String getCaption() {
        if (caption == null) {
            return Optional.ofNullable(attachments).map(attachments -> attachments.stream()
                    .map(StoryAttachment::getTitle)
                    .filter(Objects::nonNull)
                    .findFirst().orElse(null)).orElse(null);
        }
        return caption;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public String getDescription() {
		if (description == null) {
			return Optional.ofNullable(attachments).map(attachments -> attachments.stream()
					.map(StoryAttachment::getDescription)
					.filter(Objects::nonNull)
					.findFirst().orElse(null)).orElse(null);
		}
        return description;
    }

    public Reference getFrom() {
        return from;
    }

    public String getIcon() {
        return icon;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public String getLink() {
		if (link == null) {
			return Optional.ofNullable(attachments).map(attachments -> attachments.stream()
					.map(StoryAttachment::getUnshimmedUrl)
					.filter(Objects::nonNull)
					.findFirst().orElse(null)).orElse(null);
		}
        return link;
    }

    public String getMessage() {
        return message;
    }

    public Map<Integer, List<MessageTag>> getMessageTags() {
        return messageTags;
    }

    public String getName() {
		if (name == null) {
			return Optional.ofNullable(attachments).map(attachments -> attachments.stream()
					.map(StoryAttachment::getTitle)
					.filter(Objects::nonNull)
					.findFirst().orElse(null)).orElse(null);
		}
        return name;
    }

    public String getObjectId() {
		if (objectId == null) {
			return Optional.ofNullable(attachments).map(attachments -> attachments.stream()
					.map(StoryAttachment::getTarget)
					.filter(Objects::nonNull)
					.map(StoryAttachment.StoryAttachmentTarget::getId)
					.filter(Objects::nonNull)
					.findFirst().orElse(null)).orElse(null);
		}
        return objectId;
    }

    public String getPicture() {
        return picture;
    }

    public Page getPlace() {
        return place;
    }

    public Privacy getPrivacy() {
        return privacy;
    }

    public List<PostProperty> getProperties() {
        return properties;
    }

    public String getSource() {
		if (source == null) {
			return Optional.ofNullable(attachments).map(attachments -> attachments.stream()
					.map(StoryAttachment::getMedia)
					.filter(Objects::nonNull)
					.map(StoryAttachment.StoryAttachmentMedia::getSource)
					.filter(Objects::nonNull)
					.findFirst().orElse(null)).orElse(null);
		}
        return source;
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public String getStory() {
        return story;
    }

    public List<Reference> getTo() {
        return to;
    }

    public PostType getType() {
        if (type.equals(PostType.UNKNOWN)) {
            try {
                return PostType.valueOf(Optional.ofNullable(attachments).map(attachments -> attachments.stream()
                        .map(attachment -> {
                            if (attachment.getMediaType() == null || attachment.getMediaType().equals("link")) {
                                return attachment.getType();
                            } else {
                                return attachment.getMediaType();
                            }
                        })
                        .filter(Objects::nonNull)
                        .findFirst().orElse("unknown")).orElse("unknown").toUpperCase());
            } catch (IllegalArgumentException e) {
            }
        }
        return type;
    }

    public void setType(PostType type) {
        this.type = type;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    @Deprecated
    public List<Reference> getWithTags() {
        return withTags;
    }

    public int getShares() {
        return sharesCount;
    }

    public static class AdminCreator {

        private String id;

        private String name;

        private String namespace;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getNamespace() {
            return namespace;
        }

    }

    public static class Privacy {

        private String description;

        private PrivacyType value;

        private FriendsPrivacyType friends;

        private String networks;

        private String allow;

        private String deny;

        public String getDescription() {
            return description;
        }

        public PrivacyType getValue() {
            return value;
        }

        public FriendsPrivacyType getFriends() {
            return friends;
        }

        public String getNetworks() {
            return networks;
        }

        public String getAllow() {
            return allow;
        }

        public String getDeny() {
            return deny;
        }

    }

    public static enum PostType {LINK, STATUS, PHOTO, VIDEO, UNKNOWN}

    public static enum StatusType {
        MOBILE_STATUS_UPDATE, CREATED_NOTE, ADDED_PHOTOS, ADDED_VIDEO, SHARED_STORY, CREATED_GROUP,
        CREATED_EVENT, WALL_POST, APP_CREATED_STORY, PUBLISHED_STORY, TAGGED_IN_PHOTO, APPROVED_FRIEND, UNKNOWN
    }

    public static enum PrivacyType {EVERYONE, ALL_FRIENDS, FRIENDS_OF_FRIENDS, SELF, CUSTOM, UNKNOWN}

    public static enum FriendsPrivacyType {ALL_FRIENDS, FRIENDS_OF_FRIENDS, SOME_FRIENDS, UNKNOWN}

}
