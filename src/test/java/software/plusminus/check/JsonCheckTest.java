package software.plusminus.check;

import org.junit.Test;

import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class JsonCheckTest {

    @Test
    public void isSuccess() {
        new JsonCheck("{\"name\":\"a\"}").is("{\"name\":\"a\"}");
    }

    @Test
    public void isPrettyEquivalentSuccess() {
        new JsonCheck("{\"name\":\"a\"}").is("{ \"name\" : \"a\" }");
    }

    @Test
    public void isFail() {
        assertFail(() -> new JsonCheck("{\"name\":\"a\"}").is("{\"name\":\"b\"}"),
                "{\n  \"name\": \"a\"\n}", "{\n  \"name\": \"b\"\n}");
    }

    @Test
    public void ignoringFieldsOrderSuccess() {
        new JsonCheck("{\"a\":1,\"b\":2}").ignoringFieldsOrder().is("{\"b\":2,\"a\":1}");
    }

    @Test
    public void hasFieldSuccess() {
        new JsonCheck("{\"id\":42,\"name\":\"a\"}")
                .hasField("id", id -> id.isLike(42))
                .is("{\"id\":1,\"name\":\"a\"}");
    }

    @Test
    public void hasFieldMissingFail() {
        assertFail(() -> new JsonCheck("{\"name\":\"a\"}").hasField("id", id -> id.isLike(42)),
                "Field id is present", "Field id is missed");
    }

    @Test
    public void constructorFailsOnNonJson() {
        assertFail(() -> new JsonCheck("not json"), "is not json", "is json");
    }

    @Test
    public void constructorFailsOnNull() {
        assertFail(() -> new JsonCheck(null), "is not json", "is json");
    }
}
