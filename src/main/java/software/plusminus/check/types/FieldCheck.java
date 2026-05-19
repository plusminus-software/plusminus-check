package software.plusminus.check.types;

import software.plusminus.check.getter.AbstractGetter;
import software.plusminus.check.getter.ArrayGetter;
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
public interface FieldCheck<T> {

    default void allFieldsChecked() {
        allFieldsChecked(FieldCoverage.ALL);
    }

    void allFieldsChecked(FieldCoverage coverage);

    <X> LinkedCheck<X, ObjectCheck<X>, ObjectCheck<T>> field(String fieldName);

    default <X> LinkedCheck<X, ObjectCheck<X>, ObjectCheck<T>> field(AbstractGetter<T, X> getter) {
        BiFunction<X, List<String>, ObjectCheck<X>> checkBuilder = ObjectCheck::new;
        return field(getter, checkBuilder);
    }

    default LinkedCheck<Boolean, BooleanCheck, ObjectCheck<T>> field(PrimitiveBooleanGetter<T> getter) {
        return field(getter, getter::apply, BooleanCheck::new);
    }

    default LinkedCheck<Boolean, NullableBooleanCheck, ObjectCheck<T>> field(BooleanGetter<T> getter) {
        return field(getter, NullableBooleanCheck::new);
    }

    default LinkedCheck<Character, CharacterCheck, ObjectCheck<T>> field(
            PrimitiveCharacterGetter<T> getter) {
        return field(getter, getter::apply, CharacterCheck::new);
    }

    default LinkedCheck<Character, NullableCharacterCheck, ObjectCheck<T>> field(CharacterGetter<T> getter) {
        return field(getter, NullableCharacterCheck::new);
    }

    default LinkedCheck<Byte, NumberCheck<Byte>, ObjectCheck<T>> field(PrimitiveByteGetter<T> getter) {
        return field(getter, getter::apply, NumberCheck::new);
    }

    default LinkedCheck<Byte, NullableNumberCheck<Byte>, ObjectCheck<T>> field(ByteGetter<T> getter) {
        return field(getter, NullableNumberCheck::new);
    }

    default LinkedCheck<Short, NumberCheck<Short>, ObjectCheck<T>> field(PrimitiveShortGetter<T> getter) {
        return field(getter, getter::apply, NumberCheck::new);
    }

    default LinkedCheck<Short, NullableNumberCheck<Short>, ObjectCheck<T>> field(ShortGetter<T> getter) {
        return field(getter, NullableNumberCheck::new);
    }

    default LinkedCheck<Integer, NumberCheck<Integer>, ObjectCheck<T>> field(
            PrimitiveIntegerGetter<T> getter) {
        return field(getter, getter::apply, NumberCheck::new);
    }

    default LinkedCheck<Integer, NullableNumberCheck<Integer>, ObjectCheck<T>> field(IntegerGetter<T> getter) {
        return field(getter, NullableNumberCheck::new);
    }

    default LinkedCheck<Long, NumberCheck<Long>, ObjectCheck<T>> field(PrimitiveLongGetter<T> getter) {
        return field(getter, getter::apply, NumberCheck::new);
    }

    default LinkedCheck<Long, NullableNumberCheck<Long>, ObjectCheck<T>> field(LongGetter<T> getter) {
        return field(getter, NullableNumberCheck::new);
    }

    default LinkedCheck<BigInteger, NullableNumberCheck<BigInteger>, ObjectCheck<T>> field(
            BigIntegerGetter<T> getter) {
        return field(getter, NullableNumberCheck::new);
    }

    default LinkedCheck<Float, DecimalCheck<Float>, ObjectCheck<T>> field(PrimitiveFloatGetter<T> getter) {
        return field(getter, getter::apply, DecimalCheck::new);
    }

    default LinkedCheck<Float, NullableDecimalCheck<Float>, ObjectCheck<T>> field(FloatGetter<T> getter) {
        return field(getter, NullableDecimalCheck::new);
    }

    default LinkedCheck<Double, DecimalCheck<Double>, ObjectCheck<T>> field(
            PrimitiveDoubleGetter<T> getter) {
        return field(getter, getter::apply, DecimalCheck::new);
    }

    default LinkedCheck<Double, NullableDecimalCheck<Double>, ObjectCheck<T>> field(DoubleGetter<T> getter) {
        return field(getter, NullableDecimalCheck::new);
    }

    default LinkedCheck<BigDecimal, NullableDecimalCheck<BigDecimal>, ObjectCheck<T>> field(
            BigDecimalGetter<T> getter) {
        return field(getter, NullableDecimalCheck::new);
    }

    default LinkedCheck<String, StringCheck, ObjectCheck<T>> field(StringGetter<T> getter) {
        return field(getter, StringCheck::new);
    }

    default <E> LinkedCheck<Collection<E>, CollectionCheck<E, Collection<E>, ObjectCheck<E>>, ObjectCheck<T>> field(
            CollectionGetter<T, E> getter) {
        return field(getter, CollectionCheck::create);
    }

    default <E> LinkedCheck<List<E>, ListCheck<E, List<E>, ObjectCheck<E>>, ObjectCheck<T>> field(
            ListGetter<T, E> getter) {
        BiFunction<E, List<String>, ObjectCheck<E>> elementCheckBuilder = ObjectCheck::new;
        return field(getter, (v, l) -> new ListCheck<>(v, l, elementCheckBuilder));
    }

    default <E> LinkedCheck<SortedSet<E>, ListCheck<E, SortedSet<E>, ObjectCheck<E>>, ObjectCheck<T>>
            field(SortedSetGetter<T, E> getter) {
        return field(getter, ListCheck::create);
    }

    default <E> LinkedCheck<Deque<E>, ListCheck<E, Deque<E>, ObjectCheck<E>>, ObjectCheck<T>> field(
            DequeGetter<T, E> getter) {
        return field(getter, ListCheck::create);
    }

    default <O> LinkedCheck<Optional<O>, OptionalCheck<O>, ObjectCheck<T>> field(OptionalGetter<T, O> getter) {
        return field(getter, OptionalCheck::new);
    }

    default <K, V> LinkedCheck<Map<K, V>, MapCheck<K, V>, ObjectCheck<T>> field(MapGetter<T, K, V> getter) {
        return field(getter, MapCheck::new);
    }

    default <E> LinkedCheck<E[], ArrayCheck<E, ObjectCheck<E>>, ObjectCheck<T>> field(ArrayGetter<T, E> getter) {
        BiFunction<E, List<String>, ObjectCheck<E>> elementCheckBuilder = ObjectCheck::new;
        return field(getter, (v, l) -> new ArrayCheck<>(v, l, elementCheckBuilder));
    }

    <V, C extends AbstractCheck<V>> LinkedCheck<V, C, ObjectCheck<T>> field(
            AbstractGetter<T, V> getter,
            BiFunction<V, List<String>, C> checkBuilder);

    <V, C extends AbstractCheck<V>> LinkedCheck<V, C, ObjectCheck<T>> field(
            Serializable getter,
            Function<T, V> valueProvider,
            BiFunction<V, List<String>, C> checkBuilder);

}
