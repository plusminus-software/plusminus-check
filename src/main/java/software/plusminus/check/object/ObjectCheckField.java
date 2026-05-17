package software.plusminus.check.object;

import software.plusminus.check.AbstractCheck;
import software.plusminus.check.BooleanCheck;
import software.plusminus.check.CharacterCheck;
import software.plusminus.check.CollectionCheck;
import software.plusminus.check.DecimalCheck;
import software.plusminus.check.LinkedCheck;
import software.plusminus.check.MapCheck;
import software.plusminus.check.NumberCheck;
import software.plusminus.check.ObjectCheck;
import software.plusminus.check.OptionalCheck;
import software.plusminus.check.OrderedCollectionCheck;
import software.plusminus.check.PrimitiveBooleanCheck;
import software.plusminus.check.PrimitiveCharacterCheck;
import software.plusminus.check.PrimitiveDecimalCheck;
import software.plusminus.check.PrimitiveNumberCheck;
import software.plusminus.check.StringCheck;
import software.plusminus.check.getter.AbstractGetter;
import software.plusminus.check.getter.BigDecimalGetter;
import software.plusminus.check.getter.BigIntegerGetter;
import software.plusminus.check.getter.BooleanGetter;
import software.plusminus.check.getter.ByteGetter;
import software.plusminus.check.getter.CharacterGetter;
import software.plusminus.check.getter.CollectionGetter;
import software.plusminus.check.getter.DequeGetter;
import software.plusminus.check.getter.DoubleGetter;
import software.plusminus.check.getter.FloatGetter;
import software.plusminus.check.getter.IntegerGetter;
import software.plusminus.check.getter.ListGetter;
import software.plusminus.check.getter.LongGetter;
import software.plusminus.check.getter.MapGetter;
import software.plusminus.check.getter.OptionalGetter;
import software.plusminus.check.getter.PrimitiveBooleanGetter;
import software.plusminus.check.getter.PrimitiveByteGetter;
import software.plusminus.check.getter.PrimitiveCharacterGetter;
import software.plusminus.check.getter.PrimitiveDoubleGetter;
import software.plusminus.check.getter.PrimitiveFloatGetter;
import software.plusminus.check.getter.PrimitiveIntegerGetter;
import software.plusminus.check.getter.PrimitiveLongGetter;
import software.plusminus.check.getter.PrimitiveShortGetter;
import software.plusminus.check.getter.ShortGetter;
import software.plusminus.check.getter.SortedSetGetter;
import software.plusminus.check.getter.StringGetter;
import software.plusminus.check.util.FieldCoverage;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;
import java.util.function.BiFunction;
import java.util.function.Function;
import javax.annotation.CheckReturnValue;

@SuppressWarnings("checkstyle:ClassFanOutComplexity")
@CheckReturnValue
public interface ObjectCheckField<T> {

    default void allFieldsChecked() {
        allFieldsChecked(FieldCoverage.ALL);
    }

    void allFieldsChecked(FieldCoverage coverage);

    default <X> LinkedCheck<X, ObjectCheck<X>, ObjectCheck<T>> field(AbstractGetter<T, X> getter) {
        BiFunction<X, List<String>, ObjectCheck<X>> checkBuilder = ObjectCheck::new;
        return fieldOf(getter, checkBuilder);
    }

    <X> LinkedCheck<X, ObjectCheck<X>, ObjectCheck<T>> field(String fieldName);

    default LinkedCheck<Boolean, PrimitiveBooleanCheck, ObjectCheck<T>> fieldOf(PrimitiveBooleanGetter<T> getter) {
        return fieldOf(getter, getter::apply, PrimitiveBooleanCheck::new);
    }

    default LinkedCheck<Boolean, BooleanCheck, ObjectCheck<T>> fieldOf(BooleanGetter<T> getter) {
        return fieldOf(getter, BooleanCheck::new);
    }

    default LinkedCheck<Character, PrimitiveCharacterCheck, ObjectCheck<T>> fieldOf(
            PrimitiveCharacterGetter<T> getter) {
        return fieldOf(getter, getter::apply, PrimitiveCharacterCheck::new);
    }

    default LinkedCheck<Character, CharacterCheck, ObjectCheck<T>> fieldOf(CharacterGetter<T> getter) {
        return fieldOf(getter, CharacterCheck::new);
    }

    default LinkedCheck<Byte, PrimitiveNumberCheck<Byte>, ObjectCheck<T>> fieldOf(PrimitiveByteGetter<T> getter) {
        return fieldOf(getter, getter::apply, PrimitiveNumberCheck::new);
    }

    default LinkedCheck<Byte, NumberCheck<Byte>, ObjectCheck<T>> fieldOf(ByteGetter<T> getter) {
        return fieldOf(getter, NumberCheck::new);
    }

    default LinkedCheck<Short, PrimitiveNumberCheck<Short>, ObjectCheck<T>> fieldOf(PrimitiveShortGetter<T> getter) {
        return fieldOf(getter, getter::apply, PrimitiveNumberCheck::new);
    }

