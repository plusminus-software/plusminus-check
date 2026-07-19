package software.plusminus.check.types;

import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;

public class ExceptionCheck extends AbstractCheck<Throwable> {

    private static final String NO_EXCEPTION = "no exception";

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

    public void isNotThrown() {
        if (actual() != null) {
            fail(actual().getClass(), NO_EXCEPTION);
        }
    }
}
