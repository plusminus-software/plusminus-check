package software.plusminus.check;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class CollectionCheckTest {

    @Test
    public void isEmptySuccess() {
        CollectionCheck.create(Collections.emptySet()).isEmpty();
    }

    @Test
    public void isEmptyFail() {
        assertFail(() -> CollectionCheck.create(new HashSet<>(Arrays.asList("a"))).isEmpty(),
                "not empty", "empty");
    }

    @Test
    public void isNotEmptySuccess() {
        CollectionCheck.create(new HashSet<>(Arrays.asList("a"))).isNotEmpty();
    }

    @Test
    public void isNotEmptyFail() {
        assertFail(() -> CollectionCheck.create(Collections.emptySet()).isNotEmpty(),
                "empty", "not empty");
    }

    @Test
    public void hasSizeSuccess() {
        CollectionCheck.create(new HashSet<>(Arrays.asList("a", "b"))).hasSize(2);
    }

    @Test
    public void hasSizeFail() {
        assertFail(() -> CollectionCheck.create(new HashSet<>(Arrays.asList("a"))).hasSize(2),
                "size is 1", "size is 2");
    }

    @Test
    public void containsSuccess() {
        CollectionCheck.create(new HashSet<>(Arrays.asList("a", "b", "c"))).contains("a", "b");
    }

    @Test
    public void containsExactlySuccess() {
        CollectionCheck.create(new HashSet<>(Arrays.asList("a", "b"))).containsExactly("a", "b");
    }

    @Test
    public void containsConsumerSuccess() {
        CollectionCheck.create(new HashSet<>(Arrays.asList(1, 2, 3)))
                .contains(c -> c.is(2));
    }

    @Test
    public void isNullSuccess() {
        CollectionCheck.create(null).isNull();
    }

    @Test
    public void isNotNullFail() {
        assertFail(() -> CollectionCheck.create(null).isNotNull(), "null", "not null");
    }
}
