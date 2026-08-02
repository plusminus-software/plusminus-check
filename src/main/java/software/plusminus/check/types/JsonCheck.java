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
package software.plusminus.check.types;

import software.plusminus.check.util.JsonUtil;
import software.plusminus.util.ResourceUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

/**
 * Json checker.
 *
 * <p>Field names are dot-separated paths into nested objects, for instance
 * {@code "user.address.city"}. Fields checked with {@code hasField} or listed in
 * {@code ignoringFields} are masked on both sides before {@code is} compares
 * the documents, so volatile values do not have to appear in the expected json.
 *
 * @author Taras Shpek
 */
@SuppressWarnings({"java:S2160", "unchecked"})
public class JsonCheck extends AbstractCheck<String> {

    private static final String MASK = "SEPARATELY CHECKED";
    private static final String FIELD = "Field ";

    private Set<String> maskedFields = new HashSet<>();
    private boolean ignoreFieldsOrder;

    protected JsonCheck(@Nullable String actual) {
        super(actual);
        assertJson(actual);
    }

    protected JsonCheck(@Nullable String actual, List<String> levels) {
        super(actual, levels);
        assertJson(actual);
    }

    @Override
    public void is(String expected) {
        if (expected == null) {
            fail(null);
        }
        if (!JsonUtil.isJson(expected)
                && ResourceUtils.isResource(expected)) {
            expected = ResourceUtils.toString(expected);
        }
        if (!JsonUtil.isJson(expected)) {
            fail("is not json", "is json");
        }
        String actual = actual();
        if (!maskedFields.isEmpty()) {
            actual = maskFields(actual);
            expected = maskFields(expected);
        }
        actual = JsonUtil.pretty(actual);
        expected = ignoreFieldsOrder ? JsonUtil.prettyOrdered(expected, actual) : JsonUtil.pretty(expected);
        if (!actual.equals(expected)) {
            fail(actual, expected);
        }
    }

    public JsonCheck hasField(String fieldName) {
        return hasField(fieldName, check -> { });
    }

    public JsonCheck hasField(String fieldName, Consumer<ObjectCheck<?>> fieldValueChecker) {
        assertJsonObject();
        Map<Object, Object> jsonMap = JsonUtil.fromJson(actual(), Map.class);
        String[] segments = segments(fieldName);
        Map<Object, Object> parent = parent(jsonMap, segments);
        String field = segments[segments.length - 1];
        if (parent == null || !parent.containsKey(field)) {
            fail(FIELD + fieldName + " is missed", FIELD + fieldName + " is present");
        }
        maskedFields.add(fieldName);
        List<String> fieldLevels = new ArrayList<>(levels());
        for (String segment : segments) {
            fieldLevels.add("." + segment);
        }
        fieldValueChecker.accept(new ObjectCheck<>(parent.get(field), fieldLevels));
        return this;
    }

    public JsonCheck doesNotHaveField(String fieldName) {
        assertJsonObject();
        Map<Object, Object> jsonMap = JsonUtil.fromJson(actual(), Map.class);
        String[] segments = segments(fieldName);
        Map<Object, Object> parent = parent(jsonMap, segments);
        if (parent != null && parent.containsKey(segments[segments.length - 1])) {
            fail(FIELD + fieldName + " is present", FIELD + fieldName + " is absent");
        }
        return this;
    }

    @CheckReturnValue
    public JsonCheck ignoringFields(String... fieldNames) {
        assertJsonObject();
        maskedFields.addAll(Arrays.asList(fieldNames));
        return this;
    }

    @CheckReturnValue
    public JsonCheck ignoringFieldsOrder() {
        this.ignoreFieldsOrder = true;
        return this;
    }

    private String maskFields(String json) {
        Map<Object, Object> jsonMap = JsonUtil.fromJson(json, Map.class);
        maskedFields.forEach(fieldName -> {
            String[] segments = segments(fieldName);
            Map<Object, Object> parent = parent(jsonMap, segments);
            String field = segments[segments.length - 1];
            if (parent != null && parent.containsKey(field)) {
                parent.put(field, MASK);
            }
        });
        return JsonUtil.toJson(jsonMap);
    }

    @Nullable
    private Map<Object, Object> parent(Map<Object, Object> jsonMap, String[] segments) {
        Map<Object, Object> current = jsonMap;
        for (int i = 0; i < segments.length - 1; i++) {
            Object value = current.get(segments[i]);
            if (!(value instanceof Map)) {
                return null;
            }
            current = (Map<Object, Object>) value;
        }
        return current;
    }

    private String[] segments(String fieldName) {
        return fieldName.split("\\.");
    }

    private void assertJsonObject() {
        if (actual().startsWith("[")) {
            fail("is not a json object", "is a json object");
        }
    }

    private void assertJson(String actual) {
        if (actual == null || !JsonUtil.isJson(actual)) {
            fail("is not json", "is json");
        }
    }
}
