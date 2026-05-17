package software.plusminus.check.getter;


import java.time.temporal.Temporal;

@FunctionalInterface
public interface TemporalGetter<T extends Temporal> extends AbstractGetter<T, Temporal> {
}
