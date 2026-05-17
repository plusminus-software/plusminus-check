package software.plusminus.check;

import org.junit.Test;

import java.util.Optional;

import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class OptionalCheckTest {

    @Test
    public void isEmptySuccess() {
        new OptionalCheck<>(Optional.empty()).isEmpty();
    }

    @Test
    public void isEmptyFail() {
        assertFail(() -> new OptionalCheck<>(Optional.of("x")).isEmpty(), "not empty", "empty");
    }

    @Test
    public void isNotEmptySuccess() {
        new OptionalCheck<>(Optional.of(42)).isNotEmpty().is(42);
    }

    @Test
    public void isNotEmptyFail() {
        assertFail(() -> new OptionalCheck<>(Optional.empty()).isNotEmpty(), "empty", "not empty");
    }

    @Test
    public void isNotEmptyChainedFail() {
        assertFail(() -> new OptionalCheck<>(Optional.of(1)).isNotEmpty().is(2), 1, 2);
    }

    @Test
    public void isNullSuccess() {
        new OptionalCheck<String>(null).isNull();
    }

    @Test
    public void isNotNullFail() {
        assertFail(() -> new OptionalCheck<String>(null).isNotNull(), "null", "not null");
    }
}
