package software.plusminus.check.supplier;

import java.util.Collection;
import java.util.function.Supplier;

@FunctionalInterface
public interface EnumCollectionSupplier<E extends Enum<E>> extends Supplier<Collection<E>> {
}
