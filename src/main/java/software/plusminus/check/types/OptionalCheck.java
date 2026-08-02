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

import java.util.List;
import java.util.Optional;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@SuppressWarnings("java:S2789")
public class OptionalCheck<T> extends AbstractObjectCheck<Optional<T>> {

    public OptionalCheck(@Nullable Optional<T> actual) {
        super(actual);
    }

    public OptionalCheck(@Nullable Optional<T> actual, List<String> levels) {
        super(actual, levels);
    }

    @Override
    public OptionalCheck<T> isNotNull() {
        super.isNotNull();
        return this;
    }

    @Override
    public OptionalCheck<T> isNot(Optional<T> unexpected) {
        super.isNot(unexpected);
        return this;
    }

    @Override
    public void is(String expected) {
        isPresent().is(expected);
    }

    @Override
    public void isLike(Object expected) {
        isPresent().isLike(expected);
    }

    public void hasValue(T expected) {
        isPresent().is(expected);
    }

    public void isEmpty() {
        isNotNull();
        if (actual().isPresent()) {
            fail("not empty", "empty");
        }
    }

    @CheckReturnValue
    public ObjectCheck<T> isPresent() {
        isNotNull();
        Optional<T> actual = actual();
        if (!actual.isPresent()) {
            fail("empty", "not empty");
        }
        return new ObjectCheck<>(actual.get(), levels());
    }
}
