package software.plusminus.check.types;

import software.plusminus.check.util.JsonUtil;
import software.plusminus.util.ResourceUtils;

import java.util.List;
import java.util.Objects;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

public class StringCheck extends AbstractCheck<String> {

    private boolean ignoreLineEndings;

    public StringCheck(@Nullable String actual) {
        super(actual);
    }

    public StringCheck(@Nullable String actual, List<String> levels) {
        super(actual, levels);
    }

    @Override
    public void is(String expected) {
        if (checkNull(expected)) {
            return;
        }
        if (matchesText(expected) || matchesResource(expected)) {
            return;
        }
        fail(actual(), expected);
    }

    @Override
    public void isNull() {
        super.isNull();
    }

    @Override
    public StringCheck isNotNull() {
        super.isNotNull();
        return this;
    }

    @Override
    public StringCheck isNot(String unexpected) {
        super.isNot(unexpected);
        return this;
    }

    @Override
    @SuppressWarnings("PMD.UselessOverridingMethod")
    public void isSame(String expected) {
        super.isSame(expected);
    }

    /**
     * Compares {@code \r\n}, {@code \r} and {@code \n} as equal, on both sides.
     * Makes assertions on generated text portable between Windows and Unix.
     *
     * @return this check, for chaining
     */
    @CheckReturnValue
    public StringCheck ignoringLineEndings() {
        this.ignoreLineEndings = true;
        return this;
    }

    public void isEmpty() {
        isNotNull();
        if (!actual().isEmpty()) {
            fail(actual(), "empty");
        }
    }

    public StringCheck isNotEmpty() {
        isNotNull();
        if (actual().isEmpty()) {
            fail("empty", "not empty");
        }
        return this;
    }

    public StringCheck isBlank() {
        isNotNull();
        if (!actual().trim().isEmpty()) {
            fail(actual(), "blank");
        }
        return this;
    }

    public StringCheck isNotBlank() {
        isNotNull();
        if (actual().trim().isEmpty()) {
            fail(actual(), "not blank");
        }
        return this;
    }

    public StringCheck hasLength(int expectedLength) {
        isNotNull();
        if (actual().length() != expectedLength) {
            fail("length is " + actual().length(), "length is " + expectedLength);
        }
        return this;
    }

    public StringCheck contains(String expectedSubstring) {
        isNotNull();
        if (!actual().contains(expectedSubstring)) {
            fail("does not contain " + expectedSubstring, "contains " + expectedSubstring);
        }
        return this;
    }

    public StringCheck doesNotContain(String unexpectedSubstring) {
        isNotNull();
        if (actual().contains(unexpectedSubstring)) {
            fail("contains " + unexpectedSubstring, "does not contain " + unexpectedSubstring);
        }
        return this;
    }

    public StringCheck startsWith(String expectedPrefix) {
        isNotNull();
        if (!actual().startsWith(expectedPrefix)) {
            fail("does not start with " + expectedPrefix, "starts with " + expectedPrefix);
        }
        return this;
    }

    public StringCheck endsWith(String expectedSuffix) {
        isNotNull();
        if (!actual().endsWith(expectedSuffix)) {
            fail("does not end with " + expectedSuffix, "ends with " + expectedSuffix);
        }
        return this;
    }

    /**
     * Asserts the whole string matches the regular expression,
     * as in {@link String#matches(String)}.
     *
     * @param regex regular expression the string must match
     * @return this check, for chaining
     */
    public StringCheck matches(String regex) {
        isNotNull();
        if (!actual().matches(regex)) {
            fail("does not match " + regex, "matches " + regex);
        }
        return this;
    }

    public JsonCheck isJson() {
        isNotNull();
        String actual = actual();
        if (!JsonUtil.isJson(actual)) {
            fail("not json", "json");
        }
        return new JsonCheck(actual, levels());
    }

    @Override
    @Nullable
    protected String actual() {
        return normalize(super.actual());
    }

    private boolean matchesText(String expected) {
        return Objects.equals(actual(), normalize(expected));
    }

    private boolean matchesResource(String expected) {
        if (!ResourceUtils.isResource(expected)) {
            return false;
        }
        String expectedContent = normalize(ResourceUtils.toString(expected));
        return JsonUtil.pretty(actual()).equals(JsonUtil.pretty(expectedContent));
    }

    @Nullable
    private String normalize(@Nullable String value) {
        if (!ignoreLineEndings || value == null) {
            return value;
        }
        return value.replace("\r\n", "\n").replace('\r', '\n');
    }
}
