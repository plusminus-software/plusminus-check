package software.plusminus.check;

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
import java.util.function.BiFunction;
import javax.annotation.CheckReturnValue;

@SuppressWarnings({"checkstyle:ClassDataAbstractionCoupling", "checkstyle:ClassFanOutComplexity"})
@CheckReturnValue
public class CheckFactory {

    public BooleanCheck build(boolean actual) {
        return new BooleanCheck(actual);
    }

    public NullableBooleanCheck build(Boolean actual) {
        return new NullableBooleanCheck(actual);
    }

    public CharacterCheck build(char actual) {
        return new CharacterCheck(actual);
    }

    public NullableCharacterCheck build(Character actual) {
        return new NullableCharacterCheck(actual);
    }

    public NumberCheck<Byte> build(byte actual) {
        return new NumberCheck<>(actual);
    }

    public NullableNumberCheck<Byte> build(Byte actual) {
        return new NullableNumberCheck<>(actual);
    }

    public NumberCheck<Short> build(short actual) {
        return new NumberCheck<>(actual);
    }

    public NullableNumberCheck<Short> build(Short actual) {
        return new NullableNumberCheck<>(actual);
    }

    public NumberCheck<Integer> build(int actual) {
        return new NumberCheck<>(actual);
    }

    public NullableNumberCheck<Integer> build(Integer actual) {
        return new NullableNumberCheck<>(actual);
    }

    public NumberCheck<Long> build(long actual) {
        return new NumberCheck<>(actual);
    }

    public NullableNumberCheck<Long> build(Long actual) {
        return new NullableNumberCheck<>(actual);
    }

    public NullableNumberCheck<BigInteger> build(BigInteger actual) {
        return new NullableNumberCheck<>(actual);
    }

    public DecimalCheck<Float> build(float actual) {
        return new DecimalCheck<>(actual);
    }

    public NullableDecimalCheck<Float> build(Float actual) {
        return new NullableDecimalCheck<>(actual);
    }

    public DecimalCheck<Double> build(double actual) {
        return new DecimalCheck<>(actual);
    }

    public NullableDecimalCheck<Double> build(Double actual) {
        return new NullableDecimalCheck<>(actual);
    }

    public NullableDecimalCheck<BigDecimal> build(BigDecimal actual) {
        return new NullableDecimalCheck<>(actual);
    }

    public StringCheck build(String actual) {
        return new StringCheck(actual);
    }

    public <T extends Temporal> TemporalCheck<T> build(T actual) {
        return new TemporalCheck<>(actual);
    }

    public <T> OptionalCheck<T> build(Optional<T> actual) {
        return new OptionalCheck<>(actual);
    }

    public <T> CollectionCheck<T, Collection<T>, ObjectCheck<T>> build(Collection<T> actual) {
        return CollectionCheck.create(actual);
    }

    public <T> OrderedCollectionCheck<T, List<T>, ObjectCheck<T>> build(List<T> actual) {
        return OrderedCollectionCheck.create(actual);
    }

    public <T> OrderedCollectionCheck<T, SortedSet<T>, ObjectCheck<T>> build(SortedSet<T> actual) {
        return OrderedCollectionCheck.create(actual);
    }

    public <T> OrderedCollectionCheck<T, Deque<T>, ObjectCheck<T>> build(Deque<T> actual) {
        return OrderedCollectionCheck.create(actual);
    }

    public <K, V> MapCheck<K, V> build(Map<K, V> actual) {
        return new MapCheck<>(actual);
    }

    public <E extends Enum<E>> EnumCheck<E> build(E actual) {
        return new EnumCheck<>(actual);
    }

    public <T> ObjectCheck<T> build(T actual) {
        return new ObjectCheck<>(actual);
    }

    // --- Collection suppliers ---

    public CollectionCheck<Boolean, Collection<Boolean>, NullableBooleanCheck> build(
            BooleanCollectionSupplier supplier) {
        return build(supplier.get(), NullableBooleanCheck::new);
    }

