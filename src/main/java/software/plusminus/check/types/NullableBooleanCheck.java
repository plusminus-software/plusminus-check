package software.plusminus.check.types;

import java.util.List;
import javax.annotation.Nullable;

public class NullableBooleanCheck extends BooleanCheck {

    public NullableBooleanCheck(@Nullable Boolean actual) {
        super(actual);
    }

    public NullableBooleanCheck(@Nullable Boolean actual, List<String> levels) {
        super(actual, levels);
    }

    @Override
    public void isNull() {
        super.isNull();
    }

    @Override
    public NullableBooleanCheck isNotNull() {
        super.isNotNull();
        return this;
    }

    @Override
    public NullableBooleanCheck isNot(Boolean unexpected) {
        super.isNot(unexpected);
        return this;
    }
}
