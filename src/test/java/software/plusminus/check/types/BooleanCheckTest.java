package software.plusminus.check.types;

import org.junit.Test;

import static software.plusminus.check.Checks.check;
import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class BooleanCheckTest {

    @Test
    public void isTrueOk() {
        check(true).isTrue();
    }

    @Test
    public void isTrueFail() {
        assertFail(() -> check(true).isFalse(), "true", "false");
    }

    @Test
    public void isFalseOk() {
        check(false).isFalse();
    }

    @Test
    public void isFalseFail() {
        assertFail(() -> check(false).isTrue(), "false", "true");
    }
}
