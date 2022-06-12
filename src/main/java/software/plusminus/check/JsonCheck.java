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

import lombok.RequiredArgsConstructor;
import org.junit.Assert;
import software.plusminus.check.util.CheckUtils;
import software.plusminus.check.util.JsonUtils;
import software.plusminus.util.ResourceUtils;

import java.util.function.BiConsumer;

import static org.junit.Assert.assertEquals;

/**
 * Json checker.
 *
 * @author Taras Shpek
 */
@RequiredArgsConstructor
public class JsonCheck extends AbstractCheck {
    
    private final String actual;
    private BiConsumer<String, String> checker =
            (e, a) -> assertEquals(JsonUtils.pretty(e), JsonUtils.pretty(a));
    
    public void is(String expected) {
        check(expected);
    }
    
    public void is(Object expected) {
        is(CheckUtils.toJson(expected));
    }
    
    public JsonCheck exact() {
        checker = Assert::assertEquals;
        return this;
    }
    
    public JsonCheck ignoringFieldsOrder() {
        checker = (e, a) -> assertEquals(JsonUtils.pretty(e), JsonUtils.prettyOrdered(a, e));
        return this;
    }
    
    private void check(String expected) {
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
