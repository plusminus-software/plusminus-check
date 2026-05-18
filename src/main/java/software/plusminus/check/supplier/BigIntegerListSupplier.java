package software.plusminus.check.supplier;

import java.math.BigInteger;
import java.util.List;
import java.util.function.Supplier;

@FunctionalInterface
public interface BigIntegerListSupplier extends Supplier<List<BigInteger>> {
}
