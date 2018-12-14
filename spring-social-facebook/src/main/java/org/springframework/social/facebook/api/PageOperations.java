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

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.social.ApiException;
import org.springframework.social.InsufficientPermissionException;
import org.springframework.social.MissingAuthorizationException;

import java.util.List;

/**
 * Interface defining operations that can be performed on a Facebook pages.
 * @author Craig Walls
 */
public interface PageOperations {
	
	/**
	 * Retrieves data for a page.
	 * @param pageId the page ID.
	 * @return a {@link Page}
	 */
	Page getPage(String pageId);
	
	/**
	 * Updates page information.
	 * Requires that the application is granted "manage_pages" permission and that the authenticated user be an administrator of the page.
	 * @param pageUpdate a {@link PageUpdate} object that carries the fields to update on the page.
	 */
	void updatePage(PageUpdate pageUpdate);
	
	/**
	 * Checks whether the logged-in user for this session is an admin of the page with the given page ID.
	 * Requires "manage_pages" permission.
	 * @param pageId the page ID
	 * @return true if the authenticated user is an admin of the specified page.
	 * @throws ApiException if there is an error while communicating with Facebook.
	 * @throws InsufficientPermissionException if the user has not granted "manage_pages" permission.
	 * @throws MissingAuthorizationException if FacebookTemplate was not created with an access token.
	 */	
	boolean isPageAdmin(String pageId);
	
	/**
	 * Retrieves a list of Account objects for the pages that the authenticated user is an administrator.
	 * Requires "manage_pages" permission.
	 * @return a paged list of accounts
	 */
	PagedList<Account> getAccounts();
	
	/**
	 * Posts a message to a page's feed as a page administrator.
	 * Requires that the application is granted "manage_pages" permission and that the authenticated user be an administrator of the page.
	 * To post to the page's feed as the authenticated user, use {@link FeedOperations#post(String, String)} instead.
	 * @param pageId the page ID
	 * @param message the message to post
	 * @return the ID of the new feed entry
	 * @throws ApiException if there is an error while communicating with Facebook.
	 * @throws InsufficientPermissionException if the user has not granted "manage_pages" permission.
	 * @throws PageAdministrationException if the user is not a page administrator.
	 * @throws MissingAuthorizationException if FacebookTemplate was not created with an access token.
	 * @deprecated Use {@link PostData} instead.
	 */
	@Deprecated
	String post(String pageId, String message);
	
	/**
	 * Posts a link to the page's feed as a page administrator.
	 * Requires that the application is granted "manage_pages" permission and that the authenticated user be an administrator of the page.
	 * To post a link to the page's feed as the authenticated user, use {@link FeedOperations#postLink(String, String, FacebookLink)} instead.
	 * @param pageId the page ID
	 * @param message a message to send with the link.
	 * @param link the link details
	 * @return the ID of the new feed entry.
	 * @throws ApiException if there is an error while communicating with Facebook.
	 * @throws InsufficientPermissionException if the user has not granted "manage_pages" permission.
	 * @throws PageAdministrationException if the user is not a page administrator.
	 * @throws MissingAuthorizationException if FacebookTemplate was not created with an access token.
	 * @deprecated Use {@link PostData} instead.
	 */
	@Deprecated
	String post(String pageId, String message, FacebookLink link);
	
	/**
	 * Posts to a page's feed as a page administrator.
	 * Requires that the application is granted "manage_pages" permission and that the authenticated user be an administrator of the page.
	 * To post to the page's feed as the authenticated user, use {@link FeedOperations#post(String, String)} instead.
	 * @param post A {@link PagePostData} with the post's payload.
	 * @return the ID of the new feed entry
	 * @throws ApiException if there is an error while communicating with Facebook.
	 * @throws InsufficientPermissionException if the user has not granted "manage_pages" permission.
	 * @throws PageAdministrationException if the user is not a page administrator.
	 * @throws MissingAuthorizationException if FacebookTemplate was not created with an access token.
	 */
	String post(PagePostData post);
	
	/**
	 * Posts a photo to a page's album as the page administrator.
	 * Requires that the application is granted "manage_pages" permission and that the authenticated user be an administrator of the page.
	 * @param pageId the page ID
	 * @param albumId the album ID
	 * @param photo A {@link Resource} for the photo data. The given Resource must implement the getFilename() method (such as {@link FileSystemResource} or {@link ClassPathResource}).
	 * @return the ID of the photo.
	 * @throws ApiException if there is an error while communicating with Facebook.
	 * @throws InsufficientPermissionException if the user has not granted "manage_pages" permission.
	 * @throws PageAdministrationException if the user is not a page administrator.
	 * @throws MissingAuthorizationException if FacebookTemplate was not created with an access token.
	 */
	String postPhoto(String pageId, String albumId, Resource photo);

