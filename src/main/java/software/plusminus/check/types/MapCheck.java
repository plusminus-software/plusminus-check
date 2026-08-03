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

import software.plusminus.check.util.StringUtil;
import software.plusminus.util.ResourceUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

/**
 * Map (including HashMap, TreeMap etc) checker.
 * Converts Map to string (in Json or Jsog formats) before comparing.
 *
 * <p>The {@code is} overloads take alternating keys and values, up to ten pairs,
 * the way {@code Map.of} does. Past that, or when the pairs are built elsewhere,
 * use the entry overload with {@code Checks.entry(key, value)}.
 *
 * @author Taras Shpek
 */
@SuppressWarnings({"checkstyle:ParameterNumber", "java:S107", "java:S2160"})
public class MapCheck<K, V> extends AbstractObjectCheck<Map<K, V>> implements CoverageCheck {

    private final CheckedParts checkedParts = new CheckedParts("keys");

    public MapCheck(@Nullable Map<K, V> actual) {
        super(actual);
    }

    public MapCheck(@Nullable Map<K, V> actual, List<String> levels) {
        super(actual, levels);
    }

    /**
     * Asserts every key of the map was checked, by {@code valueAt}, {@code containsKey},
     * {@code containsEntry}.
     */
    @Override
    public void allChecked() {
        isNotNull();
        Set<String> required = new LinkedHashSet<>();
        actual().keySet().forEach(key -> required.add(StringUtil.toString(key)));
        checkedParts.verify(required, this::fail);
    }

    @Override
    public MapCheck<K, V> isNotNull() {
        super.isNotNull();
        return this;
    }

    @Override
    public MapCheck<K, V> isNot(Map<K, V> unexpected) {
        super.isNot(unexpected);
        return this;
    }

    @Override
    public MapCheck<K, V> isSameTypeAs(Map<K, V> expected) {
        super.isSameTypeAs(expected);
        return this;
    }

