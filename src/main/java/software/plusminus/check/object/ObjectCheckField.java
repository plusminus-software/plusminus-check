package software.plusminus.check.object;

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
import software.plusminus.check.types.AbstractCheck;
import software.plusminus.check.types.BooleanCheck;
import software.plusminus.check.types.CharacterCheck;
import software.plusminus.check.types.CollectionCheck;
import software.plusminus.check.types.DecimalCheck;
import software.plusminus.check.types.LinkedCheck;
import software.plusminus.check.types.ListCheck;
import software.plusminus.check.types.MapCheck;
import software.plusminus.check.types.NullableBooleanCheck;
import software.plusminus.check.types.NullableCharacterCheck;
import software.plusminus.check.types.NullableDecimalCheck;
import software.plusminus.check.types.NullableNumberCheck;
import software.plusminus.check.types.NumberCheck;
import software.plusminus.check.types.ObjectCheck;
import software.plusminus.check.types.OptionalCheck;
import software.plusminus.check.types.StringCheck;
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

    default LinkedCheck<Boolean, BooleanCheck, ObjectCheck<T>> fieldOf(PrimitiveBooleanGetter<T> getter) {
        return fieldOf(getter, getter::apply, BooleanCheck::new);
    }

    default LinkedCheck<Boolean, NullableBooleanCheck, ObjectCheck<T>> fieldOf(BooleanGetter<T> getter) {
        return fieldOf(getter, NullableBooleanCheck::new);
    }

    default LinkedCheck<Character, CharacterCheck, ObjectCheck<T>> fieldOf(
            PrimitiveCharacterGetter<T> getter) {
        return fieldOf(getter, getter::apply, CharacterCheck::new);
    }

    default LinkedCheck<Character, NullableCharacterCheck, ObjectCheck<T>> fieldOf(CharacterGetter<T> getter) {
        return fieldOf(getter, NullableCharacterCheck::new);
    }

    default LinkedCheck<Byte, NumberCheck<Byte>, ObjectCheck<T>> fieldOf(PrimitiveByteGetter<T> getter) {
        return fieldOf(getter, getter::apply, NumberCheck::new);
    }

    default LinkedCheck<Byte, NullableNumberCheck<Byte>, ObjectCheck<T>> fieldOf(ByteGetter<T> getter) {
        return fieldOf(getter, NullableNumberCheck::new);
    }

    default LinkedCheck<Short, NumberCheck<Short>, ObjectCheck<T>> fieldOf(PrimitiveShortGetter<T> getter) {
        return fieldOf(getter, getter::apply, NumberCheck::new);
    }

    default LinkedCheck<Short, NullableNumberCheck<Short>, ObjectCheck<T>> fieldOf(ShortGetter<T> getter) {
        return fieldOf(getter, NullableNumberCheck::new);
    }

    default LinkedCheck<Integer, NumberCheck<Integer>, ObjectCheck<T>> fieldOf(
            PrimitiveIntegerGetter<T> getter) {
        return fieldOf(getter, getter::apply, NumberCheck::new);
    }

    default LinkedCheck<Integer, NullableNumberCheck<Integer>, ObjectCheck<T>> fieldOf(IntegerGetter<T> getter) {
        return fieldOf(getter, NullableNumberCheck::new);
    }

    default LinkedCheck<Long, NumberCheck<Long>, ObjectCheck<T>> fieldOf(PrimitiveLongGetter<T> getter) {
        return fieldOf(getter, getter::apply, NumberCheck::new);
    }

    default LinkedCheck<Long, NullableNumberCheck<Long>, ObjectCheck<T>> fieldOf(LongGetter<T> getter) {
        return fieldOf(getter, NullableNumberCheck::new);
    }

    default LinkedCheck<BigInteger, NullableNumberCheck<BigInteger>, ObjectCheck<T>> fieldOf(
            BigIntegerGetter<T> getter) {
        return fieldOf(getter, NullableNumberCheck::new);
    }

    default LinkedCheck<Float, DecimalCheck<Float>, ObjectCheck<T>> fieldOf(PrimitiveFloatGetter<T> getter) {
        return fieldOf(getter, getter::apply, DecimalCheck::new);
    }

    default LinkedCheck<Float, NullableDecimalCheck<Float>, ObjectCheck<T>> fieldOf(FloatGetter<T> getter) {
        return fieldOf(getter, NullableDecimalCheck::new);
    }

    default LinkedCheck<Double, DecimalCheck<Double>, ObjectCheck<T>> fieldOf(
            PrimitiveDoubleGetter<T> getter) {
        return fieldOf(getter, getter::apply, DecimalCheck::new);
    }

    default LinkedCheck<Double, NullableDecimalCheck<Double>, ObjectCheck<T>> fieldOf(DoubleGetter<T> getter) {
        return fieldOf(getter, NullableDecimalCheck::new);
    }

    default LinkedCheck<BigDecimal, NullableDecimalCheck<BigDecimal>, ObjectCheck<T>> fieldOf(
            BigDecimalGetter<T> getter) {
        return fieldOf(getter, NullableDecimalCheck::new);
    }

    default LinkedCheck<String, StringCheck, ObjectCheck<T>> fieldOf(StringGetter<T> getter) {
        return fieldOf(getter, StringCheck::new);
    }

    default <E> LinkedCheck<Collection<E>, CollectionCheck<E, Collection<E>, ObjectCheck<E>>, ObjectCheck<T>> fieldOf(
            CollectionGetter<T, E> getter) {
        return fieldOf(getter, CollectionCheck::create);
    }

    default <E> LinkedCheck<List<E>, ListCheck<E, List<E>, ObjectCheck<E>>, ObjectCheck<T>> fieldOf(
            ListGetter<T, E> getter) {
        BiFunction<E, List<String>, ObjectCheck<E>> elementCheckBuilder = ObjectCheck::new;
        return fieldOf(getter, (v, l) -> new ListCheck<>(v, l, elementCheckBuilder));
    }

    default <E> LinkedCheck<SortedSet<E>, ListCheck<E, SortedSet<E>, ObjectCheck<E>>, ObjectCheck<T>>
            fieldOf(SortedSetGetter<T, E> getter) {
        return fieldOf(getter, ListCheck::create);
    }

    default <E> LinkedCheck<Deque<E>, ListCheck<E, Deque<E>, ObjectCheck<E>>, ObjectCheck<T>> fieldOf(
            DequeGetter<T, E> getter) {
        return fieldOf(getter, ListCheck::create);
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
