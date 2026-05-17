package software.plusminus.check;

import org.junit.Test;

import java.math.BigDecimal;

import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class PrimitiveDecimalCheckTest {

    @Test
    public void isZeroSuccess() {
        new PrimitiveDecimalCheck<>(new BigDecimal("0.00")).isZero();
    }

    @Test
    public void isDoubleSuccess() {
        new PrimitiveDecimalCheck<>(1.5d).is(1.5d);
    }

    @Test
    public void isDoubleFail() {
        assertFail(() -> new PrimitiveDecimalCheck<>(1.5d).is(2.5d), 1.5d, 2.5d);
    }

    @Test
    public void isIntSuccess() {
        new PrimitiveDecimalCheck<>(1.0d).is(1);
    }

    @Test
    public void isStringSuccess() {
        new PrimitiveDecimalCheck<>(new BigDecimal("1.5")).is("1.5");
    }

    @Test
    public void limitScale() {
        new PrimitiveDecimalCheck<>(1.234567d).limitScale().is(1.2346d);
    }

    @Test
    public void limitScaleCustom() {
        new PrimitiveDecimalCheck<>(1.234567d).limitScale(2).is(1.23d);
    }
}
