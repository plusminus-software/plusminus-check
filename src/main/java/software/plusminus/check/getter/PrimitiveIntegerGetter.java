package software.plusminus.check.getter;

import java.io.Serializable;

@FunctionalInterface
public interface PrimitiveIntegerGetter<T> extends Serializable {

    int apply(T t);
}
