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

import java.io.IOException;

import static software.plusminus.check.Checks.checkException;
import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class ExceptionCheckTest {

    @Test
    public void isOk() {
        checkException(() -> throwException(new IllegalArgumentException("Very bad exception")))
                .is(IllegalArgumentException.class);
    }

    @Test
    public void isCheckedExceptionOk() {
        checkException(() -> {
            throw new IOException("Very bad exception");
        }).is(IOException.class);
    }

    @Test
    public void isFailOnDifferentType() {
        assertFail(() -> checkException(() -> throwException(new IllegalStateException("Very bad exception")))
                        .is(IllegalArgumentException.class),
                "type java.lang.IllegalStateException", "type java.lang.IllegalArgumentException");
    }

    @Test
    public void isFailOnSubtype() {
        assertFail(() -> checkException(() -> throwException(new IllegalArgumentException("Very bad exception")))
                        .is(RuntimeException.class),
                "type java.lang.IllegalArgumentException", "type java.lang.RuntimeException");
    }

    @Test
    public void isFailOnNoException() {
        assertFail(() -> checkException(() -> { }).is(IllegalArgumentException.class),
                "no exception", "type java.lang.IllegalArgumentException");
    }

    @Test
    public void isInstanceOfOk() {
        checkException(() -> throwException(new IllegalArgumentException("Very bad exception")))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void isInstanceOfFail() {
        assertFail(() -> checkException(() -> throwException(new IllegalStateException("Very bad exception")))
                        .isInstanceOf(IllegalArgumentException.class),
                "type java.lang.IllegalStateException", "type java.lang.IllegalArgumentException");
    }

    @Test
    public void isInstanceOfFailOnNoException() {
        assertFail(() -> checkException(() -> { }).isInstanceOf(IllegalArgumentException.class),
                "no exception", "type java.lang.IllegalArgumentException");
    }

    @Test
    public void hasMessageOk() {
        checkException(() -> throwException(new IllegalArgumentException("Very bad exception")))
                .hasMessage("Very bad exception");
    }

    @Test
    public void hasMessageNullOk() {
        checkException(() -> throwException(new IllegalArgumentException()))
                .hasMessage(null);
    }

    @Test
    public void hasMessageFail() {
        assertFail(() -> checkException(() -> throwException(new IllegalArgumentException("Very bad exception")))
                        .hasMessage("Other message"),
                "Very bad exception", "Other message");
    }

    @Test
    public void hasMessageFailOnNoException() {
        assertFail(() -> checkException(() -> { }).hasMessage("Very bad exception"),
                "no exception", "Very bad exception");
    }

    @Test
    public void isWithMessageOk() {
        checkException(() -> throwException(new IllegalArgumentException("Very bad exception")))
                .is(IllegalArgumentException.class)
                .hasMessage("Very bad exception");
    }

    @Test
    public void isNotThrownOk() {
        checkException(() -> { }).isNotThrown();
    }

    @Test
    public void isNotThrownFail() {
        assertFail(() -> checkException(() -> throwException(new IllegalArgumentException("Very bad exception")))
                        .isNotThrown(),
                "type java.lang.IllegalArgumentException", "no exception");
    }

    private void throwException(RuntimeException exception) {
        throw exception;
    }
}
