package software.plusminus.check;

import software.plusminus.check.factory.CheckFactory;
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

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Period;
import java.time.temporal.Temporal;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;
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

    static InputStreamCheck check(InputStream actual) {
        return FACTORY.get().build(actual);
    }

    static PathCheck check(Path actual) {
        return FACTORY.get().build(actual);
    }

    static DurationCheck check(Duration actual) {
        return FACTORY.get().build(actual);
    }

    static PeriodCheck check(Period actual) {
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

    static <T> IterableCheck<T, Iterable<T>, ObjectCheck<T>> check(Iterable<T> actual) {
        return FACTORY.get().build(actual);
    }

    static <T> ListCheck<T, List<T>, ObjectCheck<T>> check(Stream<T> actual) {
        return FACTORY.get().build(actual);
    }

    static <T> ListCheck<T, List<T>, ObjectCheck<T>> check(Iterator<T> actual) {
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

    static BytesCheck check(byte[] actual) {
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

    static ArrayCheck<Path, PathCheck> check(Path[] actual) {
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

    /* Expected values */

    static <K, V> Map.Entry<K, V> entry(K key, V value) {
        return new AbstractMap.SimpleImmutableEntry<>(key, value);
    }

    /* Exceptions */

    /**
     * Checks an exception thrown by the passed lambda.
     * Named differently from other check() methods as a lambda argument
     * is ambiguous with the generic check(T) overload.
     */
    static ExceptionCheck checkException(ThrowingRunnable actual) {
        return FACTORY.get().build(actual);
    }
}
