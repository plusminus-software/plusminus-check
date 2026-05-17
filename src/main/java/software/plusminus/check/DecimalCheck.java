package software.plusminus.check;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.List;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@SuppressFBWarnings(value = "RV_RETURN_VALUE_IGNORED",
        justification = "super calls return this; narrowing override return type")
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
