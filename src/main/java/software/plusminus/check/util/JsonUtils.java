/*
 * Copyright 2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package software.plusminus.check.util;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import lombok.experimental.UtilityClass;
import software.plusminus.check.exception.JsonException;
import software.plusminus.util.ResourceUtils;
import software.plusminus.util.StreamUtils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Utility class for json processing.
 *
 * @author Taras Shpek
 */
@SuppressWarnings("checkstyle:ClassDataAbstractionCoupling")
@UtilityClass
public class JsonUtils {

    private ObjectMapper jsonMapper;
    private ObjectMapper jsogMapper;
    private Gson prettyMapper;
    
    static {
        jsonMapper = createObjectMapper();
        jsogMapper = createObjectMapper();
        jsogMapper.addMixIn(Object.class, JsogMixin.class);
        prettyMapper = new GsonBuilder().setPrettyPrinting().create();
    }
    
    /* Had to suppress PMD.UselessParentheses to incease a code readability
       https://stackoverflow.com/questions/34911230/pmd-uselessparentheses-violation */
    @SuppressWarnings("PMD.UselessParentheses")
    public boolean isJson(String json) {
        return (json.startsWith("[") && json.endsWith("]")) 
                || (json.startsWith("{") && json.endsWith("}"));
    }
    
    public String toJson(Object object) {
        try {
            return jsonMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new JsonException(e);
        }
    }
    
    public String toJsog(Object object) {
        try {
            return jsogMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new JsonException(e);
        }
    }
    
    public <T> T fromJson(String json, Class<T> type) {
        try {
            json = readJson(json);
            return jsonMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new JsonException(e);
        }
    }
    
    public <T> List<T> fromJsonList(String json, Class<T[]> type) {
        T[] array;
        try {
            json = readJson(json);
            array = jsonMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new JsonException(e);
        }
        return Arrays.asList(array);
    }
    
    public String pretty(String json) {
        if (!json.contains("{")) {
            return json;
        }
        JsonElement jsonElement = JsonParser.parseString(json);
        return prettyMapper.toJson(jsonElement);
    }
    
    public String prettyOrdered(String targetJson, String baseJson) {
        if (!targetJson.contains("{")) {
            return targetJson;
        }
        JsonElement baseJsonElement = JsonParser.parseString(baseJson);
        JsonElement targetJsonElement = JsonParser.parseString(targetJson);
        if (baseJsonElement.isJsonObject() && targetJsonElement.isJsonObject()) {
            targetJsonElement = orderJsonObject(targetJsonElement.getAsJsonObject(),
                    baseJsonElement.getAsJsonObject());
        }
        return prettyMapper.toJson(targetJsonElement);
    }
    
    public String prettyAlternative(String json) {
        try {
            Object jsonObject = jsonMapper.readValue(json, Object.class);
            return jsonMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(jsonObject);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public String readJson(String json) {
        if (isJson(json)) {
            return json;
        }
        if (ResourceUtils.isResource(json)) {
            return ResourceUtils.toString(json);
        }
        throw new AssertionError("Unknown json: " + json);
    }
    
    private ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setDateFormat(new ISO8601DateFormat());
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }

    private JsonObject orderJsonObject(JsonObject target, JsonObject base) {
        Map<String, JsonElement> baseFields = jsonObjectToMap(base);
        Map<String, JsonElement> targetFields = jsonObjectToMap(target);
        JsonObject result = new JsonObject();
        for (Map.Entry<String, JsonElement> baseField : baseFields.entrySet()) {
            JsonElement targetFieldElement = targetFields.get(baseField.getKey());
            if (targetFieldElement != null) {
                if (baseField.getValue().isJsonObject()
                        && targetFieldElement.isJsonObject()) {
                    targetFieldElement = orderJsonObject(targetFieldElement.getAsJsonObject(),
                            baseField.getValue().getAsJsonObject());
                }
                result.add(baseField.getKey(), targetFieldElement);
                targetFields.remove(baseField.getKey());
            }
        }
        targetFields.forEach(result::add);
        return result;
    }
    
    private static Map<String, JsonElement> jsonObjectToMap(JsonObject jsonObject) {
        return jsonObject.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        StreamUtils.noDuplicatesMergeFunction(), LinkedHashMap::new));
    }
    
    @JsonIdentityInfo(generator = JSOGGenerator.class)
    private static class JsogMixin {
    }
}
