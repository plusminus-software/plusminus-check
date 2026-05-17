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

import software.plusminus.util.ResourceUtils;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

/**
 * Map (including HashMap, TreeMap etc) checker.
 * Converts Map to string (in Json or Jsog formats) before comparing.
 *
 * @author Taras Shpek
 */
@SuppressWarnings("checkstyle:ParameterNumber")
public class MapCheck<K, V> extends AbstractObjectCheck<Map<K, V>> {

    public MapCheck(@Nullable Map<K, V> actual) {
        super(actual);
    }

    public MapCheck(@Nullable Map<K, V> actual, List<String> levels) {
        super(actual, levels);
    }

    @Override
    public void isEqual(Map<K, V> expected) {
        super.isEqual(expected);
    }

    @Override
    public void isType(Map<K, V> expected) {
        super.isType(expected);
    }

    @Override
    public void isType(Class<?> expectedType) {
        super.isType(expectedType);
    }

    public void isEmpty() {
        isNotNull();
        if (!actual().isEmpty()) {
            fail("contains " + actual().size() + " elements", "empty");
        }
    }

    public MapCheck<K, V> isNotEmpty() {
        isNotNull();
        if (actual().isEmpty()) {
            fail("empty", "not empty");
        }
        return this;
    }

    public MapCheck<K, V> hasSize(int expectedSize) {
        isNotNull();
        if (actual().size() != expectedSize) {
            fail("size is " + actual().size(), "size is " + expectedSize);
        }
        return this;
    }

    public void is(K key, V value) {
        checkMap(key, value);
    }

    public void is(K key1, V value1, K key2, V value2) {
        checkMap(key1, value1, key2, value2);
    }

    public void is(K key1, V value1, K key2, V value2, K key3, V value3) {
        checkMap(key1, value1, key2, value2, key3, value3);
    }

    @SuppressWarnings("java:S107")
    public void is(K key1, V value1, K key2, V value2, K key3, V value3,
                   K key4, V value4) {
        checkMap(key1, value1, key2, value2, key3, value3, key4, value4);
    }

    private void checkMap(Object... expectedKeyValues) {
        Map<Object, Object> expected = toMap(expectedKeyValues);
        expected = prepareExpectedMap(expected);
        isLike(expected);
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
