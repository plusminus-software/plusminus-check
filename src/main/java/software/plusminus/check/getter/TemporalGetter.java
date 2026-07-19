package software.plusminus.check.getter;


import java.time.temporal.Temporal;

@FunctionalInterface
public interface TemporalGetter<T, X extends Temporal> extends AbstractGetter<T, X> {
}
