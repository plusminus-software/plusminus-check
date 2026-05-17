package software.plusminus.check.getter;

import java.util.Optional;

@FunctionalInterface
public interface OptionalGetter<T, O> extends AbstractGetter<T, Optional<O>> {
}
