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

import software.plusminus.check.util.CheckUtils;
import software.plusminus.util.ResourceUtils;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Map (including HashMap, TreeMap etc) checker.
 * Converts Map to to string (in Json or Jsog formats) before comparing.
 *
 * @author Taras Shpek
 */
@SuppressWarnings("checkstyle:ParameterNumber")
public class MapCheck<K, V> extends AbstractCheck {

    private Map<K, V> actual;

    public MapCheck(Map<K, V> actual) {
        this.actual = actual;
    }

    public void hasSize(int expected) {
        if (actual.size() != expected) {
            fail("size is " + expected, "size is " + actual.size());
        }
    }

    public void isEmpty() {
        if (!actual.isEmpty()) {
            fail("to be empty", "contains " + actual.size() + " elements");
        }
    }
    
    public void is(Map<K, V> expected) {
        checkJson(CheckUtils.toJson(expected), CheckUtils.toJson(actual));
    }
    
    public void is(String expected) {
        if (ResourceUtils.isResource(expected)) {
            expected = ResourceUtils.toString(expected);
        }
        checkJson(expected, CheckUtils.toJson(actual));
    }

    public void is(Object key, Object value) {
        checkMap(key, value);
    }

    public void is(Object key1, Object value1, Object key2, Object value2) {
        checkMap(key1, value1, key2, value2);
    }

    public void is(Object key1, Object value1, Object key2, Object value2, Object key3, Object value3) {
        checkMap(key1, value1, key2, value2, key3, value3);
    }

    public void is(Object key1, Object value1, Object key2, Object value2, Object key3, Object value3,
                   Object key4, Object value4) {
        checkMap(key1, value1, key2, value2, key3, value3, key4, value4);
    }

    private void checkMap(Object... expectedKeyValues) {
        Map<Object, Object> expected = toMap(expectedKeyValues);
        expected = prepareExpectedMap(expected);
        checkJson(CheckUtils.toJson(prepareExpectedMap(expected)), CheckUtils.toJson(actual));
    }

    private Map<Object, Object> prepareExpectedMap(Map<Object, Object> expected) {
        return expected.entrySet().stream()
                .map(e -> {
                    if (e.getKey().getClass() == String.class) {
                        String keyString = (String) e.getKey();
                        if (ResourceUtils.isResource(keyString)) {
                            e = new AbstractMap.SimpleEntry<>(ResourceUtils.toString(keyString), e.getValue());
                        }
                    }
                    
                    if (e.getValue().getClass() == String.class) {
                        String valueString = (String) e.getKey();
                        if (ResourceUtils.isResource(valueString)) {
                            e = new AbstractMap.SimpleEntry<>(e.getKey(), ResourceUtils.toString(valueString));
                        }
                    }
                    return e;
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    
    private Map<Object, Object> toMap(Object... keyValues) {
        Map<Object, Object> map = new HashMap<>();
        for (int i = 0; i < keyValues.length; i = i + 2) {
            map.put(keyValues[i], keyValues[i + 1]);
        }
        return map;
    }

}
