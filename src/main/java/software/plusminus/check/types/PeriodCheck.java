package software.plusminus.check.types;

import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.annotation.Nullable;

public class PeriodCheck extends AbstractCheck<Period> {

    public PeriodCheck(@Nullable Period actual) {
        super(actual);
    }

    public PeriodCheck(@Nullable Period actual, List<String> levels) {
        super(actual, levels);
    }

    /**
     * Compares against an ISO-8601 period, for instance {@code "P1Y2M3D"}.
     *
     * @param expected expected period in ISO-8601 format
     */
    public void is(String expected) {
        if (checkNull(expected)) {
            return;
        }
        is(parse(expected));
    }

    @Override
    public void isNull() {
        super.isNull();
    }

    @Override
    public PeriodCheck isNotNull() {
        super.isNotNull();
        return this;
    }

    @Override
    public PeriodCheck isNot(Period unexpected) {
        super.isNot(unexpected);
        return this;
    }

    public PeriodCheck isZero() {
        isNotNull();
        if (!actual().isZero()) {
            fail("zero");
        }
        return this;
    }

    public PeriodCheck isNegative() {
        isNotNull();
        if (!actual().isNegative()) {
            fail("negative");
        }
        return this;
    }

    public PeriodCheck hasYears(int expectedYears) {
        isNotNull();
        if (actual().getYears() != expectedYears) {
            fail(actual().getYears() + " years", expectedYears + " years");
        }
        return this;
    }

    public PeriodCheck hasMonths(int expectedMonths) {
        isNotNull();
        if (actual().getMonths() != expectedMonths) {
            fail(actual().getMonths() + " months", expectedMonths + " months");
        }
        return this;
    }

    public PeriodCheck hasDays(int expectedDays) {
        isNotNull();
        if (actual().getDays() != expectedDays) {
            fail(actual().getDays() + " days", expectedDays + " days");
        }
        return this;
    }

    private Period parse(String expected) {
        try {
            return Period.parse(expected);
        } catch (DateTimeParseException e) {
            fail("not an ISO-8601 period: " + expected);
            return null;
        }
    }
}
