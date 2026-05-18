package software.plusminus.check;

import software.plusminus.check.factory.CheckFactory;
import software.plusminus.check.supplier.BigDecimalCollectionSupplier;
import software.plusminus.check.supplier.BigDecimalListSupplier;
import software.plusminus.check.supplier.BigIntegerCollectionSupplier;
import software.plusminus.check.supplier.BigIntegerListSupplier;
import software.plusminus.check.supplier.BooleanCollectionSupplier;
import software.plusminus.check.supplier.BooleanListSupplier;
import software.plusminus.check.supplier.ByteCollectionSupplier;
import software.plusminus.check.supplier.ByteListSupplier;
import software.plusminus.check.supplier.CharacterCollectionSupplier;
import software.plusminus.check.supplier.CharacterListSupplier;
import software.plusminus.check.supplier.CollectionCollectionSupplier;
import software.plusminus.check.supplier.CollectionListSupplier;
import software.plusminus.check.supplier.DoubleCollectionSupplier;
import software.plusminus.check.supplier.DoubleListSupplier;
import software.plusminus.check.supplier.EnumCollectionSupplier;
import software.plusminus.check.supplier.EnumListSupplier;
import software.plusminus.check.supplier.FloatCollectionSupplier;
import software.plusminus.check.supplier.FloatListSupplier;
import software.plusminus.check.supplier.IntegerCollectionSupplier;
import software.plusminus.check.supplier.IntegerListSupplier;
import software.plusminus.check.supplier.ListCollectionSupplier;
import software.plusminus.check.supplier.ListListSupplier;
import software.plusminus.check.supplier.LongCollectionSupplier;
import software.plusminus.check.supplier.LongListSupplier;
import software.plusminus.check.supplier.MapCollectionSupplier;
import software.plusminus.check.supplier.MapListSupplier;
import software.plusminus.check.supplier.OptionalCollectionSupplier;
import software.plusminus.check.supplier.OptionalListSupplier;
import software.plusminus.check.supplier.ShortCollectionSupplier;
import software.plusminus.check.supplier.ShortListSupplier;
import software.plusminus.check.supplier.StringCollectionSupplier;
import software.plusminus.check.supplier.StringListSupplier;
import software.plusminus.check.supplier.TemporalCollectionSupplier;
import software.plusminus.check.supplier.TemporalListSupplier;
import software.plusminus.check.types.ArrayCheck;
import software.plusminus.check.types.BooleanCheck;
import software.plusminus.check.types.CharacterCheck;
import software.plusminus.check.types.CollectionCheck;
import software.plusminus.check.types.DecimalCheck;
import software.plusminus.check.types.EnumCheck;
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
import software.plusminus.check.types.TemporalCheck;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.temporal.Temporal;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.CheckReturnValue;

/**
 * Main entry point for all the checkers.
 * Recommend to static import this class in a test classes.
 *
 * @author Taras Shpek
 */
@SuppressWarnings("checkstyle:ClassFanOutComplexity")
@CheckReturnValue
public interface Checks {

    AtomicReference<CheckFactory> FACTORY = new AtomicReference<>(new CheckFactory());

    static BooleanCheck check(boolean actual) {
        return FACTORY.get().build(actual);
    }

    static NullableBooleanCheck check(Boolean actual) {
        return FACTORY.get().build(actual);
    }

    static CharacterCheck check(char actual) {
        return FACTORY.get().build(actual);
    }

    static NullableCharacterCheck check(Character actual) {
        return FACTORY.get().build(actual);
    }

    static NumberCheck<Byte> check(byte actual) {
        return FACTORY.get().build(actual);
    }

    static NullableNumberCheck<Byte> check(Byte actual) {
        return FACTORY.get().build(actual);
    }

    static NumberCheck<Short> check(short actual) {
        return FACTORY.get().build(actual);
    }

    static NullableNumberCheck<Short> check(Short actual) {
        return FACTORY.get().build(actual);
    }

    static NumberCheck<Integer> check(int actual) {
        return FACTORY.get().build(actual);
    }

    static NullableNumberCheck<Integer> check(Integer actual) {
        return FACTORY.get().build(actual);
    }

    static NumberCheck<Long> check(long actual) {
        return FACTORY.get().build(actual);
    }

