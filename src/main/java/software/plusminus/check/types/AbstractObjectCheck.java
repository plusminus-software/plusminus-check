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

    @Override
    public void isLike(Object expected) {
        super.isLike(expected);
    }

    @Override
    public void isNull() {
        super.isNull();
    }

    @Override
    public AbstractObjectCheck<T> isNotNull() {
        super.isNotNull();
        return this;
    }

    @Override
    public AbstractObjectCheck<T> isNot(T unexpected) {
        super.isNot(unexpected);
        return this;
    }

    public void isEqual(T expected) {
        check(expected, this::checkNull, this::checkType, this::checkEquals);
    }

    @Override
    public void isSame(T expected) {
        super.isSame(expected);
    }

    public AbstractObjectCheck<T> isSameTypeAs(T expected) {
        checkType(expected);
        return this;
    }

    public AbstractObjectCheck<T> isType(Class<?> expectedType) {
        checkType(expectedType);
        return this;
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
}
