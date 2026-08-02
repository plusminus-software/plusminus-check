package software.plusminus.check.types;

import org.junit.Test;
import software.plusminus.check.fixtures.TestEnum;
import software.plusminus.check.fixtures.TestObject;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import static software.plusminus.check.Checks.check;
import static software.plusminus.check.Checks.checkOf;
import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class ListCheckTest {

    @Test
    public void isOk() {
        check(Arrays.asList("a", "b", "c")).is("a", "b", "c");
    }

    @Test
    public void isFail() {
        assertFail(() -> check(Arrays.asList(1, 2, 3)).is(1, 2, 4), "[2] ", 3, 4);
    }

    @Test
    public void containsExactlyWithDuplicatesOk() {
        check(Arrays.asList("a", "a")).containsExactly("a", "a");
    }

    @Test
    public void containsWithDuplicatesOk() {
        check(Arrays.asList("a", "a", "b")).contains("a", "a");
    }

    @Test
    public void containsMoreDuplicatesThanActualFail() {
        assertFail(() -> check(Arrays.asList("a")).contains("a", "a"),
                "does not contain [\n  \"a\"\n]", "contains all elements");
    }

    @Test
    public void containsExactlyMoreDuplicatesThanActualFail() {
        assertFail(() -> check(Arrays.asList("a")).containsExactly("a", "a"),
                "does not contain: [\n  \"a\"\n]", "contains exactly elements");
    }

    @Test
    public void at() {
        List<Integer> list = Arrays.asList(0, 1, -2);
        checkOf(() -> list)
                .at(0).is(c -> c.isZero())
                .at(1).is(c -> c.isPositive())
                .at(2).is(c -> c.isNegative())
                .hasSize(3);
    }

    @Test
    public void map() {
        check(objects()).map(TestObject::getName)
                .is("One", "Two");
    }

    @Test
    public void mapKeepsIndexInFailMessage() {
        assertFail(() -> check(objects()).map(TestObject::getName).is("One", "Three"),
                "[1] ", "Two", "Three");
    }

    @Test
    public void mapChain() {
        check(objects())
                .map(TestObject::getName)
                .map(String::length)
                .is(3, 3);
    }

    @Test
    public void mapWithElementCheck() {
        check(objects())
                .map(TestObject::getName, StringCheck::new)
                .at(0).is(c -> c.startsWith("O"))
                .at(1).is(c -> c.endsWith("wo"));
    }

    @Test
    public void mapFailOnNull() {
        List<TestObject> nullList = null;
        assertFail(() -> check(nullList).map(TestObject::getName));
    }

    @Test
    public void mapFailOnNullElement() {
        List<TestObject> list = Arrays.asList(TestObject.of("One", 1), null);
        assertFail(() -> check(list).map(TestObject::getName),
                "element at index 1 is null", "all elements are non-null");
    }

    @Test
    public void mapWithLambda() {
        check(objects())
                .map(o -> o.getName().toUpperCase(Locale.ROOT))
                .is("ONE", "TWO");
    }

    @Test
    public void mapWithLambdaAndElementCheck() {
        check(objects())
                .map(o -> o.getName().toUpperCase(Locale.ROOT), StringCheck::new)
                .at(0).is(c -> c.startsWith("ON"));
    }

    @Test
    public void mapTo() {
        check(objects()).mapTo(TestObject::isPrimitiveBooleanField)
                .at(0).is((BooleanCheck c) -> c.isTrue());
        check(objects()).mapTo(TestObject::getBooleanField)
                .at(0).is(NullableBooleanCheck::isNotNull);
        check(objects()).mapTo(TestObject::getPrimitiveCharacterField)
                .at(0).is((CharacterCheck c) -> c.is('O'));
        check(objects()).mapTo(TestObject::getCharacterField)
                .at(0).is(NullableCharacterCheck::isNotNull);
        check(objects()).mapTo(TestObject::getPrimitiveByteField)
                .at(0).is((NumberCheck<Byte> c) -> c.isPositive());
        check(objects()).mapTo(TestObject::getByteField)
                .at(0).is(NullableNumberCheck::isNotNull);
        check(objects()).mapTo(TestObject::getPrimitiveShortField)
                .at(0).is((NumberCheck<Short> c) -> c.isPositive());
        check(objects()).mapTo(TestObject::getShortField)
                .at(0).is(NullableNumberCheck::isNotNull);
        check(objects()).mapTo(TestObject::getPrimitiveIntegerField)
                .at(0).is((NumberCheck<Integer> c) -> c.isPositive());
        check(objects()).mapTo(TestObject::getCount)
                .at(0).is(NullableNumberCheck::isNotNull);
        check(objects()).mapTo(TestObject::getPrimitiveLongField)
                .at(0).is((NumberCheck<Long> c) -> c.isPositive());
        check(objects()).mapTo(TestObject::getLongField)
                .at(0).is(NullableNumberCheck::isNotNull);
        check(objects()).mapTo(TestObject::getBigIntegerField)
                .at(0).is(NullableNumberCheck::isNotNull);
        check(objects()).mapTo(TestObject::getPrimitiveFloatField)
                .at(0).is((DecimalCheck<Float> c) -> c.limitScale().isPositive());
        check(objects()).mapTo(TestObject::getFloatField)
                .at(0).is(NullableDecimalCheck::isNotNull);
        check(objects()).mapTo(TestObject::getPrimitiveDoubleField)
                .at(0).is((DecimalCheck<Double> c) -> c.limitScale().isPositive());
        check(objects()).mapTo(TestObject::getDoubleField)
                .at(0).is(NullableDecimalCheck::isNotNull);
        check(objects()).mapTo(TestObject::getBigDecimalField)
                .at(0).is(NullableDecimalCheck::isNotNull);
        check(objects()).mapTo(TestObject::getName)
                .at(0).is(c -> c.startsWith("On"));
        check(objects()).mapTo(TestObject::getPathField)
                .at(0).is(PathCheck::isRelative);
        check(objects()).mapTo(TestObject::getTemporalField)
                .at(0).is(c -> c.isRecent(Duration.ofHours(1)));
        check(objects()).mapTo(TestObject::getStreamField)
                .at(0).is(c -> c.is("One"));
        check(objects()).mapTo(TestObject::getEnumField)
                .at(0).is((EnumCheck<TestEnum> c) -> c.isNotNull());
        check(objects()).mapTo(TestObject::getCollectionField)
                .at(0).is(c -> c.hasSize(1));
        check(objects()).mapTo(TestObject::getListField)
                .at(0).is(c -> c.is("One"));
        check(objects()).mapTo(TestObject::getSortedSetField)
                .at(0).is(c -> c.is("One"));
        check(objects()).mapTo(TestObject::getDequeField)
                .at(0).is(c -> c.is("One"));
        check(objects()).mapTo(TestObject::getOptionalField)
                .at(0).is(c -> c.isNotEmpty().isEqual("One"));
        check(objects()).mapTo(TestObject::getMapField)
                .at(0).is(c -> c.is("key", "One"));
        check(objects()).mapTo(TestObject::getArrayField)
                .at(0).is(c -> c.is("One"));
    }

    @Test
    public void filter() {
        check(objects()).filter(o -> o.getCount() > 1)
                .map(TestObject::getName)
                .is("Two");
    }

    @Test
    public void filterKeepsElementCheck() {
        check(objects())
                .map(TestObject::getName, StringCheck::new)
                .filter(name -> name.startsWith("O"))
                .at(0).is(c -> c.endsWith("ne"));
    }

    @Test
    public void filterMatchingNothing() {
        check(objects()).filter(o -> o.getCount() > 9)
                .isEmpty();
    }

    @Test
    public void filterFailOnNull() {
        List<TestObject> nullList = null;
        assertFail(() -> check(nullList).filter(o -> true));
    }

    @Test
    public void filterFailOnNullElement() {
        List<TestObject> list = Arrays.asList(TestObject.of("One", 1), null);
        assertFail(() -> check(list).filter(o -> o.getCount() > 0),
                "element at index 1 is null", "all elements are non-null");
    }

    @Test
    public void sorted() {
        check(Arrays.asList("c", "a", "b")).sorted()
                .is("a", "b", "c");
    }

    @Test
    public void sortedWithComparator() {
        check(objects()).sorted(Comparator.comparing(TestObject::getName).reversed())
                .map(TestObject::getName)
                .is("Two", "One");
    }

    @Test
    public void sortedFailOnNullElement() {
        assertFail(() -> check(Arrays.asList("b", null)).sorted(),
                "element at index 1 is null", "all elements are non-null");
    }

    @Test
    public void sortedFailOnNotComparable() {
        assertFail(() -> check(objects()).sorted(),
                "elements are not mutually comparable", "all elements are comparable");
    }

    @Test
    public void sortedAllowsNullsWithComparator() {
        check(Arrays.asList("b", null)).sorted(Comparator.nullsFirst(Comparator.naturalOrder()))
                .is(null, "b");
    }

    @Test
    public void flatMap() {
        check(objects()).flatMap(TestObject::getListField)
                .is("One", "Two");
    }

    @Test
    public void flatMapWithElementCheck() {
        check(objects()).flatMap(TestObject::getListField)
                .isStringList()
                .at(0).is(c -> c.startsWith("On"));
    }

    @Test
    public void flatMapFailsOnNullCollection() {
        List<TestObject> list = objects();
        list.get(1).setListField(null);

        assertFail(() -> check(list).flatMap(TestObject::getListField),
                "element at index 1 has no collection", "all elements have a collection");
    }

    @Test
    public void flatMapAllowsEmptyCollection() {
        List<TestObject> list = objects();
        list.get(1).setListField(Collections.emptyList());

        check(list).flatMap(TestObject::getListField)
                .is("One");
    }

    @Test
    public void flatMapKeepsNullInsideCollection() {
        List<TestObject> list = objects();
        list.get(0).setListField(Arrays.asList("One", null));

        check(list).flatMap(TestObject::getListField)
                .is("One", null, "Two");
    }

    @Test
    public void flatMapFailsOnNullElement() {
        List<TestObject> list = Arrays.asList(TestObject.of("One", 1), null);
        assertFail(() -> check(list).flatMap(TestObject::getListField),
                "element at index 1 is null", "all elements are non-null");
    }

    @Test
    public void distinct() {
        check(Arrays.asList("a", "b", "a")).distinct()
                .is("a", "b");
    }

    @Test
    public void distinctComparesStructurally() {
        check(Arrays.asList(new TestObject("a", 1), new TestObject("a", 1))).distinct()
                .hasSize(1);
    }

    @Test
    public void hasNoDuplicates() {
        check(Arrays.asList("a", "b")).hasNoDuplicates()
                .hasSize(2);
    }

    @Test
    public void hasNoDuplicatesFail() {
        assertFail(() -> check(Arrays.asList("a", "b", "a")).hasNoDuplicates(),
                "element at index 2 duplicates index 0", "all elements are unique");
    }

    @Test
    public void allMatch() {
        check(objects()).allMatch(o -> o.getCount() > 0)
                .hasSize(2);
    }

    @Test
    public void allMatchFail() {
        assertFail(() -> check(objects()).allMatch(o -> o.getCount() > 1),
                "element at index 0 does not match", "all elements match");
    }

    @Test
    public void noneMatch() {
        check(objects()).noneMatch(o -> o.getCount() > 9)
                .hasSize(2);
    }

    @Test
    public void noneMatchFail() {
        assertFail(() -> check(objects()).noneMatch(o -> o.getCount() > 1),
                "element at index 1 matches", "no elements match");
    }

    @Test
    public void isStringList() {
        check(Arrays.<Object>asList("a", "b")).isStringList()
                .at(0).is(c -> c.startsWith("a"));
    }

    @Test
    public void isStringListFail() {
        assertFail(() -> check(Arrays.<Object>asList("a", 1)).isStringList(),
                "element at index 1 is java.lang.Integer", "all elements are java.lang.String");
    }

    @Test
    public void isBooleanList() {
        check(Arrays.<Object>asList(true, false)).isBooleanList()
                .at(0).is(BooleanCheck::isTrue);
    }

    @Test
    public void isNumberList() {
        check(Arrays.<Object>asList(1, 2L)).isNumberList()
                .at(1).is(NumberCheck::isPositive);
    }

    @Test
    public void isEnumList() {
        check(Arrays.asList(TestEnum.ONE)).isEnumList(TestEnum.class)
                .at(0).is(TestEnum.ONE);
    }

    @Test
    public void isEnumListFail() {
        assertFail(() -> check(Arrays.<Object>asList(TestEnum.ONE, "a")).isEnumList(TestEnum.class),
                "element at index 1 is java.lang.String",
                "all elements are software.plusminus.check.fixtures.TestEnum");
    }

    @Test
    public void isNumberListWithType() {
        check(Arrays.<Object>asList(1, 2)).isNumberList(Integer.class)
                .at(0).is(1);
    }

    @Test
    public void isTemporalList() {
        check(Arrays.asList(Instant.now()))
                .isTemporalList()
                .at(0).is(c -> c.isRecent(Duration.ofHours(1)));
    }

    @Test
    public void isTemporalListWithType() {
        Instant now = Instant.now();
        check(Arrays.asList(now)).isTemporalList(Instant.class)
                .at(0).is(now);
    }

    @Test
    public void isStringListAllowsNulls() {
        check(Arrays.<Object>asList("a", null))
                .isStringList().hasSize(2);
    }

    @Test
    public void isStringListFailOnNullActual() {
        List<Object> nullList = null;
        assertFail(() -> check(nullList).isStringList());
    }

    private List<TestObject> objects() {
        return Arrays.asList(TestObject.of("One", 1), TestObject.of("Two", 2));
    }
}
