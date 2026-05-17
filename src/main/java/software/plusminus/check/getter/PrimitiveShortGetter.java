package software.plusminus.check.getter;

import java.io.Serializable;

@FunctionalInterface
public interface PrimitiveShortGetter<T> extends Serializable {

    short apply(T t);
}
