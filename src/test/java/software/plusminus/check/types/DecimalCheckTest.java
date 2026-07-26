package software.plusminus.check.types;

import org.junit.Test;

import java.math.BigDecimal;

import static software.plusminus.check.Checks.check;
import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class DecimalCheckTest {

    @Test
    public void isDoubleOk() {
        check(1.5d).is(1.5d);
    }

    @Test
    public void isDoubleFail() {
        assertFail(() -> check(1.5d).is(2.5d), 1.5d, 2.5d);
    }

    @Test
    public void isIntOk() {
        check(1.0d).is(1);
    }

    @Test
    public void isIntFail() {
        assertFail(() -> check(1.0d).is(2), 1.0d, 2);
    }

    @Test
    public void isStringOk() {
        check(new BigDecimal("1.5")).is("1.5");
    }

    @Test
    public void isStringFail() {
        assertFail(() -> check(new BigDecimal("1.5")).is("1.6"), "1.5", "1.6");
    }

    @Test
    public void isStringKeepsBigDecimalScale() {
        assertFail(() -> check(new BigDecimal("1.5")).is("1.50"), "1.5", "1.50");
        assertFail(() -> check(new BigDecimal("1.50")).is("1.5"), "1.50", "1.5");
        check(new BigDecimal("1.50")).is("1.50");
    }

    @Test
    public void isStringIgnoresScaleOfScalelessTypes() {
        check(1.5d).is("1.50");
        check(1.5f).is("1.50");
    }

    @Test
    public void isStringAfterLimitScale() {
        check(new BigDecimal("1.234567")).limitScale(2).is("1.23");
    }

    @Test
    public void isBigDecimalScaleAgnostic() {
        check(new BigDecimal("1.50")).is(new BigDecimal("1.5"));
    }

    @Test
    public void limitScale() {
        check(1.234567d).limitScale().is(1.2346d);
    }

    @Test
    public void limitScaleCustom() {
        check(1.234567d).limitScale(2).is(1.23d);
    }

    @Test
    public void limitScaleFail() {
        assertFail(() -> check(1.234567d).limitScale(2).is(1.25d), 1.23d, 1.25d);
    }
}
