package software.plusminus.check.supplier;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

@FunctionalInterface
public interface CollectionListSupplier<E> extends Supplier<List<Collection<E>>> {
}
