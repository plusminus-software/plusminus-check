package software.plusminus.check.types;

import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class LinkedCheckTest {

    private StringCheck stringCheck = new StringCheck("some string");
    private ObjectCheck<?> previous = mock(ObjectCheck.class);
    private LinkedCheck<String, StringCheck, ObjectCheck<?>> linkedCheck =
            new LinkedCheck<>(stringCheck, previous);

    @Test
    public void isReturnsPrevious() {
        ObjectCheck<?> actual = linkedCheck.is("some string");
        assertSame(previous, actual);
    }

    @Test
    public void consumerIsReturnsPrevious() {
        ObjectCheck<?> actual = linkedCheck.is(check -> check.is("some string"));
        assertSame(previous, actual);
    }

    @Test
    public void isFail() {
        assertFail(() -> linkedCheck.is("other string"), "some string", "other string");
    }

    @Test
    public void consumerIsFail() {
        assertFail(() -> linkedCheck.is(check -> check.is("other string")), "some string", "other string");
    }
}
