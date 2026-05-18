package software.plusminus.check;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@SuppressFBWarnings(value = "RV_RETURN_VALUE_IGNORED",
        justification = "super calls return this; narrowing override return type")
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

    @SafeVarargs
    public final void is(T... expected) {
        isLike(Arrays.asList(expected));
    }

    @Override
    public OrderedCollectionCheck<T, C, E> contains(Object... expectedElements) {
        super.contains(expectedElements);
        return this;
    }

    @CheckReturnValue
    public LinkedCheck<T, E, OrderedCollectionCheck<T, C, E>> at(int index) {
        isNotNull();
        int size = actual().size();
        if (index < 0 || index >= size) {
            fail("size is " + size, "has element at index " + index);
        }
        T element = get(index);
        List<String> elementLevels = new ArrayList<>(levels());
        elementLevels.add("[" + index + "]");
        E check = elementCheck.apply(element, elementLevels);
        return new LinkedCheck<>(check, this);
    }

    @CheckReturnValue
    public static <T, C extends Collection<T>> OrderedCollectionCheck<T, C, ObjectCheck<T>> create(C actual) {
        return new OrderedCollectionCheck<>(actual, ObjectCheck::new);
    }

    @CheckReturnValue
    public static <T, C extends Collection<T>> OrderedCollectionCheck<T, C, ObjectCheck<T>> create(
            C actual, List<String> levels) {
        return new OrderedCollectionCheck<>(actual, levels, ObjectCheck::new);
    }

    private T get(int index) {
        if (actual() instanceof List) {
            return ((List<T>) actual()).get(index);
        } else {
            Iterator<T> iterator = actual().iterator();
            T current = null;
            for (int i = 0; i <= index; i++) {
                current = iterator.next();
            }
            return current;
        }
    }
}
