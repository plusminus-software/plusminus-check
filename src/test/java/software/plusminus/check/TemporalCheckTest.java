package software.plusminus.check;

import org.junit.Test;

import java.time.Instant;

import static org.junit.Assert.assertTrue;

public class TemporalCheckTest {
    
    @Test
    public void recentIfNow() throws Exception {
        new TemporalCheck(Instant.now()).recent();
    }
    
    @Test
    public void recentIfLessThanSecond() throws Exception {
        new TemporalCheck(Instant.now().minusMillis(900)).recent();
    }
    
    @Test
    public void recentFail() throws Exception {
        boolean failed = false;
        try {
            new TemporalCheck(Instant.now().minusMillis(1001)).recent();
        } catch (AssertionError e) {
            failed = true;
        }
        assertTrue(failed);
    }

}