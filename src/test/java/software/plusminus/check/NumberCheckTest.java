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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@SuppressWarnings("java:S2699")
public class NumberCheckTest {
    
    @Test
    public void isPositiveSuccess() {
        NumberCheck<Double> integerCheck = new NumberCheck<>(1d);
        integerCheck.isPositive();
    }
    
    @Test
    public void isPositiveFail() {
        NumberCheck<Double> integerCheck = new NumberCheck<>(0d);
        try {
            integerCheck.isPositive();
        } catch (AssertionError e) {
            assertEquals("expected:<positive> but was:<zero>", e.getMessage());
            return;
        }
        fail();
    }

    @Test
    public void isNegativeSuccess() {
        NumberCheck<Double> integerCheck = new NumberCheck<>(-1d);
        integerCheck.isNegative();
    }
    
    @Test
    public void isNegativeFail() {
        NumberCheck<Double> integerCheck = new NumberCheck<>(0d);
        try {
            integerCheck.isNegative();
        } catch (AssertionError e) {
            assertEquals("expected:<negative> but was:<zero>", e.getMessage());
            return;
        }
        fail();
    }

    @Test
    public void isZeroSuccess() {
        NumberCheck<Double> integerCheck = new NumberCheck<>(0d);
        integerCheck.isZero();
    }
    
    @Test
    public void isZeroFail() {
        NumberCheck<Double> integerCheck = new NumberCheck<>(1d);
        try {
            integerCheck.isZero();
        } catch (AssertionError e) {
            assertEquals("expected:<zero> but was:<positive>", e.getMessage());
            return;
        }
        fail();
    }
}