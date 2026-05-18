package software.plusminus.check.supplier;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@FunctionalInterface
public interface MapListSupplier<K, V> extends Supplier<List<Map<K, V>>> {
}
