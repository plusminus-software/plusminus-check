package software.plusminus.check.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

public class ExceptionCheck extends AbstractCheck<Throwable> {

    private static final String NO_EXCEPTION = "no exception";
    private static final String NO_CAUSE = "no cause";
    private static final String CAUSE = ".cause";

    public ExceptionCheck(@Nullable Throwable actual) {
        super(actual);
    }

    public ExceptionCheck(@Nullable Throwable actual, List<String> levels) {
        super(actual, levels);
    }

    public ExceptionCheck is(Class<? extends Throwable> expected) {
        if (actual() == null) {
            fail(NO_EXCEPTION, expected);
        }
        if (actual().getClass() != expected) {
            fail(actual().getClass(), expected);
        }
        return this;
    }

    public ExceptionCheck isInstanceOf(Class<? extends Throwable> expected) {
        if (actual() == null) {
            fail(NO_EXCEPTION, expected);
        }
        if (!expected.isInstance(actual())) {
            fail(actual().getClass(), expected);
        }
        return this;
    }

    public ExceptionCheck hasMessage(@Nullable String expected) {
        if (actual() == null) {
            fail(NO_EXCEPTION, expected);
        }
        String actualMessage = actual().getMessage();
        if (!Objects.equals(actualMessage, expected)) {
            fail(actualMessage, expected);
        }
        return this;
    }

    public ExceptionCheck hasMessageContaining(String expected) {
        if (actual() == null) {
            fail(NO_EXCEPTION, expected);
        }
        String actualMessage = actual().getMessage();
        if (actualMessage == null || !actualMessage.contains(expected)) {
            fail(actualMessage, "message containing " + expected);
        }
        return this;
    }

    /**
     * Asserts the whole message matches the regular expression,
     * as in {@link String#matches(String)}.
     *
     * @param regex regular expression the message must match
     * @return this check, for chaining
     */
    public ExceptionCheck hasMessageMatching(String regex) {
        if (actual() == null) {
            fail(NO_EXCEPTION, regex);
        }
        String actualMessage = actual().getMessage();
        if (actualMessage == null || !actualMessage.matches(regex)) {
            fail(actualMessage, "message matching " + regex);
        }
        return this;
    }

    public ExceptionCheck hasCause(Class<? extends Throwable> expected) {
        if (actual() == null) {
            fail(NO_EXCEPTION, expected);
        }
        Throwable cause = actual().getCause();
        if (cause == null) {
            fail(NO_CAUSE, expected);
        }
        if (cause.getClass() != expected) {
            fail(cause.getClass(), expected);
        }
        return this;
    }

    public ExceptionCheck hasNoCause() {
        if (actual() == null) {
            fail(NO_EXCEPTION, NO_CAUSE);
        }
        Throwable cause = actual().getCause();
        if (cause != null) {
            fail(cause.getClass(), NO_CAUSE);
        }
        return this;
    }

    /**
     * Descends into the cause of the exception. Fails if there is no cause.
     *
     * @return a check of the cause
     */
    @CheckReturnValue
    public ExceptionCheck cause() {
        if (actual() == null) {
            fail(NO_EXCEPTION, "an exception with a cause");
        }
        Throwable cause = actual().getCause();
        if (cause == null) {
            fail(NO_CAUSE, "a cause");
        }
        return new ExceptionCheck(cause, sublevels(levels()));
    }

    /**
     * Descends into the deepest cause of the exception,
     * or into the exception itself when it has no cause.
     *
     * @return a check of the root cause
     */
    @CheckReturnValue
    public ExceptionCheck rootCause() {
        if (actual() == null) {
            fail(NO_EXCEPTION, "an exception");
        }
        Throwable root = actual();
        List<String> rootLevels = levels();
        while (root.getCause() != null && root.getCause() != root) {
            root = root.getCause();
            rootLevels = sublevels(rootLevels);
        }
        return new ExceptionCheck(root, rootLevels);
    }

    public void isNotThrown() {
        if (actual() != null) {
            fail(actual().getClass(), NO_EXCEPTION);
        }
    }

    @SuppressWarnings("checkstyle:HiddenField")
    private List<String> sublevels(List<String> levels) {
        List<String> sublevels = new ArrayList<>(levels);
        sublevels.add(CAUSE);
        return sublevels;
    }
}
