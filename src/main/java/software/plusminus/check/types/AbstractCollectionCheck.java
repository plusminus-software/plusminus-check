package software.plusminus.check.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

/**
 * Shared accessor base for {@link AbstractArrayCheck} subclasses whose actual
 * is a {@link Collection}. Holds the {@code size} / {@code snapshot} /
 * {@code elementAt} implementations once so {@link CollectionCheck} and
 * {@link ListCheck} don't duplicate them.
 */
@SuppressWarnings({"checkstyle:ClassTypeParameterName", "java:S119"})
abstract class AbstractCollectionCheck<T, C extends Collection<T>, E extends AbstractCheck<T>,
        Self extends AbstractCollectionCheck<T, C, E, Self>>
        extends AbstractArrayCheck<T, C, E, Self> {

    protected AbstractCollectionCheck(@Nullable C actual, BiFunction<T, List<String>, E> elementCheck) {
        super(actual, elementCheck);
    }

    protected AbstractCollectionCheck(@Nullable C actual, List<String> levels,
                                      BiFunction<T, List<String>, E> elementCheck) {
        super(actual, levels, elementCheck);
    }

    @CheckReturnValue
    public ListCheck<T, List<T>, E> sorted() {
        return new ListCheck<>(operations().sort(null), levels(), elementCheck);
    }

    @CheckReturnValue
    public ListCheck<T, List<T>, E> sorted(Comparator<? super T> comparator) {
        return new ListCheck<>(operations().sort(comparator), levels(), elementCheck);
    }

    @Override
    protected int size() {
        return actual().size();
    }

    @Override
    protected List<T> actualList() {
        return new ArrayList<>(actual());
    }

    @Override
    protected T get(int index) {
        if (actual() instanceof List) {
            List<T> actualList = (List<T>) actual();
            return actualList.get(index);
        }
        Iterator<T> iterator = actual().iterator();
        T current = null;
        for (int i = 0; i <= index; i++) {
            current = iterator.next();
        }
        return current;
    }
}
