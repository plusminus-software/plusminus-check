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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static software.plusminus.check.Checks.check;
import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class InputStreamCheckTest {

    @Test
    public void isOk() {
        check(stream("hello")).is(stream("hello"));
    }

    @Test
    public void isFail() {
        assertFail(() -> check(stream("hello")).is(stream("world")), "hello", "world");
    }

    @Test
    public void isBytesOk() {
        check(stream("hello")).is(bytes("hello"));
    }

    @Test
    public void isBytesFail() {
        assertFail(() -> check(stream("hello")).is(bytes("world")), "hello", "world");
    }

    @Test
    public void isBinaryFail() {
        byte[] actual = {1, 2, 3};
        byte[] expected = {1, 2, 4};
        assertFail(() -> check(new ByteArrayInputStream(actual)).is(expected),
                "[1, 2, 3]", "[1, 2, 4]");
    }

    @Test
    public void isMixedFail() {
        byte[] expected = {(byte) 0xC3, (byte) 0x28};
        assertFail(() -> check(stream("hi")).is(expected),
                "hi", "[-61, 40]");
    }

    @Test
    public void isStringOk() {
        check(stream("hello")).is("hello");
    }

    @Test
    public void isStringFail() {
        assertFail(() -> check(stream("hello")).is("world"), "hello", "world");
    }

    @Test
    public void isResourceOk() {
        check(stream("One")).is("one.txt");
    }

    @Test
    public void isEmptyOk() {
        check(stream("")).is(stream(""));
    }

    @Test
    public void isNullOk() {
        check((InputStream) null).isNull();
    }

    @Test
    public void isNullFail() {
        assertFail(() -> check((InputStream) null).is(bytes("hello")));
    }

    @Test
    public void isSameOk() throws IOException {
        try (InputStream actual = stream("hello")) {
            check(actual).isSame(actual);
        }
    }

    @Test
    public void isSameFail() {
        assertFail(() -> check(stream("hello")).isSame(stream("hello")));
    }

    private InputStream stream(String content) {
        return new ByteArrayInputStream(bytes(content));
    }

    private byte[] bytes(String content) {
        return content.getBytes(StandardCharsets.UTF_8);
    }
}
