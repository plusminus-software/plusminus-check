package software.plusminus.check.supplier;

import java.time.temporal.Temporal;
import java.util.List;
import java.util.function.Supplier;

@FunctionalInterface
public interface TemporalListSupplier<T extends Temporal> extends Supplier<List<T>> {
}
