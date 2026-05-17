package software.plusminus.check;

import software.plusminus.check.util.JsonUtil;
import software.plusminus.check.util.StringUtil;
import software.plusminus.check.util.TypeUtil;
import software.plusminus.util.ResourceUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public abstract class AbstractCheck<T> implements Check<T> {

    private static final String MESSAGE_PATTERN = "%sexpected:<%s> but was:<%s>";
    public static final String EMPTY = "empty";
    public static final String NOT_EMPTY = "not empty";

    @Nullable
    private T actual;
    private List<String> levels;

    protected AbstractCheck(@Nullable T actual) {
        this(actual, Collections.emptyList());
    }

    protected AbstractCheck(@Nullable T actual, List<String> levels) {
        this.actual = actual;
        this.levels = levels;
    }

    public void is(T expected) {
        if (TypeUtil.isSimpleType(actual)) {
            check(expected, this::checkNull, this::checkType, this::checkEquals);
            return;
        }
        check(expected, this::checkNull, this::checkType, this::checkEmpty, this::checkEquals,
                this::checkJson);
    }

    /**
     * Not supported on checks.
     * @deprecated equals() and hashCode() must not be called on checks
     */
    @Override
    @Deprecated
    public boolean equals(Object object) {
        throw new UnsupportedOperationException("Method equals() is not supported. Did you mean is()?");
    }

    /**
     * Not supported on checks.
     * @deprecated equals() and hashCode() must not be called on checks
     */
    @Override
    @Deprecated
    public int hashCode() {
        throw new UnsupportedOperationException("Method hashCode() is not supported.");
    }

    protected void isString(String expected) {
        if (TypeUtil.isSimpleType(actual)) {
            check(expected, this::checkNull, this::checkType, this::checkEquals);
            return;
        }
        check(expected, this::checkNull, this::checkType, this::checkEmpty, this::checkEquals,
                this::checkJson, this::checkResource);
    }

    protected void isNull() {
        if (actual != null) {
            fail(actual, null);
        }
    }

    protected void isNotNull() {
        if (actual == null) {
            fail(actual, "not null");
        }
    }

    protected void isSame(T expected) {
        if (actual != expected) {
            fail("same as " + StringUtil.toString(expected));
        }
    }

    protected T actual() {
        return actual;
    }

    protected List<String> levels() {
        return levels;
    }

    @SafeVarargs
    protected final void check(Object expected, Predicate<Object>... checks) {
        for (Predicate<Object> check : checks) {
            boolean completed = check.test(expected);
            if (completed) {
                return;
            }
        }
        fail(StringUtil.toString(actual), StringUtil.toString(expected));
    }

    protected boolean checkNull(Object expected) {
        if (actual == null && expected == null) {
            return true;
        }
        if (actual == null || expected == null) {
            fail(expected);
        }
        return false;
    }

    protected boolean checkEquals(Object expected) {
        return Objects.equals(actual, expected);
    }

    protected boolean checkType(Object expected) {
        return checkType(expected == null ? null : expected.getClass());
    }

    protected boolean checkType(Class<?> expectedType) {
        if (actual == null) {
            fail(null, "not null");
        }
        Class<?> actualType = actual.getClass();
        if (actualType != expectedType) {
            fail(actualType, expectedType);
        }
        return false;
    }

    protected boolean checkEmpty(Object expected) {
        if (actual instanceof Optional && expected instanceof Optional) {
            return checkEmptyOptional(expected);
        }
        if (actual instanceof Collection && expected instanceof Collection) {
            return checkEmptyCollection(expected);
        }
        return false;
    }

    protected boolean checkJson(Object expected) {
        String actualJson = StringUtil.toString(actual);
        String expectedJson = StringUtil.toString(expected);
        return actualJson.equals(expectedJson);
    }

    protected boolean checkResource(Object expected) {
        if (!(expected instanceof String)) {
            return false;
        }
        String expectedString = expected.toString();
        if (!ResourceUtils.isResource(expectedString)) {
            return false;
        }
        String actualString = JsonUtil.pretty(StringUtil.toString(actual));
        expectedString = JsonUtil.pretty(ResourceUtils.toString(expectedString));
        return actualString.equals(expectedString);
    }

    protected void fail(@Nullable Object expected) {
        fail(actual, expected);
    }

    @SuppressWarnings("checkstyle:HiddenField")
    protected void fail(@Nullable Object actual, @Nullable Object expected) {
        String message = String.format(MESSAGE_PATTERN,
                String.join(" -> ", levels) + (levels.isEmpty() ? "" : " "),
                StringUtil.toString(expected, true),
                StringUtil.toString(actual));
        throw new AssertionError(message);
    }

    private boolean checkEmptyOptional(Object expected) {
        Optional<?> actualOptional = (Optional<?>) actual;
        Optional<?> expectedOptional = (Optional<?>) expected;
        if (!actualOptional.isPresent() && !expectedOptional.isPresent()) {
            return true;
        }
        if (!actualOptional.isPresent()) {
            fail(EMPTY, NOT_EMPTY);
        }
        if (!expectedOptional.isPresent()) {
            fail(NOT_EMPTY, EMPTY);
        }
        return false;
    }

    private boolean checkEmptyCollection(Object expected) {
        Collection<?> actualCollection = (Collection<?>) actual;
        Collection<?> expectedCollection = (Collection<?>) expected;
        if (actualCollection.isEmpty() && expectedCollection.isEmpty()) {
            return true;
        }
        if (actualCollection.isEmpty()) {
            fail(EMPTY, NOT_EMPTY);
        }
        if (expectedCollection.isEmpty()) {
            fail(NOT_EMPTY, EMPTY);
        }
        return false;
    }
}
