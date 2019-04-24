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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.social.UncategorizedApiException;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.PagingParameters;
import org.springframework.social.facebook.api.impl.json.FacebookModule;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.JsonNode;

public class PagedListUtils {

	public static <T> PagedList<T> pagify(Class<T> type, JsonNode jsonNode) {
		List<T> data = deserializeDataList(jsonNode.get("data"), type);
		if (!jsonNode.has("paging")) {
			return new PagedList<T>(data, null, null);
		}

		JsonNode pagingNode = jsonNode.get("paging");
		PagingParameters previousPage = getPagedListParameters(pagingNode, "previous");
		PagingParameters nextPage = getPagedListParameters(pagingNode, "next");
		if (nextPage == null && previousPage == null && pagingNode != null && pagingNode.has("cursors")) {
			JsonNode cursorNode = pagingNode.get("cursors");
			if (cursorNode.has("after")) {
				nextPage = new PagingParameters(null, null, null, null,
						cursorNode.get("after").asText(), null);;
			}
			if (cursorNode.has("before")) {
				previousPage = new PagingParameters(null, null, null, null,
						null, cursorNode.get("before").asText());;
			}
		}

		Integer totalCount = null;
		if (jsonNode.has("summary")) {
			JsonNode summaryNode = jsonNode.get("summary");
			if (summaryNode.has("total_count")) {
				totalCount = summaryNode.get("total_count").intValue();
			}
		}

		return new PagedList<T>(data, previousPage, nextPage, totalCount);
	}

	public static PagingParameters getPagedListParameters(JsonNode pagingNode, String pageKey) {
		if (pagingNode == null || pagingNode.get(pageKey) == null) {
			return null;
		}
		String pageNode = pagingNode.get(pageKey).textValue();
		String limitString = extractParameterValueFromUrl(pageNode, "limit");
		String sinceString = extractParameterValueFromUrl(pageNode, "since");
		String untilString = extractParameterValueFromUrl(pageNode, "until");
		String offsetString = extractParameterValueFromUrl(pageNode, "offset");
		String after = extractEncodedParameterValueFromUrl(pageNode, "after");
		String before = extractEncodedParameterValueFromUrl(pageNode, "before");
		String pagingToken = extractEncodedParameterValueFromUrl(pageNode, "__paging_token");
		
		return new PagingParameters(
				limitString != null ? Integer.valueOf(limitString) : null, 
				offsetString != null ? Integer.valueOf(offsetString) : null,
				sinceString != null ? Long.valueOf(sinceString) : null, 
				untilString != null ? Long.valueOf(untilString) : null,
				after, before, pagingToken, pageNode);
	}
	
	public static MultiValueMap<String, String> getPagingParameters(PagingParameters pagedListParameters) {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		if (pagedListParameters.getOffset() != null) {
			parameters.add("offset", String.valueOf(pagedListParameters.getOffset()));
		}
		if (pagedListParameters.getLimit() != null) {
			parameters.add("limit", String.valueOf(pagedListParameters.getLimit()));
		}
		if (pagedListParameters.getSince() != null) {
			parameters.add("since", String.valueOf(pagedListParameters.getSince()));
		}
		if (pagedListParameters.getUntil() != null) {
			parameters.add("until", String.valueOf(pagedListParameters.getUntil()));
		}
		if (pagedListParameters.getBefore() != null) {
			parameters.add("before", String.valueOf(pagedListParameters.getBefore()));
		}
		if (pagedListParameters.getAfter() != null) {
			parameters.add("after", String.valueOf(pagedListParameters.getAfter()));
		}
		return parameters;
	}

	private static String extractEncodedParameterValueFromUrl(String url, String paramName) {
		try {
			String value = extractParameterValueFromUrl(url, paramName);
			return value != null ? URLDecoder.decode(value, "UTF-8") : null;
		} catch (UnsupportedEncodingException e) {
			// shouldn't happen
			return null;
		}
	}
	
	private static String extractParameterValueFromUrl(String url, String paramName) {
		int queryStart = url.indexOf('?') >= 0 ? url.indexOf('?') : 0;
		int startPos = url.indexOf(paramName + "=", queryStart);
		if (startPos == -1) {
			return null;
		}
		int ampPos = url.indexOf("&", startPos);
		if (ampPos >= 0) {
			return url.substring(startPos + paramName.length() + 1, ampPos);
		}
		return url.substring(startPos + paramName.length() + 1);
	}

	@SuppressWarnings("unchecked")
	private static <T> List<T> deserializeDataList(JsonNode jsonNode, final Class<T> elementType) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new FacebookModule());
		try {
			CollectionType listType = TypeFactory.defaultInstance().constructCollectionType(List.class, elementType);
			return (List<T>) mapper.readerFor(listType).readValue(jsonNode.toString());
		} catch (IOException e) {
			throw new UncategorizedApiException("facebook", "Error deserializing data from Facebook: " + e.getMessage(), e);
		}
	}

}
