package software.plusminus.check.factory;

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
import software.plusminus.check.types.AbstractCheck;
import software.plusminus.check.types.ArrayCheck;
import software.plusminus.check.types.BooleanCheck;
import software.plusminus.check.types.CharacterCheck;
import software.plusminus.check.types.CollectionCheck;
import software.plusminus.check.types.DecimalCheck;
import software.plusminus.check.types.EnumCheck;
import software.plusminus.check.types.InputStreamCheck;
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

import java.io.InputStream;
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

@SuppressWarnings({
    "checkstyle:ClassDataAbstractionCoupling",
    "checkstyle:ClassFanOutComplexity",
    "PMD.CouplingBetweenObjects",
    "PMD.CyclomaticComplexity",
    "PMD.ExcessiveClassLength",
    "PMD.ExcessivePublicCount",
    "java:S1168"})
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

    public InputStreamCheck build(InputStream actual) {
        return new InputStreamCheck(actual);
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

    public <T> ListCheck<T, List<T>, ObjectCheck<T>> build(List<T> actual) {
        return ListCheck.create(actual);
    }

    public <T> ListCheck<T, SortedSet<T>, ObjectCheck<T>> build(SortedSet<T> actual) {
        return ListCheck.create(actual);
    }

    public <T> ListCheck<T, Deque<T>, ObjectCheck<T>> build(Deque<T> actual) {
        return ListCheck.create(actual);
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

    // --- Arrays ---

    public ArrayCheck<Boolean, BooleanCheck> build(boolean[] actual) {
        return new ArrayCheck<>(box(actual), BooleanCheck::new);
    }

    public ArrayCheck<Boolean, NullableBooleanCheck> build(Boolean[] actual) {
        return new ArrayCheck<>(actual, NullableBooleanCheck::new);
    }

    public ArrayCheck<Character, CharacterCheck> build(char[] actual) {
        return new ArrayCheck<>(box(actual), CharacterCheck::new);
    }

    public ArrayCheck<Character, NullableCharacterCheck> build(Character[] actual) {
        return new ArrayCheck<>(actual, NullableCharacterCheck::new);
    }

    public ArrayCheck<Byte, NumberCheck<Byte>> build(byte[] actual) {
        return new ArrayCheck<>(box(actual), NumberCheck::new);
    }

    public ArrayCheck<Byte, NullableNumberCheck<Byte>> build(Byte[] actual) {
        return new ArrayCheck<>(actual, NullableNumberCheck::new);
    }

    public ArrayCheck<Short, NumberCheck<Short>> build(short[] actual) {
        return new ArrayCheck<>(box(actual), NumberCheck::new);
    }

    public ArrayCheck<Short, NullableNumberCheck<Short>> build(Short[] actual) {
        return new ArrayCheck<>(actual, NullableNumberCheck::new);
    }

    public ArrayCheck<Integer, NumberCheck<Integer>> build(int[] actual) {
        return new ArrayCheck<>(box(actual), NumberCheck::new);
    }

    public ArrayCheck<Integer, NullableNumberCheck<Integer>> build(Integer[] actual) {
        return new ArrayCheck<>(actual, NullableNumberCheck::new);
    }

    public ArrayCheck<Long, NumberCheck<Long>> build(long[] actual) {
        return new ArrayCheck<>(box(actual), NumberCheck::new);
    }

    public ArrayCheck<Long, NullableNumberCheck<Long>> build(Long[] actual) {
        return new ArrayCheck<>(actual, NullableNumberCheck::new);
    }

    public ArrayCheck<BigInteger, NullableNumberCheck<BigInteger>> build(BigInteger[] actual) {
        return new ArrayCheck<>(actual, NullableNumberCheck::new);
    }

    public ArrayCheck<Float, DecimalCheck<Float>> build(float[] actual) {
        return new ArrayCheck<>(box(actual), DecimalCheck::new);
    }

    public ArrayCheck<Float, NullableDecimalCheck<Float>> build(Float[] actual) {
        return new ArrayCheck<>(actual, NullableDecimalCheck::new);
    }

    public ArrayCheck<Double, DecimalCheck<Double>> build(double[] actual) {
        return new ArrayCheck<>(box(actual), DecimalCheck::new);
    }

    public ArrayCheck<Double, NullableDecimalCheck<Double>> build(Double[] actual) {
        return new ArrayCheck<>(actual, NullableDecimalCheck::new);
    }

    public ArrayCheck<BigDecimal, NullableDecimalCheck<BigDecimal>> build(BigDecimal[] actual) {
        return new ArrayCheck<>(actual, NullableDecimalCheck::new);
    }

    public ArrayCheck<String, StringCheck> build(String[] actual) {
        return new ArrayCheck<>(actual, StringCheck::new);
    }

    public <T extends Temporal> ArrayCheck<T, TemporalCheck<T>> build(T[] actual) {
        return new ArrayCheck<>(actual, TemporalCheck::new);
    }

    public <E extends Enum<E>> ArrayCheck<E, EnumCheck<E>> build(E[] actual) {
        return new ArrayCheck<>(actual, EnumCheck::new);
    }

    public <T> ArrayCheck<Optional<T>, OptionalCheck<T>> build(Optional<T>[] actual) {
        return new ArrayCheck<>(actual, OptionalCheck::new);
    }

    public <K, V> ArrayCheck<Map<K, V>, MapCheck<K, V>> build(Map<K, V>[] actual) {
        return new ArrayCheck<>(actual, MapCheck::new);
    }

    public <T> ArrayCheck<T, ObjectCheck<T>> build(T[] actual) {
        return new ArrayCheck<>(actual, ObjectCheck::new);
    }

    // --- Collection suppliers ---

    public CollectionCheck<Boolean, Collection<Boolean>, NullableBooleanCheck> build(
            BooleanCollectionSupplier supplier) {
        return build(supplier.get(), NullableBooleanCheck::new);
    }

    public ListCheck<Boolean, List<Boolean>, NullableBooleanCheck> build(BooleanListSupplier supplier) {
        return build(supplier.get(), NullableBooleanCheck::new);
    }

    public CollectionCheck<Character, Collection<Character>, NullableCharacterCheck> build(
            CharacterCollectionSupplier supplier) {
        return build(supplier.get(), NullableCharacterCheck::new);
    }

    public ListCheck<Character, List<Character>, NullableCharacterCheck> build(
            CharacterListSupplier supplier) {
        return build(supplier.get(), NullableCharacterCheck::new);
    }

    public CollectionCheck<Byte, Collection<Byte>, NullableNumberCheck<Byte>> build(ByteCollectionSupplier supplier) {
        return build(supplier.get(), NullableNumberCheck::new);
    }

    public ListCheck<Byte, List<Byte>, NullableNumberCheck<Byte>> build(ByteListSupplier supplier) {
        return build(supplier.get(), NullableNumberCheck::new);
    }

    public CollectionCheck<Short, Collection<Short>, NullableNumberCheck<Short>> build(
            ShortCollectionSupplier supplier) {
        return build(supplier.get(), NullableNumberCheck::new);
    }

    public ListCheck<Short, List<Short>, NullableNumberCheck<Short>> build(ShortListSupplier supplier) {
        return build(supplier.get(), NullableNumberCheck::new);
    }

    public CollectionCheck<Integer, Collection<Integer>, NullableNumberCheck<Integer>> build(
            IntegerCollectionSupplier supplier) {
        return build(supplier.get(), NullableNumberCheck::new);
    }

    public ListCheck<Integer, List<Integer>, NullableNumberCheck<Integer>> build(
            IntegerListSupplier supplier) {
        return build(supplier.get(), NullableNumberCheck::new);
    }

    public CollectionCheck<Long, Collection<Long>, NullableNumberCheck<Long>> build(LongCollectionSupplier supplier) {
        return build(supplier.get(), NullableNumberCheck::new);
    }

    public ListCheck<Long, List<Long>, NullableNumberCheck<Long>> build(LongListSupplier supplier) {
        return build(supplier.get(), NullableNumberCheck::new);
    }

    public CollectionCheck<BigInteger, Collection<BigInteger>, NullableNumberCheck<BigInteger>> build(
            BigIntegerCollectionSupplier supplier) {
        return build(supplier.get(), NullableNumberCheck::new);
    }

    public ListCheck<BigInteger, List<BigInteger>, NullableNumberCheck<BigInteger>> build(
            BigIntegerListSupplier supplier) {
        return build(supplier.get(), NullableNumberCheck::new);
    }

    public CollectionCheck<Float, Collection<Float>, NullableDecimalCheck<Float>> build(
            FloatCollectionSupplier supplier) {
        return build(supplier.get(), NullableDecimalCheck::new);
    }

    public ListCheck<Float, List<Float>, NullableDecimalCheck<Float>> build(FloatListSupplier supplier) {
        return build(supplier.get(), NullableDecimalCheck::new);
    }

    public CollectionCheck<Double, Collection<Double>, NullableDecimalCheck<Double>> build(
            DoubleCollectionSupplier supplier) {
        return build(supplier.get(), NullableDecimalCheck::new);
    }

    public ListCheck<Double, List<Double>, NullableDecimalCheck<Double>> build(
            DoubleListSupplier supplier) {
        return build(supplier.get(), NullableDecimalCheck::new);
    }

    public CollectionCheck<BigDecimal, Collection<BigDecimal>, NullableDecimalCheck<BigDecimal>> build(
            BigDecimalCollectionSupplier supplier) {
        return build(supplier.get(), NullableDecimalCheck::new);
    }

    public ListCheck<BigDecimal, List<BigDecimal>, NullableDecimalCheck<BigDecimal>> build(
            BigDecimalListSupplier supplier) {
        return build(supplier.get(), NullableDecimalCheck::new);
    }

    public CollectionCheck<String, Collection<String>, StringCheck> build(StringCollectionSupplier supplier) {
        return build(supplier.get(), StringCheck::new);
    }

    public ListCheck<String, List<String>, StringCheck> build(StringListSupplier supplier) {
        return build(supplier.get(), StringCheck::new);
    }

    public <E extends Enum<E>> CollectionCheck<E, Collection<E>, EnumCheck<E>> build(
            EnumCollectionSupplier<E> supplier) {
        return build(supplier.get(), EnumCheck::new);
    }

    public <E extends Enum<E>> ListCheck<E, List<E>, EnumCheck<E>> build(EnumListSupplier<E> supplier) {
        return build(supplier.get(), EnumCheck::new);
    }

    public <T extends Temporal> CollectionCheck<T, Collection<T>, TemporalCheck<T>> build(
            TemporalCollectionSupplier<T> supplier) {
        return build(supplier.get(), TemporalCheck::new);
    }

    public <T extends Temporal> ListCheck<T, List<T>, TemporalCheck<T>> build(
            TemporalListSupplier<T> supplier) {
        return build(supplier.get(), TemporalCheck::new);
    }

    public <E> CollectionCheck<Collection<E>, Collection<Collection<E>>,
            CollectionCheck<E, Collection<E>, ObjectCheck<E>>> build(CollectionCollectionSupplier<E> supplier) {
        return build(supplier.get(), CollectionCheck::create);
    }

    public <E> ListCheck<Collection<E>, List<Collection<E>>,
            CollectionCheck<E, Collection<E>, ObjectCheck<E>>> build(CollectionListSupplier<E> supplier) {
        return build(supplier.get(), CollectionCheck::create);
    }

    public <E> CollectionCheck<List<E>, Collection<List<E>>, CollectionCheck<E, List<E>, ObjectCheck<E>>> build(
            ListCollectionSupplier<E> supplier) {
        return build(supplier.get(), CollectionCheck::create);
    }

    public <E> ListCheck<List<E>, List<List<E>>, CollectionCheck<E, List<E>, ObjectCheck<E>>> build(
            ListListSupplier<E> supplier) {
        return build(supplier.get(), CollectionCheck::create);
    }

    public <K, V> CollectionCheck<Map<K, V>, Collection<Map<K, V>>, MapCheck<K, V>> build(
            MapCollectionSupplier<K, V> supplier) {
        return build(supplier.get(), MapCheck::new);
    }

    public <K, V> ListCheck<Map<K, V>, List<Map<K, V>>, MapCheck<K, V>> build(
            MapListSupplier<K, V> supplier) {
        return build(supplier.get(), MapCheck::new);
    }

    public <E> CollectionCheck<Optional<E>, Collection<Optional<E>>, OptionalCheck<E>> build(
            OptionalCollectionSupplier<E> supplier) {
        return build(supplier.get(), OptionalCheck::new);
    }

    public <E> ListCheck<Optional<E>, List<Optional<E>>, OptionalCheck<E>> build(
            OptionalListSupplier<E> supplier) {
        return build(supplier.get(), OptionalCheck::new);
    }

    private <T, C extends Collection<T>, E extends AbstractCheck<T>> CollectionCheck<T, C, E> build(
            C actual, BiFunction<T, List<String>, E> elementCheck) {
        return new CollectionCheck<>(actual, elementCheck);
    }

    private <T, C extends List<T>, E extends AbstractCheck<T>> ListCheck<T, C, E> build(
            C actual, BiFunction<T, List<String>, E> elementCheck) {
        return new ListCheck<>(actual, elementCheck);
    }

    private static Boolean[] box(boolean[] actual) {
        if (actual == null) {
            return null;
        }
        Boolean[] boxed = new Boolean[actual.length];
        for (int i = 0; i < actual.length; i++) {
            boxed[i] = actual[i];
        }
        return boxed;
    }

    private static Character[] box(char[] actual) {
        if (actual == null) {
            return null;
        }
        Character[] boxed = new Character[actual.length];
        for (int i = 0; i < actual.length; i++) {
            boxed[i] = actual[i];
        }
        return boxed;
    }

    private static Byte[] box(byte[] actual) {
        if (actual == null) {
            return null;
        }
        Byte[] boxed = new Byte[actual.length];
        for (int i = 0; i < actual.length; i++) {
            boxed[i] = actual[i];
        }
        return boxed;
    }

    private static Short[] box(short[] actual) {
        if (actual == null) {
            return null;
        }
        Short[] boxed = new Short[actual.length];
        for (int i = 0; i < actual.length; i++) {
            boxed[i] = actual[i];
        }
        return boxed;
    }

    private static Integer[] box(int[] actual) {
        if (actual == null) {
            return null;
        }
        Integer[] boxed = new Integer[actual.length];
        for (int i = 0; i < actual.length; i++) {
            boxed[i] = actual[i];
        }
        return boxed;
    }

    private static Long[] box(long[] actual) {
        if (actual == null) {
            return null;
        }
        Long[] boxed = new Long[actual.length];
        for (int i = 0; i < actual.length; i++) {
            boxed[i] = actual[i];
        }
        return boxed;
    }

    private static Float[] box(float[] actual) {
        if (actual == null) {
            return null;
        }
        Float[] boxed = new Float[actual.length];
        for (int i = 0; i < actual.length; i++) {
            boxed[i] = actual[i];
        }
        return boxed;
    }

    private static Double[] box(double[] actual) {
        if (actual == null) {
            return null;
        }
        Double[] boxed = new Double[actual.length];
        for (int i = 0; i < actual.length; i++) {
            boxed[i] = actual[i];
        }
        return boxed;
    }

}
