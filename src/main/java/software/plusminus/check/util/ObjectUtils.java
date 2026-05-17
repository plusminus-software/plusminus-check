package software.plusminus.check.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@UtilityClass
public class ObjectUtils {

    private static final ConcurrentMap<String, Field> CACHE = new ConcurrentHashMap<>();

    public Field toField(Serializable getter) {
        SerializedLambda lambda = serializedLambda(getter);
        String key = lambda.getImplClass() + "::" + lambda.getImplMethodName();
        return CACHE.computeIfAbsent(key, k -> resolveField(lambda));
    }

    public Set<String> declaredFieldNames(Class<?> clazz) {
        Set<String> names = new HashSet<>();
        Class<?> current = clazz;
        while (current != null && current != Object.class) {
            for (Field field : current.getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers()) && !field.isSynthetic()) {
                    names.add(field.getName());
                }
            }
            current = current.getSuperclass();
        }
        return names;
    }

    @SuppressWarnings("java:S2259")
    @SneakyThrows
    public Field findField(Class<?> clazz, String fieldName) {
        Class<?> current = clazz;
        while (current != null && current != Object.class) {
            try {
                return current.getDeclaredField(fieldName);
            } catch (NoSuchFieldException ignored) {
                current = current.getSuperclass();
            }
        }
        throw new IllegalArgumentException("No field '" + fieldName + "' on " + clazz.getName());
    }

    @SneakyThrows
    public Object readField(Object instance, String fieldName) {
        Field field = findField(instance.getClass(), fieldName);
        field.setAccessible(true);
        return field.get(instance);
    }

    public boolean hasGetter(Class<?> clazz, String fieldName) {
        if (fieldName.isEmpty()) {
            return false;
        }
        String capitalized = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        try {
            clazz.getMethod("get" + capitalized);
            return true;
        } catch (NoSuchMethodException ignored) {
            // fall through
        }
        try {
            clazz.getMethod("is" + capitalized);
            return true;
        } catch (NoSuchMethodException ignored) {
            return false;
        }
    }

    @SneakyThrows
    private SerializedLambda serializedLambda(Serializable getter) {
        Method writeReplace = getter.getClass().getDeclaredMethod("writeReplace");
        writeReplace.setAccessible(true);
        return (SerializedLambda) writeReplace.invoke(getter);
    }

    @SneakyThrows
    private Field resolveField(SerializedLambda lambda) {
        String implMethodName = lambda.getImplMethodName();
        if (implMethodName.startsWith("lambda$")) {
            throw new IllegalArgumentException(
                    "Getter must be a method reference like User::getName, not an inline lambda. "
                            + "Inline lambdas have no field they can be resolved to.");
        }
        Class<?> implClass = Class.forName(lambda.getImplClass().replace('/', '.'));
        String fieldName = stripPrefix(implMethodName);
        try {
            return implClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException(
                    "Cannot resolve field for getter " + implClass.getName() + "::" + implMethodName
                            + " — no field named '" + fieldName + "'", e);
        }
    }

    private String stripPrefix(String methodName) {
        if (methodName.startsWith("get") && methodName.length() > 3) {
            return Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);
        }
        if (methodName.startsWith("is") && methodName.length() > 2) {
            return Character.toLowerCase(methodName.charAt(2)) + methodName.substring(3);
        }
        return methodName;
    }
}
