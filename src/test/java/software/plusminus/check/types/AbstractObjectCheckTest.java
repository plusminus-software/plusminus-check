package software.plusminus.check.types;

import org.junit.Before;
import org.junit.Test;
import software.plusminus.check.fixtures.TestObject;

import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;
import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class AbstractObjectCheckTest {

    private TestObject actual = new TestObject("a", 1);
    private AbstractObjectCheck<TestObject> check;
    private AbstractObjectCheck<TestObject> nullCheck;

    @SuppressWarnings("unchecked")
    @Before
    public void before() {
        check = mock(AbstractObjectCheck.class, withSettings()
                .useConstructor(actual)
                .defaultAnswer(CALLS_REAL_METHODS));
        nullCheck = mock(AbstractObjectCheck.class, withSettings()
                .useConstructor((String) null)
                .defaultAnswer(CALLS_REAL_METHODS));
    }

    @Test
    public void isStringOk() {
        check.isString("{\n"
                + "  \"name\": \"a\",\n"
                + "  \"count\": 1\n"
                + "}");
    }

    @Test
    public void isStringFail() {
        assertFail(() -> check.isString("hello"), "{\n"
                + "  \"name\": \"a\",\n"
                + "  \"count\": 1\n"
                + "}",
                "hello");
    }

    @Test
    public void isLikeOk() {
        check.isLike(new TestObject("a", 1));
    }

    @Test
    public void isLikeFail() {
        assertFail(() -> check.isLike(new TestObject("b", 2)),
                "{\n  \"name\": \"a\",\n  \"count\": 1\n}",
                "{\n  \"name\": \"b\",\n  \"count\": 2\n}");
    }

    @Test
    public void isEqualOk() {
        check.isEqual(actual);
    }

    @Test
    public void isEqualFail() {
        assertFail(() -> check.isEqual(new TestObject("b", 2)),
                "{\n  \"name\": \"a\",\n  \"count\": 1\n}",
                "{\n  \"name\": \"b\",\n  \"count\": 2\n}");
    }

    @Test
    public void isTypeOk() {
        TestObject testObject = new TestObject("b", 2);
        check.isType(testObject);
    }

    @Test
    public void isTypeFail() {
        TestObject mock = mock(TestObject.class);
        assertFail(() -> check.isType(mock),
                "type software.plusminus.check.fixtures.TestObject",
                "type " + mock.getClass().getName());
    }

    @Test
    public void isTypeByClassOk() {
        check.isType(TestObject.class);
    }

    @Test
    public void isTypeByClassFail() {
        assertFail(() -> check.isType(String.class),
                "type software.plusminus.check.fixtures.TestObject",
                "type java.lang.String");
    }

    @Test
    public void isNullOk() {
        nullCheck.isNull();
    }

    @Test
    public void isNullFail() {
        assertFail(() -> check.isNull(),
                "{\n  \"name\": \"a\",\n  \"count\": 1\n}", "null");
    }

    @Test
    public void isNotNullOk() {
        check.isNotNull();
    }

    @Test
    public void isNotNullFail() {
        assertFail(() -> nullCheck.isNotNull(), "null", "not null");
    }
}