    @Override
    public MapCheck<K, V> isType(Class<?> expectedType) {
        super.isType(expectedType);
        return this;
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

    public void is(K key1, V value1, K key2, V value2, K key3, V value3,
                   K key4, V value4) {
        checkMap(key1, value1, key2, value2, key3, value3, key4, value4);
    }

    public void is(K key1, V value1, K key2, V value2, K key3, V value3,
                   K key4, V value4, K key5, V value5) {
        checkMap(key1, value1, key2, value2, key3, value3, key4, value4, key5, value5);
    }

    public void is(K key1, V value1, K key2, V value2, K key3, V value3,
                   K key4, V value4, K key5, V value5, K key6, V value6) {
        checkMap(key1, value1, key2, value2, key3, value3, key4, value4, key5, value5,
                key6, value6);
    }

    public void is(K key1, V value1, K key2, V value2, K key3, V value3,
                   K key4, V value4, K key5, V value5, K key6, V value6,
                   K key7, V value7) {
        checkMap(key1, value1, key2, value2, key3, value3, key4, value4, key5, value5,
                key6, value6, key7, value7);
    }

    public void is(K key1, V value1, K key2, V value2, K key3, V value3,
                   K key4, V value4, K key5, V value5, K key6, V value6,
                   K key7, V value7, K key8, V value8) {
        checkMap(key1, value1, key2, value2, key3, value3, key4, value4, key5, value5,
                key6, value6, key7, value7, key8, value8);
    }

    public void is(K key1, V value1, K key2, V value2, K key3, V value3,
                   K key4, V value4, K key5, V value5, K key6, V value6,
                   K key7, V value7, K key8, V value8, K key9, V value9) {
        checkMap(key1, value1, key2, value2, key3, value3, key4, value4, key5, value5,
                key6, value6, key7, value7, key8, value8, key9, value9);
    }

    public void is(K key1, V value1, K key2, V value2, K key3, V value3,
                   K key4, V value4, K key5, V value5, K key6, V value6,
                   K key7, V value7, K key8, V value8, K key9, V value9,
                   K key10, V value10) {
        checkMap(key1, value1, key2, value2, key3, value3, key4, value4, key5, value5,
                key6, value6, key7, value7, key8, value8, key9, value9, key10, value10);
    }

    /**
     * Compares the map against the expected entries, for any number of them.
     * Build the entries with {@code Checks.entry(key, value)}.
     *
     * @param entries expected entries
     */
    @SafeVarargs
    public final void is(Map.Entry<K, V>... entries) {
        Object[] keyValues = new Object[entries.length * 2];
        for (int i = 0; i < entries.length; i++) {
            keyValues[i * 2] = entries[i].getKey();
            keyValues[i * 2 + 1] = entries[i].getValue();
        }
        checkMap(keyValues);
    }

    public MapCheck<K, V> containsKey(K key) {
        isNotNull();
        if (!actual().containsKey(key)) {
            fail(keysDescription(), "contains key " + StringUtil.toString(key));
        }
        checkedParts.mark(StringUtil.toString(key));
        return this;
    }

    public MapCheck<K, V> doesNotContainKey(K key) {
        isNotNull();
        if (actual().containsKey(key)) {
            fail(keysDescription(), "does not contain key " + StringUtil.toString(key));
        }
        return this;
    }

    /**
     * Asserts the map contains the entry. Values are compared structurally, the same way
     * {@code is} compares them, so this works on types that do not implement {@code equals}.
     *
     * @param key expected key
     * @param value expected value
     * @return this check, for chaining
     */
    public MapCheck<K, V> containsEntry(K key, V value) {
        containsKey(key);
        if (!sameValue(actual().get(key), value)) {
            fail(StringUtil.toString(actual().get(key)),
                    "value " + StringUtil.toString(value) + " for key " + StringUtil.toString(key));
        }
        return this;
    }

    /**
     * Asserts at least one value matches. Values are compared structurally,
     * as in {@link #containsEntry}.
     *
     * @param value expected value
     * @return this check, for chaining
     */
    public MapCheck<K, V> containsValue(V value) {
        isNotNull();
        for (V actualValue : actual().values()) {
            if (sameValue(actualValue, value)) {
                return this;
            }
        }
        fail("does not contain " + StringUtil.toString(value),
                "contains value " + StringUtil.toString(value));
        return this;
    }

    /**
     * Descends into the value of an entry. Fails if the key is absent.
     *
     * @param key key of the entry to check
     * @return a check of the value, linked back to this check
     */
    @CheckReturnValue
    public LinkedCheck<V, ObjectCheck<V>, MapCheck<K, V>> valueAt(K key) {
        containsKey(key);
        List<String> valueLevels = new ArrayList<>(levels());
        valueLevels.add(level(key));
        return new LinkedCheck<>(new ObjectCheck<>(actual().get(key), valueLevels), this);
    }

    private boolean sameValue(@Nullable V actualValue, @Nullable V expectedValue) {
        if (actualValue == null || expectedValue == null) {
            return actualValue == expectedValue;
        }
        return predicate(actualValue, levels(), ObjectCheck::new, c -> c.isLike(expectedValue));
    }

    private String keysDescription() {
        return "keys are " + StringUtil.toString(new ArrayList<>(actual().keySet()));
    }

    private String level(K key) {
        if (key instanceof CharSequence) {
            return "." + key;
        }
        return "[" + StringUtil.toString(key) + "]";
    }

    private void checkMap(Object... expectedKeyValues) {
        Map<Object, Object> expected = prepareExpectedMap(toMap(expectedKeyValues));
        isLike(expected);
    }

    private Map<Object, Object> prepareExpectedMap(Map<Object, Object> expected) {
        Map<Object, Object> result = new LinkedHashMap<>();
        for (Map.Entry<Object, Object> e : expected.entrySet()) {
            result.put(resolveResource(e.getKey()), resolveResource(e.getValue()));
        }
        return result;
    }

    private Object resolveResource(Object value) {
        if (value instanceof String) {
            String string = (String) value;
            if (ResourceUtils.isResource(string)) {
                return ResourceUtils.toString(string);
            }
        }
        return value;
    }

    private Map<Object, Object> toMap(Object... keyValues) {
        Map<Object, Object> map = new LinkedHashMap<>();
        for (int i = 0; i < keyValues.length; i = i + 2) {
            map.put(keyValues[i], keyValues[i + 1]);
        }
        return map;
    }
}
