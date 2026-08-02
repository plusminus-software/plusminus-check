package software.plusminus.check.types;

import software.plusminus.check.getter.ArrayGetter;
import software.plusminus.check.getter.BigDecimalGetter;
import software.plusminus.check.getter.BigIntegerGetter;
import software.plusminus.check.getter.BooleanGetter;
import software.plusminus.check.getter.ByteGetter;
import software.plusminus.check.getter.CharacterGetter;
import software.plusminus.check.getter.CollectionGetter;
import software.plusminus.check.getter.DequeGetter;
import software.plusminus.check.getter.DoubleGetter;
import software.plusminus.check.getter.EnumGetter;
import software.plusminus.check.getter.FloatGetter;
import software.plusminus.check.getter.InputStreamGetter;
import software.plusminus.check.getter.IntegerGetter;
import software.plusminus.check.getter.ListGetter;
import software.plusminus.check.getter.LongGetter;
import software.plusminus.check.getter.MapGetter;
import software.plusminus.check.getter.OptionalGetter;
import software.plusminus.check.getter.PathGetter;
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
import software.plusminus.check.getter.TemporalGetter;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Path;
import java.time.temporal.Temporal;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;
import java.util.function.BiFunction;
import java.util.function.Function;
import javax.annotation.CheckReturnValue;

@SuppressWarnings({"checkstyle:ClassFanOutComplexity", "PMD.CouplingBetweenObjects",
    "PMD.ExcessiveImports", "PMD.ExcessivePublicCount", "PMD.TooManyMethods"})
@CheckReturnValue
public interface CollectionMapCheck<T> {

    <R, M extends AbstractCheck<R>> CollectionCheck<R, Collection<R>, M> map(
            Function<T, R> mapper, BiFunction<R, List<String>, M> elementCheck);

    default <R> CollectionCheck<R, Collection<R>, ObjectCheck<R>> map(Function<T, R> mapper) {
        BiFunction<R, List<String>, ObjectCheck<R>> elementCheck = ObjectCheck::new;
        return map(mapper, elementCheck);
    }

    default CollectionCheck<Boolean, Collection<Boolean>, BooleanCheck> mapTo(PrimitiveBooleanGetter<T> mapper) {
        return map(mapper::apply, BooleanCheck::new);
    }

    default CollectionCheck<Boolean, Collection<Boolean>, NullableBooleanCheck> mapTo(BooleanGetter<T> mapper) {
        return map(mapper, NullableBooleanCheck::new);
    }

    default CollectionCheck<Character, Collection<Character>, CharacterCheck> mapTo(
            PrimitiveCharacterGetter<T> mapper) {
        return map(mapper::apply, CharacterCheck::new);
    }

    default CollectionCheck<Character, Collection<Character>, NullableCharacterCheck> mapTo(CharacterGetter<T> mapper) {
        return map(mapper, NullableCharacterCheck::new);
    }

    default CollectionCheck<Byte, Collection<Byte>, NumberCheck<Byte>> mapTo(PrimitiveByteGetter<T> mapper) {
        return map(mapper::apply, NumberCheck::new);
    }

    default CollectionCheck<Byte, Collection<Byte>, NullableNumberCheck<Byte>> mapTo(ByteGetter<T> mapper) {
        return map(mapper, NullableNumberCheck::new);
    }

    default CollectionCheck<Short, Collection<Short>, NumberCheck<Short>> mapTo(PrimitiveShortGetter<T> mapper) {
        return map(mapper::apply, NumberCheck::new);
    }

    default CollectionCheck<Short, Collection<Short>, NullableNumberCheck<Short>> mapTo(ShortGetter<T> mapper) {
        return map(mapper, NullableNumberCheck::new);
    }

    default CollectionCheck<Integer, Collection<Integer>, NumberCheck<Integer>> mapTo(
            PrimitiveIntegerGetter<T> mapper) {
        return map(mapper::apply, NumberCheck::new);
    }

    default CollectionCheck<Integer, Collection<Integer>, NullableNumberCheck<Integer>> mapTo(IntegerGetter<T> mapper) {
        return map(mapper, NullableNumberCheck::new);
    }

    default CollectionCheck<Long, Collection<Long>, NumberCheck<Long>> mapTo(PrimitiveLongGetter<T> mapper) {
        return map(mapper::apply, NumberCheck::new);
    }

    default CollectionCheck<Long, Collection<Long>, NullableNumberCheck<Long>> mapTo(LongGetter<T> mapper) {
        return map(mapper, NullableNumberCheck::new);
    }

