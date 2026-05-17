package software.plusminus.check;

import java.util.List;
import javax.annotation.Nullable;

public class CharacterCheck extends PrimitiveCharacterCheck {

    public CharacterCheck(@Nullable Character actual) {
        super(actual);
    }

    public CharacterCheck(@Nullable Character actual, List<String> levels) {
        super(actual, levels);
    }

    @Override
    public void isNull() {
        super.isNull();
    }

    @Override
    public void isNotNull() {
        super.isNotNull();
    }
}
