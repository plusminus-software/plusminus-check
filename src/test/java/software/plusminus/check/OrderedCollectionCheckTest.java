package software.plusminus.check;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

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
}
