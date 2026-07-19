package software.plusminus.check.types;

import java.nio.file.Path;
import java.util.List;
import javax.annotation.Nullable;

public class PathCheck extends AbstractCheck<Path> {

    public PathCheck(@Nullable Path actual) {
        super(actual);
    }

    public PathCheck(@Nullable Path actual, List<String> levels) {
        super(actual, levels);
    }

    @Override
    public void is(Path expected) {
        if (checkNull(expected)) {
            return;
        }
        if (!actual().equals(expected)) {
            fail(actual().toString(), expected.toString());
        }
    }

    public void is(String expected) {
        if (checkNull(expected)) {
            return;
        }
        String actualString = actual().toString();
        if (!normalize(actualString).equals(normalize(expected))) {
            fail(actualString, expected);
        }
    }

    public void isAbsolute() {
        isNotNull();
        if (!actual().isAbsolute()) {
            fail(actual().toString(), "absolute path");
        }
    }

    public void isRelative() {
        isNotNull();
        if (actual().isAbsolute()) {
            fail(actual().toString(), "relative path");
        }
    }

    private String normalize(String path) {
        return path.replace('\\', '/');
    }
}
