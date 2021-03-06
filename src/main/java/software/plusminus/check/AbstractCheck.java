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

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import software.plusminus.check.util.JsonUtils;

import static org.junit.Assert.assertEquals;

/**
 * Base class for all checkers.
 *
 * @author Taras Shpek
 */
public abstract class AbstractCheck {
    
    @SuppressFBWarnings({ "EQ_UNUSUAL", "HE_EQUALS_USE_HASHCODE"})
    @SuppressWarnings({ "checkstyle:EqualsHashCode", "PMD.OverrideBothEqualsAndHashcode" })
    @Override
    @Deprecated
    public boolean equals(Object object) {
        throw new UnsupportedOperationException("Method 'equals' is not supported. Maybe you intended to call 'is'?");
    }
    
    protected void checkJson(String expected, String actual) {
        if (expected.equals(actual)) {
            return;
        }
        String expectedPretty = JsonUtils.pretty(expected); 
        String actualPretty = JsonUtils.pretty(actual);
        if (!expectedPretty.equals(actualPretty)) {
            fail(expectedPretty, actualPretty);
        }
    }
    
    protected void fail(String expected, String actual) {
        if (expected.equals(actual)) {
            throw new IllegalArgumentException("Expected and actual strings should not be equal");
        }
        assertEquals(expected, actual);
    }
}
