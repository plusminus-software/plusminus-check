package software.plusminus.check.types;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
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

    public void exists() {
        isNotNull();
        if (!Files.exists(actual())) {
            fail(actual().toString(), "existing path");
        }
    }

    public void doesNotExist() {
        isNotNull();
        if (Files.exists(actual())) {
            fail(actual().toString(), "non-existing path");
        }
    }

    public void isFile() {
        exists();
        if (!Files.isRegularFile(actual())) {
            fail(actual().toString(), "regular file");
        }
    }

    public void isDirectory() {
        exists();
        if (!Files.isDirectory(actual())) {
            fail(actual().toString(), "directory");
        }
    }

    public void startsWith(Path expected) {
        if (checkNull(expected)) {
            return;
        }
        if (!actual().startsWith(expected)) {
            fail(actual().toString(), "starts with " + expected);
        }
    }

    public void startsWith(String expected) {
        if (checkNull(expected)) {
            return;
        }
        if (!actual().startsWith(toPath(expected))) {
            fail(actual().toString(), "starts with " + expected);
        }
    }

    public void endsWith(Path expected) {
        if (checkNull(expected)) {
            return;
        }
        if (!actual().endsWith(expected)) {
            fail(actual().toString(), "ends with " + expected);
        }
    }

    public void endsWith(String expected) {
        if (checkNull(expected)) {
            return;
        }
        if (!actual().endsWith(toPath(expected))) {
            fail(actual().toString(), "ends with " + expected);
        }
    }

    /**
     * Checks the content of the file the path points to.
     * The expected value may be either the content itself
     * or a classpath resource reference (e.g. {@code "/ExpectedFile.java"})
     * whose content is used as the expected value.
     *
     * @param expected expected content or a classpath resource reference
     */
    public void hasContent(String expected) {
        isFile();
        new StringCheck(readContent(), levels()).is(expected);
    }

    private String readContent() {
        try {
            byte[] bytes = Files.readAllBytes(actual());
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Path toPath(String path) {
        return actual().getFileSystem().getPath(normalize(path));
    }

    private String normalize(String path) {
        return path.replace('\\', '/');
    }
}
