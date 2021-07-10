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

import org.junit.ComparisonFailure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public final class Assertions {

    private Assertions() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static void assertFail(Runnable lambda, Object actual, Object expected) {
        try {
            lambda.run();
        } catch (ComparisonFailure e) {
            assertEquals(expected, e.getExpected());
            assertEquals(actual, e.getActual());
            return;
        } catch (AssertionError e) {
            assertEquals("expected:<" + expected + "> but was:<" + actual + ">", e.getMessage());
            return;
        }
        fail();
    }
    
}
