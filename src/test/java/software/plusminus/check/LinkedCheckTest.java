package software.plusminus.check;

import org.junit.Test;
import software.plusminus.check.fixtures.TestObject;

import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class LinkedCheckTest {

    @Test
    public void isValueReturnsPrevious() {
        ObjectCheck<TestObject> check = new ObjectCheck<>(new TestObject("a", 1));
        ObjectCheck<TestObject> previous = check.fieldOf(TestObject::getName).is("a");
        previous.fieldOf(TestObject::getCount).is(1);
    }

    @Test
    public void isConsumerReturnsPrevious() {
        ObjectCheck<TestObject> check = new ObjectCheck<>(new TestObject("a", 1));
        ObjectCheck<TestObject> previous = check.field("name").is(o -> o.is("a"));
        previous.field("count").is(o -> o.is(1));
    }

    @Test
    public void isValueFail() {
        ObjectCheck<TestObject> check = new ObjectCheck<>(new TestObject("a", 1));
        try {
            check.fieldOf(TestObject::getName).is("b");
            org.junit.Assert.fail();
        } catch (AssertionError e) {
            org.junit.Assert.assertEquals(".name expected:<b> but was:<a>", e.getMessage());
        }
    }

    @Test
    public void isConsumerFail() {
        ObjectCheck<TestObject> check = new ObjectCheck<>(new TestObject("a", 1));
        assertFail(() -> check.field("name").is(o -> o.is("b")), "a", "b");
    }
}