    default LinkedCheck<Short, NumberCheck<Short>, ObjectCheck<T>> fieldOf(ShortGetter<T> getter) {
        return fieldOf(getter, NumberCheck::new);
    }

    default LinkedCheck<Integer, PrimitiveNumberCheck<Integer>, ObjectCheck<T>> fieldOf(
            PrimitiveIntegerGetter<T> getter) {
        return fieldOf(getter, getter::apply, PrimitiveNumberCheck::new);
    }

    default LinkedCheck<Integer, NumberCheck<Integer>, ObjectCheck<T>> fieldOf(IntegerGetter<T> getter) {
        return fieldOf(getter, NumberCheck::new);
    }

    default LinkedCheck<Long, PrimitiveNumberCheck<Long>, ObjectCheck<T>> fieldOf(PrimitiveLongGetter<T> getter) {
        return fieldOf(getter, getter::apply, PrimitiveNumberCheck::new);
    }

    default LinkedCheck<Long, NumberCheck<Long>, ObjectCheck<T>> fieldOf(LongGetter<T> getter) {
        return fieldOf(getter, NumberCheck::new);
    }

    default LinkedCheck<BigInteger, NumberCheck<BigInteger>, ObjectCheck<T>> fieldOf(BigIntegerGetter<T> getter) {
        return fieldOf(getter, NumberCheck::new);
    }

    default LinkedCheck<Float, PrimitiveDecimalCheck<Float>, ObjectCheck<T>> fieldOf(PrimitiveFloatGetter<T> getter) {
        return fieldOf(getter, getter::apply, PrimitiveDecimalCheck::new);
    }

    default LinkedCheck<Float, DecimalCheck<Float>, ObjectCheck<T>> fieldOf(FloatGetter<T> getter) {
        return fieldOf(getter, DecimalCheck::new);
    }

    default LinkedCheck<Double, PrimitiveDecimalCheck<Double>, ObjectCheck<T>> fieldOf(
            PrimitiveDoubleGetter<T> getter) {
        return fieldOf(getter, getter::apply, PrimitiveDecimalCheck::new);
    }

    default LinkedCheck<Double, DecimalCheck<Double>, ObjectCheck<T>> fieldOf(DoubleGetter<T> getter) {
        return fieldOf(getter, DecimalCheck::new);
    }

    default LinkedCheck<BigDecimal, DecimalCheck<BigDecimal>, ObjectCheck<T>> fieldOf(BigDecimalGetter<T> getter) {
        return fieldOf(getter, DecimalCheck::new);
    }

    default LinkedCheck<String, StringCheck, ObjectCheck<T>> fieldOf(StringGetter<T> getter) {
        return fieldOf(getter, StringCheck::new);
    }

    default <E> LinkedCheck<Collection<E>, CollectionCheck<E, Collection<E>, ObjectCheck<E>>, ObjectCheck<T>> fieldOf(
            CollectionGetter<T, E> getter) {
        return fieldOf(getter, CollectionCheck::create);
    }

    default <E> LinkedCheck<List<E>, OrderedCollectionCheck<E, List<E>, ObjectCheck<E>>, ObjectCheck<T>> fieldOf(
            ListGetter<T, E> getter) {
        BiFunction<E, List<String>, ObjectCheck<E>> elementCheckBuilder = ObjectCheck::new;
        return fieldOf(getter, (v, l) -> new OrderedCollectionCheck<>(v, l, elementCheckBuilder));
    }

    default <E> LinkedCheck<SortedSet<E>, OrderedCollectionCheck<E, SortedSet<E>, ObjectCheck<E>>, ObjectCheck<T>>
            fieldOf(SortedSetGetter<T, E> getter) {
        return fieldOf(getter, OrderedCollectionCheck::create);
    }

    default <E> LinkedCheck<Deque<E>, OrderedCollectionCheck<E, Deque<E>, ObjectCheck<E>>, ObjectCheck<T>> fieldOf(
            DequeGetter<T, E> getter) {
        return fieldOf(getter, OrderedCollectionCheck::create);
    }

    default <O> LinkedCheck<Optional<O>, OptionalCheck<O>, ObjectCheck<T>> fieldOf(OptionalGetter<T, O> getter) {
        return fieldOf(getter, OptionalCheck::new);
    }

    default <K, V> LinkedCheck<Map<K, V>, MapCheck<K, V>, ObjectCheck<T>> fieldOf(MapGetter<T, K, V> getter) {
        return fieldOf(getter, MapCheck::new);
    }

    <V, C extends AbstractCheck<V>> LinkedCheck<V, C, ObjectCheck<T>> fieldOf(
            AbstractGetter<T, V> getter,
            BiFunction<V, List<String>, C> checkBuilder);

    <V, C extends AbstractCheck<V>> LinkedCheck<V, C, ObjectCheck<T>> fieldOf(
            Serializable getter,
            Function<T, V> valueProvider,
            BiFunction<V, List<String>, C> checkBuilder);
    
}
