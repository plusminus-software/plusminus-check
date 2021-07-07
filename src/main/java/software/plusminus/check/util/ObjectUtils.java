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
package software.plusminus.check.util;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Utility class for Reflection API work with objects.
 *
 * @author Taras Shpek
 */
@UtilityClass
public class ObjectUtils {
    
    public boolean containsCircularReferences(Object object) {
        boolean noDuplicates = distinctReferencesOnly(object, identitySet());
        return !noDuplicates;
    }

    public boolean isJvmClass(Class<?> type) {
        return type.getPackage().getName().startsWith("java.");
    }

    public boolean equalsMethodIsOverridden(Object object) {
        Method equals;
        try {
            equals = object.getClass().getMethod("equals", Object.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
        return object.getClass() != equals.getDeclaringClass();
    }

    private boolean distinctReferencesOnly(Object object, Set<Object> references) {
        boolean isJvmClass = isJvmClass(object.getClass());
        boolean isCollection = Collection.class.isAssignableFrom(object.getClass());
        boolean isMap = Map.class.isAssignableFrom(object.getClass());
        if (isJvmClass && !isCollection && !isMap) {
            return true;
        }
        
        boolean added = references.add(object);
        if (!added) {
            return false;
        }
        if (isCollection) {
            Collection<?> collection = (Collection<?>) object;
            return collection.stream()
                    .allMatch(o -> ObjectUtils.distinctReferencesOnly(o, references));
        } else if (isMap) {
            Map<?, ?> map = (Map<?, ?>) object;
            return Stream.concat(map.keySet().stream(), map.values().stream())
                    .allMatch(k -> ObjectUtils.distinctReferencesOnly(k, references));
        }
        return FieldUtils.getFieldsStream(object.getClass())
                .map(field -> FieldUtils.read(object, field))
                .allMatch(value -> ObjectUtils.distinctReferencesOnly(value, references));
    }
    
    private Set<Object> identitySet() {
        return Collections.newSetFromMap(new IdentityHashMap<>());
    }
}
