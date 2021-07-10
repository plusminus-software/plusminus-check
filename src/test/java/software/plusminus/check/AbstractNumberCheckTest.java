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
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

/**
 * Abstract number checker test.
 *
 * @author Taras Shpek
 */
@RunWith(MockitoJUnitRunner.class)
public class AbstractNumberCheckTest {
    
    @Spy
    private AbstractNumberCheck numberCheck;
    
    @Test
    public void isPositiveSuccess() throws Exception {
        when(numberCheck.actual()).thenReturn(1d);
        numberCheck.isPositive();
    }
    
    @Test
    public void isPositiveFail() throws Exception {
        when(numberCheck.actual()).thenReturn(0d);
        try {
            numberCheck.isPositive();
        } catch (AssertionError e) {
            assertEquals("expected:<[positive]> but was:<[zero]>", e.getMessage());
            return;
        }
        fail();
    }

    @Test
    public void isNegativeSuccess() throws Exception {
        when(numberCheck.actual()).thenReturn(-1d);
        numberCheck.isNegative();
    }
    
    @Test
    public void isNegativeFail() throws Exception {
        when(numberCheck.actual()).thenReturn(0d);
        try {
            numberCheck.isNegative();
        } catch (AssertionError e) {
            assertEquals("expected:<[negative]> but was:<[zero]>", e.getMessage());
            return;
        }
        fail();
    }

    @Test
    public void isZeroSuccess() throws Exception {
        when(numberCheck.actual()).thenReturn(0d);
        numberCheck.isZero();
    }
    
    @Test
    public void isZeroFail() throws Exception {
        when(numberCheck.actual()).thenReturn(1d);
        try {
            numberCheck.isZero();
        } catch (AssertionError e) {
            assertEquals("expected:<[zero]> but was:<[positive]>", e.getMessage());
            return;
        }
        fail();
    }

}