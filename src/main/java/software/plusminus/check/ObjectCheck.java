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
import software.plusminus.check.util.JsonUtils;
import software.plusminus.util.ObjectUtils;
import software.plusminus.util.ResourceUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Non-jvm classes checker.
 * Converts object to string (in Json or Jsog formats) before comparing. 
 *
 * @author Taras Shpek
 */
public class ObjectCheck<T> extends AbstractCheck {

    private T actual;

    public ObjectCheck(T actual) {
        this.actual = actual;
    }

    public void is(T expected) {
        if (actual == expected) {
            return;
        }
        checkClasses(expected);
        checkJson(CheckUtils.toString(expected), CheckUtils.toString(actual));
    }
    
    public void is(String expected) {
        if (actual.equals(expected)) {
            return;
        }
        if (ResourceUtils.isResource(expected)) {
            expected = ResourceUtils.toString(expected);
        }
        if (JsonUtils.isJson(expected)) {
            checkJson(expected, CheckUtils.toString(actual));
        } else {
            assertEquals(expected, actual.toString());
        }
    }

    public void isEqual(T expected) {
        if (actual == expected) {
            return;
        }
        checkClasses(expected);
        if (!ObjectUtils.equalsMethodIsOverridden(expected)) {
            fail("equals() must be overridden", "equals() is not overridden. "
                    + "Call is() method instead of isEqual()");
        }
        assertEquals(expected, actual);
    }
    
    public void isSame(T expected) {
        assertSame(expected, actual);
    }
    
    public void isNull() {
        if (actual != null) {
            fail("is null", CheckUtils.toString(actual));
        }
    }
    
    public void isNotNull() {
        if (actual == null) {
            fail("is not null", "is null");
        }
    }
    
    public <X> ObjectCheck<X> as(Class<X> expectedType) {
        if (!expectedType.isAssignableFrom(actual.getClass())) {
            fail("actual object should be an instance of " + expectedType.getName() + " class",
                    "class of the actual object is " + actual.getClass().getName());
        }
        return new ObjectCheck<>(expectedType.cast(actual));
    }
    
    private void checkClasses(Object expected) {
        if (actual.getClass() != expected.getClass()) {
            fail("class should be " + expected.getClass().getName(),
                    "class is " + actual.getClass().getName());
        }
    }
}
