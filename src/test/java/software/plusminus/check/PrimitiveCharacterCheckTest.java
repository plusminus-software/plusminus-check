package software.plusminus.check;

import org.junit.Test;

import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class PrimitiveCharacterCheckTest {

    @Test
    public void isCharSuccess() {
        new PrimitiveCharacterCheck('a').is('a');
    }

    @Test
    public void isCharFail() {
        assertFail(() -> new PrimitiveCharacterCheck('a').is('b'), "a", "b");
    }
}
