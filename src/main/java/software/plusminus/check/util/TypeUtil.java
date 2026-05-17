package software.plusminus.check.util;

import lombok.experimental.UtilityClass;
import software.plusminus.util.ClassUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

@UtilityClass
public class TypeUtil {

    public boolean isSimpleType(Object object) {
        if (object == null) {
            return true;
        }
        Class<?> type = object.getClass();
        if (ClassUtils.isJavaClass(type)) {
            return !type.isArray()
                    && !Iterable.class.isAssignableFrom(type)
                    && !Iterator.class.isAssignableFrom(type)
                    && !Map.class.isAssignableFrom(type)
                    && !Optional.class.isAssignableFrom(type);
        }
        return false;
    }
}
