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

import java.math.BigInteger;

import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class NumberCheckTest {

    @Test
    public void isPositiveSuccess() {
        new NumberCheck<>(1d).isPositive();
    }

    @Test
    public void isPositiveFail() {
        assertFail(() -> new NumberCheck<>(0d).isPositive(), "zero", "positive");
    }

    @Test
    public void isNegativeSuccess() {
        new NumberCheck<>(-1d).isNegative();
    }

    @Test
    public void isNegativeFail() {
        assertFail(() -> new NumberCheck<>(0d).isNegative(), "zero", "negative");
    }

    @Test
    public void isZeroSuccess() {
        new NumberCheck<>(0d).isZero();
    }

    @Test
    public void isZeroFail() {
        assertFail(() -> new NumberCheck<>(1d).isZero(), "positive", "zero");
    }

    @Test
    public void isIntSuccess() {
        new NumberCheck<>(1L).is(1);
    }

    @Test
    public void isIntFail() {
        assertFail(() -> new NumberCheck<>(1L).is(2), 1L, 2);
    }

    @Test
    public void isBigIntegerSuccess() {
        new NumberCheck<>(new BigInteger("123456789012345678901234567890"))
                .is(new BigInteger("123456789012345678901234567890"));
    }

    @Test
    public void isNullSuccess() {
        new NumberCheck<Integer>(null).isNull();
    }

    @Test
    public void isNullFail() {
        assertFail(() -> new NumberCheck<>(1).isNull(), 1, "null");
    }

    @Test
    public void isNotNullSuccess() {
        new NumberCheck<>(1).isNotNull();
    }

    @Test
    public void isNotNullFail() {
        assertFail(() -> new NumberCheck<Integer>(null).isNotNull(), "null", "not null");
    }
}
