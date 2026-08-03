/*
 * Copyright 2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package software.plusminus.check.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@SuppressWarnings({"checkstyle:ClassTypeParameterName", "java:S119"})
public class IterableCheck<T, I extends Iterable<T>, E extends AbstractCheck<T>>
        extends AbstractArrayCheck<T, I, E, IterableCheck<T, I, E>>
        implements CollectionMapCheck<T>, CollectionTypeCheck {

    @Nullable
    private List<T> elements;

    public IterableCheck(@Nullable I actual, BiFunction<T, List<String>, E> elementCheck) {
        super(actual, elementCheck);
    }

    public IterableCheck(@Nullable I actual, List<String> levels, BiFunction<T, List<String>, E> elementCheck) {
        super(actual, levels, elementCheck);
    }

    @Override
    @CheckReturnValue
    public <R, M extends AbstractCheck<R>> CollectionCheck<R, Collection<R>, M> map(
            Function<T, R> mapper, BiFunction<R, List<String>, M> elementCheck) {
        return elementsCheck().map(mapper, elementCheck);
    }

    @CheckReturnValue
    public CollectionCheck<T, Collection<T>, E> filter(Predicate<T> predicate) {
        return elementsCheck().filter(predicate);
    }

    @CheckReturnValue
    public <R> CollectionCheck<R, Collection<R>, ObjectCheck<R>> flatMap(Function<T, ? extends Collection<R>> mapper) {
        return elementsCheck().flatMap(mapper);
    }

    @CheckReturnValue
    public CollectionCheck<T, Collection<T>, E> distinct() {
        return elementsCheck().distinct();
    }

    @CheckReturnValue
    public ListCheck<T, List<T>, E> sorted() {
        return elementsCheck().sorted();
    }

    @CheckReturnValue
    public ListCheck<T, List<T>, E> sorted(Comparator<? super T> comparator) {
        return elementsCheck().sorted(comparator);
    }

    @Override
    @CheckReturnValue
    public <X, M extends AbstractCheck<X>> CollectionCheck<X, Collection<X>, M> isCollectionOf(
            Class<X> type, BiFunction<X, List<String>, M> checkBuilder) {
        return elementsCheck().isCollectionOf(type, checkBuilder);
    }

    @Override
    protected int size() {
        return elements().size();
    }

    @Override
    protected List<T> actualList() {
        return new ArrayList<>(elements());
    }

    @Override
    protected T get(int index) {
        return elements().get(index);
    }

    @CheckReturnValue
    public static <T, I extends Iterable<T>> IterableCheck<T, I, ObjectCheck<T>> create(I actual) {
        return new IterableCheck<>(actual, ObjectCheck::new);
    }

    @CheckReturnValue
    public static <T, I extends Iterable<T>> IterableCheck<T, I, ObjectCheck<T>> create(
            I actual, List<String> levels) {
        return new IterableCheck<>(actual, levels, ObjectCheck::new);
    }

    private List<T> elements() {
        if (elements == null) {
            List<T> read = new ArrayList<>();
            actual().forEach(read::add);
            elements = read;
        }
        return elements;
    }

    /**
     * The elements as a collection check, which is what every derived check is built from:
     * once mapped, filtered or sorted, the original iterable is no longer the value under
     * assertion, so there is nothing left for this class to hold on to.
     */
    private CollectionCheck<T, Collection<T>, E> elementsCheck() {
        isNotNull();
        return new CollectionCheck<>(actualList(), levels(), elementCheck);
    }
}
