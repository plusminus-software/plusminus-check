package software.plusminus.check;

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

@CheckReturnValue
public class CheckFactory {

    public PrimitiveBooleanCheck build(boolean actual) {
        return new PrimitiveBooleanCheck(actual);
    }

    public BooleanCheck build(Boolean actual) {
        return new BooleanCheck(actual);
    }

    public PrimitiveCharacterCheck build(char actual) {
        return new PrimitiveCharacterCheck(actual);
    }

    public CharacterCheck build(Character actual) {
        return new CharacterCheck(actual);
    }

    public PrimitiveNumberCheck<Byte> build(byte actual) {
        return new PrimitiveNumberCheck<>(actual);
    }

    public NumberCheck<Byte> build(Byte actual) {
        return new NumberCheck<>(actual);
    }

    public PrimitiveNumberCheck<Short> build(short actual) {
        return new PrimitiveNumberCheck<>(actual);
    }

    public NumberCheck<Short> build(Short actual) {
        return new NumberCheck<>(actual);
    }

    public PrimitiveNumberCheck<Integer> build(int actual) {
        return new PrimitiveNumberCheck<>(actual);
    }

    public NumberCheck<Integer> build(Integer actual) {
        return new NumberCheck<>(actual);
    }

    public PrimitiveNumberCheck<Long> build(long actual) {
        return new PrimitiveNumberCheck<>(actual);
    }

    public NumberCheck<Long> build(Long actual) {
        return new NumberCheck<>(actual);
    }

    public NumberCheck<BigInteger> build(BigInteger actual) {
        return new NumberCheck<>(actual);
    }

    public PrimitiveDecimalCheck<Float> build(float actual) {
        return new PrimitiveDecimalCheck<>(actual);
    }

    public DecimalCheck<Float> build(Float actual) {
        return new DecimalCheck<>(actual);
    }

    public PrimitiveDecimalCheck<Double> build(double actual) {
        return new PrimitiveDecimalCheck<>(actual);
    }

    public DecimalCheck<Double> build(Double actual) {
        return new DecimalCheck<>(actual);
    }

    public DecimalCheck<BigDecimal> build(BigDecimal actual) {
        return new DecimalCheck<>(actual);
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

}
