package software.plusminus.check;

import org.junit.Test;

import java.math.BigInteger;

import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class NullableNumberCheckTest {

    @Test
    public void isPositiveSuccess() {
        new NullableNumberCheck<>(1d).isPositive();
    }

    @Test
    public void isPositiveFail() {
        assertFail(() -> new NullableNumberCheck<>(0d).isPositive(), "zero", "positive");
    }

    @Test
    public void isNegativeSuccess() {
        new NullableNumberCheck<>(-1d).isNegative();
    }

    @Test
    public void isNegativeFail() {
        assertFail(() -> new NullableNumberCheck<>(0d).isNegative(), "zero", "negative");
    }

    @Test
    public void isZeroSuccess() {
        new NullableNumberCheck<>(0d).isZero();
    }

    @Test
    public void isZeroFail() {
        assertFail(() -> new NullableNumberCheck<>(1d).isZero(), "positive", "zero");
    }

    @Test
    public void isIntSuccess() {
        new NullableNumberCheck<>(1L).is(1);
    }

    @Test
    public void isIntFail() {
        assertFail(() -> new NullableNumberCheck<>(1L).is(2), 1L, 2);
    }

    @Test
    public void isBigIntegerSuccess() {
        new NullableNumberCheck<>(new BigInteger("123456789012345678901234567890"))
                .is(new BigInteger("123456789012345678901234567890"));
    }

    @Test
    public void isNullSuccess() {
        new NullableNumberCheck<Integer>(null).isNull();
    }

    @Test
    public void isNullFail() {
        assertFail(() -> new NullableNumberCheck<>(1).isNull(), 1, "null");
    }

    @Test
    public void isNotNullSuccess() {
        new NullableNumberCheck<>(1).isNotNull();
    }

    @Test
    public void isNotNullFail() {
        assertFail(() -> new NullableNumberCheck<Integer>(null).isNotNull(), "null", "not null");
    }
}
