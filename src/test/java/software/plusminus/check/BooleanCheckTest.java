package software.plusminus.check;

import org.junit.Test;

import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class BooleanCheckTest {

    @Test
    public void isTrueSuccess() {
        new BooleanCheck(true).isTrue();
    }

    @Test
    public void isTrueFail() {
        assertFail(() -> new BooleanCheck(true).isFalse(), "true", "false");
    }

    @Test
    public void isFalseSuccess() {
        new BooleanCheck(false).isFalse();
    }

    @Test
    public void isFalseFail() {
        assertFail(() -> new BooleanCheck(false).isTrue(), "false", "true");
    }

    @Test
    public void isBooleanSuccess() {
        new BooleanCheck(true).is(true);
    }

    @Test
    public void isBooleanFail() {
        assertFail(() -> new BooleanCheck(true).is(false), "true", "false");
    }
}
