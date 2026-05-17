package software.plusminus.check;

import lombok.AllArgsConstructor;

import java.util.function.Consumer;

@AllArgsConstructor
public class LinkedCheck<T, C extends AbstractCheck<T>, P extends AbstractCheck<?>> {

    private C check;
    private P previous;

    public P is(T expected) {
        check.is(expected);
        return previous;
    }

    public P is(Consumer<C> check) {
        check.accept(this.check);
        return previous;
    }
}
