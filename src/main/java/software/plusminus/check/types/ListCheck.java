package software.plusminus.check.types;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
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
    @CheckReturnValue
    public LinkedCheck<T, E, ListCheck<T, C, E>> first() {
        return super.first();
    }

    @Override
    @CheckReturnValue
    public LinkedCheck<T, E, ListCheck<T, C, E>> last() {
        return super.last();
    }

    @Override
    public final void is(Object... expectedElements) {
        super.is(expectedElements);
    }

    @Override
    @CheckReturnValue
    public <R, M extends AbstractCheck<R>> ListCheck<R, List<R>, M> map(
            Function<T, R> mapper, BiFunction<R, List<String>, M> elementCheck) {
        return new ListCheck<>(operations().map(mapper), levels(), elementCheck);
    }

    @CheckReturnValue
    public ListCheck<T, List<T>, E> filter(Predicate<T> predicate) {
        return new ListCheck<>(operations().filter(predicate), levels(), elementCheck);
    }

    @CheckReturnValue
    public <R> ListCheck<R, List<R>, ObjectCheck<R>> flatMap(Function<T, ? extends Collection<R>> mapper) {
        BiFunction<R, List<String>, ObjectCheck<R>> elementCheck = ObjectCheck::new;
        return new ListCheck<>(operations().flatMap(mapper), levels(), elementCheck);
    }

    @CheckReturnValue
    public ListCheck<T, List<T>, E> distinct() {
        return new ListCheck<>(operations().distinct(), levels(), elementCheck);
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
