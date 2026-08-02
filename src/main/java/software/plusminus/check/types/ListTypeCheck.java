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
public interface ListTypeCheck {

    <X, M extends AbstractCheck<X>> ListCheck<X, List<X>, M> isListOf(
            Class<X> type, BiFunction<X, List<String>, M> checkBuilder);

    default ListCheck<Boolean, List<Boolean>, NullableBooleanCheck> isBooleanList() {
        return isListOf(Boolean.class, NullableBooleanCheck::new);
    }

    default ListCheck<Character, List<Character>, NullableCharacterCheck> isCharacterList() {
        return isListOf(Character.class, NullableCharacterCheck::new);
    }

    default <N extends Number> ListCheck<N, List<N>, NullableNumberCheck<N>> isNumberList() {
        Class<N> type = (Class<N>) Number.class;
        return isListOf(type, NullableNumberCheck::new);
    }

    default <N extends Number> ListCheck<N, List<N>, NullableNumberCheck<N>> isNumberList(Class<N> type) {
        return isListOf(type, NullableNumberCheck::new);
    }

    default ListCheck<Float, List<Float>, NullableDecimalCheck<Float>> isFloatList() {
        return isListOf(Float.class, NullableDecimalCheck::new);
    }

    default ListCheck<Double, List<Double>, NullableDecimalCheck<Double>> isDoubleList() {
        return isListOf(Double.class, NullableDecimalCheck::new);
    }

    default ListCheck<BigDecimal, List<BigDecimal>, NullableDecimalCheck<BigDecimal>> isBigDecimalList() {
        return isListOf(BigDecimal.class, NullableDecimalCheck::new);
    }

    default ListCheck<String, List<String>, StringCheck> isStringList() {
        return isListOf(String.class, StringCheck::new);
    }

    default ListCheck<Path, List<Path>, PathCheck> isPathList() {
        return isListOf(Path.class, PathCheck::new);
    }

    default ListCheck<InputStream, List<InputStream>, InputStreamCheck> isInputStreamList() {
        return isListOf(InputStream.class, InputStreamCheck::new);
    }

    default <X extends Temporal> ListCheck<X, List<X>, TemporalCheck<X>> isTemporalList() {
        Class<X> type = (Class<X>) Temporal.class;
        return isListOf(type, TemporalCheck::new);
    }

    default <X extends Temporal> ListCheck<X, List<X>, TemporalCheck<X>> isTemporalList(Class<X> type) {
        return isListOf(type, TemporalCheck::new);
    }

    default <X extends Enum<X>> ListCheck<X, List<X>, EnumCheck<X>> isEnumList() {
        Class<X> type = (Class<X>) (Class<?>) Enum.class;
        return isListOf(type, EnumCheck::new);
    }

    default <X extends Enum<X>> ListCheck<X, List<X>, EnumCheck<X>> isEnumList(Class<X> type) {
        return isListOf(type, EnumCheck::new);
    }
}
