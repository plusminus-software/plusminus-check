package software.plusminus.check.util;

import lombok.experimental.UtilityClass;
import software.plusminus.util.ResourceUtils;

import java.util.Collection;
import java.util.Optional;
import javax.annotation.Nullable;

@UtilityClass
public class StringUtil {

    public String toString(@Nullable Object object) {
        return toString(object, false);
    }

    public String toString(@Nullable Object object, boolean checkResource) {
        if (object == null) {
            return "null";
        }
        String string = tryStringify(object, checkResource);
        if (string != null) {
            return string;
        }
        if (TypeUtil.isSimpleType(object)) {
            return object.toString();
        }
        return JsonUtil.pretty(JsonUtil.toJson(object));
    }

    @Nullable
    private String tryStringify(Object object, boolean checkResource) {
        if (object instanceof CharSequence) {
            String string = object.toString();
            if (checkResource && ResourceUtils.isResource(string)) {
                return JsonUtil.pretty(ResourceUtils.toString(string));
            }
            return string;
        }
        if (object instanceof Class) {
            return "type " + ((Class<?>) object).getName();
        }
        if (object instanceof Enum) {
            Enum<?> enumObject = (Enum<?>) object;
            return enumObject.name();
        }
        if (object instanceof Optional) {
            Optional<?> optional = (Optional<?>) object;
            if (!optional.isPresent()) {
                return "empty";
            }
        }
        if (object instanceof Collection) {
            Collection<?> collection = (Collection<?>) object;
            if (collection.isEmpty()) {
                return "empty";
            }
        }
        return null;
    }
}
