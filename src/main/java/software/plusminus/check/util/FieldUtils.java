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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.stream.Stream;

/**
 * Utility class for Reflection API work with fields.
 *
 * @author Taras Shpek
 */
@UtilityClass
public class FieldUtils {

    public <O> Object read(O object, Field field) {
        return read(object, Object.class, field);
    }

    public <O, V> V read(O object, Class<V> valueType, Field field) {
        makeAccessible(field);
        Object value = getField(field, object);
        if (value == null) {
            return null;
        }
        return valueType.cast(value);
    }

    public Stream<Field> getFieldsStream(Class<?> clazz) {
        Stream<Field> fields = Stream.of(clazz.getDeclaredFields());

        Class superClazz = clazz.getSuperclass();
        if (superClazz != null) {
            fields = Stream.concat(fields, getFieldsStream(superClazz));
        }

        return fields;
    }

    private void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) ||
                !Modifier.isPublic(field.getDeclaringClass().getModifiers()) ||
                Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    private Object getField(Field field, Object target) {
        try {
            return field.get(target);
        }
        catch (IllegalAccessException ex) {
            throw new IllegalStateException("Could not access method or field: " + ex.getMessage());
        }
    }
}