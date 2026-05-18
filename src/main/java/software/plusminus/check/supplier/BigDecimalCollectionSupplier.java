package software.plusminus.check.supplier;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.function.Supplier;

@FunctionalInterface
public interface BigDecimalCollectionSupplier extends Supplier<Collection<BigDecimal>> {
}
