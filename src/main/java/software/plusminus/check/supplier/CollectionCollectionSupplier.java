package software.plusminus.check.supplier;

import java.util.Collection;
import java.util.function.Supplier;

@FunctionalInterface
public interface CollectionCollectionSupplier<E> extends Supplier<Collection<Collection<E>>> {
}
