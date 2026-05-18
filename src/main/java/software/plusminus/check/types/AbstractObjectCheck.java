package software.plusminus.check.types;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public class AbstractObjectCheck<T> extends AbstractCheck<T> {

    protected AbstractObjectCheck(@Nullable T actual) {
        super(actual);
    }

    protected AbstractObjectCheck(@Nullable T actual, List<String> levels) {
        super(actual, levels);
    }

    public void is(String expected) {
        super.isString(expected);
    }

    public void isLike(Object expected) {
        check(expected, this::checkNull, this::checkEmpty, this::checkEquals, this::checkJson);
    }

    @Override
    public void isNull() {
        super.isNull();
    }

    @Override
    public void isNotNull() {
        super.isNotNull();
    }

    public void isEqual(T expected) {
        check(expected, this::checkNull, this::checkType, this::checkEquals);
    }

    @Override
    public void isSame(T expected) {
        super.isSame(expected);
    }

    public void isType(T expected) {
        checkType(expected);
    }

    public void isType(Class<?> expectedType) {
        checkType(expectedType);
    }

    protected <O, C extends AbstractCheck<O>> boolean predicate(O object,
                                                                List<String> levels,
                                                                BiFunction<O, List<String>, C> checkBuilder,
                                                                Consumer<C> check) {
        C c = checkBuilder.apply(object, levels);
        try {
            check.accept(c);
            return true;
        } catch (AssertionError e) {
            return false;
        }
    }

    protected void not(Runnable runnable, String expected) {
        try {
            runnable.run();
        } catch (AssertionError e) {
            return;
        }
        fail("not " + expected, expected);
    }
}
