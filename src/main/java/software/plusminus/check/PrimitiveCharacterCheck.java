package software.plusminus.check;

import java.util.List;
import javax.annotation.Nullable;

public class PrimitiveCharacterCheck extends AbstractCheck<Character> {

    public PrimitiveCharacterCheck(@Nullable Character actual) {
        super(actual);
    }

    public PrimitiveCharacterCheck(@Nullable Character actual, List<String> levels) {
        super(actual, levels);
    }

    @SuppressWarnings("PMD.UselessOverridingMethod")
    public void is(char expected) {
        super.is(expected);
    }

    public void is(String expected) {
        super.isString(expected);
    }
}
