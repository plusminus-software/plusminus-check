package software.plusminus.check.types;

import org.junit.Test;
import software.plusminus.check.fixtures.TestObject;

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
        check(Optional.of(42)).isPresent().is(42);
    }

    @Test
    public void isNotEmptyFail() {
        assertFail(() -> check(Optional.empty()).isPresent(), "empty", "not empty");
    }

    @Test
    public void isNotEmptyChainedFail() {
        assertFail(() -> check(Optional.of(1)).isPresent().is(2), 1, 2);
    }

    @Test
    public void isPresentOk() {
        check(Optional.of(42)).isPresent().is(42);
    }

    @Test
    public void isPresentFail() {
        assertFail(() -> check(Optional.empty()).isPresent(), "empty", "not empty");
    }

    @Test
    public void is() {
        check(Optional.of("abc")).is("abc");
    }

    @Test
    public void isFail() {
        assertFail(() -> check(Optional.of("abc")).is("abd"), "abc", "abd");
    }

    @Test
    public void isOnEmptyFail() {
        assertFail(() -> check(Optional.<String>empty()).is("abc"), "empty", "not empty");
    }

    @Test
    public void hasValueOk() {
        check(Optional.of(42)).hasValue(42);
    }

    @Test
    public void hasValueFail() {
        assertFail(() -> check(Optional.of(42)).hasValue(43), 42, 43);
    }

    @Test
    public void hasObjectValueOk() {
        check(Optional.of(new TestObject("a", 1))).hasValue(new TestObject("a", 1));
    }

    @Test
    public void hasEmptyValueFail() {
        assertFail(() -> check(Optional.<Integer>empty()).hasValue(42), "empty", "not empty");
    }
}
