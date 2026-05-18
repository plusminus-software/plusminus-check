package software.plusminus.check.types;

import org.junit.Test;

import java.util.Optional;

import static software.plusminus.check.Checks.check;
import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class OptionalCheckTest {

    @Test
    public void isEmptyOk() {
        check(Optional.empty()).isEmpty();
    }

    @Test
    public void isEmptyFail() {
        assertFail(() -> check(Optional.of("x")).isEmpty(), "not empty", "empty");
    }

    @Test
    public void isNotEmptyOk() {
        check(Optional.of(42)).isNotEmpty().is(42);
    }

    @Test
    public void isNotEmptyFail() {
        assertFail(() -> check(Optional.empty()).isNotEmpty(), "empty", "not empty");
    }

    @Test
    public void isNotEmptyChainedFail() {
        assertFail(() -> check(Optional.of(1)).isNotEmpty().is(2), 1, 2);
    }
}
