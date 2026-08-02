package software.plusminus.check.types;

import org.junit.Test;

import java.time.Period;

import static software.plusminus.check.Checks.check;
import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class PeriodCheckTest {

    @Test
    public void isOk() {
        check(Period.ofDays(3)).is(Period.ofDays(3));
    }

    @Test
    public void isFail() {
        assertFail(() -> check(Period.ofDays(3)).is(Period.ofDays(4)), "P3D", "P4D");
    }

    @Test
    public void isStringOk() {
        check(Period.of(1, 2, 3)).is("P1Y2M3D");
    }

    @Test
    public void isStringFail() {
        assertFail(() -> check(Period.ofDays(3)).is("P4D"), "P3D", "P4D");
    }

    @Test
    public void isNotAnIsoPeriodFail() {
        assertFail(() -> check(Period.ofDays(3)).is("three days"),
                "P3D", "not an ISO-8601 period: three days");
    }

    @Test
    public void isZeroOk() {
        check(Period.ZERO).isZero();
    }

    @Test
    public void isZeroFail() {
        assertFail(() -> check(Period.ofDays(1)).isZero(), "P1D", "zero");
    }

    @Test
    public void isNegativeOk() {
        check(Period.ofDays(-1)).isNegative();
    }

    @Test
    public void isNegativeFail() {
        assertFail(() -> check(Period.ofDays(1)).isNegative(), "P1D", "negative");
    }

    @Test
    public void hasYearsOk() {
        check(Period.of(1, 2, 3)).hasYears(1);
    }

    @Test
    public void hasYearsFail() {
        assertFail(() -> check(Period.of(1, 2, 3)).hasYears(2), "1 years", "2 years");
    }

    @Test
    public void hasMonthsOk() {
        check(Period.of(1, 2, 3)).hasMonths(2);
    }

    @Test
    public void hasMonthsFail() {
        assertFail(() -> check(Period.of(1, 2, 3)).hasMonths(3), "2 months", "3 months");
    }

    @Test
    public void hasDaysOk() {
        check(Period.of(1, 2, 3)).hasDays(3);
    }

    @Test
    public void hasDaysFail() {
        assertFail(() -> check(Period.of(1, 2, 3)).hasDays(4), "3 days", "4 days");
    }

    @Test
    public void isNullOk() {
        check((Period) null).isNull();
    }

    @Test
    public void isNotNullFail() {
        assertFail(() -> check((Period) null).isNotNull(), "null", "not null");
    }
}
