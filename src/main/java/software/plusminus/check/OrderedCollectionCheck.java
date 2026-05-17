package software.plusminus.check;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import javax.annotation.Nullable;

public class OrderedCollectionCheck<T, C extends Collection<T>, E extends AbstractCheck<T>>
        extends CollectionCheck<T, C, E> {

    public OrderedCollectionCheck(@Nullable C actual, BiFunction<T, List<String>, E> elementCheck) {
        super(actual, elementCheck);
    }

    public OrderedCollectionCheck(@Nullable C actual, List<String> levels,
                                  BiFunction<T, List<String>, E> elementCheck) {
        super(actual, levels, elementCheck);
    }

    @Override
    public OrderedCollectionCheck<T, C, E> isNotEmpty() {
        super.isNotEmpty();
        return this;
    }

    @Override
    public OrderedCollectionCheck<T, C, E> hasSize(int expectedSize) {
        super.hasSize(expectedSize);
        return this;
    }

    @Override
    public void is(C expected) {
        super.is(expected);
    }

    @Override
    public void is(String expected) {
        super.is(expected);
    }

    @Override
    public OrderedCollectionCheck<T, C, E> contains(Object... expectedElements) {
        super.contains(expectedElements);
        return this;
    }

    @SafeVarargs
    public final void is(T... expected) {
        isLike(Arrays.asList(expected));
    }

    public static <T, C extends Collection<T>> OrderedCollectionCheck<T, C, ObjectCheck<T>> create(C actual) {
        return new OrderedCollectionCheck<>(actual, ObjectCheck::new);
    }

    public static <T, C extends Collection<T>> OrderedCollectionCheck<T, C, ObjectCheck<T>> create(
            C actual, List<String> levels) {
        return new OrderedCollectionCheck<>(actual, levels, ObjectCheck::new);
    }
}
