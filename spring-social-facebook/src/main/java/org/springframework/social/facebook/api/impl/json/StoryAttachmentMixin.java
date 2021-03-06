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
package org.springframework.social.facebook.api.impl.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.social.facebook.api.EntityAtTextRange;
import org.springframework.social.facebook.api.ImageSource;
import org.springframework.social.facebook.api.StoryAttachment;
import org.springframework.social.facebook.api.StoryAttachment.StoryAttachmentMedia;
import org.springframework.social.facebook.api.StoryAttachment.StoryAttachmentTarget;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
abstract class StoryAttachmentMixin {

    @JsonProperty("title")
    String title;

    @JsonProperty("description")
    String description;

    @JsonProperty("description_tags")
    List<EntityAtTextRange> descriptionTags;

    @JsonProperty("type")
    String type;

    @JsonProperty("url")
    String url;

    @JsonProperty("target")
    StoryAttachmentTarget target;

    @JsonProperty("media")
    StoryAttachmentMedia media;

    @JsonProperty("media_type")
    String mediaType;

    @JsonProperty("unshimmed_url")
    String unshimmedUrl;

    @JsonProperty("subattachments")
    @JsonDeserialize(using = StoryAttachmentListDeserializer.class)
    List<StoryAttachment> subAttachments;

    @JsonIgnoreProperties(ignoreUnknown = true)
    static abstract class StoryAttachmentMediaMixin {

        StoryAttachmentMediaMixin(
                @JsonProperty("image") ImageSource image,
                @JsonProperty("source") String source) {
        }

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static abstract class StoryAttachmentTargetMixin {

        StoryAttachmentTargetMixin(
                @JsonProperty("id") String id,
                @JsonProperty("url") String url) {
        }

    }
}
