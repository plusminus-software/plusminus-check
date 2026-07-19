package software.plusminus.check.types;

import org.junit.Test;

import static software.plusminus.check.Checks.check;
import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class CharacterCheckTest {

    @Test
    public void isCharOk() {
        check('a').is('a');
    }

    @Test
    public void isCharFail() {
        assertFail(() -> check('a').is('b'), "a", "b");
    }

    @Test
    public void isStringOk() {
        check('a').is("a");
    }

    @Test
    public void isStringFail() {
        assertFail(() -> check('a').is("ab"), "a", "ab");
    }

    @Test
    public void isStringDifferentCharFail() {
        assertFail(() -> check('a').is("b"), "a", "b");
    }
}
