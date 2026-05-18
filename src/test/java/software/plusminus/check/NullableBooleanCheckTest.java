package software.plusminus.check;

import org.junit.Test;

import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class NullableBooleanCheckTest {

    @Test
    public void isTrueSuccess() {
        new NullableBooleanCheck(true).isTrue();
    }

    @Test
    public void isTrueFail() {
        assertFail(() -> new NullableBooleanCheck(true).isFalse(), "true", "false");
    }

    @Test
    public void isFalseSuccess() {
        new NullableBooleanCheck(false).isFalse();
    }

    @Test
    public void isFalseFail() {
        assertFail(() -> new NullableBooleanCheck(false).isTrue(), "false", "true");
    }

    @Test
    public void isBooleanSuccess() {
        new NullableBooleanCheck(true).is(true);
    }

    @Test
    public void isBooleanFail() {
        assertFail(() -> new NullableBooleanCheck(true).is(false), "true", "false");
    }

    @Test
    public void isNullSuccess() {
        new NullableBooleanCheck(null).isNull();
    }

    @Test
    public void isNullFail() {
        assertFail(() -> new NullableBooleanCheck(true).isNull(), "true", "null");
    }

    @Test
    public void isNotNullSuccess() {
        new NullableBooleanCheck(true).isNotNull();
    }

    @Test
    public void isNotNullFail() {
        assertFail(() -> new NullableBooleanCheck(null).isNotNull(), "null", "not null");
    }
}
