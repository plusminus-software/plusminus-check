package software.plusminus.check.getter;

import java.io.Serializable;

@FunctionalInterface
public interface PrimitiveFloatGetter<T> extends Serializable {

    float apply(T t);
}
