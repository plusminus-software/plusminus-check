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

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalQueries;
import java.util.List;
import javax.annotation.Nullable;

public class TemporalCheck<T extends Temporal> extends AbstractObjectCheck<T> {

    public TemporalCheck(@Nullable T actual) {
        super(actual);
    }

    public TemporalCheck(@Nullable T actual, List<String> levels) {
        super(actual, levels);
    }

    public void isRecent() {
        isRecent(Duration.ofSeconds(1));
    }
    
    public void isRecent(Duration duration) {
        isNotNull();
        Instant now = Instant.now();
        Temporal actual = actual();
        Instant actualInstant = toInstant(actual);
        if (actualInstant.isAfter(now)) {
            fail(actual + " is after now", actual + " is recent");
        }
        if (actualInstant.isBefore(now.minus(duration))) {
            fail(actual + " is more than " + duration + " before now",
                    actual + " is recent");
        }
    }

    /**
     * Converts a {@link Temporal} to an {@link Instant}, handling zoneless types
     * (LocalDateTime/LocalDate/LocalTime/YearMonth) by resolving them against the
     * system default time-zone. Zoned/offset temporals are converted directly.
     */
    private Instant toInstant(Temporal actual) {
        if (actual instanceof Instant) {
            return (Instant) actual;
        }
        ZoneId zone = ZoneId.systemDefault();
        if (actual instanceof LocalDateTime) {
            return ((LocalDateTime) actual).atZone(zone).toInstant();
        }
        if (actual instanceof LocalDate) {
            return ((LocalDate) actual).atStartOfDay(zone).toInstant();
        }
        if (actual instanceof LocalTime) {
            return ((LocalTime) actual).atDate(LocalDate.now(zone)).atZone(zone).toInstant();
        }
        if (actual instanceof YearMonth) {
            return ((YearMonth) actual).atDay(1).atStartOfDay(zone).toInstant();
        }
        if (actual.query(TemporalQueries.zone()) == null
                && actual.query(TemporalQueries.offset()) == null) {
            return LocalDateTime.from(actual).atZone(zone).toInstant();
        }
        return Instant.from(actual);
    }
}
