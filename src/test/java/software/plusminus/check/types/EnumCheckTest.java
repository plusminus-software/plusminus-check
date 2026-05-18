package software.plusminus.check.types;

import org.junit.Test;
import software.plusminus.check.fixtures.TestEnum;

import static software.plusminus.check.helper.Assertions.assertFail;

@SuppressWarnings("java:S2699")
public class EnumCheckTest {

    @Test
    public void isSuccess() {
        new EnumCheck<>(TestEnum.ONE).is(TestEnum.ONE);
    }

    @Test
    public void isFail() {
        assertFail(() -> new EnumCheck<>(TestEnum.ONE).is(TestEnum.TWO), "ONE", "TWO");
    }
}
