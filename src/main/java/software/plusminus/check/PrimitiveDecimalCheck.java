package software.plusminus.check;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@CheckReturnValue
public class PrimitiveDecimalCheck<T extends Number> extends PrimitiveNumberCheck<T> {

    private final Scale scale = new Scale();

    public PrimitiveDecimalCheck(@Nullable T actual) {
        super(actual);
    }

    public PrimitiveDecimalCheck(@Nullable T actual, List<String> levels) {
        super(actual, levels);
    }

    public PrimitiveDecimalCheck<T> limitScale() {
        scale.limit();
        return this;
    }

    public PrimitiveDecimalCheck<T> limitScale(int scale) {
        this.scale.limit(scale);
        return this;
    }

    @Override
    public void is(T expected) {
        isNumber(expected);
    }

    @Override
    public void is(int expected) {
        isNumber(expected);
    }

    public void is(double expected) {
        isNumber(expected);
    }

    public void is(String expected) {
        isNumber(new BigDecimal(expected));
    }

    @Override
    protected T actual() {
        T actual = super.actual();
        return scale.apply(actual);
    }

    static class Scale {

        private static final int DEFAULT_SCALE = 4;

        private boolean limited;
        private int value;

        void limit() {
            limit(DEFAULT_SCALE);
        }

        void limit(int scale) {
            this.limited = true;
            this.value = scale;
        }

        @SuppressWarnings("unchecked")
        <N extends Number> N apply(N number) {
            if (!limited || number == null) {
                return number;
            }
            if (number instanceof BigDecimal) {
                return (N) ((BigDecimal) number).setScale(value, RoundingMode.HALF_UP);
            }
            if (number instanceof Float) {
                BigDecimal scaled = new BigDecimal(Float.toString(number.floatValue()))
                        .setScale(value, RoundingMode.HALF_UP);
                return (N) Float.valueOf(scaled.floatValue());
            }
            if (number instanceof Double) {
                BigDecimal scaled = new BigDecimal(Double.toString(number.doubleValue()))
                        .setScale(value, RoundingMode.HALF_UP);
                return (N) Double.valueOf(scaled.doubleValue());
            }
            throw new IllegalArgumentException("Unknown decimal type " + number.getClass());
        }
    }
}
