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

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

/**
 * Check for {@link Collection}s. Inherits the common element-bearing assertions
 * from {@link AbstractArrayCheck}. Indexed access ({@code at(int)}) is kept
 * {@code protected} here because plain collections are not ordered — see
 * {@link ListCheck} for the publicly-exposed indexed variant.
 */
public class CollectionCheck<T, C extends Collection<T>, E extends AbstractCheck<T>>
        extends AbstractCollectionCheck<T, C, E, CollectionCheck<T, C, E>>
        implements CollectionMapCheck<T>, CollectionTypeCheck {

    public CollectionCheck(@Nullable C actual, BiFunction<T, List<String>, E> elementCheck) {
        super(actual, elementCheck);
    }

    public CollectionCheck(@Nullable C actual, List<String> levels, BiFunction<T, List<String>, E> elementCheck) {
        super(actual, levels, elementCheck);
    }

    @Override
    protected void is(Object... expectedElements) {
        containsExactly(expectedElements);
    }

    @Override
    @CheckReturnValue
    public <R, M extends AbstractCheck<R>> CollectionCheck<R, Collection<R>, M> map(
            Function<T, R> mapper, BiFunction<R, List<String>, M> elementCheck) {
        return new CollectionCheck<>(operations().map(mapper), levels(), elementCheck);
    }

    @CheckReturnValue
    public CollectionCheck<T, Collection<T>, E> filter(Predicate<T> predicate) {
        return new CollectionCheck<>(operations().filter(predicate), levels(), elementCheck);
    }

    @CheckReturnValue
    public <R> CollectionCheck<R, Collection<R>, ObjectCheck<R>> flatMap(
            Function<T, ? extends Collection<R>> mapper) {
        BiFunction<R, List<String>, ObjectCheck<R>> elementCheck = ObjectCheck::new;
        return new CollectionCheck<>(operations().flatMap(mapper), levels(), elementCheck);
    }

    @CheckReturnValue
    public CollectionCheck<T, Collection<T>, E> distinct() {
        return new CollectionCheck<>(operations().distinct(), levels(), elementCheck);
    }

    @Override
    @CheckReturnValue
    public <X, M extends AbstractCheck<X>> CollectionCheck<X, Collection<X>, M> isCollectionOf(
            Class<X> type, BiFunction<X, List<String>, M> checkBuilder) {
        checkElementsType(type);
        return map(type::cast, checkBuilder);
    }

    @CheckReturnValue
    public static <T, C extends Collection<T>> CollectionCheck<T, C, ObjectCheck<T>> create(C actual) {
        return new CollectionCheck<>(actual, ObjectCheck::new);
    }

    @CheckReturnValue
    public static <T, C extends Collection<T>> CollectionCheck<T, C, ObjectCheck<T>> create(
            C actual, List<String> levels) {
        return new CollectionCheck<>(actual, levels, ObjectCheck::new);
    }
}
