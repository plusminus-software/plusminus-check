package software.plusminus.check.types;

import org.junit.Test;
import software.plusminus.check.fixtures.TestObject;

import static software.plusminus.check.Checks.check;
import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class ArrayCheckTest {

    @Test
    public void isOk() {
        Integer[] array = {1, 2, 3};
        check(array).is(1, 2, 3);
    }

    @Test
    public void isFail() {
        Integer[] array = {1, 2, 3};
        assertFail(() -> check(array).is(1, 2, 4), "[2] ", 3, 4);
    }

    @Test
    public void at() {
        String[] array = {"a", "b", "c"};
        check(array).at(1).is(c -> c.is("b"));
    }

    @Test
    public void map() {
        check(objects()).map(TestObject::getCount)
                .is(1, 2);
    }

    @Test
    public void mapChain() {
        check(objects()).map(TestObject::getName)
                .map(String::length)
                .is(3, 3);
    }

    @Test
    public void mapKeepsIndex() {
        assertFail(() -> check(objects()).map(TestObject::getCount)
                .is(2, 2), "[0] ", 1, 2);
    }

    @Test
    public void mapWithElementCheck() {
        check(objects()).map(TestObject::getName, StringCheck::new)
                .at(0).is(c -> c.startsWith("On"));
    }

    @Test
    public void mapOnNull() {
        TestObject[] array = null;
        assertFail(() -> check(array).map(TestObject::getName));
    }

    @Test
    public void mapTo() {
        check(objects()).mapTo(TestObject::getName)
                .at(0).is(c -> c.endsWith("ne"));
        check(objects()).mapTo(TestObject::getPathField)
                .at(0).is(PathCheck::isRelative);
        check(objects()).mapTo(TestObject::getStreamField)
                .at(0).is(c -> c.is("One"));
        check(objects()).mapTo(TestObject::getSortedSetField)
                .at(0).is(c -> c.hasSize(1));
        check(objects()).mapTo(TestObject::getMapField).at(0)
                .is(c -> c.is("key", "One"));
        check(objects()).mapTo(TestObject::getPrimitiveLongField)
                .at(0).is(NumberCheck::isPositive);
        check(objects()).mapTo(TestObject::getLongField)
                .at(0).is(NullableNumberCheck::isNotNull);
    }

    @Test
    public void isStringArray() {
        Object[] array = {"a", "b"};
        check(array).isStringArray()
                .at(1).is(c -> c.endsWith("b"));
    }

    @Test
    public void isStringArrayFail() {
        Object[] array = {"a", 1};
        assertFail(() -> check(array).isStringArray(),
                "element at index 1 is java.lang.Integer", "all elements are java.lang.String");
    }

    @Test
    public void isBooleanArray() {
        Object[] array = {true, false};
        check(array).isBooleanArray()
                .at(0).is(BooleanCheck::isTrue);
    }

    private TestObject[] objects() {
        return new TestObject[]{TestObject.of("One", 1), TestObject.of("Two", 2)};
    }
}
