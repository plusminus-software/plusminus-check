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
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

/**
 * Check for {@link Collection}s. Inherits the common element-bearing assertions
 * from {@link AbstractArrayCheck}. Indexed access ({@code at(int)}) is kept
 * {@code protected} here because plain collections are not ordered — see
 * {@link ListCheck} for the publicly-exposed indexed variant.
 */
public class CollectionCheck<T, C extends Collection<T>, E extends AbstractCheck<T>>
        extends AbstractCollectionCheck<T, C, E, CollectionCheck<T, C, E>> {

    public CollectionCheck(@Nullable C actual, BiFunction<T, List<String>, E> elementCheck) {
        super(actual, elementCheck);
    }

    public CollectionCheck(@Nullable C actual, List<String> levels, BiFunction<T, List<String>, E> elementCheck) {
        super(actual, levels, elementCheck);
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
