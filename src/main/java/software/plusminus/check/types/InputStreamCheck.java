package software.plusminus.check.types;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;

/**
 * Checks an {@link InputStream} by content.
 * Note: streams are one-shot — every assertion that compares content reads
 * the actual stream (and an expected stream, if given) to the end, so a
 * stream can be checked only once and is unusable afterwards.
 */
public class InputStreamCheck extends AbstractCheck<InputStream> {

    private static final int BUFFER_SIZE = 8192;

    public InputStreamCheck(@Nullable InputStream actual) {
        super(actual);
    }

    public InputStreamCheck(@Nullable InputStream actual, List<String> levels) {
        super(actual, levels);
    }

    @Override
    public void is(InputStream expected) {
        if (checkNull(expected)) {
            return;
        }
        byte[] expectedBytes = read(expected);
        is(expectedBytes);
    }

    public void is(byte[] expected) {
        if (checkNull(expected)) {
            return;
        }
        byte[] actualBytes = read(actual());
        if (!Arrays.equals(actualBytes, expected)) {
            fail(toDisplay(actualBytes), toDisplay(expected));
        }
    }

    public void is(String expected) {
        if (checkNull(expected)) {
            return;
        }
        byte[] actualBytes = read(actual());
        String actualText = new String(actualBytes, StandardCharsets.UTF_8);
        new StringCheck(actualText, levels()).is(expected);
    }

    private String toDisplay(byte[] bytes) {
        String text = decodeText(bytes);
        return text == null ? Arrays.toString(bytes) : text;
    }

    @Nullable
    private String decodeText(byte[] bytes) {
        String text;
        try {
            text = StandardCharsets.UTF_8.newDecoder()
                    .onMalformedInput(CodingErrorAction.REPORT)
                    .onUnmappableCharacter(CodingErrorAction.REPORT)
                    .decode(ByteBuffer.wrap(bytes))
                    .toString();
        } catch (CharacterCodingException e) {
            return null;
        }
        boolean printable = text.chars().noneMatch(c -> Character.isISOControl(c)
                && c != '\n' && c != '\r' && c != '\t');
        return printable ? text : null;
    }

    private byte[] read(InputStream stream) {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[BUFFER_SIZE];
        try {
            int length = stream.read(buffer);
            while (length != -1) {
                result.write(buffer, 0, length);
                length = stream.read(buffer);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return result.toByteArray();
    }
}
