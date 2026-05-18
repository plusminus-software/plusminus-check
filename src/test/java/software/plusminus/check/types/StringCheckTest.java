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

import org.junit.Test;

import static software.plusminus.check.Checks.check;
import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class StringCheckTest {

    @Test
    public void isOk() {
        check("hello").is("hello");
    }

    @Test
    public void isFail() {
        assertFail(() -> check("hello").is("world"), "hello", "world");
    }

    @Test
    public void isResourceOk() {
        check("One").is("one.txt");
    }

    @Test
    public void isJsonOk() {
        check("{\"a\":1}").isJson().is("{\"a\":1}");
    }

    @Test
    public void isJsonFail() {
        assertFail(() -> check("not json").isJson(), "not json", "json");
    }
}
