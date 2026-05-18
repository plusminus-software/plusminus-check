package software.plusminus.check.types;

import java.util.List;
import javax.annotation.Nullable;

public class BooleanCheck extends AbstractCheck<Boolean> {

    public BooleanCheck(@Nullable Boolean actual) {
        super(actual);
    }

    public BooleanCheck(@Nullable Boolean actual, List<String> levels) {
        super(actual, levels);
    }

    @SuppressWarnings("PMD.UselessOverridingMethod")
    public void is(boolean expected) {
        super.is(expected);
    }

    public void isTrue() {
        is(true);
    }

    public void isFalse() {
        is(false);
    }
}
