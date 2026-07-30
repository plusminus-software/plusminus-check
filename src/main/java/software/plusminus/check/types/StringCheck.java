package software.plusminus.check.types;

import software.plusminus.check.util.JsonUtil;

import java.util.List;
import javax.annotation.Nullable;

public class StringCheck extends AbstractCheck<String> {

    public StringCheck(@Nullable String actual) {
        super(actual);
    }

    public StringCheck(@Nullable String actual, List<String> levels) {
        super(actual, levels);
    }

    @Override
    public void is(String expected) {
        check(expected, this::checkNull, this::checkEquals, this::checkResource);
    }

    @Override
    public void isNull() {
        super.isNull();
    }

    @Override
    public void isNotNull() {
        super.isNotNull();
    }

    @Override
    @SuppressWarnings("PMD.UselessOverridingMethod")
    public void isSame(String expected) {
        super.isSame(expected);
    }

    public StringCheck contains(String expectedSubstring) {
        isNotNull();
        if (!actual().contains(expectedSubstring)) {
            fail("does not contain " + expectedSubstring, "contains " + expectedSubstring);
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

    public JsonCheck isJson() {
        isNotNull();
        String actual = actual();
        if (!JsonUtil.isJson(actual)) {
            fail("not json", "json");
        }
        return new JsonCheck(actual, levels());
    }
}
