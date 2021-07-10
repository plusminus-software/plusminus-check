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

import static software.plusminus.check.Assertions.assertFail;

public class BooleanCheckTest {
    
    @Test
    public void testTrueSuccess() {
        new BooleanCheck(true).isTrue();
    }
    
    @Test
    public void testTrueFail() {
        assertFail(() -> new BooleanCheck(true).isFalse(), "true", "false");
    }
    
    @Test
    public void testFalseSuccess() {
        new BooleanCheck(false).isFalse();
    }
    
    @Test
    public void testFalseFail() {
        assertFail(() -> new BooleanCheck(false).isTrue(), "false", "true");
    }

}