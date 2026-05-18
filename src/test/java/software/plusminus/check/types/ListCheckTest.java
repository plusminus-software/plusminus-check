package software.plusminus.check.types;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static software.plusminus.check.Checks.check;
import static software.plusminus.check.Checks.checkOf;
import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class ListCheckTest {

    @Test
    public void isOk() {
        check(Arrays.asList("a", "b", "c")).is("a", "b", "c");
    }

    @Test
    public void isFail() {
        assertFail(() -> check(Arrays.asList(1, 2, 3)).is(1, 2, 4),
                "[\n  1,\n  2,\n  3\n]", "[\n  1,\n  2,\n  4\n]");
    }

    @Test
    public void at() {
        List<Integer> list = Arrays.asList(0, 1, -2);
        checkOf(() -> list)
                .at(0).is(c -> c.isZero())
                .at(1).is(c -> c.isPositive())
                .at(2).is(c -> c.isNegative())
                .hasSize(3);
    }
}
