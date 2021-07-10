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

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Collection (including implementations of List, Set etc) checker. 
 * Converts Collection to string (in Json or Jsog formats) before comparing.
 *
 * @author Taras Shpek
 */
public class CollectionCheck<T> extends AbstractCheck {
    
    private Collection<T> actual;

    public CollectionCheck(Collection<T> actual) {
        this.actual = actual;
    }

    public void is(Object... expected) {
        if (expected.length == 1) {
            checkSingle(expected);
        } else {
            checkElements(expected);
        }
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

    private void checkSingle(Object expected) {
        if (actual.size() == 1 && isEqualToFirstElement(expected)) {
            return;
        } else if (Collection.class.isAssignableFrom(expected.getClass())) {
            checkCollection((Collection) expected);
        } else if (expected.getClass() == String.class) {
            checkString((String) expected);
        } else {
            checkElements(expected);
        }
    }

    private boolean isEqualToFirstElement(Object expected) {
        T singleElement = actual.iterator().next();
        return singleElement != null 
                && CheckUtils.toString(singleElement).equals(CheckUtils.toString(expected));
    }
    
    private void checkCollection(Collection<T> expected) {
        new ObjectCheck<>(actual).is(expected);
    }

    private void checkString(String expected) {
        new ObjectCheck<>(actual).is(expected);
    }

    private void checkElements(Object... expected) {
        checkJson(CheckUtils.toJson(expectedList(expected)), CheckUtils.toJson(actual));
    }
    
    private List<?> expectedList(Object... expected) {
        return Stream.of(expected)
                .map(e -> {
                    if (e.getClass() == String.class) {
                        String expectedString = (String) e;
                        if (ResourceUtils.isResource(expectedString)) {
                            return ResourceUtils.toString(expectedString);
                        }
                    }
                    return e;
                })
                .collect(Collectors.toList());
    }
}
