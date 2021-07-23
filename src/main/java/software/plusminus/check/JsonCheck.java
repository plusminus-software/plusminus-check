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

import lombok.AllArgsConstructor;
import org.junit.Assert;
import software.plusminus.check.util.JsonUtils;
import software.plusminus.util.ResourceUtils;

import java.util.function.BiConsumer;

import static org.junit.Assert.assertEquals;

/**
 * Json checker.
 *
 * @author Taras Shpek
 */
@AllArgsConstructor
public class JsonCheck extends AbstractCheck {
    
    private String actual;
    
    public void is(String expected) {
        check(expected, (e, a) -> assertEquals(JsonUtils.pretty(e), JsonUtils.pretty(a)));
    }
    
    public void isExact(String expected) {
        check(expected, Assert::assertEquals);
    }
    
    public void isIgnoringFieldsOrder(String expected) {
        check(expected, (e, a) -> assertEquals(JsonUtils.pretty(e), JsonUtils.prettyOrdered(a, e)));
    }
    
    private void check(String expected, BiConsumer<String, String> checker) {
        if (expected == null) {
            throw new AssertionError("expected should not be null");
        }
        if (actual.equals(expected)) {
            return;
        }
        if (ResourceUtils.isResource(expected)) {
            expected = ResourceUtils.toString(expected);
        }

        if (!JsonUtils.isJson(expected)) {
            throw new AssertionError("expected should be json");
        }
        checker.accept(expected, actual);
    }
}
