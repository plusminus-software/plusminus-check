package software.plusminus.check;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static software.plusminus.check.Checks.checkOf;
import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class OrderedCollectionCheckTest {

    @Test
    public void isVarargsSuccess() {
        OrderedCollectionCheck.create(Arrays.asList("a", "b", "c")).is("a", "b", "c");
    }

    @Test
    public void isVarargsFail() {
        assertFail(() -> OrderedCollectionCheck.create(Arrays.asList(1, 2, 3)).is(1, 2, 4),
                "[\n  1,\n  2,\n  3\n]", "[\n  1,\n  2,\n  4\n]");
    }

    @Test
    public void isEmptySuccess() {
        OrderedCollectionCheck.create(Collections.emptyList()).isEmpty();
    }

    @Test
    public void isNotEmptySuccess() {
        OrderedCollectionCheck.create(Arrays.asList(1)).isNotEmpty();
    }

    @Test
    public void hasSizeSuccess() {
        OrderedCollectionCheck.create(Arrays.asList(1, 2)).hasSize(2);
    }

    @Test
    public void containsSuccess() {
        OrderedCollectionCheck.create(Arrays.asList("a", "b", "c")).contains("a");
    }

    @Test
    public void atSuccess() {
        List<Integer> list = Arrays.asList(0, 1, -2);
        checkOf(() -> list)
                .at(0).is(c -> c.isZero())
                .at(1).is(c -> c.isPositive())
                .at(2).is(c -> c.isNegative())
                .hasSize(3);
    }

    @Test
    public void atFail() {
        List<String> list = Arrays.asList("a", "b");
        assertFail(() -> checkOf(() -> list).at(0).is("b"),
                "[0] ", "a", "b");
    }

    @Test
    public void outOfBounds() {
        List<String> list = Arrays.asList("a", "b");
        assertFail(() -> checkOf(() -> list).at(5).is("x"),
                "size is 2", "has element at index 5");
    }
}
