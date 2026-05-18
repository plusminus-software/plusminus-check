package software.plusminus.check.supplier;

import java.util.List;
import java.util.function.Supplier;

@FunctionalInterface
public interface StringListSupplier extends Supplier<List<String>> {
}
