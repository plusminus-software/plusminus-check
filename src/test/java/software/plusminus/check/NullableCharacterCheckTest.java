package software.plusminus.check;

import org.junit.Test;

import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class NullableCharacterCheckTest {

    @Test
    public void isCharSuccess() {
        new NullableCharacterCheck('a').is('a');
    }

    @Test
    public void isCharFail() {
        assertFail(() -> new NullableCharacterCheck('a').is('b'), "a", "b");
    }

    @Test
    public void isNullSuccess() {
        new NullableCharacterCheck(null).isNull();
    }

    @Test
    public void isNullFail() {
        assertFail(() -> new NullableCharacterCheck('a').isNull(), "a", "null");
    }

    @Test
    public void isNotNullSuccess() {
        new NullableCharacterCheck('a').isNotNull();
    }

    @Test
    public void isNotNullFail() {
        assertFail(() -> new NullableCharacterCheck(null).isNotNull(), "null", "not null");
    }
}
