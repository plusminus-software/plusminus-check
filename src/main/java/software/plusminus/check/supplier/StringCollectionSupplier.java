package software.plusminus.check.supplier;

import java.util.Collection;
import java.util.function.Supplier;

@FunctionalInterface
public interface StringCollectionSupplier extends Supplier<Collection<String>> {
}
