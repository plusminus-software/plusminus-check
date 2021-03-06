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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Non-primitive numbers checker (like BigDecimal, BigInteger etc).
 *
 * @author Taras Shpek
 */
@AllArgsConstructor
public class NumberCheck<T extends Number> extends AbstractNumberCheck {
    
    private T actual;

    public void is(T expected) {
        assertEquals(expected, actual);
    }
    
    public void is(String expected) {
        assertEquals(expected, actual.toString());
    }
    
    public void isNot(T unexpected) {
        assertNotEquals(unexpected, actual);
    }
    
    @Override
    protected double actual() {
        return actual.doubleValue();
    }
}
