package software.plusminus.check.supplier;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

@FunctionalInterface
public interface OptionalCollectionSupplier<E> extends Supplier<Collection<Optional<E>>> {
}
