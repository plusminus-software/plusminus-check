package software.plusminus.check.types;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;
import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings({"java:S2699", "java:S1874"})
public class AbstractCheckTest {

    private String actual = "hello";
    private AbstractCheck<String> check;
    private AbstractCheck<String> nullCheck;

    @SuppressWarnings("unchecked")
    @Before
    public void before() {
        check = mock(AbstractCheck.class, withSettings()
                .useConstructor(actual)
                .defaultAnswer(CALLS_REAL_METHODS));
        nullCheck = mock(AbstractCheck.class, withSettings()
                .useConstructor((String) null)
                .defaultAnswer(CALLS_REAL_METHODS));
    }

    @Test
    public void isOk() {
        check.is("hello");
    }

    @Test
    public void isFail() {
        assertFail(() -> check.is("world"), "hello", "world");
    }

    @Test
    public void actualNullOk() {
        nullCheck.is(null);
    }

    @Test
    public void actualNullFail() {
        assertFail(() -> nullCheck.is("x"), "null", "x");
    }

    @Test
    public void isStringOk() {
        check.isString("hello");
    }

    @Test
    public void isStringFail() {
        assertFail(() -> check.isString("world"), "hello", "world");
    }

    @Test
    public void isNullOk() {
        nullCheck.isNull();
    }

    @Test
    public void isNullFail() {
        assertFail(() -> check.isNull(), "hello", "null");
    }

    @Test
    public void isNotNullOk() {
        check.isNotNull();
    }

    @Test
    public void isNotNullFail() {
        assertFail(() -> nullCheck.isNotNull(), "null", "not null");
    }

    @Test
    public void isSameOk() {
        check.isSame(actual);
    }

    @Test
    public void isSameFail() {
        assertFail(() -> check.isSame("other"), "hello", "same as other");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void equalsThrowsException() {
        AbstractCheck<String> abstractCheck = new AbstractCheck<String>(actual) {};
        abstractCheck.equals(abstractCheck);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void hashCodeThrowsException() {
        AbstractCheck<String> abstractCheck = new AbstractCheck<String>(actual) {};
        abstractCheck.hashCode();
    }
}
