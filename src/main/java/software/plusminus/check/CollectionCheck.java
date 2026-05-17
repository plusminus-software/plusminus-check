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

import software.plusminus.check.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@SuppressWarnings("java:S2160")
@CheckReturnValue
public class CollectionCheck<T, C extends Collection<T>, E extends AbstractCheck<T>>
        extends AbstractObjectCheck<C> {

    private BiFunction<T, List<String>, E> elementCheck;

    public CollectionCheck(@Nullable C actual, BiFunction<T, List<String>, E> elementCheck) {
        super(actual);
        this.elementCheck = elementCheck;
    }

    public CollectionCheck(@Nullable C actual, List<String> levels, BiFunction<T, List<String>, E> elementCheck) {
        super(actual, levels);
        this.elementCheck = elementCheck;
    }

    public void isEmpty() {
        isNotNull();
        if (!actual().isEmpty()) {
            fail("not empty", "empty");
        }
    }

    public CollectionCheck<T, C, E> isNotEmpty() {
        isNotNull();
        if (actual().isEmpty()) {
            fail("empty", "not empty");
        }
        return this;
    }

    public CollectionCheck<T, C, E> hasSize(int expectedSize) {
        isNotNull();
        if (actual().size() != expectedSize) {
            fail("size is " + actual().size(), "size is " + expectedSize);
        }
        return this;
    }

    public CollectionCheck<T, C, E> contains(Object... expectedElements) {
        isNotNull();
        List<T> actualElements = new ArrayList<>(actual());
        List<Object> expectedElementList = new ArrayList<>(Arrays.asList(expectedElements));
        removeIntersections(actualElements, expectedElementList);
        if (!expectedElementList.isEmpty()) {
            fail("does not contain " + StringUtil.toString(expectedElementList), "contains all elements");
        }
        return this;
    }

    @SafeVarargs
    public final CollectionCheck<T, C, E> contains(Consumer<E>... elementChecks) {
        isNotNull();
        List<T> actualElements = new ArrayList<>(actual());
        List<Consumer<E>> remainingChecks = new ArrayList<>(Arrays.asList(elementChecks));
        Iterator<Consumer<E>> checkIterator = remainingChecks.iterator();
        while (checkIterator.hasNext()) {
            Consumer<E> check = checkIterator.next();
            Iterator<T> elementIterator = actualElements.iterator();
            while (elementIterator.hasNext()) {
                T element = elementIterator.next();
                if (predicate(element, levels(), elementCheck, check)) {
                    elementIterator.remove();
                    checkIterator.remove();
                    break;
                }
            }
        }
        if (!remainingChecks.isEmpty()) {
            fail("does not contain " + remainingChecks.size() + " matching element(s)",
                    "contains elements matching all checks");
        }
        return this;
    }

    public void containsExactly(Object... expectedElements) {
        isNotNull();
        List<T> actualElements = new ArrayList<>(actual());
        List<Object> expectedElementList = new ArrayList<>(Arrays.asList(expectedElements));
        removeIntersections(actualElements, expectedElementList);
        if (!expectedElementList.isEmpty() || !actualElements.isEmpty()) {
            String missedElements = expectedElementList.isEmpty()
                    ? null
                    : "does not contain: " + StringUtil.toString(expectedElementList);
            String unexpectedElements = actualElements.isEmpty()
                    ? null
                    : "contains unexpected elements: " + StringUtil.toString(actualElements);
            String actualMessage;
            if (missedElements != null && unexpectedElements == null) {
                actualMessage = missedElements;
            } else if (missedElements == null && unexpectedElements != null) {
                actualMessage = unexpectedElements;
            } else {
                actualMessage = missedElements + "\nbut " + unexpectedElements;
            }
            fail(actualMessage, "contains exactly elements");
        }
    }

    private void removeIntersections(List<T> actualElements, List<Object> expectedElements) {
        Iterator<T> actualElementsIterator = actualElements.iterator();
        while (actualElementsIterator.hasNext()) {
            T actualElement = actualElementsIterator.next();
            Iterator<Object> expectedElementsIterator = expectedElements.iterator();
            while (expectedElementsIterator.hasNext()) {
                Object expectedElement = expectedElementsIterator.next();
                if (matches(actualElement, expectedElement)) {
                    actualElementsIterator.remove();
                    expectedElementsIterator.remove();
                }
            }
        }
    }

    private boolean matches(T element, Object value) {
        if (value instanceof String) {
            return predicate(element, levels(), ObjectCheck::new, check -> check.isString(value.toString()));
        }
        return predicate(element, levels(), ObjectCheck::new, check -> check.isLike(value));
    }

    public static <T, C extends Collection<T>> CollectionCheck<T, C, ObjectCheck<T>> create(C actual) {
        return new CollectionCheck<>(actual, ObjectCheck::new);
    }

    public static <T, C extends Collection<T>> CollectionCheck<T, C, ObjectCheck<T>> create(
            C actual, List<String> levels) {
        return new CollectionCheck<>(actual, levels, ObjectCheck::new);
    }
}
