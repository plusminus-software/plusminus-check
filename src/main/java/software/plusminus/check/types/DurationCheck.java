package software.plusminus.check.types;

import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.annotation.Nullable;

public class DurationCheck extends AbstractCheck<Duration> {

    private static final String ZERO = "zero";
    private static final String POSITIVE = "positive";
    private static final String NEGATIVE = "negative";

    public DurationCheck(@Nullable Duration actual) {
        super(actual);
    }

    public DurationCheck(@Nullable Duration actual, List<String> levels) {
        super(actual, levels);
    }

    /**
     * Compares against an ISO-8601 duration, for instance {@code "PT1M30S"}.
     *
     * @param expected expected duration in ISO-8601 format
     */
    public void is(String expected) {
        if (checkNull(expected)) {
            return;
        }
        is(parse(expected));
    }

    @Override
    public void isNull() {
        super.isNull();
    }

    @Override
    public DurationCheck isNotNull() {
        super.isNotNull();
        return this;
    }

    @Override
    public DurationCheck isNot(Duration unexpected) {
        super.isNot(unexpected);
        return this;
    }

    public DurationCheck isZero() {
        isNotNull();
        if (!actual().isZero()) {
            fail(actual().isNegative() ? NEGATIVE : POSITIVE, ZERO);
        }
        return this;
    }

    public DurationCheck isPositive() {
        isNotNull();
        if (actual().isZero()) {
            fail(ZERO, POSITIVE);
        }
        if (actual().isNegative()) {
            fail(NEGATIVE, POSITIVE);
        }
        return this;
    }

    public DurationCheck isNegative() {
        isNotNull();
        if (actual().isZero()) {
            fail(ZERO, NEGATIVE);
        }
        if (!actual().isNegative()) {
            fail(POSITIVE, NEGATIVE);
        }
        return this;
    }

    public DurationCheck isLongerThan(Duration expected) {
        isNotNull();
        if (checkNull(expected) || actual().compareTo(expected) <= 0) {
            fail("longer than " + expected);
        }
        return this;
    }

    public DurationCheck isShorterThan(Duration expected) {
        isNotNull();
        if (checkNull(expected) || actual().compareTo(expected) >= 0) {
            fail("shorter than " + expected);
        }
        return this;
    }

    /**
     * Asserts the duration is within the bounds, both of them included.
     *
     * @param min lower bound, included
     * @param max upper bound, included
     * @return this check, for chaining
     */
    public DurationCheck isBetween(Duration min, Duration max) {
        isNotNull();
        if (checkNull(min) || checkNull(max)
                || actual().compareTo(min) < 0 || actual().compareTo(max) > 0) {
            fail("between " + min + " and " + max);
        }
        return this;
    }

    public DurationCheck isCloseTo(Duration expected, Duration tolerance) {
        isNotNull();
        if (checkNull(expected) || checkNull(tolerance)) {
            return this;
        }
        Duration difference = absolute(actual().minus(expected));
        if (difference.compareTo(absolute(tolerance)) > 0) {
            fail("within " + tolerance + " of " + expected);
        }
        return this;
    }

    private Duration absolute(Duration duration) {
        return duration.isNegative() ? duration.negated() : duration;
    }

    private Duration parse(String expected) {
        try {
            return Duration.parse(expected);
        } catch (DateTimeParseException e) {
            fail("not an ISO-8601 duration: " + expected);
            return null;
        }
    }
}
