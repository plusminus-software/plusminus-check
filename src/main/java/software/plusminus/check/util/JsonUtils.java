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
import com.google.gson.JsonParser;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import lombok.experimental.UtilityClass;
import software.plusminus.check.exception.JsonException;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class for json processing.
 *
 * @author Taras Shpek
 */
@UtilityClass
public class JsonUtils {

    public static final int MAX_UNPRETIFIED_JSON_LENGTH = 200;
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
    
    public <T> List<T> fromJsonList(String json, Class<T[]> type) {
        T[] array;
        try {
            array = jsonMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new JsonException(e);
        }
        return Arrays.asList(array);
    }
    
    public String pretty(String json) {
        if (!json.contains("{") && json.length() < MAX_UNPRETIFIED_JSON_LENGTH) {
            return json;
        }
        JsonElement je = JsonParser.parseString(json);
        return prettyMapper.toJson(je);
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

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setDateFormat(new ISO8601DateFormat());
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }
    
    @JsonIdentityInfo(generator = JSOGGenerator.class)
    private static class JsogMixin {
    }
}
