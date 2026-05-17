package software.plusminus.check;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;

import static org.junit.Assert.assertTrue;
import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class TemporalCheckTest {

    @Test
    public void recentIfNow() {
        new TemporalCheck<>(Instant.now()).isRecent();
    }

    @Test
    public void recentIfLessThanSecond() {
        new TemporalCheck<>(Instant.now().minusMillis(900)).isRecent();
    }

    @Test
    public void recentFail() {
        boolean failed = false;
        try {
            new TemporalCheck<>(Instant.now().minusMillis(1001)).isRecent();
        } catch (AssertionError e) {
            failed = true;
        }
        assertTrue(failed);
    }

    @Test
    public void recentWithDurationSuccess() {
        new TemporalCheck<>(Instant.now().minusSeconds(3)).isRecent(Duration.ofSeconds(5));
    }

    @Test
    public void recentWithDurationFail() {
        boolean failed = false;
        try {
            new TemporalCheck<>(Instant.now().minusSeconds(10)).isRecent(Duration.ofSeconds(5));
        } catch (AssertionError e) {
            failed = true;
        }
        assertTrue(failed);
    }

    @Test
    public void isNullSuccess() {
        new TemporalCheck<Instant>(null).isNull();
    }

    @Test
    public void isNotNullSuccess() {
        new TemporalCheck<>(Instant.now()).isNotNull();
    }

    @Test
    public void isNotNullFail() {
        assertFail(() -> new TemporalCheck<Instant>(null).isNotNull(), "null", "not null");
    }
}