    static NullableNumberCheck<Long> check(Long actual) {
        return FACTORY.get().build(actual);
    }

    static NullableNumberCheck<BigInteger> check(BigInteger actual) {
        return FACTORY.get().build(actual);
    }

    static DecimalCheck<Float> check(float actual) {
        return FACTORY.get().build(actual);
    }

    static NullableDecimalCheck<Float> check(Float actual) {
        return FACTORY.get().build(actual);
    }

    static DecimalCheck<Double> check(double actual) {
        return FACTORY.get().build(actual);
    }

    static NullableDecimalCheck<Double> check(Double actual) {
        return FACTORY.get().build(actual);
    }

    static NullableDecimalCheck<BigDecimal> check(BigDecimal actual) {
        return FACTORY.get().build(actual);
    }

    static StringCheck check(String actual) {
        return FACTORY.get().build(actual);
    }

    static <T extends Temporal> TemporalCheck<T> check(T actual) {
        return FACTORY.get().build(actual);
    }

    static <T> OptionalCheck<T> check(Optional<T> actual) {
        return FACTORY.get().build(actual);
    }

    static <T> CollectionCheck<T, Collection<T>, ObjectCheck<T>> check(Collection<T> actual) {
        return FACTORY.get().build(actual);
    }

    static <T> ListCheck<T, List<T>, ObjectCheck<T>> check(List<T> actual) {
        return FACTORY.get().build(actual);
    }

    static <T> ListCheck<T, SortedSet<T>, ObjectCheck<T>> check(SortedSet<T> actual) {
        return FACTORY.get().build(actual);
    }

    static <T> ListCheck<T, Deque<T>, ObjectCheck<T>> check(Deque<T> actual) {
        return FACTORY.get().build(actual);
    }

    static <K, V> MapCheck<K, V> check(Map<K, V> actual) {
        return FACTORY.get().build(actual);
    }

    static <E extends Enum<E>> EnumCheck<E> check(E actual) {
        return FACTORY.get().build(actual);
    }

    static <T> ObjectCheck<T> check(T actual) {
        return FACTORY.get().build(actual);
    }

    /* Arrays */

    static ArrayCheck<Boolean, BooleanCheck> check(boolean[] actual) {
        return FACTORY.get().build(actual);
    }

    static ArrayCheck<Boolean, NullableBooleanCheck> check(Boolean[] actual) {
        return FACTORY.get().build(actual);
    }

    static ArrayCheck<Character, CharacterCheck> check(char[] actual) {
        return FACTORY.get().build(actual);
    }

    static ArrayCheck<Character, NullableCharacterCheck> check(Character[] actual) {
        return FACTORY.get().build(actual);
    }

    static ArrayCheck<Byte, NumberCheck<Byte>> check(byte[] actual) {
        return FACTORY.get().build(actual);
    }

    static ArrayCheck<Byte, NullableNumberCheck<Byte>> check(Byte[] actual) {
        return FACTORY.get().build(actual);
    }

    static ArrayCheck<Short, NumberCheck<Short>> check(short[] actual) {
        return FACTORY.get().build(actual);
    }

    static ArrayCheck<Short, NullableNumberCheck<Short>> check(Short[] actual) {
        return FACTORY.get().build(actual);
    }

    static ArrayCheck<Integer, NumberCheck<Integer>> check(int[] actual) {
        return FACTORY.get().build(actual);
    }

    static ArrayCheck<Integer, NullableNumberCheck<Integer>> check(Integer[] actual) {
        return FACTORY.get().build(actual);
    }

    static ArrayCheck<Long, NumberCheck<Long>> check(long[] actual) {
        return FACTORY.get().build(actual);
    }

    static ArrayCheck<Long, NullableNumberCheck<Long>> check(Long[] actual) {
        return FACTORY.get().build(actual);
    }

    static ArrayCheck<BigInteger, NullableNumberCheck<BigInteger>> check(BigInteger[] actual) {
        return FACTORY.get().build(actual);
    }

    static ArrayCheck<Float, DecimalCheck<Float>> check(float[] actual) {
        return FACTORY.get().build(actual);
    }

    static ArrayCheck<Float, NullableDecimalCheck<Float>> check(Float[] actual) {
        return FACTORY.get().build(actual);
    }

