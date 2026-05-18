package software.plusminus.check.types;

import org.junit.Test;

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
}
