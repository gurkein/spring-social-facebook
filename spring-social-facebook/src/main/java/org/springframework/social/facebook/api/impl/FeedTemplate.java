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

import org.springframework.social.facebook.api.FacebookLink;
import org.springframework.social.facebook.api.FeedOperations;
import org.springframework.social.facebook.api.GraphApi;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.PagingParameters;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.PostData;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

class FeedTemplate implements FeedOperations {

	private static final PagingParameters FIRST_PAGE = new PagingParameters(25, null, null, null);

	private final GraphApi graphApi;
	
	public FeedTemplate(GraphApi graphApi) {
		this.graphApi = graphApi;
	}

	public PagedList<Post> getFeed() {
		return getFeed("me", FIRST_PAGE);
	}

	public PagedList<Post> getFeed(PagingParameters pagedListParameters) {
		return getFeed("me", pagedListParameters);
	}
	
	public PagedList<Post> getFeed(String ownerId) {
		return getFeed(ownerId, FIRST_PAGE);
	}
		
	public PagedList<Post> getFeed(String ownerId, PagingParameters pagedListParameters) {
		MultiValueMap<String, String> params = getPagingParameters(pagedListParameters);
		params.set("fields", StringUtils.arrayToCommaDelimitedString(ALL_POST_FIELDS));
		return graphApi.fetchConnections(ownerId, "feed", Post.class, params);
	}

	public PagedList<Post> getHomeFeed() {
		return getHomeFeed(FIRST_PAGE);
	}
	
	public PagedList<Post> getHomeFeed(PagingParameters pagedListParameters) {
		MultiValueMap<String, String> params = getPagingParameters(pagedListParameters);
		params.set("fields", StringUtils.arrayToCommaDelimitedString(ALL_POST_FIELDS));
		return graphApi.fetchConnections("me", "home", Post.class, params);
	}

	public PagedList<Post> getStatuses() {
		return getStatuses("me", FIRST_PAGE);
	}
	
	public PagedList<Post> getStatuses(PagingParameters pagedListParameters) {
		return getStatuses("me", pagedListParameters);
	}

	public PagedList<Post> getStatuses(String userId) {
		return getStatuses(userId, FIRST_PAGE);
	}
	
	public PagedList<Post> getStatuses(String userId, PagingParameters pagedListParameters) {
		MultiValueMap<String, String> params = getPagingParameters(pagedListParameters);
		params.set("fields", StringUtils.arrayToCommaDelimitedString(ALL_POST_FIELDS));
		return overridePostType(graphApi.fetchConnections(userId, "statuses", Post.class, params), Post.PostType.STATUS);
	}

	public PagedList<Post> getLinks() {
		return getLinks("me", FIRST_PAGE);
	}

	public PagedList<Post> getLinks(PagingParameters pagedListParameters) {
		return getLinks("me", pagedListParameters);
	}

	public PagedList<Post> getLinks(String ownerId) {
		return getLinks(ownerId, FIRST_PAGE);
	}
	
	public PagedList<Post> getLinks(String ownerId, PagingParameters pagedListParameters) {
		MultiValueMap<String, String> params = getPagingParameters(pagedListParameters);
		params.set("fields", StringUtils.arrayToCommaDelimitedString(ALL_POST_FIELDS));
		return overridePostType(graphApi.fetchConnections(ownerId, "links", Post.class, params), Post.PostType.LINK);
	}

	public PagedList<Post> getPosts() {
		return getPosts("me", FIRST_PAGE);
	}

	public PagedList<Post> getPosts(PagingParameters pagedListParameters) {
		return getPosts("me", pagedListParameters);
	}

	public PagedList<Post> getPosts(String ownerId) {
		return getPosts(ownerId, FIRST_PAGE);
	}
	
	public PagedList<Post> getPosts(String ownerId, PagingParameters pagedListParameters) {
		MultiValueMap<String, String> params = getPagingParameters(pagedListParameters);
		params.set("fields", StringUtils.arrayToCommaDelimitedString(ALL_POST_FIELDS));
		return graphApi.fetchConnections(ownerId, "posts", Post.class, params);
	}

	public PagedList<Post> getTagged() {
		return getTagged("me", FIRST_PAGE);
	}

	public PagedList<Post> getTagged(PagingParameters pagedListParameters) {
		return getTagged("me", pagedListParameters);
	}

	public PagedList<Post> getTagged(String ownerId) {
		return getTagged(ownerId, FIRST_PAGE);
	}
	
	public PagedList<Post> getTagged(String ownerId, PagingParameters pagedListParameters) {
		MultiValueMap<String, String> params = getPagingParameters(pagedListParameters);
		params.set("fields", StringUtils.arrayToCommaDelimitedString(ALL_POST_FIELDS));
		return graphApi.fetchConnections(ownerId, "tagged", Post.class, params);
	}

	public Post getPost(String entryId) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("fields", StringUtils.arrayToCommaDelimitedString(ALL_POST_FIELDS));
		return graphApi.fetchObject(entryId, Post.class, params);
	}

	public String updateStatus(String message) {
		return post("me", message);
	}

	public String postLink(String message, FacebookLink link) {
		return postLink("me", message, link);
	}
	
	public String postLink(String ownerId, String message, FacebookLink link) {
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.set("link", link.getLink());
		map.set("name", link.getName());
		map.set("caption", link.getCaption());
		map.set("description", link.getDescription());
		map.set("message", message);
		// Intentionally not adding null checks to the above to preserve backwards compatibility
		if(link.getPicture() != null) map.set("picture", link.getPicture());
		return graphApi.publish(ownerId, "feed", map);
	}
	
	public String post(PostData post) {
		return graphApi.publish(post.getTargetFeedId(), "feed", post.toRequestParameters());
	}
	
	public String post(String ownerId, String message) {
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.set("message", message);
		return graphApi.publish(ownerId, "feed", map);
	}

	public void deletePost(String id) {
		graphApi.delete(id);
	}

	public PagedList<Post> getCheckins() {
		return getCheckins(new PagingParameters(25, 0, null, null));
	}

	public PagedList<Post> getCheckins(PagingParameters pagedListParameters) {
		MultiValueMap<String, String> params = getPagingParameters(pagedListParameters);
		params.set("with", "location");
		return graphApi.fetchConnections("me", "posts", Post.class, params);
	}

	public Post getCheckin(String checkinId) {
		return graphApi.fetchObject(checkinId, Post.class);
	}

	private PagedList<Post> overridePostType(PagedList<Post> posts, Post.PostType type) {
		for (Post post : posts) {
			post.setType(type);
		}
		return posts;
	}

}
