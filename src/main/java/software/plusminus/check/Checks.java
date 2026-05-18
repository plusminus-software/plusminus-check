package software.plusminus.check;

import lombok.experimental.UtilityClass;
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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.temporal.Temporal;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;
import javax.annotation.CheckReturnValue;

/**
 * Main entry point for all the checkers.
 * Recommend to static import this class in a test classes.
 *
 * @author Taras Shpek
 */
@SuppressWarnings({"checkstyle:ClassDataAbstractionCoupling", "checkstyle:ClassFanOutComplexity"})
@CheckReturnValue
@UtilityClass
public class Checks {

    private CheckFactory factory = new CheckFactory();

    public void factory(CheckFactory checkFactory) {
        Checks.factory = checkFactory;
    }

    public BooleanCheck check(boolean actual) {
        return factory.build(actual);
    }

    public NullableBooleanCheck check(Boolean actual) {
        return factory.build(actual);
    }

    public CharacterCheck check(char actual) {
        return factory.build(actual);
    }

    public NullableCharacterCheck check(Character actual) {
        return factory.build(actual);
    }

    public NumberCheck<Byte> check(byte actual) {
        return factory.build(actual);
    }

    public NullableNumberCheck<Byte> check(Byte actual) {
        return factory.build(actual);
    }

    public NumberCheck<Short> check(short actual) {
        return factory.build(actual);
    }

    public NullableNumberCheck<Short> check(Short actual) {
        return factory.build(actual);
    }

    public NumberCheck<Integer> check(int actual) {
        return factory.build(actual);
    }

    public NullableNumberCheck<Integer> check(Integer actual) {
        return factory.build(actual);
    }

    public NumberCheck<Long> check(long actual) {
        return factory.build(actual);
    }

    public NullableNumberCheck<Long> check(Long actual) {
        return factory.build(actual);
    }

    public NullableNumberCheck<BigInteger> check(BigInteger actual) {
        return factory.build(actual);
    }

    public DecimalCheck<Float> check(float actual) {
        return factory.build(actual);
    }

    public NullableDecimalCheck<Float> check(Float actual) {
        return factory.build(actual);
    }

    public DecimalCheck<Double> check(double actual) {
        return factory.build(actual);
    }

    public NullableDecimalCheck<Double> check(Double actual) {
        return factory.build(actual);
    }

    public NullableDecimalCheck<BigDecimal> check(BigDecimal actual) {
        return factory.build(actual);
    }

    public StringCheck check(String actual) {
        return factory.build(actual);
    }

    public <T extends Temporal> TemporalCheck<T> check(T actual) {
        return factory.build(actual);
    }

    public <T> OptionalCheck<T> check(Optional<T> actual) {
        return factory.build(actual);
    }

    public <T> CollectionCheck<T, Collection<T>, ObjectCheck<T>> check(Collection<T> actual) {
        return factory.build(actual);
    }

    public <T> OrderedCollectionCheck<T, List<T>, ObjectCheck<T>> check(List<T> actual) {
        return factory.build(actual);
    }

    public <T> OrderedCollectionCheck<T, SortedSet<T>, ObjectCheck<T>> check(SortedSet<T> actual) {
        return factory.build(actual);
    }

    public <T> OrderedCollectionCheck<T, Deque<T>, ObjectCheck<T>> check(Deque<T> actual) {
        return factory.build(actual);
    }

    public <K, V> MapCheck<K, V> check(Map<K, V> actual) {
        return factory.build(actual);
    }

    public <E extends Enum<E>> EnumCheck<E> check(E actual) {
        return factory.build(actual);
    }

    public <T> ObjectCheck<T> check(T actual) {
        return factory.build(actual);
    }

    // --- Collection suppliers ---

    public CollectionCheck<Boolean, Collection<Boolean>, NullableBooleanCheck> checkOf(
            BooleanCollectionSupplier supplier) {
        return factory.build(supplier);
    }

    public OrderedCollectionCheck<Boolean, List<Boolean>, NullableBooleanCheck> checkOf(BooleanListSupplier supplier) {
        return factory.build(supplier);
    }

    public CollectionCheck<Character, Collection<Character>, NullableCharacterCheck> checkOf(
            CharacterCollectionSupplier supplier) {
        return factory.build(supplier);
    }

    public OrderedCollectionCheck<Character, List<Character>, NullableCharacterCheck> checkOf(
            CharacterListSupplier supplier) {
        return factory.build(supplier);
    }

    public CollectionCheck<Byte, Collection<Byte>, NullableNumberCheck<Byte>> checkOf(ByteCollectionSupplier supplier) {
        return factory.build(supplier);
    }

    public OrderedCollectionCheck<Byte, List<Byte>, NullableNumberCheck<Byte>> checkOf(ByteListSupplier supplier) {
        return factory.build(supplier);
    }

    public CollectionCheck<Short, Collection<Short>, NullableNumberCheck<Short>> checkOf(
            ShortCollectionSupplier supplier) {
        return factory.build(supplier);
    }

    public OrderedCollectionCheck<Short, List<Short>, NullableNumberCheck<Short>> checkOf(ShortListSupplier supplier) {
        return factory.build(supplier);
    }

    public CollectionCheck<Integer, Collection<Integer>, NullableNumberCheck<Integer>> checkOf(
            IntegerCollectionSupplier supplier) {
        return factory.build(supplier);
    }

