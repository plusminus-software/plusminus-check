package software.plusminus.check.getter;


import java.nio.file.Path;

@FunctionalInterface
public interface PathGetter<T> extends AbstractGetter<T, Path> {
}
