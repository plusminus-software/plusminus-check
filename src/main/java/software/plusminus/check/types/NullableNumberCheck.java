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
    public void isNotNull() {
        super.isNotNull();
    }

    @Override
    public void isNull() {
        super.isNull();
    }
}
