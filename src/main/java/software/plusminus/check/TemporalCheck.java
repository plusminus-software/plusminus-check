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

import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.temporal.Temporal;

import static org.junit.Assert.assertEquals;

/**
 * Temporal checker.
 *
 * @author Taras Shpek
 */
@RequiredArgsConstructor
public class TemporalCheck extends AbstractCheck {
    
    private final Temporal actual;
    
    public void is(Temporal expected) {
        if (expected == null) {
            throw new AssertionError("expected should not be null");
        }
        if (actual.equals(expected)) {
            return;
        }
        assertEquals(expected, actual);
    }

    public void recent() {
        recent(1000);
    }
    
    public void recent(long recentLimitInMillis) {
        Instant now = Instant.now();
        Instant actualInstant = Instant.from(actual);
        if (actualInstant.equals(now)) {
            return;
        }
        if (actualInstant.isAfter(now)) {
            fail("Expected that " + actual + " is recent",
                    "Is after the current moment");
        }
        if (actualInstant.isBefore(now.minusMillis(recentLimitInMillis))) {
            fail("Expected that " + actual + " is recent",
                    "Is more than " + recentLimitInMillis + " milliseconds before the current moment");
        }
    }
}
