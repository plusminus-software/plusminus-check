package software.plusminus.check.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.Test;

import static software.plusminus.check.Checks.check;
import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class ObjectCheckTest {

    @Test
    public void isInstanceOfOk() {
        check((Object) "hello").isInstanceOf(CharSequence.class).is("hello");
    }

    @Test
    public void isInstanceOfFail() {
        assertFail(() -> check((Object) 1).isInstanceOf(CharSequence.class),
                "class of the object is java.lang.Integer",
                "class of the object is an instance of java.lang.CharSequence");
    }

    @Test
    public void isInstanceOfNullFail() {
        assertFail(() -> check((Object) null).isInstanceOf(String.class),
                "null", "an instance of java.lang.String");
    }

    @Test
    public void isCompactInlineJsonOk() {
        check(new TestObject("x", 1)).is("{\"name\":\"x\",\"count\":1}");
    }

    @Test
    public void isCompactInlineJsonFail() {
        assertFail(() -> check(new TestObject("x", 1)).is("{\"name\":\"y\",\"count\":1}"),
                "{\n  \"name\": \"x\",\n  \"count\": 1\n}",
                "{\"name\":\"y\",\"count\":1}");
    }

    @Test
    public void fieldByName() {
        check(new TestObject("a", 1))
                .field("name").is(o -> o.is("a"));
    }

    @Test
    public void fieldByNameFail() {
        assertFail(() -> check(new TestObject("a", 1)).field("name").is(o -> o.is("b")),
                "name ", "a", "b");
    }

    @Test
    public void fieldByGetter() {
        check(new TestObject("a", 1))
                .field(TestObject::getName).is("a");
    }

    @Test
    public void fieldByGetterFail() {
        assertFail(() -> check(new TestObject("a", 1))
                .field(TestObject::getName).is("b"), "name ", "a", "b");
    }

    @Test
    public void allCheckedOk() {
        check(new TestObject("a", 1))
                .field(TestObject::getName).is("a")
                .field(TestObject::getCount).is(1)
                .allChecked();
    }

    @Test
    public void allCheckedFail() {
        ObjectCheck<TestObject> objectCheck = check(new TestObject("a", 1));
        objectCheck.field(TestObject::getName).is("a");
        assertFail(objectCheck::allChecked,
                "there are not checked fields: [count]",
                "all fields were checked");
    }

    @Test
    public void isStringNarrowing() {
        check((Object) "hello").isString().is("hello");
    }

    @Test
    public void isBooleanNarrowing() {
        check((Object) true).isBoolean().isTrue();
    }

    @AllArgsConstructor
    @Getter
    private static class TestObject {
        private String name;
        private Integer count;
    }
}
