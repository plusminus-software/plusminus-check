package software.plusminus.check.types;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

public class ArrayCheck<T, E extends AbstractCheck<T>>
        extends AbstractArrayCheck<T, T[], E, ArrayCheck<T, E>>
        implements ArrayMapCheck<T>, ArrayTypeCheck {

    public ArrayCheck(@Nullable T[] actual, BiFunction<T, List<String>, E> elementCheck) {
        super(actual, elementCheck);
    }

    public ArrayCheck(@Nullable T[] actual, List<String> levels, BiFunction<T, List<String>, E> elementCheck) {
        super(actual, levels, elementCheck);
    }

    @Override
    protected int size() {
        return actual().length;
    }

    @Override
    protected List<T> actualList() {
        return new ArrayList<>(Arrays.asList(actual()));
    }

    @Override
    protected T get(int index) {
        return actual()[index];
    }

    @Override
    @CheckReturnValue
    public LinkedCheck<T, E, ArrayCheck<T, E>> at(int index) {
        return super.at(index);
    }

    @Override
    @CheckReturnValue
    public LinkedCheck<T, E, ArrayCheck<T, E>> first() {
        return super.first();
    }

    @Override
    @CheckReturnValue
    public LinkedCheck<T, E, ArrayCheck<T, E>> last() {
        return super.last();
    }

    @Override
    public void is(Object... expectedElements) {
        super.is(expectedElements);
    }

    @Override
    @CheckReturnValue
    @SuppressWarnings("unchecked")
    public <R, M extends AbstractCheck<R>> ArrayCheck<R, M> map(
            Function<T, R> mapper, BiFunction<R, List<String>, M> elementCheck) {
        R[] mapped = (R[]) operations().map(mapper).toArray();
        return new ArrayCheck<>(mapped, levels(), elementCheck);
    }

    @CheckReturnValue
    public ArrayCheck<T, E> filter(Predicate<T> predicate) {
        return create(operations().filter(predicate));
    }

    @CheckReturnValue
    @SuppressWarnings("unchecked")
    public <R> ArrayCheck<R, ObjectCheck<R>> flatMap(Function<T, ? extends Collection<R>> mapper) {
        BiFunction<R, List<String>, ObjectCheck<R>> elementCheck = ObjectCheck::new;
        R[] flattened = (R[]) operations().flatMap(mapper).toArray();
        return new ArrayCheck<>(flattened, levels(), elementCheck);

    }

    @CheckReturnValue
    public ArrayCheck<T, E> distinct() {
        return create(operations().distinct());
    }

    @CheckReturnValue
    public ArrayCheck<T, E> sorted() {
        return create(operations().sort(null));
    }

    @CheckReturnValue
    public ArrayCheck<T, E> sorted(Comparator<? super T> comparator) {
        return create(operations().sort(comparator));
    }

    @Override
    @CheckReturnValue
    @SuppressWarnings("unchecked")
    public <X, M extends AbstractCheck<X>> ArrayCheck<X, M> isArrayOf(
            Class<X> type, BiFunction<X, List<String>, M> checkBuilder) {
        checkElementsType(type);
        List<X> mapped = operations().map(type::cast);
        X[] typed = mapped.toArray((X[]) Array.newInstance(type, mapped.size()));
        return new ArrayCheck<>(typed, levels(), checkBuilder);
    }

    private ArrayCheck<T, E> create(List<T> elements) {
        T[] empty = Arrays.copyOf(actual(), 0);
        return new ArrayCheck<>(elements.toArray(empty), levels(), elementCheck);
    }

    @CheckReturnValue
    public static <T> ArrayCheck<T, ObjectCheck<T>> create(T[] actual) {
        return new ArrayCheck<>(actual, ObjectCheck::new);
    }

    @CheckReturnValue
    public static <T> ArrayCheck<T, ObjectCheck<T>> create(T[] actual, List<String> levels) {
        return new ArrayCheck<>(actual, levels, ObjectCheck::new);
    }
}
