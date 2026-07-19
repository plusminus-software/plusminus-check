package software.plusminus.check.supplier;

import java.nio.file.Path;
import java.util.Collection;
import java.util.function.Supplier;

@FunctionalInterface
public interface PathCollectionSupplier extends Supplier<Collection<Path>> {
}
