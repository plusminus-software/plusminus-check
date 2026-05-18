package software.plusminus.check.supplier;

import java.math.BigInteger;
import java.util.Collection;
import java.util.function.Supplier;

@FunctionalInterface
public interface BigIntegerCollectionSupplier extends Supplier<Collection<BigInteger>> {
}