    static ArrayCheck<Double, DecimalCheck<Double>> check(double[] actual) {
        return FACTORY.get().build(actual);
    }

    static ArrayCheck<Double, NullableDecimalCheck<Double>> check(Double[] actual) {
        return FACTORY.get().build(actual);
    }

    static ArrayCheck<BigDecimal, NullableDecimalCheck<BigDecimal>> check(BigDecimal[] actual) {
        return FACTORY.get().build(actual);
    }

    static ArrayCheck<String, StringCheck> check(String[] actual) {
        return FACTORY.get().build(actual);
    }

    static <T extends Temporal> ArrayCheck<T, TemporalCheck<T>> check(T[] actual) {
        return FACTORY.get().build(actual);
    }

    static <E extends Enum<E>> ArrayCheck<E, EnumCheck<E>> check(E[] actual) {
        return FACTORY.get().build(actual);
    }

    static <T> ArrayCheck<Optional<T>, OptionalCheck<T>> check(Optional<T>[] actual) {
        return FACTORY.get().build(actual);
    }

    static <K, V> ArrayCheck<Map<K, V>, MapCheck<K, V>> check(Map<K, V>[] actual) {
        return FACTORY.get().build(actual);
    }

    static <T> ArrayCheck<T, ObjectCheck<T>> check(T[] actual) {
        return FACTORY.get().build(actual);
    }

    /* Collections */

    static CollectionCheck<Boolean, Collection<Boolean>, NullableBooleanCheck> checkOf(
            BooleanCollectionSupplier supplier) {
        return FACTORY.get().build(supplier);
    }

    static ListCheck<Boolean, List<Boolean>, NullableBooleanCheck> checkOf(BooleanListSupplier supplier) {
        return FACTORY.get().build(supplier);
    }

    static CollectionCheck<Character, Collection<Character>, NullableCharacterCheck> checkOf(
            CharacterCollectionSupplier supplier) {
        return FACTORY.get().build(supplier);
    }

    static ListCheck<Character, List<Character>, NullableCharacterCheck> checkOf(
            CharacterListSupplier supplier) {
        return FACTORY.get().build(supplier);
    }

    static CollectionCheck<Byte, Collection<Byte>, NullableNumberCheck<Byte>> checkOf(ByteCollectionSupplier supplier) {
        return FACTORY.get().build(supplier);
    }

    static ListCheck<Byte, List<Byte>, NullableNumberCheck<Byte>> checkOf(ByteListSupplier supplier) {
        return FACTORY.get().build(supplier);
    }

    static CollectionCheck<Short, Collection<Short>, NullableNumberCheck<Short>> checkOf(
            ShortCollectionSupplier supplier) {
        return FACTORY.get().build(supplier);
    }

    static ListCheck<Short, List<Short>, NullableNumberCheck<Short>> checkOf(ShortListSupplier supplier) {
        return FACTORY.get().build(supplier);
    }

    static CollectionCheck<Integer, Collection<Integer>, NullableNumberCheck<Integer>> checkOf(
            IntegerCollectionSupplier supplier) {
        return FACTORY.get().build(supplier);
    }

    static ListCheck<Integer, List<Integer>, NullableNumberCheck<Integer>> checkOf(
            IntegerListSupplier supplier) {
        return FACTORY.get().build(supplier);
    }

    static CollectionCheck<Long, Collection<Long>, NullableNumberCheck<Long>> checkOf(LongCollectionSupplier supplier) {
        return FACTORY.get().build(supplier);
    }

    static ListCheck<Long, List<Long>, NullableNumberCheck<Long>> checkOf(LongListSupplier supplier) {
        return FACTORY.get().build(supplier);
    }

    static CollectionCheck<BigInteger, Collection<BigInteger>, NullableNumberCheck<BigInteger>> checkOf(
            BigIntegerCollectionSupplier supplier) {
        return FACTORY.get().build(supplier);
    }

    static ListCheck<BigInteger, List<BigInteger>, NullableNumberCheck<BigInteger>> checkOf(
            BigIntegerListSupplier supplier) {
        return FACTORY.get().build(supplier);
    }

