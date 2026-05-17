package software.plusminus.check;

import java.util.List;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

public class DecimalCheck<T extends Number> extends PrimitiveDecimalCheck<T> {

    public DecimalCheck(@Nullable T actual) {
        super(actual);
    }

    public DecimalCheck(@Nullable T actual, List<String> levels) {
        super(actual, levels);
    }

    @Override
    @CheckReturnValue
    public DecimalCheck<T> limitScale() {
        super.limitScale();
        return this;
    }

    @Override
    @CheckReturnValue
    public DecimalCheck<T> limitScale(int scale) {
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
