package software.plusminus.check.getter;

import java.io.Serializable;

@FunctionalInterface
public interface PrimitiveDoubleGetter<T> extends Serializable {

    double apply(T t);
}
