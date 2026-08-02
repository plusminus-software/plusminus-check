package software.plusminus.check.util;

import lombok.experimental.UtilityClass;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import javax.annotation.Nullable;

@UtilityClass
public class BytesUtil {

    public String toDisplay(@Nullable byte[] bytes) {
        if (bytes == null) {
            return "null";
        }
        String text = toText(bytes);
        return text == null ? Arrays.toString(bytes) : text;
    }

    /**
     * Decodes the bytes as printable UTF-8 text.
     *
     * @param bytes content to decode
     * @return the decoded text, or null if the content is not printable UTF-8
     */
    @Nullable
    public String toText(byte[] bytes) {
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
}
