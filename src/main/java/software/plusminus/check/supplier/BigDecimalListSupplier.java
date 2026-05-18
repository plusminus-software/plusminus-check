package software.plusminus.check.supplier;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Supplier;

@FunctionalInterface
public interface BigDecimalListSupplier extends Supplier<List<BigDecimal>> {
}
