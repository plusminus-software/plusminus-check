package software.plusminus.check;

import org.junit.Test;

import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class PrimitiveBooleanCheckTest {

    @Test
    public void isTrueSuccess() {
        new PrimitiveBooleanCheck(true).isTrue();
    }

    @Test
    public void isTrueFail() {
        assertFail(() -> new PrimitiveBooleanCheck(true).isFalse(), "true", "false");
    }

    @Test
    public void isFalseSuccess() {
        new PrimitiveBooleanCheck(false).isFalse();
    }

    @Test
    public void isFalseFail() {
        assertFail(() -> new PrimitiveBooleanCheck(false).isTrue(), "false", "true");
    }

    @Test
    public void isBooleanSuccess() {
        new PrimitiveBooleanCheck(true).is(true);
    }

    @Test
    public void isBooleanFail() {
        assertFail(() -> new PrimitiveBooleanCheck(true).is(false), "true", "false");
    }
}
