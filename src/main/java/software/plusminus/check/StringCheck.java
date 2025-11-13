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
import lombok.AllArgsConstructor;
import software.plusminus.check.util.JsonUtils;
import software.plusminus.util.ResourceUtils;

import static org.junit.Assert.assertEquals;

/**
 * String checker.
 *
 * @author Taras Shpek
 */
@AllArgsConstructor
public class StringCheck extends AbstractCheck {
    
    private String actual;

    @SuppressFBWarnings("NP_LOAD_OF_KNOWN_NULL_VALUE")
    public void is(String expected) {
        if (expected == null) {
            assertEquals(expected, actual);
            return;
        }
        if (actual.equals(expected)) {
            return;
        }
        if (ResourceUtils.isResource(expected)) {
            expected = ResourceUtils.toString(expected);
        }
        
        if (JsonUtils.isJson(expected)) {
            checkJson(expected);
        } else {
            assertEquals(expected, actual);
        }
    }
    
    public JsonCheck isJson() {
        if (!JsonUtils.isJson(actual)) {
            throw new AssertionError("expected should be json");
        }
        return new JsonCheck(actual);
    }

    public void isNull() {
        if (actual != null) {
            fail("is null", actual);
        }
    }

    public void isNotNull() {
        if (actual == null) {
            fail("is not null", "is null");
        }
    }
    
    private void checkJson(String json) {
        assertEquals(JsonUtils.pretty(json), JsonUtils.pretty(actual));
    }
}
