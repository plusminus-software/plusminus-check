package software.plusminus.check.getter;

import java.io.Serializable;

@FunctionalInterface
public interface PrimitiveByteGetter<T> extends Serializable {

    byte apply(T t);
}
