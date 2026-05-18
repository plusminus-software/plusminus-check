package software.plusminus.check.supplier;

import java.util.List;
import java.util.function.Supplier;

@FunctionalInterface
public interface EnumListSupplier<E extends Enum<E>> extends Supplier<List<E>> {
}