    public OrderedCollectionCheck<Boolean, List<Boolean>, NullableBooleanCheck> build(BooleanListSupplier supplier) {
        return build(supplier.get(), NullableBooleanCheck::new);
    }

    public CollectionCheck<Character, Collection<Character>, NullableCharacterCheck> build(
            CharacterCollectionSupplier supplier) {
        return build(supplier.get(), NullableCharacterCheck::new);
    }

    public OrderedCollectionCheck<Character, List<Character>, NullableCharacterCheck> build(
            CharacterListSupplier supplier) {
        return build(supplier.get(), NullableCharacterCheck::new);
    }

    public CollectionCheck<Byte, Collection<Byte>, NullableNumberCheck<Byte>> build(ByteCollectionSupplier supplier) {
        return build(supplier.get(), NullableNumberCheck::new);
    }

    public OrderedCollectionCheck<Byte, List<Byte>, NullableNumberCheck<Byte>> build(ByteListSupplier supplier) {
        return build(supplier.get(), NullableNumberCheck::new);
    }

    public CollectionCheck<Short, Collection<Short>, NullableNumberCheck<Short>> build(
            ShortCollectionSupplier supplier) {
        return build(supplier.get(), NullableNumberCheck::new);
    }

    public OrderedCollectionCheck<Short, List<Short>, NullableNumberCheck<Short>> build(ShortListSupplier supplier) {
        return build(supplier.get(), NullableNumberCheck::new);
    }

    public CollectionCheck<Integer, Collection<Integer>, NullableNumberCheck<Integer>> build(
            IntegerCollectionSupplier supplier) {
        return build(supplier.get(), NullableNumberCheck::new);
    }

    public OrderedCollectionCheck<Integer, List<Integer>, NullableNumberCheck<Integer>> build(
            IntegerListSupplier supplier) {
        return build(supplier.get(), NullableNumberCheck::new);
    }

    public CollectionCheck<Long, Collection<Long>, NullableNumberCheck<Long>> build(LongCollectionSupplier supplier) {
        return build(supplier.get(), NullableNumberCheck::new);
    }

    public OrderedCollectionCheck<Long, List<Long>, NullableNumberCheck<Long>> build(LongListSupplier supplier) {
        return build(supplier.get(), NullableNumberCheck::new);
    }

    public CollectionCheck<BigInteger, Collection<BigInteger>, NullableNumberCheck<BigInteger>> build(
            BigIntegerCollectionSupplier supplier) {
        return build(supplier.get(), NullableNumberCheck::new);
    }

    public OrderedCollectionCheck<BigInteger, List<BigInteger>, NullableNumberCheck<BigInteger>> build(
            BigIntegerListSupplier supplier) {
        return build(supplier.get(), NullableNumberCheck::new);
    }

    public CollectionCheck<Float, Collection<Float>, NullableDecimalCheck<Float>> build(
            FloatCollectionSupplier supplier) {
        return build(supplier.get(), NullableDecimalCheck::new);
    }

    public OrderedCollectionCheck<Float, List<Float>, NullableDecimalCheck<Float>> build(FloatListSupplier supplier) {
        return build(supplier.get(), NullableDecimalCheck::new);
    }

    public CollectionCheck<Double, Collection<Double>, NullableDecimalCheck<Double>> build(
            DoubleCollectionSupplier supplier) {
        return build(supplier.get(), NullableDecimalCheck::new);
    }

    public OrderedCollectionCheck<Double, List<Double>, NullableDecimalCheck<Double>> build(
            DoubleListSupplier supplier) {
        return build(supplier.get(), NullableDecimalCheck::new);
    }

    public CollectionCheck<BigDecimal, Collection<BigDecimal>, NullableDecimalCheck<BigDecimal>> build(
            BigDecimalCollectionSupplier supplier) {
        return build(supplier.get(), NullableDecimalCheck::new);
    }

