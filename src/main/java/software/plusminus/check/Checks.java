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
package software.plusminus.check;

import lombok.experimental.UtilityClass;

import java.time.temporal.Temporal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import javax.annotation.CheckReturnValue;

/**
 * Main entry point for all the checkers.
 * Recommend to static import this class in a test classes.
 *
 * @author Taras Shpek
 */
@SuppressWarnings("checkstyle:ClassDataAbstractionCoupling")
@CheckReturnValue
@UtilityClass
public class Checks {
    
    public BooleanCheck check(boolean actual) {
        return new BooleanCheck(actual);
    }
    
    public IntegerCheck check(int actual) {
        return new IntegerCheck(actual);
    }
    
    public LongCheck check(long actual) {
        return new LongCheck(actual);
    }
    
    public DoubleCheck check(double actual) {
        return new DoubleCheck(actual);
    }

    public <T extends Number> NumberCheck<T> check(T actual) {
        return new NumberCheck<>(actual);
    }
    
    public StringCheck check(String actual) {
        return new StringCheck(actual);
    }
    
    public TemporalCheck check(Temporal actual) {
        return new TemporalCheck(actual);
    }

    public <T> ObjectCheck<T> check(T actual) {
        return new ObjectCheck<>(actual);
    }

    public <T> OptionalCheck<T> check(Optional<T> actual) {
        return new OptionalCheck<>(actual);
    }

    public <T> CollectionCheck<T> check(T[] actual) {
        return new CollectionCheck<>(Arrays.asList(actual));
    }
    
    public <T> CollectionCheck<T> check(Collection<T> actual) {
        return new CollectionCheck<>(actual);
    }
    
    public <K, V> MapCheck<K, V> check(Map<K, V> actual) {
        return new MapCheck<>(actual);
    }
    
}
