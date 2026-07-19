package software.plusminus.check.getter;


import java.io.InputStream;

@FunctionalInterface
public interface InputStreamGetter<T, X extends InputStream> extends AbstractGetter<T, X> {
}
