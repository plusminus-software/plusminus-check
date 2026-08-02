package software.plusminus.check.types;

import org.junit.Test;
import software.plusminus.check.fixtures.TestObject;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;

import static software.plusminus.check.Checks.check;
import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class CollectionCheckTest {

    @Test
    public void isIterableOk() {
        Collection<String> actual = Arrays.asList("a", "b");
        check(actual).is(iterable("a", "b"));
    }

    /** A plain collection has no defined order, so the comparison must not depend on it. */
    @Test
    public void isIterableIgnoresOrder() {
        Collection<String> actual = new LinkedHashSet<>(Arrays.asList("a", "b"));
        check(actual).is(iterable("b", "a"));
    }

    @Test
    public void isIterableFail() {
        Collection<String> actual = Arrays.asList("a", "b");
        assertFail(() -> check(actual).is(iterable("a", "c")),
                "does not contain: [\n  \"c\"\n]\nbut contains unexpected elements: [\n  \"b\"\n]",
                "contains exactly elements");
    }

    @Test
    public void isIterableAgainstNullFail() {
        Collection<String> actual = Arrays.asList("a");
        assertFail(() -> check(actual).is((Iterable<String>) null));
    }

    @Test
    public void map() {
        check(objects()).map(TestObject::getName)
                .contains("One", "Two");
    }

    @Test
    public void mapFail() {
        assertFail(() -> check(objects()).map(TestObject::getName).contains("Three"),
                "does not contain [\n  \"Three\"\n]", "contains all elements");
    }

    @Test
    public void mapChain() {
        check(objects())
                .map(TestObject::getName)
                .map(String::length)
                .containsExactly(3, 3);
    }

    @Test
    public void mapWithElementCheck() {
        check(objects())
                .map(TestObject::getName, StringCheck::new)
                .contains(c -> c.startsWith("O"), c -> c.endsWith("wo"));
    }

    @Test
    public void mapFailOnNull() {
        Collection<TestObject> nullCollection = null;
        assertFail(() -> check(nullCollection).map(TestObject::getName));
    }

    @Test
    public void mapTo() {
        check(objects()).mapTo(TestObject::getName)
                .contains(c -> c.startsWith("On"));
        check(objects()).mapTo(TestObject::getPathField)
                .contains(PathCheck::isRelative);
        check(objects()).mapTo(TestObject::getBigDecimalField)
                .contains(c -> c.limitScale().isPositive());
        check(objects()).mapTo(TestObject::getListField)
                .contains(c -> c.hasSize(1));
        check(objects()).mapTo(TestObject::getOptionalField)
                .contains(c -> c.isPresent().isEqual("One"));
        check(objects()).mapTo(TestObject::getPrimitiveIntegerField)
                .contains(NumberCheck::isPositive);
        check(objects()).mapTo(TestObject::getCount)
                .contains(NullableNumberCheck::isNotNull);
    }

    @Test
    public void filter() {
        check(objects()).filter(o -> o.getCount() > 1)
                .map(TestObject::getName)
                .contains("Two");
    }

    @Test
    public void sortedReturnsOrderedCheck() {
        Collection<String> values = Arrays.asList("c", "a", "b");
        check(values).sorted()
                .is("a", "b", "c");
    }

    @Test
    public void sortedWithComparator() {
        check(objects()).sorted(Comparator.comparing(TestObject::getName).reversed())
                .map(TestObject::getName)
                .is("Two", "One");
    }

    @Test
    public void isStringCollection() {
        Collection<Object> values = Arrays.asList("a", "b");
        check(values).isStringCollection()
                .contains(c -> c.startsWith("a"));
    }

    @Test
    public void isStringCollectionFail() {
        Collection<Object> values = Arrays.asList("a", 1);
        assertFail(() -> check(values).isStringCollection(),
                "element at index 1 is java.lang.Integer", "all elements are java.lang.String");
    }

    @Test
    public void isNumberCollection() {
        Collection<Object> values = Arrays.asList(1, 2L);
        check(values).isNumberCollection()
                .contains(NumberCheck::isPositive);
    }

    private Collection<TestObject> objects() {
        return Arrays.asList(TestObject.of("One", 1), TestObject.of("Two", 2));
    }

    private Iterable<String> iterable(String... elements) {
        List<String> list = Arrays.asList(elements);
        return list::iterator;
    }
}
