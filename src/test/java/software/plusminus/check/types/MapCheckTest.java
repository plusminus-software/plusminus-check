package software.plusminus.check.types;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static software.plusminus.check.Checks.check;
import static software.plusminus.check.Checks.entry;
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

    @Test
    public void isSixPairsOk() {
        check(map("a", "1", "b", "2", "c", "3", "d", "4", "e", "5", "f", "6"))
                .is("a", "1", "b", "2", "c", "3", "d", "4", "e", "5", "f", "6");
    }

    @Test
    public void isTenPairsOk() {
        check(map("a", "1", "b", "2", "c", "3", "d", "4", "e", "5",
                "f", "6", "g", "7", "h", "8", "i", "9", "j", "10"))
                .is("a", "1", "b", "2", "c", "3", "d", "4", "e", "5",
                        "f", "6", "g", "7", "h", "8", "i", "9", "j", "10");
    }

    @Test
    public void isTenPairsFail() {
        assertFail(() -> check(map("a", "1", "b", "2", "c", "3", "d", "4", "e", "5",
                        "f", "6", "g", "7", "h", "8", "i", "9", "j", "10"))
                        .is("a", "1", "b", "2", "c", "3", "d", "4", "e", "5",
                                "f", "6", "g", "7", "h", "8", "i", "9", "j", "11"),
                json("a", "1", "b", "2", "c", "3", "d", "4", "e", "5",
                        "f", "6", "g", "7", "h", "8", "i", "9", "j", "10"),
                json("a", "1", "b", "2", "c", "3", "d", "4", "e", "5",
                        "f", "6", "g", "7", "h", "8", "i", "9", "j", "11"));
    }

    @Test
    public void isEntriesOk() {
        check(map("a", "1", "b", "2")).is(entry("a", "1"), entry("b", "2"));
    }

    @Test
    public void isEntriesBeyondTenPairsOk() {
        Map<String, String> actual = new LinkedHashMap<>();
        List<Map.Entry<String, String>> entries = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            actual.put("key" + i, "value" + i);
            entries.add(entry("key" + i, "value" + i));
        }
        check(actual).is(entries.toArray(new Map.Entry[0]));
    }

    @Test
    public void isEntriesFail() {
        assertFail(() -> check(map("a", "1")).is(entry("a", "2")),
                "{\n  \"a\": \"1\"\n}", "{\n  \"a\": \"2\"\n}");
    }

    @Test
    public void containsKeyOk() {
        check(map("a", "1")).containsKey("a");
    }

    @Test
    public void containsKeyFail() {
        assertFail(() -> check(map("a", "1")).containsKey("b"),
                "keys are [\n  \"a\"\n]", "contains key b");
    }

    @Test
    public void doesNotContainKeyOk() {
        check(map("a", "1")).doesNotContainKey("b");
    }

    @Test
    public void doesNotContainKeyFail() {
        assertFail(() -> check(map("a", "1")).doesNotContainKey("a"),
                "keys are [\n  \"a\"\n]", "does not contain key a");
    }

    @Test
    public void containsEntryOk() {
        check(map("a", "1", "b", "2")).containsEntry("b", "2");
    }

    @Test
    public void containsEntryWrongValueFail() {
        assertFail(() -> check(map("a", "1")).containsEntry("a", "2"),
                "1", "value 2 for key a");
    }

    @Test
    public void containsValueOk() {
        check(map("a", "1", "b", "2")).containsValue("2");
    }

    @Test
    public void containsValueFail() {
        assertFail(() -> check(map("a", "1")).containsValue("2"),
                "does not contain 2", "contains value 2");
    }

    @Test
    public void valueAtOk() {
        check(map("a", "1", "b", "2"))
                .valueAt("a").is("1")
                .valueAt("b").is("2")
                .hasSize(2);
    }

    @Test
    public void valueAtFail() {
        assertFail(() -> check(map("a", "1")).valueAt("a").is("2"), "a ", "1", "2");
    }

    @Test
    public void valueAtMissingKeyFail() {
        assertFail(() -> check(map("a", "1")).valueAt("b"),
                "keys are [\n  \"a\"\n]", "contains key b");
    }

    private static String json(String... pairs) {
        StringBuilder json = new StringBuilder("{");
        for (int i = 0; i < pairs.length; i += 2) {
            json.append(i == 0 ? "\n  \"" : ",\n  \"")
                    .append(pairs[i])
                    .append("\": \"")
                    .append(pairs[i + 1])
                    .append('"');
        }
        return json.append("\n}").toString();
    }

    private static Map<String, String> map(String... pairs) {
        Map<String, String> result = new LinkedHashMap<>();
        for (int i = 0; i < pairs.length; i += 2) {
            result.put(pairs[i], pairs[i + 1]);
        }
        return result;
    }
}
