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

import java.io.Serializable;

@SuppressWarnings("serial")
public class InstagramUser extends FacebookObject implements Serializable {

	private String id;

	private String biography;

	private String igId;

	private Integer followersCount;

	private Integer followsCount;

	private Integer mediaCount;

	private String name;

	private String profilePictureUrl;

	private String username;

	private String website;

	public String getId() {
		return id;
	}

	public String getIgId() {
		return igId;
	}

	public Integer getFollowersCount() {
		return followersCount;
	}

	public Integer getFollowsCount() {
		return followsCount;
	}

	public Integer getMediaCount() {
		return mediaCount;
	}

	public String getName() {
		return name;
	}

	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}

	public String getUsername() {
		return username;
	}

	public String getWebsite() {
		return website;
	}

	public String getBiography() {
		return biography;
	}
}
