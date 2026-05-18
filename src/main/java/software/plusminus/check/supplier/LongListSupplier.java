package software.plusminus.check.supplier;

import java.util.List;
import java.util.function.Supplier;

@FunctionalInterface
public interface LongListSupplier extends Supplier<List<Long>> {
}
