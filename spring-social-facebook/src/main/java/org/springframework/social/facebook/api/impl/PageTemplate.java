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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.social.facebook.api.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.social.facebook.api.impl.PagedListUtils.getPagingParameters;

class PageTemplate implements PageOperations {

	private final GraphApi graphApi;
	
	public PageTemplate(GraphApi graphApi) {
		this.graphApi = graphApi;
	}

	public Page getPage(String pageId) {
		return graphApi.fetchObject(pageId, Page.class, ALL_PAGE_FIELDS);
	}

	public void updatePage(PageUpdate pageUpdate) {
		String pageId = pageUpdate.getPageId();
		String pageAccessToken = getAccessToken(pageId);
		MultiValueMap<String, Object> map = pageUpdate.toRequestParameters();
		map.add("access_token", pageAccessToken);
		graphApi.post(pageId, map);
	}
	
	public boolean isPageAdmin(String pageId) {
		return getAccount(pageId) != null;
	}
	
	public PagedList<Account> getAccounts() {
		return graphApi.fetchConnections("me", "accounts", Account.class);
	}

	public String post(String pageId, String message) {
		return post(new PagePostData(pageId).message(message));
	}
	
	public String post(String pageId, String message, FacebookLink link) {
		PagePostData postData = new PagePostData(pageId)
				.message(message)
				.link(link.getLink(), link.getPicture(), link.getName(), link.getCaption(), link.getDescription());
		return post(postData);
	}
	
	public String post(PagePostData post) {
		String pageId = post.getPageId();
		String pageAccessToken = getAccessToken(pageId);
		MultiValueMap<String, Object> map = post.toRequestParameters();
		map.set("access_token", pageAccessToken);
		return graphApi.publish(pageId, "feed", map);
	}

	public String postPhoto(String pageId, String albumId, Resource photo) {
		return postPhoto(pageId, albumId, photo, null);
	}
	
	public String postPhoto(String pageId, String albumId, Resource photo, String caption) {
		String pageAccessToken = getAccessToken(pageId);
		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
		parts.set("source", photo);
		if(caption != null) {
			parts.set("message", caption);
		}
		parts.set("access_token", pageAccessToken);
		return graphApi.publish(albumId, "photos", parts);
	}
	
	public PagedList<Page> search(String query) {
		MultiValueMap<String, String> queryMap = new LinkedMultiValueMap<String, String>();
		queryMap.add("q", query);
		queryMap.add("type", "page");
		return graphApi.fetchConnections("search", null, Page.class, queryMap);
	}
	
	public PagedList<Page> searchPlaces(String query, double latitude, double longitude, long distance) {
		MultiValueMap<String, String> queryMap = new LinkedMultiValueMap<String, String>();
		queryMap.add("q", query);
		queryMap.add("type", "place");
		queryMap.add("center", latitude + "," + longitude);
		queryMap.add("distance", String.valueOf(distance));
		return graphApi.fetchConnections("search", null, Page.class, queryMap);
	}

	public String getAccessToken(String pageId) {
		Account account = getAccount(pageId);
		if(account == null) {
			throw new PageAdministrationException(pageId);
		}
		return account.getAccessToken();
	}

	public Account getAccount(String pageId) {
		if(!accountCache.containsKey(pageId)) {
			// only bother fetching the account data in the event of a cache miss
			List<Account> accounts = getAccounts();
			for (Account account : accounts) {
				accountCache.put(account.getId(), account);
			}
		}
		return accountCache.get(pageId);
	}

	public PagedList<Conversation> getConversations(String pageId, PagingParameters pagedListParameters) {
		MultiValueMap<String, String> params = getPagingParameters(pagedListParameters);
		return graphApi.fetchConnections(pageId, "conversations", Conversation.class, params, ALL_CONVERSATION_FIELDS);
	}

	public PagedList<Conversation> getIgConversations(String pageId, PagingParameters pagedListParameters) {
		MultiValueMap<String, String> params = getPagingParameters(pagedListParameters);
		params.set("platform", "instagram");
		return graphApi.fetchConnections(pageId, "conversations", Conversation.class, params, ALL_CONVERSATION_FIELDS);
	}

	public PagedList<Conversation> getConversations(String pageId, String userId, PagingParameters pagedListParameters) {
		MultiValueMap<String, String> params = getPagingParameters(pagedListParameters);
		params.set("user_id", userId);
		return graphApi.fetchConnections(pageId, "conversations", Conversation.class, params, ALL_CONVERSATION_FIELDS);
	}

	public PagedList<Conversation> getIgConversations(String pageId, String userId, PagingParameters pagedListParameters) {
		MultiValueMap<String, String> params = getPagingParameters(pagedListParameters);
		params.set("platform", "instagram");
		params.set("user_id", userId);
		return graphApi.fetchConnections(pageId, "conversations", Conversation.class, params, ALL_CONVERSATION_FIELDS);
	}

	public PagedList<Message> getConversationMessages(String conversationId, PagingParameters pagedListParameters) {
		MultiValueMap<String, String> params = getPagingParameters(pagedListParameters);
		return graphApi.fetchConnections(conversationId, "messages", Message.class, params, ALL_MESSAGE_FIELDS);
	}

	public PagedList<Message> getIgConversationMessages(String conversationId, PagingParameters pagedListParameters) {
		MultiValueMap<String, String> params = getPagingParameters(pagedListParameters);
		return graphApi.fetchConnections(conversationId, "messages", Message.class, params, ALL_IG_MESSAGE_FIELDS);
	}

	@Override
	public Conversation getConversation(String conversationId) {
		return graphApi.fetchObject(conversationId, Conversation.class, ALL_CONVERSATION_FIELDS);
	}

	@Override
	public Message getMessage(String messageId) {
		return graphApi.fetchObject(messageId, Message.class, ALL_MESSAGE_FIELDS);
	}

	@Override
	public Message getIgMessage(String messageId) {
		return graphApi.fetchObject(messageId, Message.class, ALL_IG_MESSAGE_FIELDS);
	}

	public String postConversationMessage(String conversationId, String message) {
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.set("message", message);
		return graphApi.publish(conversationId, "messages", map);
	}

	public Facebook facebookOperations(String pageId) {
		return new FacebookTemplate(getAccessToken(pageId));
	}

	// private helper methods
	
	private Map<String, Account> accountCache = new HashMap<String, Account>();

}
