package software.plusminus.check.getter;

import java.io.Serializable;

@FunctionalInterface
public interface PrimitiveBooleanGetter<T> extends Serializable {

    boolean apply(T t);
}
