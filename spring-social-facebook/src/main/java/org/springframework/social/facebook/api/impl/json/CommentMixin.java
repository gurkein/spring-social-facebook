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

import java.util.Date;
import java.util.List;

import com.github.jonpeterson.jackson.module.interceptor.JsonInterceptors;
import org.springframework.social.facebook.api.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Annotated mixin to add Jackson annotations to Comment. 
 * @author Craig Walls
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInterceptors(beforeDeserialization = RawJsonDeserializationInterceptor.class)
abstract class CommentMixin extends FacebookObjectMixin {

	@JsonProperty("id")
	String id;
	
	@JsonProperty("attachment")
	StoryAttachment attachment;
	
	@JsonProperty("can_comment")
	boolean canComment;
	
	@JsonProperty("can_remove")
	boolean canRemove;
	
	@JsonProperty("is_hidden")
	boolean hidden;

	@JsonProperty("can_hide")
	boolean canHide;

	@JsonProperty("comment_count")
	Integer commentCount;
	
	@JsonProperty("created_time")
	Date createdTime;
	
	@JsonProperty("from")
	Reference from;
	
	@JsonProperty("like_count")
	Integer likeCount;
	
	@JsonProperty("message")
	String message;
	
	@JsonProperty("message_tags")
	List<EntityAtTextRange> messageTags;
	
	@JsonProperty("parent")
	Comment parent;

	@JsonProperty("user_likes")
	boolean userLikes;

	@JsonProperty("can_like")
	boolean canLike;

	@JsonProperty("can_reply_privately")
	boolean canReplyPrivately;

	@JsonProperty("private_reply_conversation")
	Conversation privateReplyConversation;

}
