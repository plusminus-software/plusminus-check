package software.plusminus.check.types;

import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.temporal.Temporal;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import javax.annotation.CheckReturnValue;

@SuppressWarnings({"unchecked", "checkstyle:ClassFanOutComplexity", "PMD.CouplingBetweenObjects"})
@CheckReturnValue
public interface CollectionTypeCheck {

    <X, M extends AbstractCheck<X>> CollectionCheck<X, Collection<X>, M> isCollectionOf(
            Class<X> type, BiFunction<X, List<String>, M> checkBuilder);

    default CollectionCheck<Boolean, Collection<Boolean>, NullableBooleanCheck> isBooleanCollection() {
        return isCollectionOf(Boolean.class, NullableBooleanCheck::new);
    }

    default CollectionCheck<Character, Collection<Character>, NullableCharacterCheck> isCharacterCollection() {
        return isCollectionOf(Character.class, NullableCharacterCheck::new);
    }

    default <N extends Number> CollectionCheck<N, Collection<N>, NullableNumberCheck<N>> isNumberCollection() {
        Class<N> type = (Class<N>) Number.class;
        return isCollectionOf(type, NullableNumberCheck::new);
    }

    default <N extends Number> CollectionCheck<N, Collection<N>, NullableNumberCheck<N>> isNumberCollection(
            Class<N> type) {
        return isCollectionOf(type, NullableNumberCheck::new);
    }

    default CollectionCheck<Float, Collection<Float>, NullableDecimalCheck<Float>> isFloatCollection() {
        return isCollectionOf(Float.class, NullableDecimalCheck::new);
    }

    default CollectionCheck<Double, Collection<Double>, NullableDecimalCheck<Double>> isDoubleCollection() {
        return isCollectionOf(Double.class, NullableDecimalCheck::new);
    }

    default CollectionCheck<BigDecimal, Collection<BigDecimal>,
            NullableDecimalCheck<BigDecimal>> isBigDecimalCollection() {
        return isCollectionOf(BigDecimal.class, NullableDecimalCheck::new);
    }

    default CollectionCheck<String, Collection<String>, StringCheck> isStringCollection() {
        return isCollectionOf(String.class, StringCheck::new);
    }

    default CollectionCheck<Path, Collection<Path>, PathCheck> isPathCollection() {
        return isCollectionOf(Path.class, PathCheck::new);
    }

    default CollectionCheck<InputStream, Collection<InputStream>, InputStreamCheck> isInputStreamCollection() {
        return isCollectionOf(InputStream.class, InputStreamCheck::new);
    }

    default <X extends Temporal> CollectionCheck<X, Collection<X>, TemporalCheck<X>> isTemporalCollection() {
        Class<X> type = (Class<X>) Temporal.class;
        return isCollectionOf(type, TemporalCheck::new);
    }

    default <X extends Temporal> CollectionCheck<X, Collection<X>, TemporalCheck<X>> isTemporalCollection(
            Class<X> type) {
        return isCollectionOf(type, TemporalCheck::new);
    }

    default <X extends Enum<X>> CollectionCheck<X, Collection<X>, EnumCheck<X>> isEnumCollection() {
        Class<X> type = (Class<X>) (Class<?>) Enum.class;
        return isCollectionOf(type, EnumCheck::new);
    }

    default <X extends Enum<X>> CollectionCheck<X, Collection<X>, EnumCheck<X>> isEnumCollection(Class<X> type) {
        return isCollectionOf(type, EnumCheck::new);
    }
}
