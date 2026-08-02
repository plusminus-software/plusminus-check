package software.plusminus.check.types;

import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static software.plusminus.check.Checks.check;
import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class BytesCheckTest {

    @Test
    public void isBytesOk() {
        check(bytes("abc")).is(bytes("abc"));
    }

    @Test
    public void isBytesFail() {
        assertFail(() -> check(bytes("abc")).is(bytes("abd")), "abc", "abd");
    }

    @Test
    public void isTextOk() {
        check(bytes("abc")).is("abc");
    }

    @Test
    public void isTextFail() {
        assertFail(() -> check(bytes("abc")).is("abd"), "abc", "abd");
    }

    @Test
    public void isBinaryFail() {
        byte[] actual = {0, 1};
        assertFail(() -> check(actual).is(new byte[]{0, 2}), "[0, 1]", "[0, 2]");
    }

    @Test
    public void isNullOk() {
        check((byte[]) null).isNull();
    }

    @Test
    public void isNotNullFail() {
        assertFail(() -> check((byte[]) null).isNotNull(), "null", "not null");
    }

    @Test
    public void isEmptyOk() {
        check(new byte[0]).isEmpty();
    }

    @Test
    public void isEmptyFail() {
        assertFail(() -> check(bytes("a")).isEmpty(), "size is 1", "empty");
    }

    @Test
    public void isNotEmptyOk() {
        check(bytes("a")).isNotEmpty();
    }

    @Test
    public void isNotEmptyFail() {
        assertFail(() -> check(new byte[0]).isNotEmpty(), "empty", "not empty");
    }

    @Test
    public void hasSizeOk() {
        check(bytes("abc")).hasSize(3);
    }

    @Test
    public void hasSizeFail() {
        assertFail(() -> check(bytes("abc")).hasSize(2), "size is 3", "size is 2");
    }

    @Test
    public void startsWithOk() {
        check(bytes("abc")).startsWith(bytes("ab"));
    }

    @Test
    public void startsWithFail() {
        assertFail(() -> check(bytes("abc")).startsWith(bytes("bc")), "abc", "starts with bc");
    }

    @Test
    public void startsWithLongerPrefixFail() {
        assertFail(() -> check(bytes("ab")).startsWith(bytes("abc")), "ab", "starts with abc");
    }

    private static byte[] bytes(String text) {
        return text.getBytes(StandardCharsets.UTF_8);
    }
}