    static CollectionCheck<Float, Collection<Float>, NullableDecimalCheck<Float>> checkOf(
            FloatCollectionSupplier supplier) {
        return FACTORY.get().build(supplier);
    }

    static ListCheck<Float, List<Float>, NullableDecimalCheck<Float>> checkOf(FloatListSupplier supplier) {
        return FACTORY.get().build(supplier);
    }

    static CollectionCheck<Double, Collection<Double>, NullableDecimalCheck<Double>> checkOf(
            DoubleCollectionSupplier supplier) {
        return FACTORY.get().build(supplier);
    }

    static ListCheck<Double, List<Double>, NullableDecimalCheck<Double>> checkOf(
            DoubleListSupplier supplier) {
        return FACTORY.get().build(supplier);
    }

    static CollectionCheck<BigDecimal, Collection<BigDecimal>, NullableDecimalCheck<BigDecimal>> checkOf(
            BigDecimalCollectionSupplier supplier) {
        return FACTORY.get().build(supplier);
    }

    static ListCheck<BigDecimal, List<BigDecimal>, NullableDecimalCheck<BigDecimal>> checkOf(
            BigDecimalListSupplier supplier) {
        return FACTORY.get().build(supplier);
    }

    static CollectionCheck<String, Collection<String>, StringCheck> checkOf(StringCollectionSupplier supplier) {
        return FACTORY.get().build(supplier);
    }

    static ListCheck<String, List<String>, StringCheck> checkOf(StringListSupplier supplier) {
        return FACTORY.get().build(supplier);
    }

    static <E extends Enum<E>> CollectionCheck<E, Collection<E>, EnumCheck<E>> checkOf(
            EnumCollectionSupplier<E> supplier) {
        return FACTORY.get().build(supplier);
    }

    static <E extends Enum<E>> ListCheck<E, List<E>, EnumCheck<E>> checkOf(
            EnumListSupplier<E> supplier) {
        return FACTORY.get().build(supplier);
    }

    static <T extends Temporal> CollectionCheck<T, Collection<T>, TemporalCheck<T>> checkOf(
            TemporalCollectionSupplier<T> supplier) {
        return FACTORY.get().build(supplier);
    }

    static <T extends Temporal> ListCheck<T, List<T>, TemporalCheck<T>> checkOf(
            TemporalListSupplier<T> supplier) {
        return FACTORY.get().build(supplier);
    }

    static <E> CollectionCheck<Collection<E>, Collection<Collection<E>>,
            CollectionCheck<E, Collection<E>, ObjectCheck<E>>> checkOf(CollectionCollectionSupplier<E> supplier) {
        return FACTORY.get().build(supplier);
    }

    static <E> ListCheck<Collection<E>, List<Collection<E>>,
            CollectionCheck<E, Collection<E>, ObjectCheck<E>>> checkOf(CollectionListSupplier<E> supplier) {
        return FACTORY.get().build(supplier);
    }

    static <E> CollectionCheck<List<E>, Collection<List<E>>, CollectionCheck<E, List<E>, ObjectCheck<E>>> checkOf(
            ListCollectionSupplier<E> supplier) {
        return FACTORY.get().build(supplier);
    }

    static <E> ListCheck<List<E>, List<List<E>>, CollectionCheck<E, List<E>, ObjectCheck<E>>> checkOf(
            ListListSupplier<E> supplier) {
        return FACTORY.get().build(supplier);
    }

    static <K, V> CollectionCheck<Map<K, V>, Collection<Map<K, V>>, MapCheck<K, V>> checkOf(
            MapCollectionSupplier<K, V> supplier) {
        return FACTORY.get().build(supplier);
    }

    static <K, V> ListCheck<Map<K, V>, List<Map<K, V>>, MapCheck<K, V>> checkOf(
            MapListSupplier<K, V> supplier) {
        return FACTORY.get().build(supplier);
    }

    static <E> CollectionCheck<Optional<E>, Collection<Optional<E>>, OptionalCheck<E>> checkOf(
            OptionalCollectionSupplier<E> supplier) {
        return FACTORY.get().build(supplier);
    }

    static <E> ListCheck<Optional<E>, List<Optional<E>>, OptionalCheck<E>> checkOf(
            OptionalListSupplier<E> supplier) {
        return FACTORY.get().build(supplier);
    }
}
