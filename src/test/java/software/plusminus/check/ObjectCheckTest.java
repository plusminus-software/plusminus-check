package software.plusminus.check;

import org.junit.Test;
import software.plusminus.check.fixtures.TestObject;

import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class ObjectCheckTest {

    @Test
    public void isSuccess() {
        new ObjectCheck<>(new TestObject("a", 1)).is(new TestObject("a", 1));
    }

    @Test
    public void isFail() {
        assertFail(() -> new ObjectCheck<>(new TestObject("a", 1)).is(new TestObject("b", 2)),
                "{\n  \"name\": \"a\",\n  \"count\": 1\n}",
                "{\n  \"name\": \"b\",\n  \"count\": 2\n}");
    }

    @Test
    public void isLikeSuccess() {
        new ObjectCheck<>(new TestObject("a", 1)).isLike(new TestObject("a", 1));
    }

    @Test
    public void isEqualSuccess() {
        TestObject value = new TestObject("a", 1);
        new ObjectCheck<>(value).isEqual(value);
    }

    @Test
    public void isSameSuccess() {
        TestObject value = new TestObject("a", 1);
        new ObjectCheck<>(value).isSame(value);
    }

    @Test
    public void isTypeSuccess() {
        new ObjectCheck<>(new TestObject("a", 1)).isType(TestObject.class);
    }

    @Test
    public void isTypeFail() {
        assertFail(() -> new ObjectCheck<>(new TestObject("a", 1)).isType(String.class),
                "type software.plusminus.check.fixtures.TestObject",
                "type java.lang.String");
    }

    @Test
    public void isInstanceOfSuccess() {
        new ObjectCheck<>((Object) "hello").isInstanceOf(CharSequence.class).is("hello");
    }

    @Test
    public void isInstanceOfFail() {
        assertFail(() -> new ObjectCheck<>((Object) 1).isInstanceOf(CharSequence.class),
                "class of the object is java.lang.Integer",
                "class of the object is an instance of java.lang.CharSequence");
    }

    @Test
    public void isInstanceOfNullFail() {
        assertFail(() -> new ObjectCheck<>((Object) null).isInstanceOf(String.class),
                "null", "an instance of java.lang.String");
    }

    @Test
    public void fieldByName() {
        new ObjectCheck<>(new TestObject("a", 1))
                .field("name").is(o -> o.is("a"));
    }

    @Test
    public void fieldByNameFail() {
        assertFail(() -> new ObjectCheck<>(new TestObject("a", 1))
                .field("name").is(o -> o.is("b")), "a", "b");
    }

    @Test
    public void fieldOfGetter() {
        new ObjectCheck<>(new TestObject("a", 1))
                .fieldOf(TestObject::getName).is("a");
    }

    @Test
    public void fieldOfGetterFail() {
        try {
            new ObjectCheck<>(new TestObject("a", 1))
                    .fieldOf(TestObject::getName).is("b");
            org.junit.Assert.fail();
        } catch (AssertionError e) {
            org.junit.Assert.assertEquals(".name expected:<b> but was:<a>", e.getMessage());
        }
    }

    @Test
    public void allFieldsCheckedSuccess() {
        ObjectCheck<TestObject> check = new ObjectCheck<>(new TestObject("a", 1));
        check.fieldOf(TestObject::getName).is("a");
        check.fieldOf(TestObject::getCount).is(1);
        check.allFieldsChecked();
    }

    @Test
    public void allFieldsCheckedFail() {
        ObjectCheck<TestObject> check = new ObjectCheck<>(new TestObject("a", 1));
        check.fieldOf(TestObject::getName).is("a");
        assertFail(check::allFieldsChecked,
                "there are not checked fields: [count]",
                "all fields were checked");
    }

    @Test
    public void isStringNarrowing() {
        new ObjectCheck<>((Object) "hello").isString().is("hello");
    }

    @Test
    public void isBooleanNarrowing() {
        new ObjectCheck<>((Object) true).isBoolean().isTrue();
    }

    @Test
    public void isNullSuccess() {
        new ObjectCheck<>(null).isNull();
    }

    @Test
    public void isNotNullFail() {
        assertFail(() -> new ObjectCheck<>(null).isNotNull(), "null", "not null");
    }
}
