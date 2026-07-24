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
    public void hasFieldFail() {
        assertFail(() -> check("{\"id\":42,\"name\":\"a\"}")
                        .isJson()
                        .hasField("id", id -> id.isLike(7)),
                "id ", 42, 7);
    }

    @Test
    public void hasFieldMissing() {
        assertFail(() -> check("{\"name\":\"a\"}").isJson().hasField("id", id -> id.isLike(42)),
                "Field id is missed", "Field id is present");
    }

    @Test
    public void hasFieldOnArrayFail() {
        assertFail(() -> check("[1,2,3]").isJson().hasField("id", id -> id.isLike(1)),
                "is not a json object", "is a json object");
    }

    @Test
    public void separatelyCheckedFieldMissingInExpectedFail() {
        assertFail(() -> check("{\"id\":42,\"name\":\"a\"}")
                .isJson()
                .ignoringFieldsOrder()
                .hasField("id", id -> id.isLike(42))
                .is("{\"name\":\"a\"}"));
    }

    @Test
    public void isResourceOk() {
        check("{\"name\":\"a\"}").isJson().is("json-object.json");
    }

    @Test
    public void isResourceFail() {
        assertFail(() -> check("{\"name\":\"b\"}").isJson().is("json-object.json"),
                "{\n  \"name\": \"b\"\n}", "{\n  \"name\": \"a\"\n}");
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
