package software.plusminus.check.supplier;

import java.util.Collection;
import java.util.function.Supplier;

@FunctionalInterface
public interface LongCollectionSupplier extends Supplier<Collection<Long>> {
}
