package software.plusminus.check.getter;

import java.io.Serializable;
import java.util.function.Function;

@FunctionalInterface
public interface AbstractGetter<T, R> extends Function<T, R>, Serializable {
}
