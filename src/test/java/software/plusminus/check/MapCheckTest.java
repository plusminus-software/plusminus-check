package software.plusminus.check;

import org.junit.Test;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class MapCheckTest {

    @Test
    public void isOnePairSuccess() {
        new MapCheck<>(Collections.singletonMap("a", "1")).is("a", "1");
    }

    @Test
    public void isTwoPairsSuccess() {
        new MapCheck<>(map("a", "1", "b", "2")).is("a", "1", "b", "2");
    }

    @Test
    public void isFourPairsSuccess() {
        new MapCheck<>(map("a", "1", "b", "2", "c", "3", "d", "4"))
                .is("a", "1", "b", "2", "c", "3", "d", "4");
    }

    @Test
    public void isOnePairFail() {
        assertFail(() -> new MapCheck<>(Collections.singletonMap("a", "1")).is("a", "2"),
                "{\n  \"a\": \"1\"\n}", "{\n  \"a\": \"2\"\n}");
    }

    @Test
    public void isEmptySuccess() {
        new MapCheck<>(Collections.emptyMap()).isEmpty();
    }

    @Test
    public void isEmptyFail() {
        assertFail(() -> new MapCheck<>(Collections.singletonMap("a", "1")).isEmpty(),
                "contains 1 elements", "empty");
    }

    @Test
    public void isNotEmptySuccess() {
        new MapCheck<>(Collections.singletonMap("a", "1")).isNotEmpty();
    }

    @Test
    public void isNotEmptyFail() {
        assertFail(() -> new MapCheck<>(Collections.emptyMap()).isNotEmpty(),
                "empty", "not empty");
    }

    @Test
    public void hasSizeSuccess() {
        new MapCheck<>(map("a", "1", "b", "2")).hasSize(2);
    }

    @Test
    public void hasSizeFail() {
        assertFail(() -> new MapCheck<>(Collections.singletonMap("a", "1")).hasSize(2),
                "size is 1", "size is 2");
    }

    @Test
    public void isNullSuccess() {
        new MapCheck<>(null).isNull();
    }

    @Test
    public void isNotNullFail() {
        assertFail(() -> new MapCheck<>(null).isNotNull(), "null", "not null");
    }

    private static Map<String, String> map(String... pairs) {
        Map<String, String> result = new LinkedHashMap<>();
        for (int i = 0; i < pairs.length; i += 2) {
            result.put(pairs[i], pairs[i + 1]);
        }
        return result;
    }
}
