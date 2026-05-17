package software.plusminus.check.util;

import lombok.experimental.UtilityClass;
import software.plusminus.util.ClassUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@UtilityClass
public class TypeUtil {

    private static final List<Class<?>> NOT_SIMPLE_JAVA_CLASSES = Arrays.asList(
            Iterable.class, Iterator.class, Map.class, Optional.class);

    public boolean isSimpleType(Object object) {
        if (object == null) {
            return true;
        }
        Class<?> type = object.getClass();
        if (ClassUtils.isJavaClass(type)) {
            return !type.isArray()
                    && NOT_SIMPLE_JAVA_CLASSES.stream()
                    .noneMatch(c -> c.isAssignableFrom(type));
        }
        return false;
    }
}
