package software.plusminus.check.supplier;

import java.util.List;
import java.util.function.Supplier;

@FunctionalInterface
public interface ListListSupplier<E> extends Supplier<List<List<E>>> {
}
