package software.plusminus.check;

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
    public void isSame(String expected) {
        super.isSame(expected);
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
