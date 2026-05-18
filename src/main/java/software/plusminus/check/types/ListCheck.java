package software.plusminus.check.types;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

/**
 * Check for ordered collections — {@link List}, {@link java.util.Deque},
 * {@link java.util.SortedSet}. Exposes indexed access via {@link #at(int)}
 * and an order-sensitive {@link #is(Object[])} comparison.
 */
public class ListCheck<T, C extends Collection<T>, E extends AbstractCheck<T>>
        extends AbstractCollectionCheck<T, C, E, ListCheck<T, C, E>> {

    public ListCheck(@Nullable C actual, BiFunction<T, List<String>, E> elementCheck) {
        super(actual, elementCheck);
    }

    public ListCheck(@Nullable C actual, List<String> levels, BiFunction<T, List<String>, E> elementCheck) {
        super(actual, levels, elementCheck);
    }

    @Override
    @CheckReturnValue
    public LinkedCheck<T, E, ListCheck<T, C, E>> at(int index) {
        return super.at(index);
    }

    @SafeVarargs
    public final void is(T... expected) {
        isLike(Arrays.asList(expected));
    }

    @CheckReturnValue
    public static <T, C extends Collection<T>> ListCheck<T, C, ObjectCheck<T>> create(C actual) {
        return new ListCheck<>(actual, ObjectCheck::new);
    }

    @CheckReturnValue
    public static <T, C extends Collection<T>> ListCheck<T, C, ObjectCheck<T>> create(
            C actual, List<String> levels) {
        return new ListCheck<>(actual, levels, ObjectCheck::new);
    }
}
