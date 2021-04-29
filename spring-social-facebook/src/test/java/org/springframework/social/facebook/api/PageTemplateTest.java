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

import static org.junit.Assert.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.social.facebook.api.Page.PriceRange;
import org.springframework.util.StringUtils;

/**
 * @author Craig Walls
 */
public class PageTemplateTest extends AbstractFacebookApiTest {

	@Test
	public void getPage_place_with_hours() {
		mockServer.expect(requestTo(fbUrl("220817147947513?fields=" + ALL_PAGE_FIELDS_STR)))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "OAuth someAccessToken"))
				.andRespond(withSuccess(jsonResource("place-with-hours-page"), MediaType.APPLICATION_JSON));

		Page page = facebook.pageOperations().getPage("220817147947513");
		assertEquals("220817147947513", page.getId());
		assertEquals("Denton Square Donuts", page.getName());
		assertEquals("https://www.facebook.com/DentonSquareDonuts", page.getLink());
		assertEquals(3078, page.getFanCount());
		assertEquals("Restaurant/cafe", page.getCategory());
		assertEquals("www.dsdonuts.com", page.getWebsite());
		assertEquals("Denton", page.getLocation().getCity());
		assertEquals("TX", page.getLocation().getState());
		assertEquals("United States", page.getLocation().getCountry());
		assertEquals(33.21556, page.getLocation().getLatitude(), 0.0001);
		assertEquals(-97.13414, page.getLocation().getLongitude(), 0.0001);
		assertEquals("940-220-9447", page.getPhone());
		assertEquals(959, page.getCheckins());
		assertFalse(page.canPost());
		assertTrue(page.isPublished());
		assertFalse(page.isCommunityPage());
		assertFalse(page.hasAddedApp());
		assertEquals(68, page.getTalkingAboutCount());
		assertNotNull(page.getHours());
		assertEquals("07:30", page.getHours().get("mon_1_open"));
		assertEquals("13:00", page.getHours().get("mon_1_close"));
		assertEquals("07:30", page.getHours().get("tue_1_open"));
		assertEquals("13:00", page.getHours().get("tue_1_close"));
		assertEquals("07:30", page.getHours().get("wed_1_open"));
		assertEquals("13:00", page.getHours().get("wed_1_close"));
		assertEquals("07:30", page.getHours().get("thu_1_open"));
		assertEquals("13:00", page.getHours().get("thu_1_close"));
		assertEquals("07:30", page.getHours().get("fri_1_open"));
		assertEquals("13:00", page.getHours().get("fri_1_close"));
		assertEquals("07:30", page.getHours().get("sat_1_open"));
		assertEquals("13:00", page.getHours().get("sat_1_close"));
		assertEquals("07:30", page.getHours().get("sun_1_open"));
		assertEquals("13:00", page.getHours().get("sun_1_close"));
	}

	@Test
	public void getPage_application() {
		mockServer.expect(requestTo(fbUrl("140372495981006?fields=" + ALL_PAGE_FIELDS_STR)))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "OAuth someAccessToken"))
			.andRespond(withSuccess(jsonResource("application-page"), MediaType.APPLICATION_JSON));

		Page page = facebook.pageOperations().getPage("140372495981006");
		assertEquals("140372495981006", page.getId());
		assertEquals("Greenhouse", page.getName());
		assertEquals("http://www.facebook.com/apps/application.php?id=140372495981006", page.getLink());
		assertEquals("The social destination for Spring application developers.", page.getDescription());
		assertTrue(page.canPost());
		assertEquals(0, page.getTalkingAboutCount());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getPage_withExtraData() {
		mockServer.expect(requestTo(fbUrl("11803178355?fields=" + ALL_PAGE_FIELDS_STR)))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "OAuth someAccessToken"))
			.andRespond(withSuccess(jsonResource("page-with-extra-data"), MediaType.APPLICATION_JSON));

		Page page = facebook.pageOperations().getPage("11803178355");
		assertEquals("11803178355", page.getId());
		assertEquals("A Scanner Darkly", page.getName());
		assertEquals("https://www.facebook.com/pages/A-Scanner-Darkly/11803178355", page.getLink());
		assertNull(page.getDescription());
		Map<String, Object> extraData = page.getExtraData();
		assertEquals("This is extra data", extraData.get("extra_data"));
		assertEquals(0, page.getWereHereCount());
		assertEquals("Keanu Reeves, Robert Downey Jr., Woody Harrelson, Winona Ryder, Rory Cochrane", page.getStarring());
		assertEquals("Richard Linklater based on Philip K. Dick's novel", page.getScreenplayBy());
		assertEquals("2007", page.getReleaseDate());
		assertEquals("Steven Soderbergh and George Clooney (Executive Producers)", page.getProducedBy());
		assertTrue(page.getPlotOutline().startsWith("In the future \"seven years from now\", America has lost the war on drugs. A highly addictive and debilitating illegal drug called Substance D, distilled from small blue flowers"));
		assertEquals("Science Fiction", page.getGenre());
		assertEquals("Richard Linklater", page.getDirectedBy());
		assertEquals("Winner of Best Animation award OFCS Awards 2007", page.getAwards());
		Map<String, Object> embedded = (Map<String, Object>) extraData.get("embedded");
		assertEquals("y", embedded.get("x"));
		assertEquals(2, embedded.get("a"));
		Map<String, Object> deeper = (Map<String, Object>) embedded.get("deeper");
		assertEquals("bar", deeper.get("foo"));
		assertEquals(PriceRange.$$, page.getPriceRange());
	}
	
	@Test
	public void isPageAdmin() {
		expectFetchAccounts();
		assertFalse(facebook.pageOperations().isPageAdmin("2468013579"));
		assertTrue(facebook.pageOperations().isPageAdmin("987654321"));
		assertTrue(facebook.pageOperations().isPageAdmin("1212121212"));
	}
	
	
	@Test
	public void getAccounts() {
		expectFetchAccounts();
		List<Account> accounts = facebook.pageOperations().getAccounts();
		assertEquals(2, accounts.size());
		assertEquals("987654321", accounts.get(0).getId());
		assertEquals("Test Page", accounts.get(0).getName());
		assertEquals("Page", accounts.get(0).getCategory());
		assertEquals("pageAccessToken", accounts.get(0).getAccessToken());
		assertEquals("1212121212", accounts.get(1).getId());
		assertEquals("Test Page 2", accounts.get(1).getName());
		assertEquals("Page", accounts.get(1).getCategory());
		assertEquals("page2AccessToken", accounts.get(1).getAccessToken());
	}

	@Test
	public void getAccount() {
		expectFetchAccounts();
		Account account = facebook.pageOperations().getAccount("987654321");
		assertEquals("987654321", account.getId());
		assertEquals("Test Page", account.getName());
		assertEquals("Page", account.getCategory());
		assertEquals("pageAccessToken", account.getAccessToken());
	}

	@Test
	public void getAccount_missingAccount() throws Exception {
		expectFetchAccounts();
		assertNull(facebook.pageOperations().getAccount("BOGUS"));
	}
	
	@Test
	public void getAccessToken() {
		expectFetchAccounts();
		assertEquals("pageAccessToken", facebook.pageOperations().getAccessToken("987654321"));
	}

	@Test(expected=PageAdministrationException.class)
	public void getAccessToken_missingAccount() {
		expectFetchAccounts();
		facebook.pageOperations().getAccessToken("BOGUS");
	}

	@Test
	public void post_message() throws Exception {
		expectFetchAccounts();
		String requestBody = "message=Hello+Facebook+World&access_token=pageAccessToken";
		mockServer.expect(requestTo(fbUrl("987654321/feed")))
				.andExpect(method(POST))
				.andExpect(header("Authorization", "OAuth someAccessToken"))
				.andExpect(content().string(requestBody))
				.andRespond(withSuccess("{\"id\":\"123456_78901234\"}", MediaType.APPLICATION_JSON));
		new PostData("987654321").message("Hello Facebook World");
		assertEquals("123456_78901234", facebook.pageOperations().post(new PagePostData("987654321").message("Hello Facebook World")));
		mockServer.verify();
	}

	@Test(expected = PageAdministrationException.class)
	public void postMessage_notAdmin() throws Exception {
		expectFetchAccounts();
		facebook.pageOperations().post(new PagePostData("2468013579").message("Hello Facebook World"));
	}

	@Test
	public void postLink() throws Exception {
		expectFetchAccounts();
		String requestBody = "message=Hello+Facebook+World&link=someLink&name=some+name&caption=some+caption&description=some+description&access_token=pageAccessToken";
		mockServer.expect(requestTo(fbUrl("987654321/feed")))
				.andExpect(method(POST))
				.andExpect(header("Authorization", "OAuth someAccessToken"))
				.andExpect(content().string(requestBody))
				.andRespond(withSuccess("{\"id\":\"123456_78901234\"}", MediaType.APPLICATION_JSON));
		assertEquals("123456_78901234", facebook.pageOperations().post(new PagePostData("987654321").message("Hello Facebook World").link("someLink", null, "some name", "some caption", "some description")));
		mockServer.verify();
	}

	@Test
	public void postLink_withPicture() throws Exception {
		expectFetchAccounts();
		String requestBody = "message=Hello+Facebook+World&link=someLink&name=some+name&caption=some+caption&description=some+description&picture=somePic&access_token=pageAccessToken";
		mockServer.expect(requestTo(fbUrl("987654321/feed")))
				.andExpect(method(POST))
				.andExpect(header("Authorization", "OAuth someAccessToken"))
				.andExpect(content().string(requestBody))
				.andRespond(withSuccess("{\"id\":\"123456_78901234\"}", MediaType.APPLICATION_JSON));
		assertEquals("123456_78901234", facebook.pageOperations().post(new PagePostData("987654321").message("Hello Facebook World").link("someLink", "somePic", "some name", "some caption", "some description")));
		mockServer.verify();
	}

	@Test
	public void postMessage_withTargetAudience() throws Exception {
		expectFetchAccounts();
		String requestBody = "message=Hello+Facebook+World&targeting=%7B%27value%27%3A+%27CUSTOM%27%2C%27countries%27%3A%27PE%27%7D&access_token=pageAccessToken";
		mockServer.expect(requestTo(fbUrl("987654321/feed")))
				.andExpect(method(POST))
				.andExpect(header("Authorization", "OAuth someAccessToken"))
				.andExpect(content().string(requestBody))
				.andRespond(withSuccess("{\"id\":\"123456_78901234\"}", MediaType.APPLICATION_JSON));

		assertEquals("123456_78901234", facebook.pageOperations().post(new PagePostData("987654321").message("Hello Facebook World").targeting(new Targeting().countries("PE"))));
		mockServer.verify();
	}

	@Test(expected = PageAdministrationException.class)
	public void postLink_notAdmin() throws Exception {
		expectFetchAccounts();
		facebook.pageOperations().post(new PagePostData("2468013579").message("Hello Facebook World").link("someLink", null, "some name", "some caption", "some description"));
	}

	@Test
	public void postPhoto_noCaption() {
		expectFetchAccounts();
		mockServer.expect(requestTo(fbUrl("192837465/photos")))
			.andExpect(method(POST))
			.andExpect(header("Authorization", "OAuth someAccessToken"))
			.andRespond(withSuccess("{\"id\":\"12345\"}", MediaType.APPLICATION_JSON));
		// TODO: Match body content to ensure fields and photo are included
		Resource photo = getUploadResource("photo.jpg", "PHOTO DATA");
		String photoId = facebook.pageOperations().postPhoto("987654321", "192837465", photo);
		assertEquals("12345", photoId);
	}

	@Test
	public void postPhoto_withCaption() {
		expectFetchAccounts();
		mockServer.expect(requestTo(fbUrl("192837465/photos")))
			.andExpect(method(POST))
			.andExpect(header("Authorization", "OAuth someAccessToken"))
			.andRespond(withSuccess("{\"id\":\"12345\"}", MediaType.APPLICATION_JSON));
		// TODO: Match body content to ensure fields and photo are included
		Resource photo = getUploadResource("photo.jpg", "PHOTO DATA");
		String photoId = facebook.pageOperations().postPhoto("987654321", "192837465", photo, "Some caption");
		assertEquals("12345", photoId);
	}
	
	@Test
	public void search() {
		mockServer.expect(requestTo(fbUrl("search?q=coffee&type=place&center=33.050278%2C-96.745833&distance=5280")))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "OAuth someAccessToken"))
			.andRespond(withSuccess(jsonResource("places-list"), MediaType.APPLICATION_JSON));
		List<Page> places = facebook.pageOperations().searchPlaces("coffee", 33.050278, -96.745833, 5280);
		assertEquals(2, places.size());
		assertEquals("117723491586638", places.get(0).getId());
		assertEquals("True Brew Coffee & Espresso Service", places.get(0).getName());
		assertEquals("Local business", places.get(0).getCategory());
		assertEquals("542 Haggard St", places.get(0).getLocation().getStreet());
		assertEquals("Plano", places.get(0).getLocation().getCity());
		assertEquals("TX", places.get(0).getLocation().getState());
		assertEquals("United States", places.get(0).getLocation().getCountry());
		assertEquals("75074-5529", places.get(0).getLocation().getZip());
		assertEquals(33.026239, places.get(0).getLocation().getLatitude(), 0.00001);
		assertEquals(-96.707089, places.get(0).getLocation().getLongitude(), 0.00001);
		assertEquals("169020919798274", places.get(1).getId());
		assertEquals("Starbucks Coffee", places.get(1).getName());
		assertEquals("Local business", places.get(1).getCategory());
		assertNull(places.get(1).getLocation().getStreet());
		assertEquals("Plano", places.get(1).getLocation().getCity());
		assertEquals("TX", places.get(1).getLocation().getState());
		assertEquals("United States", places.get(1).getLocation().getCountry());
		assertNull(places.get(1).getLocation().getZip());
		assertEquals(33.027734, places.get(1).getLocation().getLatitude(), 0.00001);
		assertEquals(-96.795133, places.get(1).getLocation().getLongitude(), 0.00001);		
	}

	// private helpers
	
	private void expectFetchAccounts() {
		mockServer.expect(requestTo(fbUrl("me/accounts")))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "OAuth someAccessToken"))
				.andRespond(withSuccess(jsonResource("accounts"), MediaType.APPLICATION_JSON));
	}

	private Resource getUploadResource(final String filename, String content) {
		Resource video = new ByteArrayResource(content.getBytes()) {
			public String getFilename() throws IllegalStateException {
				return filename;
			};
		};
		return video;
	}

	static final String[] ALL_PAGE_FIELDS = {
			"id", "about", "access_token", /*"business", */"can_checkin", "can_post", "category", "category_list", "checkins",
			"country_page_likes", "current_location", "description", "description_html", "emails", "engagement",
			"fan_count", "general_info", "global_brand_page_name", "global_brand_root_id", "has_added_app",
			"instagram_business_account", "link", "name", "new_like_count", "parent_page", "phone",
			"promotion_eligible", "promotion_ineligible_reason", "rating_count", "single_line_address",
			"talking_about_count", "unread_message_count", "unread_notif_count", "unseen_message_count", "username",
			"verification_status", "voip_info", "website", "were_here_count",
			"affiliation", "artists_we_like", "attire", "awards", "band_interests", "band_members", "best_page",
			"birthday", "booking_agent", "built", "company_overview", "culinary_team", "directed_by", "features",
			"food_styles", "founded", "general_manager", "genre", "hometown", "influences", "location", "members",
			"mission", "mpg", "network", "overall_star_rating", "parking", "payment_options", "personal_info",
			"personal_interests", "pharma_safety_info", "place_type", "plot_outline", "press_contact", "price_range",
			"produced_by", "products", "public_transit", "record_label", "release_date",
			"restaurant_services", "restaurant_specialties", "schedule", "screenplay_by", "season", "starring",
			"store_number", "studio", "written_by", "offer_eligible",
			"app_id", "hours", "is_community_page", "is_permanently_closed", "is_published", "is_unclaimed"
	};

	private static final String ALL_PAGE_FIELDS_STR = StringUtils.arrayToCommaDelimitedString(ALL_PAGE_FIELDS).replace(",", "%2C").replace("(", "%28").replace(")", "%29").replace("{", "%7B").replace("}", "%7D");


}
