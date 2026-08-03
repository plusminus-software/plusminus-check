package software.plusminus.check.types;

import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static software.plusminus.check.Checks.check;
import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class IterableCheckTest {

    @Test
    public void isSameOk() {
        TestIterable actual = new TestIterable("a", "b");
        check(actual).isSame(actual);
    }

    @Test
    public void isSameFail() {
        assertFail(() -> check(new TestIterable("a")).isSame(new TestIterable("a")));
    }

    @Test
    public void isTypeReportsTheIterableType() {
        check(new TestIterable("a")).isType(TestIterable.class);
    }

    @Test
    public void isSameTypeAsOk() {
        check(new TestIterable("a")).isSameTypeAs(new TestIterable("b"));
    }

    @Test
    public void isNull() {
        TestIterable actual = null;
        check(actual).isNull();
    }

    @Test
    public void isOk() {
        check(new TestIterable("a", "b")).is(new TestIterable("a", "b"));
    }

    @Test
    public void isFail() {
        assertFail(() -> check(new TestIterable("a", "b")).is(new TestIterable("a", "c")));
    }

    /**
     * {@code is} is the inherited whole-value comparison, which starts by requiring the
     * same concrete type.
     */
    @Test
    public void isDifferentIterableTypeFail() {
        assertFail(() -> check(new TestIterable("a")).is(new TestIterable2("a")));
    }

    @Test
    public void hasSize() {
        check(new TestIterable("a", "b")).hasSize(2);
    }

    @Test
    public void hasSizeFail() {
        assertFail(() -> check(new TestIterable("a", "b")).hasSize(3), "size is 2", "size is 3");
    }

    @Test
    public void containsExactlyIgnoresOrder() {
        check(new TestIterable("a", "b")).containsExactly("b", "a");
    }

    @Test
    public void containsFail() {
        assertFail(() -> check(new TestIterable("a")).contains("b"),
                "does not contain [\n  \"b\"\n]", "contains all elements");
    }

    @Test
    public void isEmpty() {
        check(new TestIterable()).isEmpty();
    }

    @Test
    public void map() {
        check(new TestIterable("One", "Two")).map(String::length)
                .containsExactly(3, 3);
    }

    @Test
    public void filter() {
        check(new TestIterable("One", "Two")).filter(name -> name.startsWith("O"))
                .containsExactly("One");
    }

    @Test
    public void sorted() {
        check(new TestIterable("b", "a")).sorted().is("a", "b");
    }

    @Test
    public void isStringCollection() {
        check(new TestIterable("a")).isStringCollection()
                .contains(c -> c.startsWith("a"));
    }

    @Test
    public void isSameDoesNotReadElements() {
        TestIterable2 actual = new TestIterable2("a", "b");
        check(actual)
                .hasSize(2)
                .isSame(actual);
    }

    @Test
    public void readsElementsOnlyOnce() {
        check(new TestIterable2("a", "b"))
                .hasSize(2)
                .contains("a")
                .isNotEmpty();
    }

    private static final class TestIterable implements Iterable<String> {

        private final List<String> values;

        private TestIterable(String... values) {
            this.values = Arrays.asList(values);
        }

        @Override
        public Iterator<String> iterator() {
            return values.iterator();
        }
    }

    private static final class TestIterable2 implements Iterable<String> {

        private final List<String> values;
        private boolean consumed;

        private TestIterable2(String... values) {
            this.values = Arrays.asList(values);
        }

        @Override
        public Iterator<String> iterator() {
            if (consumed) {
                throw new IllegalStateException("iterable was read more than once");
            }
            consumed = true;
            return values.iterator();
        }
    }
}
