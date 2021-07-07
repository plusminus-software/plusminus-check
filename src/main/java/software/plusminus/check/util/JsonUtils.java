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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import lombok.experimental.UtilityClass;
import software.plusminus.check.exception.JsonException;

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
        jsonMapper = new ObjectMapper();
        jsogMapper = new ObjectMapper();
        jsogMapper.addMixIn(Object.class, JsogMixin.class);
        prettyMapper = new GsonBuilder().setPrettyPrinting().create();
    }
    
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

    @JsonIdentityInfo(generator = JSOGGenerator.class)
    private static class JsogMixin {
    }
    
}
