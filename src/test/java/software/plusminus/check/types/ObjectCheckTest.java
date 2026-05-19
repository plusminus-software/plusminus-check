package software.plusminus.check.types;

import org.junit.Test;
import software.plusminus.check.fixtures.TestObject;

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
    public void fieldOfGetter() {
        check(new TestObject("a", 1))
                .fieldOf(TestObject::getName).is("a");
    }

    @Test
    public void fieldOfGetterFail() {
        assertFail(() -> check(new TestObject("a", 1))
                .fieldOf(TestObject::getName).is("b"), "name ", "a", "b");
    }

    @Test
    public void allFieldsCheckedOk() {
        ObjectCheck<TestObject> objectCheck = check(new TestObject("a", 1));
        objectCheck.fieldOf(TestObject::getName).is("a");
        objectCheck.fieldOf(TestObject::getCount).is(1);
        objectCheck.allFieldsChecked();
    }

    @Test
    public void allFieldsCheckedFail() {
        ObjectCheck<TestObject> objectCheck = check(new TestObject("a", 1));
        objectCheck.fieldOf(TestObject::getName).is("a");
        assertFail(objectCheck::allFieldsChecked,
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
}