    public OrderedCollectionCheck<BigDecimal, List<BigDecimal>, NullableDecimalCheck<BigDecimal>> build(
            BigDecimalListSupplier supplier) {
        return build(supplier.get(), NullableDecimalCheck::new);
    }

    public CollectionCheck<String, Collection<String>, StringCheck> build(StringCollectionSupplier supplier) {
        return build(supplier.get(), StringCheck::new);
    }

    public OrderedCollectionCheck<String, List<String>, StringCheck> build(StringListSupplier supplier) {
        return build(supplier.get(), StringCheck::new);
    }

    public <E extends Enum<E>> CollectionCheck<E, Collection<E>, EnumCheck<E>> build(
            EnumCollectionSupplier<E> supplier) {
        return build(supplier.get(), EnumCheck::new);
    }

    public <E extends Enum<E>> OrderedCollectionCheck<E, List<E>, EnumCheck<E>> build(EnumListSupplier<E> supplier) {
        return build(supplier.get(), EnumCheck::new);
    }

    public <T extends Temporal> CollectionCheck<T, Collection<T>, TemporalCheck<T>> build(
            TemporalCollectionSupplier<T> supplier) {
        return build(supplier.get(), TemporalCheck::new);
    }

    public <T extends Temporal> OrderedCollectionCheck<T, List<T>, TemporalCheck<T>> build(
            TemporalListSupplier<T> supplier) {
        return build(supplier.get(), TemporalCheck::new);
    }

    public <E> CollectionCheck<Collection<E>, Collection<Collection<E>>,
            CollectionCheck<E, Collection<E>, ObjectCheck<E>>> build(CollectionCollectionSupplier<E> supplier) {
        return build(supplier.get(), CollectionCheck::create);
    }

    public <E> OrderedCollectionCheck<Collection<E>, List<Collection<E>>,
            CollectionCheck<E, Collection<E>, ObjectCheck<E>>> build(CollectionListSupplier<E> supplier) {
        return build(supplier.get(), CollectionCheck::create);
    }

    public <E> CollectionCheck<List<E>, Collection<List<E>>, CollectionCheck<E, List<E>, ObjectCheck<E>>> build(
            ListCollectionSupplier<E> supplier) {
        return build(supplier.get(), CollectionCheck::create);
    }

    public <E> OrderedCollectionCheck<List<E>, List<List<E>>, CollectionCheck<E, List<E>, ObjectCheck<E>>> build(
            ListListSupplier<E> supplier) {
        return build(supplier.get(), CollectionCheck::create);
    }

    public <K, V> CollectionCheck<Map<K, V>, Collection<Map<K, V>>, MapCheck<K, V>> build(
            MapCollectionSupplier<K, V> supplier) {
        return build(supplier.get(), MapCheck::new);
    }

    public <K, V> OrderedCollectionCheck<Map<K, V>, List<Map<K, V>>, MapCheck<K, V>> build(
            MapListSupplier<K, V> supplier) {
        return build(supplier.get(), MapCheck::new);
    }

    public <E> CollectionCheck<Optional<E>, Collection<Optional<E>>, OptionalCheck<E>> build(
            OptionalCollectionSupplier<E> supplier) {
        return build(supplier.get(), OptionalCheck::new);
    }

    public <E> OrderedCollectionCheck<Optional<E>, List<Optional<E>>, OptionalCheck<E>> build(
            OptionalListSupplier<E> supplier) {
        return build(supplier.get(), OptionalCheck::new);
    }

    private <T, C extends Collection<T>, E extends AbstractCheck<T>> CollectionCheck<T, C, E> build(
            C actual, BiFunction<T, List<String>, E> elementCheck) {
        return new CollectionCheck<>(actual, elementCheck);
    }

    private <T, C extends List<T>, E extends AbstractCheck<T>> OrderedCollectionCheck<T, C, E> build(
            C actual, BiFunction<T, List<String>, E> elementCheck) {
        return new OrderedCollectionCheck<>(actual, elementCheck);
    }

}
