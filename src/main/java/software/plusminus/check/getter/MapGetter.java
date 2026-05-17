package software.plusminus.check.getter;

import java.util.Map;

@FunctionalInterface
public interface MapGetter<T, K, V> extends AbstractGetter<T, Map<K, V>> {
}
