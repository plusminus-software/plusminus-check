package software.plusminus.check.types;

import org.junit.Test;

import java.time.Duration;

import static software.plusminus.check.Checks.check;
import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class DurationCheckTest {

    @Test
    public void isOk() {
        check(Duration.ofMinutes(1)).is(Duration.ofSeconds(60));
    }

    @Test
    public void isFail() {
        assertFail(() -> check(Duration.ofSeconds(1)).is(Duration.ofSeconds(2)), "PT1S", "PT2S");
    }

    @Test
    public void isStringOk() {
        check(Duration.ofSeconds(90)).is("PT1M30S");
    }

    @Test
    public void isStringFail() {
        assertFail(() -> check(Duration.ofSeconds(1)).is("PT2S"), "PT1S", "PT2S");
    }

    @Test
    public void isNotAnIsoDurationFail() {
        assertFail(() -> check(Duration.ofSeconds(1)).is("one second"),
                "PT1S", "not an ISO-8601 duration: one second");
    }

    @Test
    public void isZeroOk() {
        check(Duration.ZERO).isZero();
    }

    @Test
    public void isZeroFail() {
        assertFail(() -> check(Duration.ofSeconds(1)).isZero(), "positive", "zero");
    }

    @Test
    public void isPositiveOk() {
        check(Duration.ofSeconds(1)).isPositive();
    }

    @Test
    public void isPositiveFail() {
        assertFail(() -> check(Duration.ofSeconds(-1)).isPositive(), "negative", "positive");
    }

    @Test
    public void isNegativeOk() {
        check(Duration.ofSeconds(-1)).isNegative();
    }

    @Test
    public void isNegativeFail() {
        assertFail(() -> check(Duration.ZERO).isNegative(), "zero", "negative");
    }

    @Test
    public void isLongerThanOk() {
        check(Duration.ofSeconds(2)).isLongerThan(Duration.ofSeconds(1));
    }

    @Test
    public void isLongerThanFail() {
        assertFail(() -> check(Duration.ofSeconds(1)).isLongerThan(Duration.ofSeconds(1)),
                "PT1S", "longer than PT1S");
    }

    @Test
    public void isShorterThanOk() {
        check(Duration.ofSeconds(1)).isShorterThan(Duration.ofSeconds(2));
    }

    @Test
    public void isShorterThanFail() {
        assertFail(() -> check(Duration.ofSeconds(2)).isShorterThan(Duration.ofSeconds(1)),
                "PT2S", "shorter than PT1S");
    }

    @Test
    public void isBetweenOk() {
        check(Duration.ofSeconds(2)).isBetween(Duration.ofSeconds(1), Duration.ofSeconds(3));
    }

    @Test
    public void isBetweenBoundIncluded() {
        check(Duration.ofSeconds(1)).isBetween(Duration.ofSeconds(1), Duration.ofSeconds(3));
    }

    @Test
    public void isBetweenFail() {
        assertFail(() -> check(Duration.ofSeconds(4))
                        .isBetween(Duration.ofSeconds(1), Duration.ofSeconds(3)),
                "PT4S", "between PT1S and PT3S");
    }

    @Test
    public void isCloseToOk() {
        check(Duration.ofMillis(1100)).isCloseTo(Duration.ofSeconds(1), Duration.ofMillis(200));
    }

    @Test
    public void isCloseToFail() {
        assertFail(() -> check(Duration.ofMillis(1500))
                        .isCloseTo(Duration.ofSeconds(1), Duration.ofMillis(200)),
                "PT1.5S", "within PT0.2S of PT1S");
    }

    @Test
    public void isNullOk() {
        check((Duration) null).isNull();
    }

    @Test
    public void isNotNullFail() {
        assertFail(() -> check((Duration) null).isNotNull(), "null", "not null");
    }
}
