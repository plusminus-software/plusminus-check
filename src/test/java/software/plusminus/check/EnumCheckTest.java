package software.plusminus.check;

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

    @Test
    public void isNullSuccess() {
        new EnumCheck<TestEnum>(null).isNull();
    }

    @Test
    public void isNullFail() {
        assertFail(() -> new EnumCheck<>(TestEnum.ONE).isNull(), "ONE", "null");
    }

    @Test
    public void isNotNullSuccess() {
        new EnumCheck<>(TestEnum.ONE).isNotNull();
    }

    @Test
    public void isNotNullFail() {
        assertFail(() -> new EnumCheck<TestEnum>(null).isNotNull(), "null", "not null");
    }
}
