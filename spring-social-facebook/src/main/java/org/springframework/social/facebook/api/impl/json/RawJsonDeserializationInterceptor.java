package org.springframework.social.facebook.api.impl.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.jonpeterson.jackson.module.interceptor.JsonInterceptor;

public class RawJsonDeserializationInterceptor implements JsonInterceptor {
    public JsonNode intercept(JsonNode jsonNode, JsonNodeFactory jsonNodeFactory) {
        ((ObjectNode) jsonNode).set("rawJson", ((ObjectNode) jsonNode).deepCopy());
        return jsonNode;
    }
}
