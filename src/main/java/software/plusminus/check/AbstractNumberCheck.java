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

/**
 * Base class for number checkers.
 *
 * @author Taras Shpek
 */
public abstract class AbstractNumberCheck extends AbstractCheck {
    
    public void isPositive() {
        double actual = actual();
        if (actual <= 0) {
            fail("positive", actual == 0 ? "zero" : "negative");
        }
    }
    
    public void isNegative() {
        double actual = actual();
        if (actual >= 0) {
            fail("negative", actual == 0 ? "zero" : "positive");
        }
    }
    
    public void isZero() {
        double actual = actual();
        if (actual != 0) {
            fail("zero", actual > 0 ? "positive" : "negative");
        }
    }
    
    protected abstract double actual();
    
}
