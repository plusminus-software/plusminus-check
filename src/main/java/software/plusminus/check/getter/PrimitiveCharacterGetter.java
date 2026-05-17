package software.plusminus.check.getter;

import java.io.Serializable;

@FunctionalInterface
public interface PrimitiveCharacterGetter<T> extends Serializable {

    char apply(T t);
}
