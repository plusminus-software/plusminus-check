package software.plusminus.check.types;

import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static software.plusminus.check.Checks.check;
import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class MapCheckTest {

    @Test
    public void isOnePairOk() {
        check(map("a", "1")).is("a", "1");
    }

    @Test
    public void isTwoPairsOk() {
        check(map("a", "1", "b", "2")).is("a", "1", "b", "2");
    }

    @Test
    public void isFourPairsOk() {
        check(map("a", "1", "b", "2", "c", "3", "d", "4"))
                .is("a", "1", "b", "2", "c", "3", "d", "4");
    }

    @Test
    public void isFail() {
        assertFail(() -> check(map("a", "1")).is("a", "2"),
                "{\n  \"a\": \"1\"\n}", "{\n  \"a\": \"2\"\n}");
    }

    @Test
    public void isEmptyOk() {
        check(map()).isEmpty();
    }

    @Test
    public void isEmptyFail() {
        assertFail(() -> check(map("a", "1")).isEmpty(),
                "contains 1 elements", "empty");
    }

    @Test
    public void isNotEmptyOk() {
        check(map("a", "1")).isNotEmpty();
    }

    @Test
    public void isNotEmptyFail() {
        assertFail(() -> check(map()).isNotEmpty(), "empty", "not empty");
    }

    @Test
    public void hasSizeOk() {
        check(map("a", "1", "b", "2")).hasSize(2);
    }

    @Test
    public void hasSizeFail() {
        assertFail(() -> check(map("a", "1")).hasSize(2),
                "size is 1", "size is 2");
    }

    private static Map<String, String> map(String... pairs) {
        Map<String, String> result = new LinkedHashMap<>();
        for (int i = 0; i < pairs.length; i += 2) {
            result.put(pairs[i], pairs[i + 1]);
        }
        return result;
    }
}
