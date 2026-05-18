package software.plusminus.check.types;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.List;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@SuppressFBWarnings(value = "RV_RETURN_VALUE_IGNORED",
        justification = "super calls return this; narrowing override return type")
public class NullableDecimalCheck<T extends Number> extends DecimalCheck<T> {

    public NullableDecimalCheck(@Nullable T actual) {
        super(actual);
    }

    public NullableDecimalCheck(@Nullable T actual, List<String> levels) {
        super(actual, levels);
    }

    @Override
    @CheckReturnValue
    public NullableDecimalCheck<T> limitScale() {
        super.limitScale();
        return this;
    }

    @Override
    @CheckReturnValue
    public NullableDecimalCheck<T> limitScale(int scale) {
        super.limitScale(scale);
        return this;
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
