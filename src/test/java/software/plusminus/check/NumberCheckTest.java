package software.plusminus.check;

import org.junit.Test;

import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class NumberCheckTest {

    @Test
    public void isIntSuccess() {
        new NumberCheck<>(1L).is(1);
    }

    @Test
    public void isIntFail() {
        assertFail(() -> new NumberCheck<>(1L).is(2), 1L, 2);
    }

    @Test
    public void isPositiveSuccess() {
        new NumberCheck<>(1).isPositive();
    }

    @Test
    public void isPositiveFail() {
        assertFail(() -> new NumberCheck<>(-1).isPositive(), "negative", "positive");
    }

    @Test
    public void isNegativeSuccess() {
        new NumberCheck<>(-1).isNegative();
    }

    @Test
    public void isNegativeFail() {
        assertFail(() -> new NumberCheck<>(1).isNegative(), "positive", "negative");
    }

    @Test
    public void isZeroSuccess() {
        new NumberCheck<>(0).isZero();
    }

    @Test
    public void isZeroFail() {
        assertFail(() -> new NumberCheck<>(1).isZero(), "positive", "zero");
    }
}
