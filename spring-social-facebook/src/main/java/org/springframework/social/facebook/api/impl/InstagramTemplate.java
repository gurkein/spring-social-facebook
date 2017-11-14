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

import org.springframework.social.facebook.api.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import static org.springframework.social.facebook.api.impl.PagedListUtils.getPagingParameters;

class InstagramTemplate implements InstagramOperations {

    private final GraphApi graphApi;

    private final RestTemplate restTemplate;

    public InstagramTemplate(GraphApi graphApi, RestTemplate restTemplate) {
        this.graphApi = graphApi;
        this.restTemplate = restTemplate;
    }

    public InstagramUser getUserProfile(String userId) {
        return graphApi.fetchObject(userId, InstagramUser.class, BASIC_PROFILE_FIELDS);
    }

    public PagedList<InstagramMedia> getMedia(String ownerId) {
        return graphApi.fetchConnections(ownerId, "media", InstagramMedia.class, BASIC_MEDIA_FIELDS);
    }

    public PagedList<InstagramMedia> getMedia(String ownerId, PagingParameters pagedListParameters) {
        MultiValueMap<String, String> params = getPagingParameters(pagedListParameters);
        params.set("fields", StringUtils.arrayToCommaDelimitedString(BASIC_MEDIA_FIELDS));
        return graphApi.fetchConnections(ownerId, "media", InstagramMedia.class, params);
    }

    public InstagramMedia getSingleMedia(String mediaId) {
        return graphApi.fetchObject(mediaId, InstagramMedia.class, BASIC_MEDIA_FIELDS);
    }

    public PagedList<InstagramComment> getComments(String mediaId) {
        return graphApi.fetchConnections(mediaId, "comments", InstagramComment.class, BASIC_COMMENT_FIELDS);
    }

    public PagedList<InstagramComment> getComments(String mediaId, PagingParameters pagedListParameters) {
        MultiValueMap<String, String> params = getPagingParameters(pagedListParameters);
        params.set("fields", StringUtils.arrayToCommaDelimitedString(BASIC_COMMENT_FIELDS));
        return graphApi.fetchConnections(mediaId, "comments", InstagramComment.class, params);
    }

    public InstagramComment getComment(String commentId) {
        return graphApi.fetchObject(commentId, InstagramComment.class, BASIC_COMMENT_FIELDS);
    }

    public PagedList<InstagramMedia> getCarouselChildren(String mediaId) {
        return graphApi.fetchConnections(mediaId, "children", InstagramMedia.class, BASIC_CAROUSEL_FIELDS);
    }

    public PagedList<InstagramMedia> getStories(String ownerId) {
        return graphApi.fetchConnections(ownerId, "stories", InstagramMedia.class, BASIC_MEDIA_FIELDS);
    }

    public PagedList<InstagramMedia> getStories(String ownerId, PagingParameters pagedListParameters) {
        MultiValueMap<String, String> params = getPagingParameters(pagedListParameters);
        params.set("fields", StringUtils.arrayToCommaDelimitedString(BASIC_MEDIA_FIELDS));
        return graphApi.fetchConnections(ownerId, "stories", InstagramMedia.class, params);
    }

    public void toggleComments(String mediaId, boolean enabled) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
        params.set("comment_enabled", Boolean.toString(enabled));
        graphApi.post(mediaId, params);
    }

    public void hideComment(String commentId, boolean hidden) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
        params.set("hide", Boolean.toString(hidden));
        graphApi.post(commentId, params);
    }

    public void deleteComment(String commentId) {
        graphApi.delete(commentId);
    }

}
