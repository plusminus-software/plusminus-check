package software.plusminus.check.factory;

import software.plusminus.check.ThrowingRunnable;
import software.plusminus.check.types.ArrayCheck;
import software.plusminus.check.types.BooleanCheck;
import software.plusminus.check.types.BytesCheck;
import software.plusminus.check.types.CharacterCheck;
import software.plusminus.check.types.CollectionCheck;
import software.plusminus.check.types.DecimalCheck;
import software.plusminus.check.types.DurationCheck;
import software.plusminus.check.types.EnumCheck;
import software.plusminus.check.types.ExceptionCheck;
import software.plusminus.check.types.InputStreamCheck;
import software.plusminus.check.types.IterableCheck;
import software.plusminus.check.types.ListCheck;
import software.plusminus.check.types.MapCheck;
import software.plusminus.check.types.NullableBooleanCheck;
import software.plusminus.check.types.NullableCharacterCheck;
import software.plusminus.check.types.NullableDecimalCheck;
import software.plusminus.check.types.NullableNumberCheck;
import software.plusminus.check.types.NumberCheck;
import software.plusminus.check.types.ObjectCheck;
import software.plusminus.check.types.OptionalCheck;
import software.plusminus.check.types.PathCheck;
import software.plusminus.check.types.PeriodCheck;
import software.plusminus.check.types.StringCheck;
import software.plusminus.check.types.TemporalCheck;
import software.plusminus.check.util.ArrayUtil;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Period;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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

    public PathCheck build(Path actual) {
        return new PathCheck(actual);
    }

    public DurationCheck build(Duration actual) {
        return new DurationCheck(actual);
    }

    public PeriodCheck build(Period actual) {
        return new PeriodCheck(actual);
    }

    @SuppressWarnings({"PMD.AvoidCatchingThrowable", "java:S1181"})
    public ExceptionCheck build(ThrowingRunnable actual) {
        try {
            actual.run();
        } catch (Throwable throwable) {
            return new ExceptionCheck(throwable);
        }
        return new ExceptionCheck(null);
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

    public <T> IterableCheck<T, Iterable<T>, ObjectCheck<T>> build(Iterable<T> actual) {
        return IterableCheck.create(actual);
    }

    public <T> ListCheck<T, List<T>, ObjectCheck<T>> build(Stream<T> actual) {
        List<T> elements = actual == null ? null : actual.collect(Collectors.toList());
        return ListCheck.create(elements);
    }

    public <T> ListCheck<T, List<T>, ObjectCheck<T>> build(Iterator<T> actual) {
        List<T> elements;
        if (actual == null) {
            elements = null;
        } else {
            elements = new ArrayList<>();
            actual.forEachRemaining(elements::add);
        }
        return ListCheck.create(elements);
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
        return new ArrayCheck<>(ArrayUtil.box(actual), BooleanCheck::new);
    }

    public ArrayCheck<Boolean, NullableBooleanCheck> build(Boolean[] actual) {
        return new ArrayCheck<>(actual, NullableBooleanCheck::new);
    }

    public ArrayCheck<Character, CharacterCheck> build(char[] actual) {
        return new ArrayCheck<>(ArrayUtil.box(actual), CharacterCheck::new);
    }

    public ArrayCheck<Character, NullableCharacterCheck> build(Character[] actual) {
        return new ArrayCheck<>(actual, NullableCharacterCheck::new);
    }

    public BytesCheck build(byte[] actual) {
        return new BytesCheck(actual);
    }

    public ArrayCheck<Byte, NullableNumberCheck<Byte>> build(Byte[] actual) {
        return new ArrayCheck<>(actual, NullableNumberCheck::new);
    }

    public ArrayCheck<Short, NumberCheck<Short>> build(short[] actual) {
        return new ArrayCheck<>(ArrayUtil.box(actual), NumberCheck::new);
    }

    public ArrayCheck<Short, NullableNumberCheck<Short>> build(Short[] actual) {
        return new ArrayCheck<>(actual, NullableNumberCheck::new);
    }

    public ArrayCheck<Integer, NumberCheck<Integer>> build(int[] actual) {
        return new ArrayCheck<>(ArrayUtil.box(actual), NumberCheck::new);
    }

    public ArrayCheck<Integer, NullableNumberCheck<Integer>> build(Integer[] actual) {
        return new ArrayCheck<>(actual, NullableNumberCheck::new);
    }

    public ArrayCheck<Long, NumberCheck<Long>> build(long[] actual) {
        return new ArrayCheck<>(ArrayUtil.box(actual), NumberCheck::new);
    }

    public ArrayCheck<Long, NullableNumberCheck<Long>> build(Long[] actual) {
        return new ArrayCheck<>(actual, NullableNumberCheck::new);
    }

    public ArrayCheck<BigInteger, NullableNumberCheck<BigInteger>> build(BigInteger[] actual) {
        return new ArrayCheck<>(actual, NullableNumberCheck::new);
    }

    public ArrayCheck<Float, DecimalCheck<Float>> build(float[] actual) {
        return new ArrayCheck<>(ArrayUtil.box(actual), DecimalCheck::new);
    }

    public ArrayCheck<Float, NullableDecimalCheck<Float>> build(Float[] actual) {
        return new ArrayCheck<>(actual, NullableDecimalCheck::new);
    }

    public ArrayCheck<Double, DecimalCheck<Double>> build(double[] actual) {
        return new ArrayCheck<>(ArrayUtil.box(actual), DecimalCheck::new);
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

    public ArrayCheck<Path, PathCheck> build(Path[] actual) {
        return new ArrayCheck<>(actual, PathCheck::new);
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
}
