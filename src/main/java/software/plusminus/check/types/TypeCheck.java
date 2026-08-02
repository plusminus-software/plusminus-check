package software.plusminus.check.types;

import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.temporal.Temporal;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;
import java.util.function.BiFunction;

@SuppressWarnings("unchecked")
public interface TypeCheck {

    <X, C extends AbstractCheck<X>> C isType(Class<X> type,
                                             BiFunction<X, List<String>, C> checkBuilder);

    default NullableBooleanCheck isBoolean() {
        return isType(Boolean.class, NullableBooleanCheck::new);
    }

    default NullableCharacterCheck isCharacter() {
        return isType(Character.class, NullableCharacterCheck::new);
    }

    default <X> ArrayCheck<X, ObjectCheck<X>> isArray() {
        Class<X[]> type = (Class<X[]>) (Class<?>) Object[].class;
        return isType(type, ArrayCheck::create);
    }

    default <X> CollectionCheck<X, Collection<X>, ObjectCheck<X>> isCollection() {
        Class<Collection<X>> type = (Class<Collection<X>>) (Class<?>) Collection.class;
        return isType(type, CollectionCheck::create);
    }

    default <X> ListCheck<X, List<X>, ObjectCheck<X>> isList() {
        Class<List<X>> type = (Class<List<X>>) (Class<?>) List.class;
        return isType(type, ListCheck::create);
    }

    default <X> ListCheck<X, SortedSet<X>, ObjectCheck<X>> isSortedSet() {
        Class<SortedSet<X>> type = (Class<SortedSet<X>>) (Class<?>) SortedSet.class;
        return isType(type, ListCheck::create);
    }

    default <X> ListCheck<X, Deque<X>, ObjectCheck<X>> isDeque() {
        Class<Deque<X>> type = (Class<Deque<X>>) (Class<?>) Deque.class;
        return isType(type, ListCheck::create);
    }

    default <X extends Enum<X>> EnumCheck<X> isEnum() {
        Class<X> type = (Class<X>) (Class<?>) Enum.class;
        return isType(type, EnumCheck::new);
    }

    default <N extends Number> NullableNumberCheck<N> isNumber() {
        Class<N> type = (Class<N>) Number.class;
        return isType(type, NullableNumberCheck::new);
    }

    default NullableDecimalCheck<Float> isFloat() {
        return isType(Float.class, NullableDecimalCheck::new);
    }

    default NullableDecimalCheck<Double> isDouble() {
        return isType(Double.class, NullableDecimalCheck::new);
    }

    default NullableDecimalCheck<BigDecimal> isBigDecimal() {
        return isType(BigDecimal.class, NullableDecimalCheck::new);
    }

    default <K, V> MapCheck<K, V> isMap() {
        Class<Map<K, V>> type = (Class<Map<K, V>>) (Class<?>) Map.class;
        return isType(type, MapCheck::new);
    }

    default <X> OptionalCheck<X> isOptional() {
        Class<Optional<X>> type = (Class<Optional<X>>) (Class<?>) Optional.class;
        return isType(type, OptionalCheck::new);
    }

    default StringCheck isString() {
        return isType(String.class, StringCheck::new);
    }

    default PathCheck isPath() {
        return isType(Path.class, PathCheck::new);
    }

    default InputStreamCheck isInputStream() {
        return isType(InputStream.class, InputStreamCheck::new);
    }

    default <X extends Temporal> TemporalCheck<X> isTemporal() {
        Class<X> type = (Class<X>) Temporal.class;
        return isType(type, TemporalCheck::new);
    }
}
