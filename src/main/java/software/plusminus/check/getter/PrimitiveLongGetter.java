package software.plusminus.check.getter;

import java.io.Serializable;

@FunctionalInterface
public interface PrimitiveLongGetter<T> extends Serializable {

    long apply(T t);
}
