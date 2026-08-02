package software.plusminus.check.types;

import lombok.Data;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;

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

    @Test
    public void recentLocalDateTime() {
        check(LocalDateTime.now()).isRecent();
    }

    @Test
    public void recentLocalTime() {
        check(LocalTime.now()).isRecent();
    }

    @Test
    public void recentLocalDateWithinDay() {
        check(LocalDate.now()).isRecent(Duration.ofDays(2));
    }

    @Test
    public void recentYearMonthWithinRange() {
        check(YearMonth.now()).isRecent(Duration.ofDays(40));
    }

    @Test
    public void fieldWithGetterOk() {
        TestEvent event = new TestEvent(Instant.now());
        check(event).field(TestEvent::getCreatedAt).is(c -> c.isRecent());
    }

    @Test
    public void fieldWithGetterFail() {
        TestEvent event = new TestEvent(Instant.now().minusSeconds(10));
        assertFail(() -> check(event).field(TestEvent::getCreatedAt).is(c -> c.isRecent()));
    }

    @Test
    public void fieldWithGetterIsOk() {
        Instant now = Instant.now();
        TestEvent event = new TestEvent(now);
        check(event).field(TestEvent::getCreatedAt).is(now);
    }

    @Test
    public void isBeforeOk() {
        check(LocalDate.of(2020, 1, 1)).isBefore(LocalDate.of(2020, 1, 2));
    }

    @Test
    public void isBeforeSameFail() {
        assertFail(() -> check(LocalDate.of(2020, 1, 1)).isBefore(LocalDate.of(2020, 1, 1)),
                "2020-01-01", "before 2020-01-01");
    }

    @Test
    public void isAfterOk() {
        check(LocalDate.of(2020, 1, 2)).isAfter(LocalDate.of(2020, 1, 1));
    }

    @Test
    public void isAfterFail() {
        assertFail(() -> check(LocalDate.of(2020, 1, 1)).isAfter(LocalDate.of(2020, 1, 2)),
                "2020-01-01", "after 2020-01-02");
    }

    @Test
    public void isBetweenOk() {
        check(LocalDate.of(2020, 1, 2))
                .isBetween(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 3));
    }

    @Test
    public void isBetweenBoundIncluded() {
        check(LocalDate.of(2020, 1, 1))
                .isBetween(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 3));
    }

    @Test
    public void isBetweenFail() {
        assertFail(() -> check(LocalDate.of(2020, 1, 4))
                        .isBetween(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 3)),
                "2020-01-04", "between 2020-01-01 and 2020-01-03");
    }

    @Test
    public void isCloseToOk() {
        Instant now = Instant.now();
        check(now.plusMillis(500)).isCloseTo(now, Duration.ofSeconds(1));
    }

    @Test
    public void isCloseToEarlierOk() {
        Instant now = Instant.now();
        check(now.minusMillis(500)).isCloseTo(now, Duration.ofSeconds(1));
    }

    @Test
    public void isCloseToFail() {
        Instant now = Instant.parse("2020-01-01T00:00:00Z");
        assertFail(() -> check(now.plusSeconds(5)).isCloseTo(now, Duration.ofSeconds(1)),
                "2020-01-01T00:00:05Z", "within PT1S of 2020-01-01T00:00:00Z");
    }

    @Test
    public void isBeforeAcrossTypesOk() {
        check(LocalDate.of(2020, 1, 1)).isBefore(LocalDateTime.of(2020, 1, 1, 12, 0));
    }

    @Test
    public void chainsAssertions() {
        Instant now = Instant.now();
        check(now)
                .isNotNull()
                .isRecent()
                .isAfter(now.minusSeconds(10))
                .isBefore(now.plusSeconds(10))
                .isBetween(now.minusSeconds(10), now.plusSeconds(10))
                .isCloseTo(now, Duration.ofSeconds(1))
                .isNot(now.plusSeconds(1));
    }

    @Data
    private static class TestEvent {

        private Instant createdAt;

        TestEvent(Instant createdAt) {
            this.createdAt = createdAt;
        }
    }
}
