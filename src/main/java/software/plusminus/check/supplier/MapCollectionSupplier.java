package software.plusminus.check.supplier;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

@FunctionalInterface
public interface MapCollectionSupplier<K, V> extends Supplier<Collection<Map<K, V>>> {
}
