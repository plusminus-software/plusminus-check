package software.plusminus.check.types;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

public class ListCheck<T, C extends Collection<T>, E extends AbstractCheck<T>>
        extends AbstractCollectionCheck<T, C, E, ListCheck<T, C, E>>
        implements ListMapCheck<T>, ListTypeCheck {

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

    @Override
    public final void is(Object... expectedElements) {
        super.is(expectedElements);
    }

    @Override
    @CheckReturnValue
    public <R, M extends AbstractCheck<R>> ListCheck<R, List<R>, M> map(
            Function<T, R> mapper, BiFunction<R, List<String>, M> elementCheck) {
        return new ListCheck<>(mapToList(mapper), levels(), elementCheck);
    }

    @Override
    @CheckReturnValue
    public <X, M extends AbstractCheck<X>> ListCheck<X, List<X>, M> isListOf(
            Class<X> type, BiFunction<X, List<String>, M> checkBuilder) {
        checkElementsType(type);
        return map(type::cast, checkBuilder);
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
