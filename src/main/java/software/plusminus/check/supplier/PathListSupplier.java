package software.plusminus.check.supplier;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Supplier;

@FunctionalInterface
public interface PathListSupplier extends Supplier<List<Path>> {
}
