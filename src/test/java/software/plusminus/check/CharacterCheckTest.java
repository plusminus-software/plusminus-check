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
}
