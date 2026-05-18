package software.plusminus.check.types;

import org.junit.Test;

import static software.plusminus.check.Checks.check;
import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class JsonCheckTest {

    @Test
    public void isOk() {
        check("{\"name\":\"a\"}").isJson().is("{\"name\":\"a\"}");
    }

    @Test
    public void isPrettyEquivalentOk() {
        check("{\"name\":\"a\"}").isJson().is("{ \"name\" : \"a\" }");
    }

    @Test
    public void isFail() {
        assertFail(() -> check("{\"name\":\"a\"}").isJson().is("{\"name\":\"b\"}"),
                "{\n  \"name\": \"a\"\n}", "{\n  \"name\": \"b\"\n}");
    }

    @Test
    public void ignoringFieldsOrderOk() {
        check("{\"a\":1,\"b\":2}").isJson().ignoringFieldsOrder().is("{\"b\":2,\"a\":1}");
    }

    @Test
    public void hasFieldOk() {
        check("{\"id\":42,\"name\":\"a\"}")
                .isJson()
                .hasField("id", id -> id.isLike(42))
                .is("{\"id\":\"ignoredSinceItsAlreadyChecked\",\"name\":\"a\"}");
    }

    @Test
    public void hasFieldMissingFail() {
        assertFail(() -> check("{\"name\":\"a\"}").isJson().hasField("id", id -> id.isLike(42)),
                "Field id is present", "Field id is missed");
    }

    @Test
    public void notJsonFail() {
        assertFail(() -> check("not json").isJson(), "not json", "json");
    }

    @Test
    public void nullFail() {
        assertFail(() -> check((String) null).isJson(), "null", "not null");
    }
}
