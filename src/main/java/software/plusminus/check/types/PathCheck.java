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

    @Override
    public PathCheck isNot(Path unexpected) {
        super.isNot(unexpected);
        return this;
    }

    @Override
    public PathCheck isNotNull() {
        super.isNotNull();
        return this;
    }

    public PathCheck isAbsolute() {
        isNotNull();
        if (!actual().isAbsolute()) {
            fail(actual().toString(), "absolute path");
        }
        return this;
    }

    public PathCheck isRelative() {
        isNotNull();
        if (actual().isAbsolute()) {
            fail(actual().toString(), "relative path");
        }
        return this;
    }

    public PathCheck exists() {
        isNotNull();
        if (!Files.exists(actual())) {
            fail(actual().toString(), "existing path");
        }
        return this;
    }

    public PathCheck doesNotExist() {
        isNotNull();
        if (Files.exists(actual())) {
            fail(actual().toString(), "non-existing path");
        }
        return this;
    }

    public PathCheck isFile() {
        exists();
        if (!Files.isRegularFile(actual())) {
            fail(actual().toString(), "regular file");
        }
        return this;
    }

    public PathCheck isDirectory() {
        exists();
        if (!Files.isDirectory(actual())) {
            fail(actual().toString(), "directory");
        }
        return this;
    }

    public PathCheck startsWith(Path expected) {
        if (checkNull(expected)) {
            return this;
        }
        if (!actual().startsWith(expected)) {
            fail(actual().toString(), "starts with " + expected);
        }
        return this;
    }

    public PathCheck startsWith(String expected) {
        if (checkNull(expected)) {
            return this;
        }
        if (!actual().startsWith(toPath(expected))) {
            fail(actual().toString(), "starts with " + expected);
        }
        return this;
    }

    public PathCheck endsWith(Path expected) {
        if (checkNull(expected)) {
            return this;
        }
        if (!actual().endsWith(expected)) {
            fail(actual().toString(), "ends with " + expected);
        }
        return this;
    }

    public PathCheck endsWith(String expected) {
        if (checkNull(expected)) {
            return this;
        }
        if (!actual().endsWith(toPath(expected))) {
            fail(actual().toString(), "ends with " + expected);
        }
        return this;
    }

    /**
     * Checks the content of the file the path points to.
     * The expected value may be either the content itself
     * or a classpath resource reference (e.g. {@code "/ExpectedFile.java"})
     * whose content is used as the expected value.
     * Line endings are compared as equal, so the assertion holds
     * on both Windows and Unix.
     *
     * @param expected expected content or a classpath resource reference
     * @return this check, for chaining
     */
    public PathCheck hasContent(String expected) {
        isFile();
        new StringCheck(readContent(), levels())
                .ignoringLineEndings()
                .is(expected);
        return this;
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
