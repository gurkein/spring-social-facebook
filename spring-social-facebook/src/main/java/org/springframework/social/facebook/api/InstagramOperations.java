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

import org.springframework.social.ApiException;
import org.springframework.social.InsufficientPermissionException;
import org.springframework.social.MissingAuthorizationException;

public interface InstagramOperations {

    /**
     * Retrieves the profile for the specified IG user.
     *
     * @param userId the Instagram Business Account ID to retrieve profile data for.
     * @return the user's profile information.
     * @throws ApiException if there is an error while communicating with Facebook.
     */
    InstagramUser getUserProfile(String userId);

    /**
     * Retrieves recent Media entries for a given Instagram Business Account.
     * Returns up to the most recent 25 media.
     * Requires "instagram_basic, instagram_manage_comments, manage_pages" permission to read media.
     *
     * @param ownerId the Instagram Business Account ID.
     * @return a list of {@link InstagramMedia}s for the specified user.
     * @throws ApiException                  if there is an error while communicating with Facebook.
     * @throws MissingAuthorizationException if FacebookTemplate was not created with an access token.
     */
    PagedList<InstagramMedia> getMedia(String ownerId);

    /**
     * Retrieves recent Media entries for a given Instagram Business Account.
     * Returns up to the most recent 25 media.
     * Requires "instagram_basic, instagram_manage_comments, manage_pages" permission to read media.
     *
     * @param ownerId             the Instagram Business Account ID.
     * @param pagedListParameters the parameters defining the bounds of the list to return.
     * @return a list of {@link InstagramMedia}s for the specified user.
     * @throws ApiException                  if there is an error while communicating with Facebook.
     * @throws MissingAuthorizationException if FacebookTemplate was not created with an access token.
     */
    PagedList<InstagramMedia> getMedia(String ownerId, PagingParameters pagedListParameters);

    /**
     * Retrieves a single media.
     *
     * @param mediaId the media ID.
     * @return the requested {@link InstagramMedia}
     * @throws ApiException if there is an error while communicating with Facebook.
     */
    InstagramMedia getSingleMedia(String mediaId);

    /**
     * Retrieves recent comments for a given media.
     * Returns up to the most recent 25 comments.
     * Requires "instagram_basic, instagram_manage_comments" permission to read comments.
     *
     * @param mediaId the media ID.
     * @return a list of {@link InstagramComment}s for the specified user.
     * @throws ApiException                  if there is an error while communicating with Facebook.
     * @throws MissingAuthorizationException if FacebookTemplate was not created with an access token.
     */
    PagedList<InstagramComment> getComments(String mediaId);

    /**
     * Retrieves recent comments for a given media.
     * Returns up to the most recent 25 comments.
     * Requires "instagram_basic, instagram_manage_comments" permission to read comments.
     *
     * @param mediaId             the media ID.
     * @param pagedListParameters the parameters defining the bounds of the list to return.
     * @return a list of {@link InstagramComment}s for the specified user.
     * @throws ApiException                  if there is an error while communicating with Facebook.
     * @throws MissingAuthorizationException if FacebookTemplate was not created with an access token.
     */
    PagedList<InstagramComment> getComments(String mediaId, PagingParameters pagedListParameters);

    /**
     * Retrieves a single comment.
     *
     * @param commentId the Comment ID.
     * @return the requested {@link InstagramComment}
     * @throws ApiException if there is an error while communicating with Facebook.
     */
    InstagramComment getComment(String commentId);

    /**
     * Retrieves children entries for a given album carousel media.
     * Returns children media.
     * Requires "instagram_basic" permission to read stories.
     *
     * @param mediaId the media ID.
     * @return a list of {@link InstagramMedia}s for the specified user.
     * @throws ApiException                  if there is an error while communicating with Facebook.
     * @throws MissingAuthorizationException if FacebookTemplate was not created with an access token.
     */
    PagedList<InstagramMedia> getCarouselChildren(String mediaId);

    /**
     * Retrieves recent Story entries for a given Instagram Business Account.
     * Returns up to the most recent 25 stories.
     * Requires "instagram_basic, instagram_manage_comments, instagram_manage_insights, manage_pages" permission to read stories.
     *
     * @param ownerId the Instagram Business Account ID.
     * @return a list of {@link InstagramMedia}s for the specified user.
     * @throws ApiException                  if there is an error while communicating with Facebook.
     * @throws MissingAuthorizationException if FacebookTemplate was not created with an access token.
     */
    PagedList<InstagramMedia> getStories(String ownerId);

    /**
     * Retrieves recent Story entries for a given Instagram Business Account.
     * Returns up to the most recent 25 stories.
     * Requires "instagram_basic, instagram_manage_comments, instagram_manage_insights, manage_pages" permission to read stories.
     *
     * @param ownerId             the Instagram Business Account ID.
     * @param pagedListParameters the parameters defining the bounds of the list to return.
     * @return a list of {@link InstagramMedia}s for the specified user.
     * @throws ApiException                  if there is an error while communicating with Facebook.
     * @throws MissingAuthorizationException if FacebookTemplate was not created with an access token.
     */
    PagedList<InstagramMedia> getStories(String ownerId, PagingParameters pagedListParameters);

    /**
     * Toggle comments of a given Media.
     * Requires "instagram_basic, instagram_manage_comments" permission.
     * @param mediaId the Media ID
     * @param enabled the parameter to enable/disabled.
     * @throws ApiException if there is an error while communicating with Facebook.
     * @throws InsufficientPermissionException if the user has not granted "instagram_manage_comments" permission.
     * @throws MissingAuthorizationException if FacebookTemplate was not created with an access token.
     */
    void toggleComments(String mediaId, boolean enabled);

    /**
     * Hide a given Comment.
     * Requires "instagram_basic, instagram_manage_comments" permission.
     * @param commentId the Comment ID.
     * @param hidden    the parameter to hide/unhide.
     * @throws ApiException if there is an error while communicating with Facebook.
     * @throws InsufficientPermissionException if the user has not granted "instagram_manage_comments" permission.
     * @throws MissingAuthorizationException if FacebookTemplate was not created with an access token.
     */
    void hideComment(String commentId, boolean hidden);

    /**
     * Delete a given Comment.
     * Requires "instagram_basic, instagram_manage_comments" permission.
     * @param commentId the Comment ID
     * @throws ApiException if there is an error while communicating with Facebook.
     * @throws InsufficientPermissionException if the user has not granted "instagram_manage_comments" permission.
     * @throws MissingAuthorizationException if FacebookTemplate was not created with an access token.
     */
    void deleteComment(String commentId);

    static final String[] ALL_PROFILE_FIELDS = {
            "biography", "id", "ig_id", "followers_count", "follows_count", "media_count", "name", "profile_picture_url", "username", "website"
    };

    static final String[] ALL_MEDIA_FIELDS = {
            "caption", "comments_count", "id", "ig_id", "like_count", "media_type", "media_url", "owner", "permalink",
            "shortcode", "thumbnail_url", "timestamp", "is_comment_enabled"
    };

    static final String[] ALL_COMMENT_FIELDS = {
            "hidden", "id", "media", "text", "timestamp", "user"
    };

}
