package software.plusminus.check.types;

import org.junit.Test;

import java.math.BigDecimal;

import static software.plusminus.check.Checks.check;
import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class NumberCheckTest {

    @Test
    public void isIntOk() {
        check(1L).is(1);
    }

    @Test
    public void isIntFail() {
        assertFail(() -> check(1L).is(2), 1L, 2);
    }

    @Test
    public void positiveOk() {
        check(1).isPositive();
    }

    @Test
    public void positiveFail() {
        assertFail(() -> check(-1).isPositive(), "negative", "positive");
    }

    @Test
    public void negativeOk() {
        check(-1).isNegative();
    }

    @Test
    public void negativeFail() {
        assertFail(() -> check(1).isNegative(), "positive", "negative");
    }

    @Test
    public void zeroOk() {
        check(0).isZero();
    }

    @Test
    public void zeroFail() {
        assertFail(() -> check(1).isZero(), "positive", "zero");
    }

    @Test
    public void nanIsNotPositive() {
        assertFail(() -> check(Double.NaN).isPositive(), "NaN", "positive");
    }

    @Test
    public void nanIsNotNegative() {
        assertFail(() -> check(Double.NaN).isNegative(), "NaN", "negative");
    }

    @Test
    public void nanIsNotZero() {
        assertFail(() -> check(Double.NaN).isZero(), "NaN", "zero");
    }

    @Test
    public void tinyBigDecimalIsNotZero() {
        assertFail(() -> check(new BigDecimal("1E-30")).isZero(), "positive", "zero");
    }

    @Test
    public void tinyBigDecimalIsPositive() {
        check(new BigDecimal("1E-30")).isPositive();
    }

    @Test
    public void tinyNegativeBigDecimalIsNegative() {
        check(new BigDecimal("-1E-30")).isNegative();
    }

    @Test
    public void isGreaterThanOk() {
        check(2).isGreaterThan(1);
    }

    @Test
    public void isGreaterThanEqualFail() {
        assertFail(() -> check(1).isGreaterThan(1), "1", "greater than 1");
    }

    @Test
    public void isGreaterThanAcrossTypesOk() {
        check(new BigDecimal("1.5")).isGreaterThan(1);
    }

    @Test
    public void isGreaterThanOrEqualToOk() {
        check(1).isGreaterThanOrEqualTo(1);
    }

    @Test
    public void isGreaterThanOrEqualToFail() {
        assertFail(() -> check(0).isGreaterThanOrEqualTo(1), "0", "greater than or equal to 1");
    }

    @Test
    public void isLessThanOk() {
        check(1).isLessThan(2);
    }

    @Test
    public void isLessThanFail() {
        assertFail(() -> check(2).isLessThan(2), "2", "less than 2");
    }

    @Test
    public void isLessThanOrEqualToOk() {
        check(2).isLessThanOrEqualTo(2);
    }

    @Test
    public void isLessThanOrEqualToFail() {
        assertFail(() -> check(3).isLessThanOrEqualTo(2), "3", "less than or equal to 2");
    }

    @Test
    public void isBetweenOk() {
        check(2).isBetween(1, 3);
    }

    @Test
    public void isBetweenBoundsIncluded() {
        check(1).isBetween(1, 3);
        check(3).isBetween(1, 3);
    }

    @Test
    public void isBetweenFail() {
        assertFail(() -> check(4).isBetween(1, 3), "4", "between 1 and 3");
    }

    @Test
    public void isCloseToOk() {
        check(1.05).isCloseTo(1.0, 0.1);
    }

    @Test
    public void isCloseToFail() {
        assertFail(() -> check(1.5).isCloseTo(1.0, 0.1), "1.5", "within 0.1 of 1.0");
    }

    @Test
    public void nanIsNotGreaterThan() {
        assertFail(() -> check(Double.NaN).isGreaterThan(1), "NaN", "greater than 1");
    }

    @Test
    public void infinityIsGreaterThan() {
        check(Double.POSITIVE_INFINITY).isGreaterThan(Long.MAX_VALUE);
    }

    @Test
    public void isNotOk() {
        check(1).isNot(2);
    }

    @Test
    public void isNotFail() {
        assertFail(() -> check(1).isNot(1), "1", "not 1");
    }

    @Test
    public void chainsAssertions() {
        check(5).isPositive()
                .isGreaterThan(1)
                .isGreaterThanOrEqualTo(5)
                .isLessThan(10)
                .isLessThanOrEqualTo(5)
                .isBetween(1, 10)
                .isCloseTo(5, 0)
                .isNot(6);
    }

    @Test
    public void chainsFromIsNotNull() {
        check(Integer.valueOf(5))
                .isNotNull()
                .isPositive();
    }

    @Test
    public void chainsAfterLimitScale() {
        check(new BigDecimal("1.005"))
                .limitScale(2)
                .isPositive()
                .isLessThan(2);
    }
}
