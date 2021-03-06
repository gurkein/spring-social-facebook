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
import com.github.jonpeterson.jackson.module.interceptor.JsonInterceptors;
import org.springframework.social.facebook.api.ExtendedReference;

import java.util.Date;
import java.util.List;

/**
 * Annotated mixin to add Jackson annotations to Conversation.
 *
 * @author Gurkan Vural
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInterceptors(beforeDeserialization = RawJsonDeserializationInterceptor.class)
abstract class ConversationMixin extends FacebookObjectMixin {

    @JsonProperty
    String id;

    @JsonProperty
    String snippet;

    @JsonProperty("updated_time")
    Date updatedTime;

    @JsonProperty("message_count")
    int messageCount;

    @JsonProperty("unread_count")
    int unreadCount;

    @JsonProperty
    @JsonDeserialize(using = ExtendedReferenceListDeserializer.class)
    List<ExtendedReference> participants;

    @JsonProperty
    @JsonDeserialize(using = ExtendedReferenceListDeserializer.class)
    List<ExtendedReference> senders;

    @JsonProperty("can_reply")
    boolean canReply;

    @JsonProperty("is_subscribed")
    boolean isSubscribed;

    @JsonProperty
    String link;

}
