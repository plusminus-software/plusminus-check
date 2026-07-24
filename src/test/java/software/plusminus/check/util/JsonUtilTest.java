package software.plusminus.check.util;

import org.junit.Test;
import software.plusminus.check.fixtures.TestObject;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("java:S2699")
public class JsonUtilTest {

    @Test
    public void isJson() {
        assertTrue(JsonUtil.isJson("{\"a\":1}"));
        assertTrue(JsonUtil.isJson("[1,2]"));
        assertFalse(JsonUtil.isJson("not json"));
        assertFalse(JsonUtil.isJson("{unterminated"));
    }

    @Test
    public void toJson() {
        assertEquals("{\"name\":\"a\",\"count\":1}",
                JsonUtil.toJson(new TestObject("a", 1)));
    }

    @Test
    public void toJsonSkipsNulls() {
        assertEquals("{\"name\":\"a\"}", JsonUtil.toJson(new TestObject("a", null)));
    }

    @Test
    public void fromJson() {
        TestObject object = JsonUtil.fromJson("{\"name\":\"a\",\"count\":1}", TestObject.class);
        assertEquals(new TestObject("a", 1), object);
    }

    @Test
    public void fromJsonList() {
        List<TestObject> list = JsonUtil.fromJsonList(
                "[{\"name\":\"a\",\"count\":1}]", TestObject[].class);
        assertEquals(1, list.size());
        assertEquals(new TestObject("a", 1), list.get(0));
    }

    @Test
    public void pretty() {
        assertEquals("{\n  \"name\": \"a\"\n}", JsonUtil.pretty("{\"name\":\"a\"}"));
    }

    @Test
    public void prettyNonJsonReturnedAsIs() {
        assertEquals("not json", JsonUtil.pretty("not json"));
    }

    @Test
    public void prettyOrdered() {
        assertEquals("{\n  \"a\": 1,\n  \"b\": 2\n}",
                JsonUtil.prettyOrdered("{\"b\":2,\"a\":1}", "{\"a\":1,\"b\":2}"));
    }

    @Test
    public void readJsonInline() {
        assertEquals("{\"a\":1}", JsonUtil.readJson("{\"a\":1}"));
    }

    @Test
    public void readJsonResource() {
        assertTrue(JsonUtil.readJson("json-object.json").contains("\"name\":\"a\""));
    }

    @Test(expected = AssertionError.class)
    public void readJsonUnknown() {
        JsonUtil.readJson("unknown-thing");
    }
}
