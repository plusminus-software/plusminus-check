package software.plusminus.check.types;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;

import static software.plusminus.check.Checks.check;
import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class TemporalCheckTest {

    @Test
    public void recentNow() {
        check(Instant.now()).isRecent();
    }

    @Test
    public void recentBelowSecond() {
        check(Instant.now().minusMillis(900)).isRecent();
    }

    @Test
    public void recentFail() {
        assertFail(() -> check(Instant.now().minusMillis(1001)).isRecent());
    }

    @Test
    public void recentWithDurationOk() {
        check(Instant.now().minusSeconds(3)).isRecent(Duration.ofSeconds(5));
    }

    @Test
    public void recentWithDurationFail() {
        assertFail(() -> check(Instant.now().minusSeconds(10)).isRecent(Duration.ofSeconds(5)));
    }
}
