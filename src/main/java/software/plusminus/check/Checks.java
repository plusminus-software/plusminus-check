package software.plusminus.check;

import lombok.experimental.UtilityClass;

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

    public PrimitiveBooleanCheck check(boolean actual) {
        return factory.build(actual);
    }

    public BooleanCheck check(Boolean actual) {
        return factory.build(actual);
    }

    public PrimitiveCharacterCheck check(char actual) {
        return factory.build(actual);
    }

    public CharacterCheck check(Character actual) {
        return factory.build(actual);
    }

    public PrimitiveNumberCheck<Byte> check(byte actual) {
        return factory.build(actual);
    }

    public NumberCheck<Byte> check(Byte actual) {
        return factory.build(actual);
    }

    public PrimitiveNumberCheck<Short> check(short actual) {
        return factory.build(actual);
    }

    public NumberCheck<Short> check(Short actual) {
        return factory.build(actual);
    }

    public PrimitiveNumberCheck<Integer> check(int actual) {
        return factory.build(actual);
    }

    public NumberCheck<Integer> check(Integer actual) {
        return factory.build(actual);
    }

    public PrimitiveNumberCheck<Long> check(long actual) {
        return factory.build(actual);
    }

    public NumberCheck<Long> check(Long actual) {
        return factory.build(actual);
    }

    public NumberCheck<BigInteger> check(BigInteger actual) {
        return factory.build(actual);
    }

    public PrimitiveDecimalCheck<Float> check(float actual) {
        return factory.build(actual);
    }

    public DecimalCheck<Float> check(Float actual) {
        return factory.build(actual);
    }

    public PrimitiveDecimalCheck<Double> check(double actual) {
        return factory.build(actual);
    }

    public DecimalCheck<Double> check(Double actual) {
        return factory.build(actual);
    }

    public DecimalCheck<BigDecimal> check(BigDecimal actual) {
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
}
