package software.plusminus.check;

import org.junit.Test;

import java.time.Instant;

import static org.junit.Assert.assertTrue;

@SuppressWarnings("java:S2699")
public class TemporalCheckTest {
    
    @Test
    public void recentIfNow() {
        new TemporalCheck(Instant.now()).isRecent();
    }
    
    @Test
    public void recentIfLessThanSecond() {
        new TemporalCheck(Instant.now().minusMillis(900)).isRecent();
    }
    
    @Test
    public void recentFail() {
        boolean failed = false;
        try {
            new TemporalCheck(Instant.now().minusMillis(1001)).isRecent();
        } catch (AssertionError e) {
            failed = true;
        }
        assertTrue(failed);
    }

}