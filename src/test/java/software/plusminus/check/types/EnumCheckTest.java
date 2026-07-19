package software.plusminus.check.types;

import lombok.Data;
import org.junit.Test;
import software.plusminus.check.fixtures.TestEnum;

import static software.plusminus.check.Checks.check;
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
    public void fieldWithGetterOk() {
        TestTask task = new TestTask(TestEnum.ONE);
        check(task).field(TestTask::getType).is(TestEnum.ONE);
    }

    @Test
    public void fieldWithGetterFail() {
        TestTask task = new TestTask(TestEnum.ONE);
        assertFail(() -> check(task).field(TestTask::getType).is(TestEnum.TWO),
                "type ", "ONE", "TWO");
    }

    @Test
    public void fieldWithGetterNullOk() {
        TestTask task = new TestTask(null);
        check(task).field(TestTask::getType).is(c -> c.isNull());
    }

    @Data
    private static class TestTask {

        private TestEnum type;

        TestTask(TestEnum type) {
            this.type = type;
        }
    }
}
