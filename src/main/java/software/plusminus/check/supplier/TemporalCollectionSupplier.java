package software.plusminus.check.supplier;

import java.time.temporal.Temporal;
import java.util.Collection;
import java.util.function.Supplier;

@FunctionalInterface
public interface TemporalCollectionSupplier<T extends Temporal> extends Supplier<Collection<T>> {
}
