package software.plusminus.check.types;

import software.plusminus.check.util.BytesUtil;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;

public class BytesCheck extends AbstractCheck<byte[]> {

    private static final String SIZE_IS = "size is ";

    public BytesCheck(@Nullable byte[] actual) {
        super(actual);
    }

    public BytesCheck(@Nullable byte[] actual, List<String> levels) {
        super(actual, levels);
    }

    @Override
    public void is(byte[] expected) {
        if (checkNull(expected)) {
            return;
        }
        if (!Arrays.equals(actual(), expected)) {
            fail(BytesUtil.toDisplay(actual()), BytesUtil.toDisplay(expected));
        }
    }

    public void is(String expected) {
        if (checkNull(expected)) {
            return;
        }
        String text = new String(actual(), StandardCharsets.UTF_8);
        new StringCheck(text, levels()).is(expected);
    }

    @Override
    public void isNull() {
        super.isNull();
    }

    @Override
    public BytesCheck isNotNull() {
        super.isNotNull();
        return this;
    }

    @Override
    public BytesCheck isNot(byte[] unexpected) {
        super.isNot(unexpected);
        return this;
    }

    public void isEmpty() {
        isNotNull();
        if (actual().length != 0) {
            fail(SIZE_IS + actual().length, "empty");
        }
    }

    public BytesCheck isNotEmpty() {
        isNotNull();
        if (actual().length == 0) {
            fail("empty", "not empty");
        }
        return this;
    }

    public BytesCheck hasSize(int expectedSize) {
        isNotNull();
        if (actual().length != expectedSize) {
            fail(SIZE_IS + actual().length, SIZE_IS + expectedSize);
        }
        return this;
    }

    public BytesCheck startsWith(byte[] expectedPrefix) {
        isNotNull();
        byte[] actual = actual();
        boolean starts = expectedPrefix.length <= actual.length
                && Arrays.equals(Arrays.copyOf(actual, expectedPrefix.length), expectedPrefix);
        if (!starts) {
            fail(BytesUtil.toDisplay(actual), "starts with " + BytesUtil.toDisplay(expectedPrefix));
        }
        return this;
    }
}
