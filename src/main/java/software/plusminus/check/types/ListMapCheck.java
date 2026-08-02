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
public interface ListMapCheck<T> {

    <R, M extends AbstractCheck<R>> ListCheck<R, List<R>, M> map(
            Function<T, R> mapper, BiFunction<R, List<String>, M> elementCheck);

    default <R> ListCheck<R, List<R>, ObjectCheck<R>> map(Function<T, R> mapper) {
        BiFunction<R, List<String>, ObjectCheck<R>> elementCheck = ObjectCheck::new;
        return map(mapper, elementCheck);
    }

    default ListCheck<Boolean, List<Boolean>, BooleanCheck> mapTo(PrimitiveBooleanGetter<T> mapper) {
        return map(mapper::apply, BooleanCheck::new);
    }

    default ListCheck<Boolean, List<Boolean>, NullableBooleanCheck> mapTo(BooleanGetter<T> mapper) {
        return map(mapper, NullableBooleanCheck::new);
    }

    default ListCheck<Character, List<Character>, CharacterCheck> mapTo(PrimitiveCharacterGetter<T> mapper) {
        return map(mapper::apply, CharacterCheck::new);
    }

    default ListCheck<Character, List<Character>, NullableCharacterCheck> mapTo(CharacterGetter<T> mapper) {
        return map(mapper, NullableCharacterCheck::new);
    }

    default ListCheck<Byte, List<Byte>, NumberCheck<Byte>> mapTo(PrimitiveByteGetter<T> mapper) {
        return map(mapper::apply, NumberCheck::new);
    }

    default ListCheck<Byte, List<Byte>, NullableNumberCheck<Byte>> mapTo(ByteGetter<T> mapper) {
        return map(mapper, NullableNumberCheck::new);
    }

    default ListCheck<Short, List<Short>, NumberCheck<Short>> mapTo(PrimitiveShortGetter<T> mapper) {
        return map(mapper::apply, NumberCheck::new);
    }

    default ListCheck<Short, List<Short>, NullableNumberCheck<Short>> mapTo(ShortGetter<T> mapper) {
        return map(mapper, NullableNumberCheck::new);
    }

    default ListCheck<Integer, List<Integer>, NumberCheck<Integer>> mapTo(PrimitiveIntegerGetter<T> mapper) {
        return map(mapper::apply, NumberCheck::new);
    }

    default ListCheck<Integer, List<Integer>, NullableNumberCheck<Integer>> mapTo(IntegerGetter<T> mapper) {
        return map(mapper, NullableNumberCheck::new);
    }

    default ListCheck<Long, List<Long>, NumberCheck<Long>> mapTo(PrimitiveLongGetter<T> mapper) {
        return map(mapper::apply, NumberCheck::new);
    }

    default ListCheck<Long, List<Long>, NullableNumberCheck<Long>> mapTo(LongGetter<T> mapper) {
        return map(mapper, NullableNumberCheck::new);
    }

    default ListCheck<BigInteger, List<BigInteger>, NullableNumberCheck<BigInteger>> mapTo(BigIntegerGetter<T> mapper) {
        return map(mapper, NullableNumberCheck::new);
    }

    default ListCheck<Float, List<Float>, DecimalCheck<Float>> mapTo(PrimitiveFloatGetter<T> mapper) {
        return map(mapper::apply, DecimalCheck::new);
    }

    default ListCheck<Float, List<Float>, NullableDecimalCheck<Float>> mapTo(FloatGetter<T> mapper) {
        return map(mapper, NullableDecimalCheck::new);
    }

    default ListCheck<Double, List<Double>, DecimalCheck<Double>> mapTo(PrimitiveDoubleGetter<T> mapper) {
        return map(mapper::apply, DecimalCheck::new);
    }

    default ListCheck<Double, List<Double>, NullableDecimalCheck<Double>> mapTo(DoubleGetter<T> mapper) {
        return map(mapper, NullableDecimalCheck::new);
    }

    default ListCheck<BigDecimal, List<BigDecimal>, NullableDecimalCheck<BigDecimal>> mapTo(
            BigDecimalGetter<T> mapper) {
        return map(mapper, NullableDecimalCheck::new);
    }

    default ListCheck<String, List<String>, StringCheck> mapTo(StringGetter<T> mapper) {
        return map(mapper, StringCheck::new);
    }

    default ListCheck<Path, List<Path>, PathCheck> mapTo(PathGetter<T> mapper) {
        return map(mapper, PathCheck::new);
    }

    default <X extends Temporal> ListCheck<X, List<X>, TemporalCheck<X>> mapTo(TemporalGetter<T, X> mapper) {
        return map(mapper, TemporalCheck::new);
    }

    default <X extends InputStream> ListCheck<InputStream, List<InputStream>, InputStreamCheck> mapTo(
            InputStreamGetter<T, X> mapper) {
        return map(mapper::apply, InputStreamCheck::new);
    }

    default <X extends Enum<X>> ListCheck<X, List<X>, EnumCheck<X>> mapTo(EnumGetter<T, X> mapper) {
        return map(mapper, EnumCheck::new);
    }

    default <X> ListCheck<Collection<X>, List<Collection<X>>, CollectionCheck<X, Collection<X>, ObjectCheck<X>>> mapTo(
            CollectionGetter<T, X> mapper) {
        return map(mapper, CollectionCheck::create);
    }

    default <X> ListCheck<List<X>, List<List<X>>, ListCheck<X, List<X>, ObjectCheck<X>>> mapTo(
            ListGetter<T, X> mapper) {
        BiFunction<X, List<String>, ObjectCheck<X>> elementCheck = ObjectCheck::new;
        return map(mapper, (v, l) -> new ListCheck<>(v, l, elementCheck));
    }

    default <X> ListCheck<SortedSet<X>, List<SortedSet<X>>, ListCheck<X, SortedSet<X>, ObjectCheck<X>>> mapTo(
            SortedSetGetter<T, X> mapper) {
        return map(mapper, ListCheck::create);
    }

    default <X> ListCheck<Deque<X>, List<Deque<X>>, ListCheck<X, Deque<X>, ObjectCheck<X>>> mapTo(
            DequeGetter<T, X> mapper) {
        return map(mapper, ListCheck::create);
    }

    default <X> ListCheck<Optional<X>, List<Optional<X>>, OptionalCheck<X>> mapTo(OptionalGetter<T, X> mapper) {
        return map(mapper, OptionalCheck::new);
    }

    default <K, V> ListCheck<Map<K, V>, List<Map<K, V>>, MapCheck<K, V>> mapTo(MapGetter<T, K, V> mapper) {
        return map(mapper, MapCheck::new);
    }

    default <X> ListCheck<X[], List<X[]>, ArrayCheck<X, ObjectCheck<X>>> mapTo(ArrayGetter<T, X> mapper) {
        BiFunction<X, List<String>, ObjectCheck<X>> elementCheck = ObjectCheck::new;
        return map(mapper, (v, l) -> new ArrayCheck<>(v, l, elementCheck));
    }
}
