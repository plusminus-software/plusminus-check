package software.plusminus.check;

import org.junit.Test;

import java.math.BigDecimal;

import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class DecimalCheckTest {

    @Test
    public void isDoubleSuccess() {
        new DecimalCheck<>(1.5d).is(1.5d);
    }

    @Test
    public void isDoubleFail() {
        assertFail(() -> new DecimalCheck<>(1.5d).is(2.5d), 1.5d, 2.5d);
    }

    @Test
    public void isIntSuccess() {
        new DecimalCheck<>(1.0d).is(1);
    }

    @Test
    public void isStringSuccess() {
        new DecimalCheck<>(new BigDecimal("1.5")).is("1.5");
    }

    @Test
    public void isStringFail() {
        assertFail(() -> new DecimalCheck<>(new BigDecimal("1.5")).is("2.5"),
                new BigDecimal("1.5"), new BigDecimal("2.5"));
    }

    @Test
    public void isBigDecimalSuccess() {
        new DecimalCheck<>(new BigDecimal("1.50")).is(new BigDecimal("1.5"));
    }

    @Test
    public void limitScaleDefault() {
        new DecimalCheck<>(1.234567d).limitScale().is(1.2346d);
    }

    @Test
    public void limitScaleCustom() {
        new DecimalCheck<>(1.234567d).limitScale(2).is(1.23d);
    }

    @Test
    public void limitScaleFail() {
        assertFail(() -> new DecimalCheck<>(1.234567d).limitScale(2).is(1.25d),
                1.234567d, 1.25d);
    }

    @Test
    public void isNullSuccess() {
        new DecimalCheck<Double>(null).isNull();
    }

    @Test
    public void isNotNullFail() {
        assertFail(() -> new DecimalCheck<Double>(null).isNotNull(), "null", "not null");
    }
}
