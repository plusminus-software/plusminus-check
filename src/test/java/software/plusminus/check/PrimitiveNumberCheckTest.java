package software.plusminus.check;

import org.junit.Test;

import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class PrimitiveNumberCheckTest {

    @Test
    public void isIntSuccess() {
        new PrimitiveNumberCheck<>(1L).is(1);
    }

    @Test
    public void isIntFail() {
        assertFail(() -> new PrimitiveNumberCheck<>(1L).is(2), 1L, 2);
    }

    @Test
    public void isPositiveSuccess() {
        new PrimitiveNumberCheck<>(1).isPositive();
    }

    @Test
    public void isPositiveFail() {
        assertFail(() -> new PrimitiveNumberCheck<>(-1).isPositive(), "negative", "positive");
    }

    @Test
    public void isNegativeSuccess() {
        new PrimitiveNumberCheck<>(-1).isNegative();
    }

    @Test
    public void isNegativeFail() {
        assertFail(() -> new PrimitiveNumberCheck<>(1).isNegative(), "positive", "negative");
    }

    @Test
    public void isZeroSuccess() {
        new PrimitiveNumberCheck<>(0).isZero();
    }

    @Test
    public void isZeroFail() {
        assertFail(() -> new PrimitiveNumberCheck<>(1).isZero(), "positive", "zero");
    }
}
