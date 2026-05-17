package software.plusminus.check;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.annotation.Nullable;

public class PrimitiveNumberCheck<T extends Number> extends AbstractCheck<T> {

    public static final String NEGATIVE = "negative";
    public static final String POSITIVE = "positive";
    public static final String ZERO = "zero";

    public PrimitiveNumberCheck(@Nullable T actual) {
        super(actual);
    }

    public PrimitiveNumberCheck(@Nullable T actual, List<String> levels) {
        super(actual, levels);
    }

    public void is(int expected) {
        isNumber(expected);
    }

    public void isPositive() {
        isNotNull();
        if (actual().doubleValue() < 0) {
            fail(NEGATIVE, POSITIVE);
        }
        if (actual().doubleValue() == 0) {
            fail(ZERO, POSITIVE);
        }
    }

    public void isNegative() {
        isNotNull();
        if (actual().doubleValue() > 0) {
            fail(POSITIVE, NEGATIVE);
        }
        if (actual().doubleValue() == 0) {
            fail(ZERO, NEGATIVE);
        }
    }

    public void isZero() {
        isNotNull();
        if (actual().doubleValue() > 0) {
            fail(POSITIVE, ZERO);
        }
        if (actual().doubleValue() < 0) {
            fail(NEGATIVE, ZERO);
        }
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
