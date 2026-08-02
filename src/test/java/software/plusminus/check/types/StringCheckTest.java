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
    public void containsOk() {
        check("hello world").contains("hello");
    }

    @Test
    public void containsChainOk() {
        check("hello world").contains("hello").contains("world");
    }

    @Test
    public void containsFail() {
        assertFail(() -> check("hello world").contains("planet"),
                "does not contain planet", "contains planet");
    }

    @Test
    public void containsFailOnNull() {
        assertFail(() -> check((String) null).contains("hello"));
    }

    @Test
    public void startsWithOk() {
        check("hello world").startsWith("hello");
    }

    @Test
    public void startsWithChainOk() {
        check("hello world").startsWith("hello").endsWith("world");
    }

    @Test
    public void startsWithFail() {
        assertFail(() -> check("hello world").startsWith("world"),
                "does not start with world", "starts with world");
    }

    @Test
    public void startsWithFailOnNull() {
        assertFail(() -> check((String) null).startsWith("hello"));
    }

    @Test
    public void endsWithOk() {
        check("hello world").endsWith("world");
    }

    @Test
    public void endsWithFail() {
        assertFail(() -> check("hello world").endsWith("hello"),
                "does not end with hello", "ends with hello");
    }

    @Test
    public void endsWithFailOnNull() {
        assertFail(() -> check((String) null).endsWith("world"));
    }

    @Test
    public void isJsonOk() {
        check("{\"a\":1}").isJson().is("{\"a\":1}");
    }

    @Test
    public void isJsonFail() {
        assertFail(() -> check("not json").isJson(), "not json", "json");
    }

    @Test
    public void isEmptyOk() {
        check("").isEmpty();
    }

    @Test
    public void isEmptyFail() {
        assertFail(() -> check("a").isEmpty(), "a", "empty");
    }

    @Test
    public void isNotEmptyOk() {
        check("a").isNotEmpty();
    }

    @Test
    public void isNotEmptyFail() {
        assertFail(() -> check("").isNotEmpty(), "empty", "not empty");
    }

    @Test
    public void isBlankOk() {
        check(" \t\n").isBlank();
    }

    @Test
    public void isBlankFail() {
        assertFail(() -> check(" a ").isBlank(), " a ", "blank");
    }

    @Test
    public void isNotBlankOk() {
        check(" a ").isNotBlank();
    }

    @Test
    public void isNotBlankFail() {
        assertFail(() -> check("  ").isNotBlank(), "  ", "not blank");
    }

    @Test
    public void hasLengthOk() {
        check("abc").hasLength(3);
    }

    @Test
    public void hasLengthFail() {
        assertFail(() -> check("abc").hasLength(2), "length is 3", "length is 2");
    }

    @Test
    public void doesNotContainOk() {
        check("hello").doesNotContain("world");
    }

    @Test
    public void doesNotContainFail() {
        assertFail(() -> check("hello world").doesNotContain("world"),
                "contains world", "does not contain world");
    }

    @Test
    public void matchesOk() {
        check("abc123").matches("[a-z]+\\d+");
    }

    @Test
    public void matchesPartiallyFail() {
        assertFail(() -> check("abc123!").matches("[a-z]+\\d+"),
                "does not match [a-z]+\\d+", "matches [a-z]+\\d+");
    }

    @Test
    public void ignoringLineEndingsOk() {
        check("a\r\nb").ignoringLineEndings().is("a\nb");
    }

    @Test
    public void ignoringLineEndingsCarriageReturnOk() {
        check("a\rb").ignoringLineEndings().is("a\nb");
    }

    @Test
    public void ignoringLineEndingsStillComparesContent() {
        assertFail(() -> check("a\r\nb").ignoringLineEndings().is("a\nc"), "a\nb", "a\nc");
    }

    @Test
    public void lineEndingsMatterByDefault() {
        assertFail(() -> check("a\r\nb").is("a\nb"), "a\r\nb", "a\nb");
    }

    @Test
    public void isNotOk() {
        check("hello").isNot("world");
    }

    @Test
    public void isNotFail() {
        assertFail(() -> check("hello").isNot("hello"), "hello", "not hello");
    }

    @Test
    public void chainsAssertions() {
        check("hello world")
                .isNotBlank()
                .hasLength(11)
                .startsWith("hello")
                .endsWith("world")
                .contains("o w")
                .doesNotContain("bye")
                .matches("hello.*");
    }
}
