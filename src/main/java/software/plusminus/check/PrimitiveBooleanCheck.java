package software.plusminus.check;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;

public class PrimitiveBooleanCheck extends AbstractCheck<Boolean> {

    public PrimitiveBooleanCheck(@Nullable Boolean actual) {
        this(actual, Collections.emptyList());
    }

    public PrimitiveBooleanCheck(@Nullable Boolean actual, List<String> levels) {
        super(actual, levels);
    }

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
