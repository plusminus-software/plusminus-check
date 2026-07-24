package software.plusminus.check.util;

import org.junit.Test;
import software.plusminus.check.fixtures.TestEnum;
import software.plusminus.check.fixtures.TestObject;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("java:S2699")
public class StringUtilTest {

    @Test
    public void nullString() {
        assertEquals("null", StringUtil.toString(null));
    }

    @Test
    public void charSequence() {
        assertEquals("abc", StringUtil.toString("abc"));
    }

    @Test
    public void number() {
        assertEquals("42", StringUtil.toString(42));
    }

    @Test
    public void enumName() {
        assertEquals("ONE", StringUtil.toString(TestEnum.ONE));
    }

    @Test
    public void classType() {
        assertEquals("type java.lang.String", StringUtil.toString(String.class));
    }

    @Test
    public void emptyOptional() {
        assertEquals("empty", StringUtil.toString(Optional.empty()));
    }

    @Test
    public void emptyCollection() {
        assertEquals("empty", StringUtil.toString(new ArrayList<>()));
    }

    @Test
    public void objectAsPrettyJson() {
        assertEquals("{\n  \"name\": \"a\",\n  \"count\": 1\n}",
                StringUtil.toString(new TestObject("a", 1)));
    }

    @Test
    public void resourceExpandedWhenChecked() {
        assertEquals("{\n  \"name\": \"a\"\n}", StringUtil.toString("json-object.json", true));
    }

    @Test
    public void resourceNotExpandedByDefault() {
        assertEquals("json-object.json", StringUtil.toString("json-object.json"));
    }
}