	/**
	 * Posts a photo to a page's album as the page administrator.
	 * Requires that the application is granted "manage_pages" permission and that the authenticated user be an administrator of the page.
	 * @param pageId the page ID
	 * @param albumId the album ID
	 * @param photo A {@link Resource} for the photo data. The given Resource must implement the getFilename() method (such as {@link FileSystemResource} or {@link ClassPathResource}).
	 * @param caption A caption describing the photo.
	 * @return the ID of the photo.
	 * @throws ApiException if there is an error while communicating with Facebook.
	 * @throws InsufficientPermissionException if the user has not granted "manage_pages" permission.
	 * @throws PageAdministrationException if the user is not a page administrator.
	 * @throws MissingAuthorizationException if FacebookTemplate was not created with an access token.
	 */
	String postPhoto(String pageId, String albumId, Resource photo, String caption);
	
	/**
	 * Searches for pages that match a given query.
	 * @param query the search query
	 * @return a list of {@link Page}s matching the search
	 * @throws ApiException if there is an error while communicating with Facebook.
	 * @throws MissingAuthorizationException if FacebookTemplate was not created with an access token.
	 */
	PagedList<Page> search(String query);
	
	/**
	 * Searches for places near a given coordinate.
	 * @param query the search query (e.g., "Burritos")
	 * @param latitude the latitude of the point to search near
	 * @param longitude the longitude of the point to search near
	 * @param distance the radius to search within (in feet)
	 * @return a list of {@link Page}s matching the search
	 * @throws ApiException if there is an error while communicating with Facebook.
	 * @throws MissingAuthorizationException if FacebookTemplate was not created with an access token.
	 */
	PagedList<Page> searchPlaces(String query, double latitude, double longitude, long distance);

	/**
	 * Returns the access token for a given page. This makes it possible to perform operations on behalf of a page that 
	 * aren't already covered by PageOperations methods.
	 * @param pageId the page to fetch the access token for
	 * @return the page access token
	 */
	String getAccessToken(String pageId);
	
	/**
	 * Returns an {@link Account} object for the user's access to the given page.
	 * @param pageId the page to fetch the Account details for
	 * @return an {@link Account} object for the given page ID
	 */
	Account getAccount(String pageId);

	/**
	 *
	 * @param pageId
	 * @param pagedListParameters
	 * @return
	 */
	PagedList<Conversation> getConversations(String pageId, PagingParameters pagedListParameters);

	/**
	 *
	 * @param conversationId
	 * @return
	 */
	PagedList<Message> getConversationMessages(String conversationId, PagingParameters pagedListParameters);

	/**
	 *
	 * @param conversationId
	 * @return
	 */
	String postConversationMessage(String conversationId, String message);

	/**
	 * Returns a {@link Facebook} instance that will act on behalf of the given page.
	 * @param pageId the page to create a {@link Facebook} instance for.
	 * @return a {@link Facebook} instance for the page.
	 */
	Facebook facebookOperations(String pageId);

	static final String[] ALL_PAGE_FIELDS = {
			"id", "about", "access_token", "business", "can_checkin", "can_post", "category", "category_list", "checkins",
			"country_page_likes", "current_location", "description", "description_html", "emails", "engagement",
			"fan_count", "general_info", "global_brand_page_name", "global_brand_root_id", "has_added_app",
			"instagram_business_account", "link", "name", "new_like_count", "parent_page", "phone",
			"promotion_eligible", "promotion_ineligible_reason", "rating_count", "single_line_address",
			"talking_about_count", "unread_message_count", "unread_notif_count", "unseen_message_count", "username",
			"verification_status", "voip_info", "website", "were_here_count",
			"affiliation", "artists_we_like", "attire", "awards", "band_interests", "band_members", "best_page", "bio",
			"birthday", "booking_agent", "built", "company_overview", "culinary_team", "directed_by", "features",
			"food_styles", "founded", "general_manager", "genre", "hometown", "influences", "location", "members",
			"mission", "mpg", "network", "overall_star_rating", "parking", "payment_options", "personal_info",
			"personal_interests", "pharma_safety_info", "place_type", "plot_outline", "press_contact", "price_range",
			"produced_by", "products", "public_transit", "record_label", "release_date",
			"restaurant_services", "restaurant_specialties", "schedule", "screenplay_by", "season", "starring",
			"store_number", "studio", "written_by", "offer_eligible",
			"app_id", "hours", "is_community_page", "is_permanently_closed", "is_published", "is_unclaimed"
	};
}
