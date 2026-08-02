package software.plusminus.check.types;

import java.util.List;
import javax.annotation.Nullable;

public class NullableCharacterCheck extends CharacterCheck {

    public NullableCharacterCheck(@Nullable Character actual) {
        super(actual);
    }

    public NullableCharacterCheck(@Nullable Character actual, List<String> levels) {
        super(actual, levels);
    }

    @Override
    public void isNull() {
        super.isNull();
    }

    @Override
    public NullableCharacterCheck isNotNull() {
        super.isNotNull();
        return this;
    }

    @Override
    public NullableCharacterCheck isNot(Character unexpected) {
        super.isNot(unexpected);
        return this;
    }
}
