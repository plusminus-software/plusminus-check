package software.plusminus.check;

import java.util.List;
import javax.annotation.Nullable;

public class NumberCheck<T extends Number> extends PrimitiveNumberCheck<T> {

    public NumberCheck(@Nullable T actual) {
        super(actual);
    }

    public NumberCheck(@Nullable T actual, List<String> levels) {
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
