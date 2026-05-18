package software.plusminus.check.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

/**
 * Check for object arrays. Holds the array as the raw actual ({@code T[]}),
 * exposes indexed access via {@link #at(int)} and array-equality via
 * {@link #is(Object[])}.
 */
public class ArrayCheck<T, E extends AbstractCheck<T>>
        extends AbstractArrayCheck<T, T[], E, ArrayCheck<T, E>> {

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
    @SafeVarargs
    public final void is(T... expected) {
        isNotNull();
        if (!Arrays.equals(actual(), expected)) {
            fail(Arrays.asList(actual()), Arrays.asList(expected));
        }
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
