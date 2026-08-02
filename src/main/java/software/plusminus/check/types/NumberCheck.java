package software.plusminus.check.types;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.annotation.Nullable;

public class NumberCheck<T extends Number> extends AbstractCheck<T> {

    public static final String NEGATIVE = "negative";
    public static final String POSITIVE = "positive";
    public static final String ZERO = "zero";
    public static final String NAN = "NaN";

    public NumberCheck(@Nullable T actual) {
        super(actual);
    }

    public NumberCheck(@Nullable T actual, List<String> levels) {
        super(actual, levels);
    }

    public void is(int expected) {
        isNumber(expected);
    }

    @Override
    public NumberCheck<T> isNot(T unexpected) {
        super.isNot(unexpected);
        return this;
    }

    public NumberCheck<T> isPositive() {
        isNotNull();
        Integer sign = signOrNull();
        if (sign == null) {
            fail(NAN, POSITIVE);
        } else if (sign < 0) {
            fail(NEGATIVE, POSITIVE);
        } else if (sign == 0) {
            fail(ZERO, POSITIVE);
        }
        return this;
    }

    public NumberCheck<T> isNegative() {
        isNotNull();
        Integer sign = signOrNull();
        if (sign == null) {
            fail(NAN, NEGATIVE);
        } else if (sign > 0) {
            fail(POSITIVE, NEGATIVE);
        } else if (sign == 0) {
            fail(ZERO, NEGATIVE);
        }
        return this;
    }

    public void isZero() {
        isNotNull();
        Integer sign = signOrNull();
        if (sign == null) {
            fail(NAN, ZERO);
        } else if (sign > 0) {
            fail(POSITIVE, ZERO);
        } else if (sign < 0) {
            fail(NEGATIVE, ZERO);
        }
    }

    public NumberCheck<T> isGreaterThan(Number expected) {
        Integer comparison = compare(expected);
        if (comparison == null || comparison <= 0) {
            fail("greater than " + expected);
        }
        return this;
    }

    public NumberCheck<T> isGreaterThanOrEqualTo(Number expected) {
        Integer comparison = compare(expected);
        if (comparison == null || comparison < 0) {
            fail("greater than or equal to " + expected);
        }
        return this;
    }

    public NumberCheck<T> isLessThan(Number expected) {
        Integer comparison = compare(expected);
        if (comparison == null || comparison >= 0) {
            fail("less than " + expected);
        }
        return this;
    }

    public NumberCheck<T> isLessThanOrEqualTo(Number expected) {
        Integer comparison = compare(expected);
        if (comparison == null || comparison > 0) {
            fail("less than or equal to " + expected);
        }
        return this;
    }

    /**
     * Asserts the number is within the bounds, both of them included.
     *
     * @param min lower bound, included
     * @param max upper bound, included
     * @return this check, for chaining
     */
    public NumberCheck<T> isBetween(Number min, Number max) {
        Integer minComparison = compare(min);
        Integer maxComparison = compare(max);
        if (minComparison == null || maxComparison == null
                || minComparison < 0 || maxComparison > 0) {
            fail("between " + min + " and " + max);
        }
        return this;
    }

    /**
     * Asserts the number differs from the expected one by no more than the tolerance.
     * Unlike {@code limitScale}, which rounds before comparing, this compares
     * the difference itself.
     *
     * @param expected expected number
     * @param tolerance maximum accepted difference, included
     * @return this check, for chaining
     */
    public NumberCheck<T> isCloseTo(Number expected, Number tolerance) {
        isNotNull();
        if (checkNull(expected) || checkNull(tolerance)) {
            return this;
        }
        T actual = actual();
        if (!isFinite(actual) || !isFinite(expected)) {
            if (Double.compare(actual.doubleValue(), expected.doubleValue()) != 0) {
                fail("within " + tolerance + " of " + expected);
            }
            return this;
        }
        BigDecimal difference = toBigDecimal(actual).subtract(toBigDecimal(expected)).abs();
        if (difference.compareTo(toBigDecimal(tolerance).abs()) > 0) {
            fail("within " + tolerance + " of " + expected);
        }
        return this;
    }

    @Nullable
    private Integer compare(Number expected) {
        isNotNull();
        if (checkNull(expected)) {
            return null;
        }
        T actual = actual();
        if (isNaN(actual) || isNaN(expected)) {
            return null;
        }
        if (!isFinite(actual) || !isFinite(expected)) {
            return Double.compare(actual.doubleValue(), expected.doubleValue());
        }
        return toBigDecimal(actual).compareTo(toBigDecimal(expected));
    }

    private static boolean isNaN(Number number) {
        return (number instanceof Double || number instanceof Float)
                && Double.isNaN(number.doubleValue());
    }

    private static boolean isFinite(Number number) {
        if (!(number instanceof Double) && !(number instanceof Float)) {
            return true;
        }
        double value = number.doubleValue();
        return !Double.isNaN(value) && !Double.isInfinite(value);
    }

    @Nullable
    private Integer signOrNull() {
        T value = actual();
        if (value instanceof Double || value instanceof Float) {
            double d = value.doubleValue();
            if (Double.isNaN(d)) {
                return null;
            }
            if (Double.isInfinite(d)) {
                return d > 0 ? 1 : -1;
            }
        }
        return toBigDecimal(value).signum();
    }

    protected void isNumber(Number expected) {
        if (checkNull(expected)) {
            return;
        }
        if (toBigDecimal(actual()).compareTo(toBigDecimal(expected)) != 0) {
            fail(expected);
        }
    }

    private BigDecimal toBigDecimal(Number number) {
        if (number instanceof BigDecimal) {
            return (BigDecimal) number;
        }
        if (number instanceof BigInteger) {
            return new BigDecimal((BigInteger) number);
        }
        if (number instanceof Double || number instanceof Float) {
            return new BigDecimal(number.toString());
        }
        return BigDecimal.valueOf(number.longValue());
    }
}
