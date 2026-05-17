package software.plusminus.check.object;

import software.plusminus.check.AbstractCheck;
import software.plusminus.check.BooleanCheck;
import software.plusminus.check.CharacterCheck;
import software.plusminus.check.CollectionCheck;
import software.plusminus.check.DecimalCheck;
import software.plusminus.check.EnumCheck;
import software.plusminus.check.MapCheck;
import software.plusminus.check.NumberCheck;
import software.plusminus.check.ObjectCheck;
import software.plusminus.check.OptionalCheck;
import software.plusminus.check.OrderedCollectionCheck;
import software.plusminus.check.StringCheck;
import software.plusminus.check.TemporalCheck;

import java.math.BigDecimal;
import java.time.temporal.Temporal;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;
import java.util.function.BiFunction;

@SuppressWarnings("unchecked")
public interface ObjectCheckType {

    default BooleanCheck isBoolean() {
        return isType(Boolean.class, BooleanCheck::new);
    }

    default CharacterCheck isCharacter() {
        return isType(Character.class, CharacterCheck::new);
    }

    default <X> CollectionCheck<X, Collection<X>, ObjectCheck<X>> isCollection() {
        Class<Collection<X>> type = (Class<Collection<X>>) (Class<?>) Collection.class;
        return isType(type, CollectionCheck::create);
    }

    default <X> OrderedCollectionCheck<X, List<X>, ObjectCheck<X>> isList() {
        Class<List<X>> type = (Class<List<X>>) (Class<?>) List.class;
        return isType(type, OrderedCollectionCheck::create);
    }

    default <X> OrderedCollectionCheck<X, SortedSet<X>, ObjectCheck<X>> isSortedSet() {
        Class<SortedSet<X>> type = (Class<SortedSet<X>>) (Class<?>) SortedSet.class;
        return isType(type, OrderedCollectionCheck::create);
    }

    default <X> OrderedCollectionCheck<X, Deque<X>, ObjectCheck<X>> isDeque() {
        Class<Deque<X>> type = (Class<Deque<X>>) (Class<?>) Deque.class;
        return isType(type, OrderedCollectionCheck::create);
    }

    default <X extends Enum<X>> EnumCheck<X> isEnum() {
        Class<X> type = (Class<X>) (Class<?>) Enum.class;
        return isType(type, EnumCheck::new);
    }

    default <N extends Number> NumberCheck<N> isNumber() {
        Class<N> type = (Class<N>) (Class<?>) Number.class;
        return isType(type, NumberCheck::new);
    }

    default DecimalCheck<Float> isFloat() {
        return isType(Float.class, DecimalCheck::new);
    }

    default DecimalCheck<Double> isDouble() {
        return isType(Double.class, DecimalCheck::new);
    }

    default DecimalCheck<BigDecimal> isBigDecimal() {
        return isType(BigDecimal.class, DecimalCheck::new);
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

    default <X extends Temporal> TemporalCheck<X> isTemporal() {
        Class<X> type = (Class<X>) (Class<?>) Temporal.class;
        return isType(type, TemporalCheck::new);
    }

    <X, C extends AbstractCheck<X>> C isType(Class<X> type,
                                             BiFunction<X, List<String>, C> checkBuilder);
}
