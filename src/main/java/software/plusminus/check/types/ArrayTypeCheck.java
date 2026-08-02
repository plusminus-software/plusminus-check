package software.plusminus.check.types;

import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.temporal.Temporal;
import java.util.List;
import java.util.function.BiFunction;
import javax.annotation.CheckReturnValue;

@SuppressWarnings({"unchecked", "checkstyle:ClassFanOutComplexity", "PMD.CouplingBetweenObjects"})
@CheckReturnValue
public interface ArrayTypeCheck {

    <X, M extends AbstractCheck<X>> ArrayCheck<X, M> isArrayOf(
            Class<X> type, BiFunction<X, List<String>, M> checkBuilder);

    default ArrayCheck<Boolean, NullableBooleanCheck> isBooleanArray() {
        return isArrayOf(Boolean.class, NullableBooleanCheck::new);
    }

    default ArrayCheck<Character, NullableCharacterCheck> isCharacterArray() {
        return isArrayOf(Character.class, NullableCharacterCheck::new);
    }

    default <N extends Number> ArrayCheck<N, NullableNumberCheck<N>> isNumberArray() {
        Class<N> type = (Class<N>) Number.class;
        return isArrayOf(type, NullableNumberCheck::new);
    }

    default <N extends Number> ArrayCheck<N, NullableNumberCheck<N>> isNumberArray(Class<N> type) {
        return isArrayOf(type, NullableNumberCheck::new);
    }

    default ArrayCheck<Float, NullableDecimalCheck<Float>> isFloatArray() {
        return isArrayOf(Float.class, NullableDecimalCheck::new);
    }

    default ArrayCheck<Double, NullableDecimalCheck<Double>> isDoubleArray() {
        return isArrayOf(Double.class, NullableDecimalCheck::new);
    }

    default ArrayCheck<BigDecimal, NullableDecimalCheck<BigDecimal>> isBigDecimalArray() {
        return isArrayOf(BigDecimal.class, NullableDecimalCheck::new);
    }

    default ArrayCheck<String, StringCheck> isStringArray() {
        return isArrayOf(String.class, StringCheck::new);
    }

    default ArrayCheck<Path, PathCheck> isPathArray() {
        return isArrayOf(Path.class, PathCheck::new);
    }

    default ArrayCheck<InputStream, InputStreamCheck> isInputStreamArray() {
        return isArrayOf(InputStream.class, InputStreamCheck::new);
    }

    default <X extends Temporal> ArrayCheck<X, TemporalCheck<X>> isTemporalArray() {
        Class<X> type = (Class<X>) Temporal.class;
        return isArrayOf(type, TemporalCheck::new);
    }

    default <X extends Temporal> ArrayCheck<X, TemporalCheck<X>> isTemporalArray(Class<X> type) {
        return isArrayOf(type, TemporalCheck::new);
    }

    default <X extends Enum<X>> ArrayCheck<X, EnumCheck<X>> isEnumArray() {
        Class<X> type = (Class<X>) (Class<?>) Enum.class;
        return isArrayOf(type, EnumCheck::new);
    }

    default <X extends Enum<X>> ArrayCheck<X, EnumCheck<X>> isEnumArray(Class<X> type) {
        return isArrayOf(type, EnumCheck::new);
    }
}
