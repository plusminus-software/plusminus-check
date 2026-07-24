package software.plusminus.check.types;

import org.junit.Test;

import static software.plusminus.check.Checks.check;
import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class ArrayCheckTest {

    @Test
    public void isOk() {
        Integer[] array = {1, 2, 3};
        check(array).is(1, 2, 3);
    }

    @Test
    public void isFail() {
        Integer[] array = {1, 2, 3};
        assertFail(() -> check(array).is(1, 2, 4), "[2] ", 3, 4);
    }

    @Test
    public void at() {
        String[] array = {"a", "b", "c"};
        check(array).at(1).is(c -> c.is("b"));
    }
}
