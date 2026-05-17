package software.plusminus.check;

import org.junit.Test;

import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class CharacterCheckTest {

    @Test
    public void isCharSuccess() {
        new CharacterCheck('a').is('a');
    }

    @Test
    public void isCharFail() {
        assertFail(() -> new CharacterCheck('a').is('b'), "a", "b");
    }

    @Test
    public void isNullSuccess() {
        new CharacterCheck(null).isNull();
    }

    @Test
    public void isNullFail() {
        assertFail(() -> new CharacterCheck('a').isNull(), "a", "null");
    }

    @Test
    public void isNotNullSuccess() {
        new CharacterCheck('a').isNotNull();
    }

    @Test
    public void isNotNullFail() {
        assertFail(() -> new CharacterCheck(null).isNotNull(), "null", "not null");
    }
}
