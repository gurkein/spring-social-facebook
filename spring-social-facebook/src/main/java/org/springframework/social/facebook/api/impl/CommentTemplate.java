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
package org.springframework.social.facebook.api.impl;

import static org.springframework.social.facebook.api.impl.PagedListUtils.*;

import org.springframework.social.facebook.api.Comment;
import org.springframework.social.facebook.api.CommentOperations;
import org.springframework.social.facebook.api.GraphApi;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.PagingParameters;
import org.springframework.social.support.URIBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

class CommentTemplate implements CommentOperations {

	private final GraphApi graphApi;
	private final RestTemplate restTemplate;

	public CommentTemplate(GraphApi graphApi, RestTemplate restTemplate) {
		this.graphApi = graphApi;
		this.restTemplate = restTemplate;
	}

	public PagedList<Comment> getComments(String objectId) {
		return getComments(objectId, new PagingParameters(25, 0, null, null));
	}

	public PagedList<Comment> getComments(String objectId, PagingParameters pagedListParameters) {
		return graphApi.fetchConnections(objectId, "comments", Comment.class, getPagingParameters(pagedListParameters), ALL_COMMENT_FIELDS);
	}

	public Comment getComment(String commentId) {
		return graphApi.fetchObject(commentId, Comment.class, ALL_COMMENT_FIELDS);
	}

	public String addComment(String objectId, String message) {
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.set("message", message);
		return graphApi.publish(objectId, "comments", map);
	}

	public void deleteComment(String objectId) {
		graphApi.delete(objectId);
	}

	public void hideComment(String commentId, boolean hidden) {
		URI uri = URIBuilder.fromUri(graphApi.getBaseGraphApiUrl() + commentId)
				.queryParam("is_hidden", Boolean.toString(hidden)).build();
		restTemplate.postForObject(uri, null, String.class);
	}

}
