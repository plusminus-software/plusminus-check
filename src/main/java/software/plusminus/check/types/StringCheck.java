package software.plusminus.check.types;

import software.plusminus.check.util.JsonUtil;
import software.plusminus.check.util.StringUtil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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

    public StringCheck contains(String... expectedSubstrings) {
        isNotNull();
        List<String> missing = Stream.of(expectedSubstrings)
                .filter(substring -> !actual().contains(substring))
                .collect(Collectors.toList());
        if (!missing.isEmpty()) {
            fail("does not contain " + StringUtil.toString(missing), "contains all substrings");
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
