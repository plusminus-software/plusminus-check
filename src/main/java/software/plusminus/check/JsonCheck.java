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
package software.plusminus.check;

import software.plusminus.check.util.JsonUtil;

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
 * @author Taras Shpek
 */
@SuppressWarnings("java:S2160")
@CheckReturnValue
public class JsonCheck extends AbstractObjectCheck<String> {

    private Set<String> separatelyCheckedFields = new HashSet<>();
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
        String actual = actual();
        if (!separatelyCheckedFields.isEmpty()) {
            actual = replaceSeparatelyCheckedFields(actual);
            expected = replaceSeparatelyCheckedFields(expected);
        }
        actual = JsonUtil.pretty(actual);
        expected = ignoreFieldsOrder ? JsonUtil.prettyOrdered(expected, actual) : JsonUtil.pretty(expected);
        if (!actual.equals(expected)) {
            fail(actual, expected);
        }
    }
    
    public JsonCheck hasField(String fieldName, Consumer<ObjectCheck<?>> fieldValueChecker) {
        separatelyCheckedFields.add(fieldName);
        Map<Object, Object> actualMap = JsonUtil.fromJson(actual(), Map.class);
        if (!actualMap.containsKey(fieldName)) {
            fail("Field " + fieldName + " is present", "Field " + fieldName + " is missed");
        }
        Object value = actualMap.get(fieldName);
        ObjectCheck<?> objectCheck = new ObjectCheck<>(value);
        fieldValueChecker.accept(objectCheck);
        return this;
    }

    @Override
    public void isEqual(String expected) {
        super.isEqual(expected);
    }

    public JsonCheck ignoringFieldsOrder() {
        this.ignoreFieldsOrder = true;
        return this;
    }
    
    private String replaceSeparatelyCheckedFields(String json) {
        Map<Object, Object> jsonMap = JsonUtil.fromJson(json, Map.class);
        separatelyCheckedFields.forEach(field -> jsonMap.put(field, "SEPARATELY CHECKED"));
        return JsonUtil.toJson(jsonMap);
    }

    private void assertJson(String actual) {
        if (actual == null || !JsonUtil.isJson(actual)) {
            fail("is not json", "is json");
        }
    }
}