    default CollectionCheck<BigInteger, Collection<BigInteger>, NullableNumberCheck<BigInteger>> mapTo(
            BigIntegerGetter<T> mapper) {
        return map(mapper, NullableNumberCheck::new);
    }

    default CollectionCheck<Float, Collection<Float>, DecimalCheck<Float>> mapTo(PrimitiveFloatGetter<T> mapper) {
        return map(mapper::apply, DecimalCheck::new);
    }

    default CollectionCheck<Float, Collection<Float>, NullableDecimalCheck<Float>> mapTo(FloatGetter<T> mapper) {
        return map(mapper, NullableDecimalCheck::new);
    }

    default CollectionCheck<Double, Collection<Double>, DecimalCheck<Double>> mapTo(PrimitiveDoubleGetter<T> mapper) {
        return map(mapper::apply, DecimalCheck::new);
    }

    default CollectionCheck<Double, Collection<Double>, NullableDecimalCheck<Double>> mapTo(DoubleGetter<T> mapper) {
        return map(mapper, NullableDecimalCheck::new);
    }

    default CollectionCheck<BigDecimal, Collection<BigDecimal>, NullableDecimalCheck<BigDecimal>> mapTo(
            BigDecimalGetter<T> mapper) {
        return map(mapper, NullableDecimalCheck::new);
    }

    default CollectionCheck<String, Collection<String>, StringCheck> mapTo(StringGetter<T> mapper) {
        return map(mapper, StringCheck::new);
    }

    default CollectionCheck<Path, Collection<Path>, PathCheck> mapTo(PathGetter<T> mapper) {
        return map(mapper, PathCheck::new);
    }

    default <X extends Temporal> CollectionCheck<X, Collection<X>, TemporalCheck<X>> mapTo(
            TemporalGetter<T, X> mapper) {
        return map(mapper, TemporalCheck::new);
    }

    default <X extends InputStream> CollectionCheck<InputStream, Collection<InputStream>, InputStreamCheck> mapTo(
            InputStreamGetter<T, X> mapper) {
        return map(mapper::apply, InputStreamCheck::new);
    }

    default <X extends Enum<X>> CollectionCheck<X, Collection<X>, EnumCheck<X>> mapTo(EnumGetter<T, X> mapper) {
        return map(mapper, EnumCheck::new);
    }

    default <X> CollectionCheck<Collection<X>, Collection<Collection<X>>, CollectionCheck<X, Collection<X>,
            ObjectCheck<X>>> mapTo(CollectionGetter<T, X> mapper) {
        return map(mapper, CollectionCheck::create);
    }

    default <X> CollectionCheck<List<X>, Collection<List<X>>, ListCheck<X, List<X>, ObjectCheck<X>>> mapTo(
            ListGetter<T, X> mapper) {
        BiFunction<X, List<String>, ObjectCheck<X>> elementCheck = ObjectCheck::new;
        return map(mapper, (v, l) -> new ListCheck<>(v, l, elementCheck));
    }

    default <X> CollectionCheck<SortedSet<X>, Collection<SortedSet<X>>, ListCheck<X, SortedSet<X>,
            ObjectCheck<X>>> mapTo(SortedSetGetter<T, X> mapper) {
        return map(mapper, ListCheck::create);
    }

    default <X> CollectionCheck<Deque<X>, Collection<Deque<X>>, ListCheck<X, Deque<X>, ObjectCheck<X>>> mapTo(
            DequeGetter<T, X> mapper) {
        return map(mapper, ListCheck::create);
    }

    default <X> CollectionCheck<Optional<X>, Collection<Optional<X>>, OptionalCheck<X>> mapTo(
            OptionalGetter<T, X> mapper) {
        return map(mapper, OptionalCheck::new);
    }

    default <K, V> CollectionCheck<Map<K, V>, Collection<Map<K, V>>, MapCheck<K, V>> mapTo(MapGetter<T, K, V> mapper) {
        return map(mapper, MapCheck::new);
    }

    default <X> CollectionCheck<X[], Collection<X[]>, ArrayCheck<X, ObjectCheck<X>>> mapTo(ArrayGetter<T, X> mapper) {
        BiFunction<X, List<String>, ObjectCheck<X>> elementCheck = ObjectCheck::new;
        return map(mapper, (v, l) -> new ArrayCheck<>(v, l, elementCheck));
    }
}