    public OrderedCollectionCheck<Integer, List<Integer>, NullableNumberCheck<Integer>> checkOf(
            IntegerListSupplier supplier) {
        return factory.build(supplier);
    }

    public CollectionCheck<Long, Collection<Long>, NullableNumberCheck<Long>> checkOf(LongCollectionSupplier supplier) {
        return factory.build(supplier);
    }

    public OrderedCollectionCheck<Long, List<Long>, NullableNumberCheck<Long>> checkOf(LongListSupplier supplier) {
        return factory.build(supplier);
    }

    public CollectionCheck<BigInteger, Collection<BigInteger>, NullableNumberCheck<BigInteger>> checkOf(
            BigIntegerCollectionSupplier supplier) {
        return factory.build(supplier);
    }

    public OrderedCollectionCheck<BigInteger, List<BigInteger>, NullableNumberCheck<BigInteger>> checkOf(
            BigIntegerListSupplier supplier) {
        return factory.build(supplier);
    }

    public CollectionCheck<Float, Collection<Float>, NullableDecimalCheck<Float>> checkOf(
            FloatCollectionSupplier supplier) {
        return factory.build(supplier);
    }

    public OrderedCollectionCheck<Float, List<Float>, NullableDecimalCheck<Float>> checkOf(FloatListSupplier supplier) {
        return factory.build(supplier);
    }

    public CollectionCheck<Double, Collection<Double>, NullableDecimalCheck<Double>> checkOf(
            DoubleCollectionSupplier supplier) {
        return factory.build(supplier);
    }

    public OrderedCollectionCheck<Double, List<Double>, NullableDecimalCheck<Double>> checkOf(
            DoubleListSupplier supplier) {
        return factory.build(supplier);
    }

    public CollectionCheck<BigDecimal, Collection<BigDecimal>, NullableDecimalCheck<BigDecimal>> checkOf(
            BigDecimalCollectionSupplier supplier) {
        return factory.build(supplier);
    }

    public OrderedCollectionCheck<BigDecimal, List<BigDecimal>, NullableDecimalCheck<BigDecimal>> checkOf(
            BigDecimalListSupplier supplier) {
        return factory.build(supplier);
    }

    public CollectionCheck<String, Collection<String>, StringCheck> checkOf(StringCollectionSupplier supplier) {
        return factory.build(supplier);
    }

    public OrderedCollectionCheck<String, List<String>, StringCheck> checkOf(StringListSupplier supplier) {
        return factory.build(supplier);
    }

    public <E extends Enum<E>> CollectionCheck<E, Collection<E>, EnumCheck<E>> checkOf(
            EnumCollectionSupplier<E> supplier) {
        return factory.build(supplier);
    }

    public <E extends Enum<E>> OrderedCollectionCheck<E, List<E>, EnumCheck<E>> checkOf(
            EnumListSupplier<E> supplier) {
        return factory.build(supplier);
    }

    public <T extends Temporal> CollectionCheck<T, Collection<T>, TemporalCheck<T>> checkOf(
            TemporalCollectionSupplier<T> supplier) {
        return factory.build(supplier);
    }

    public <T extends Temporal> OrderedCollectionCheck<T, List<T>, TemporalCheck<T>> checkOf(
            TemporalListSupplier<T> supplier) {
        return factory.build(supplier);
    }

    public <E> CollectionCheck<Collection<E>, Collection<Collection<E>>,
            CollectionCheck<E, Collection<E>, ObjectCheck<E>>> checkOf(CollectionCollectionSupplier<E> supplier) {
        return factory.build(supplier);
    }

    public <E> OrderedCollectionCheck<Collection<E>, List<Collection<E>>,
            CollectionCheck<E, Collection<E>, ObjectCheck<E>>> checkOf(CollectionListSupplier<E> supplier) {
        return factory.build(supplier);
    }

    public <E> CollectionCheck<List<E>, Collection<List<E>>, CollectionCheck<E, List<E>, ObjectCheck<E>>> checkOf(
            ListCollectionSupplier<E> supplier) {
        return factory.build(supplier);
    }

    public <E> OrderedCollectionCheck<List<E>, List<List<E>>, CollectionCheck<E, List<E>, ObjectCheck<E>>> checkOf(
            ListListSupplier<E> supplier) {
        return factory.build(supplier);
    }

    public <K, V> CollectionCheck<Map<K, V>, Collection<Map<K, V>>, MapCheck<K, V>> checkOf(
            MapCollectionSupplier<K, V> supplier) {
        return factory.build(supplier);
    }

    public <K, V> OrderedCollectionCheck<Map<K, V>, List<Map<K, V>>, MapCheck<K, V>> checkOf(
            MapListSupplier<K, V> supplier) {
        return factory.build(supplier);
    }

    public <E> CollectionCheck<Optional<E>, Collection<Optional<E>>, OptionalCheck<E>> checkOf(
            OptionalCollectionSupplier<E> supplier) {
        return factory.build(supplier);
    }

    public <E> OrderedCollectionCheck<Optional<E>, List<Optional<E>>, OptionalCheck<E>> checkOf(
            OptionalListSupplier<E> supplier) {
        return factory.build(supplier);
    }
}
