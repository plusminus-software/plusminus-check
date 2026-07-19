package software.plusminus.check.types;

import java.util.List;
import javax.annotation.Nullable;

public class CharacterCheck extends AbstractCheck<Character> {

    public CharacterCheck(@Nullable Character actual) {
        super(actual);
    }

    public CharacterCheck(@Nullable Character actual, List<String> levels) {
        super(actual, levels);
    }

    @SuppressWarnings("PMD.UselessOverridingMethod")
    public void is(char expected) {
        super.is(expected);
    }

    public void is(String expected) {
        isNotNull();
        Character actual = actual();
        if (expected == null || expected.length() != 1 || expected.charAt(0) != actual) {
            fail(String.valueOf(actual), expected);
        }
    }
}
