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

import org.junit.Test;

import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class StringCheckTest {

    @Test
    public void isStringSuccess() {
        new StringCheck("hello").is("hello");
    }

    @Test
    public void isStringFail() {
        assertFail(() -> new StringCheck("hello").is("world"), "hello", "world");
    }

    @Test
    public void successWithResource() {
        new StringCheck("One").is("one.txt");
    }

    @Test
    public void isNullSuccess() {
        new StringCheck(null).isNull();
    }

    @Test
    public void isNullFail() {
        assertFail(() -> new StringCheck("x").isNull(), "x", "null");
    }

    @Test
    public void isNotNullSuccess() {
        new StringCheck("x").isNotNull();
    }

    @Test
    public void isNotNullFail() {
        assertFail(() -> new StringCheck(null).isNotNull(), "null", "not null");
    }
}
