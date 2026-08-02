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

    @Override
    public TemporalCheck<T> isNotNull() {
        super.isNotNull();
        return this;
    }

    @Override
    public TemporalCheck<T> isNot(T unexpected) {
        super.isNot(unexpected);
        return this;
    }

    @Override
    public TemporalCheck<T> isSameTypeAs(T expected) {
        super.isSameTypeAs(expected);
        return this;
    }

    @Override
    public TemporalCheck<T> isType(Class<?> expectedType) {
        super.isType(expectedType);
        return this;
    }

    public TemporalCheck<T> isRecent() {
        return isRecent(Duration.ofSeconds(1));
    }

    public TemporalCheck<T> isRecent(Duration duration) {
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
        return this;
    }

    public TemporalCheck<T> isBefore(Temporal expected) {
        isNotNull();
        if (checkNull(expected)) {
            return this;
        }
        if (!toInstant(actual()).isBefore(toInstant(expected))) {
            fail("before " + expected);
        }
        return this;
    }

    public TemporalCheck<T> isAfter(Temporal expected) {
        isNotNull();
        if (checkNull(expected)) {
            return this;
        }
        if (!toInstant(actual()).isAfter(toInstant(expected))) {
            fail("after " + expected);
        }
        return this;
    }

    /**
     * Asserts the temporal is within the bounds, both of them included.
     * Zoneless bounds are resolved against the system default time-zone,
     * the same way the actual value is.
     *
     * @param min lower bound, included
     * @param max upper bound, included
     * @return this check, for chaining
     */
    public TemporalCheck<T> isBetween(Temporal min, Temporal max) {
        isNotNull();
        if (checkNull(min) || checkNull(max)) {
            return this;
        }
        Instant actualInstant = toInstant(actual());
        if (actualInstant.isBefore(toInstant(min)) || actualInstant.isAfter(toInstant(max))) {
            fail("between " + min + " and " + max);
        }
        return this;
    }

    public TemporalCheck<T> isCloseTo(Temporal expected, Duration tolerance) {
        isNotNull();
        if (checkNull(expected) || checkNull(tolerance)) {
            return this;
        }
        Duration difference = Duration.between(toInstant(expected), toInstant(actual()));
        if (absolute(difference).compareTo(absolute(tolerance)) > 0) {
            fail("within " + tolerance + " of " + expected);
        }
        return this;
    }

    private Duration absolute(Duration duration) {
        return duration.isNegative() ? duration.negated() : duration;
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
