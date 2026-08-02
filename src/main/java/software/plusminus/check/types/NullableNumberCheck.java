package software.plusminus.check.types;

import java.util.List;
import javax.annotation.Nullable;

public class NullableNumberCheck<T extends Number> extends NumberCheck<T> {

    public NullableNumberCheck(@Nullable T actual) {
        super(actual);
    }

    public NullableNumberCheck(@Nullable T actual, List<String> levels) {
        super(actual, levels);
    }

    @Override
    public NullableNumberCheck<T> isNotNull() {
        super.isNotNull();
        return this;
    }

    @Override
    public NullableNumberCheck<T> isNot(T unexpected) {
        super.isNot(unexpected);
        return this;
    }

    @Override
    public void isNull() {
        super.isNull();
    }
}
