package software.plusminus.check.supplier;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@FunctionalInterface
public interface OptionalListSupplier<E> extends Supplier<List<Optional<E>>> {
}
