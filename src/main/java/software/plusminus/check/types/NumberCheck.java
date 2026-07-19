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

    public void isPositive() {
        isNotNull();
        Integer sign = signOrNull();
        if (sign == null) {
            fail(NAN, POSITIVE);
        } else if (sign < 0) {
            fail(NEGATIVE, POSITIVE);
        } else if (sign == 0) {
            fail(ZERO, POSITIVE);
        }
    }

    public void isNegative() {
        isNotNull();
        Integer sign = signOrNull();
        if (sign == null) {
            fail(NAN, NEGATIVE);
        } else if (sign > 0) {
            fail(POSITIVE, NEGATIVE);
        } else if (sign == 0) {
            fail(ZERO, NEGATIVE);
        }
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
