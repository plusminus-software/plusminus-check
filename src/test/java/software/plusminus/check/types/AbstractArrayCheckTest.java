package software.plusminus.check.types;

import org.junit.Before;
import org.junit.Test;
import software.plusminus.check.fixtures.TestObject;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;
import static software.plusminus.check.Checks.check;
import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class AbstractArrayCheckTest {

    private List<String> actual = Arrays.asList("1", "2", "3");

    private AbstractArrayCheck<String, List<String>, StringCheck, ?> check;

    @SuppressWarnings("unchecked")
    @Before
    public void before() {
        BiFunction<String, List<String>, StringCheck> elementCheck = StringCheck::new;
        check = mock(AbstractArrayCheck.class, withSettings()
                .useConstructor(actual, elementCheck)
                .defaultAnswer(CALLS_REAL_METHODS));
        when(check.actualList()).thenReturn(actual);
        when(check.size()).thenReturn(actual.size());
        doAnswer(invocation -> {
            int i = invocation.getArgument(0);
            return actual.get(i);
        }).when(check).get(anyInt());
    }

    @Test
    public void isEmptyOk() {
        when(check.size()).thenReturn(0);
        check.isEmpty();
    }

    @Test
    public void isEmptyFail() {
        assertFail(() -> check.isEmpty(), "not empty", "empty");
    }

    @Test
    public void isNotEmptyOk() {
        check.isNotEmpty();
    }

    @Test
    public void isNotEmptyFail() {
        when(check.size()).thenReturn(0);
        assertFail(() -> check.isNotEmpty(), "empty", "not empty");
    }

    @Test
    public void hasSizeOk() {
        check.hasSize(3);
    }

    @Test
    public void hasSizeFail() {
        assertFail(() -> check.hasSize(2), "size is 3", "size is 2");
    }

    @Test
    public void containsOk() {
        check.contains(1, "2");
    }

    @Test
    public void containsFail() {
        assertFail(() -> check.contains(1, 9), "does not contain [\n  9\n]", "contains all elements");
    }

    @Test
    public void consumerContainsOk() {
        check.contains(c -> c.is("1"));
    }

    @Test
    public void containsExactlyOk() {
        check.containsExactly(1, 2, 3);
    }

    @Test
    public void containsExactlyFail() {
        assertFail(() -> check.containsExactly(1, 2),
                "contains unexpected elements: [\n  \"3\"\n]", "contains exactly elements");
    }

    @Test
    public void atOk() {
        check.at(1).is(c -> c.is("2"));
    }

    @Test
    public void atFail() {
        assertFail(() -> check.at(0).is("9"), "[0] ", 1, 9);
    }

    @Test
    public void atOutOfBounds() {
        assertFail(() -> check.at(5).is("0"), "size is 3", "has element at index 5");
    }

    @Test
    public void isStringArrayResourceOk() {
        String[] array = {"One"};
        check(array).is("list-one.txt");
    }

    @Test
    public void isStringArrayResourceFail() {
        String[] array = {"Other"};
        assertFail(() -> check(array).is("list-one.txt"),
                "[\n  \"Other\"\n]", "[\n  \"One\"\n]");
    }

    @Test
    public void isObjectArrayResourceOk() {
        TestObject[] array = {new TestObject("One", 1)};
        check(array).is("one-object.json");
    }

    @Test
    public void isObjectArrayResourceFail() {
        TestObject[] array = {new TestObject("One", 1), new TestObject("One", 1)};
        assertFail(() -> check(array).is("one-object.json"),
                "[\n  {\n    \"name\": \"One\",\n    \"count\": 1\n  },"
                        + "\n  {\n    \"name\": \"One\",\n    \"count\": 1\n  }\n]",
                "{\n  \"name\": \"One\",\n  \"count\": 1\n}");
    }
}
