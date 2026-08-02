package software.plusminus.check.types;

import software.plusminus.check.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@SuppressWarnings({"checkstyle:ClassTypeParameterName", "java:S2160", "java:S119"})
public abstract class AbstractArrayCheck<T, A, E extends AbstractCheck<T>,
        Self extends AbstractArrayCheck<T, A, E, Self>> extends AbstractObjectCheck<A> {

    private static final String SIZE_IS = "size is ";
    public static final String ELEMENT_AT_INDEX = "element at index ";

    protected BiFunction<T, List<String>, E> elementCheck;

    protected AbstractArrayCheck(@Nullable A actual, BiFunction<T, List<String>, E> elementCheck) {
        super(actual);
        this.elementCheck = elementCheck;
    }

    protected AbstractArrayCheck(@Nullable A actual, List<String> levels,
                                 BiFunction<T, List<String>, E> elementCheck) {
        super(actual, levels);
        this.elementCheck = elementCheck;
    }

    protected abstract int size();

    protected abstract List<T> actualList();

    protected abstract T get(int index);

    @Override
    public void is(String expected) {
        if (actual() == null || size() != 1) {
            super.is(expected);
            return;
        }
        try {
            super.is(expected);
        } catch (AssertionError firstError) {
            try {
                at(0).is(check -> check.isString(expected));
            } catch (AssertionError ignored) {
                throw firstError;
            }
        }
    }

    protected void is(Object... expectedElements) {
        isNotNull();
        hasSize(expectedElements.length);
        for (int i = 0; i < expectedElements.length; i++) {
            int index = i;
            if (expectedElements[i] instanceof String) {
                at(i).is(check -> check.isString((String) expectedElements[index]));
            } else {
                at(i).is(check -> check.isLike(expectedElements[index]));
            }
        }
    }

    public void isEmpty() {
        isNotNull();
        if (size() != 0) {
            fail("not empty", "empty");
        }
    }

    public Self isNotEmpty() {
        isNotNull();
        if (size() == 0) {
            fail("empty", "not empty");
        }
        return self();
    }

    public Self hasSize(int expectedSize) {
        isNotNull();
        if (size() != expectedSize) {
            fail(SIZE_IS + size(), SIZE_IS + expectedSize);
        }
        return self();
    }

    public Self contains(Object... expectedElements) {
        isNotNull();
        List<T> remaining = new ArrayList<>(actualList());
        List<Object> expected = new ArrayList<>(Arrays.asList(expectedElements));
        removeIntersections(remaining, expected);
        if (!expected.isEmpty()) {
            fail("does not contain " + StringUtil.toString(expected), "contains all elements");
        }
        return self();
    }

    @SafeVarargs
    public final Self contains(Consumer<E>... elementChecks) {
        isNotNull();
        List<T> remaining = new ArrayList<>(actualList());
        List<Consumer<E>> remainingChecks = new ArrayList<>(Arrays.asList(elementChecks));
        Iterator<Consumer<E>> checkIterator = remainingChecks.iterator();
        while (checkIterator.hasNext()) {
            Consumer<E> consumer = checkIterator.next();
            Iterator<T> elementIterator = remaining.iterator();
            while (elementIterator.hasNext()) {
                T element = elementIterator.next();
                if (predicate(element, levels(), elementCheck, consumer)) {
                    elementIterator.remove();
                    checkIterator.remove();
                    break;
                }
            }
        }
        if (!remainingChecks.isEmpty()) {
            fail("does not contain " + remainingChecks.size() + " matching element(s)",
                    "contains elements matching all checks");
        }
        return self();
    }

    public Self allMatch(Predicate<T> predicate) {
        int index = operations().firstNotMatching(predicate);
        if (index >= 0) {
            fail(ELEMENT_AT_INDEX + index + " does not match", "all elements match");
        }
        return self();
    }

    public Self noneMatch(Predicate<T> predicate) {
        int index = operations().firstMatching(predicate);
        if (index >= 0) {
            fail(ELEMENT_AT_INDEX + index + " matches", "no elements match");
        }
        return self();
    }

    public Self hasNoDuplicates() {
        int[] duplicate = operations().firstDuplicate();
        if (duplicate != null) {
            fail(ELEMENT_AT_INDEX + duplicate[0] + " duplicates index " + duplicate[1],
                    "all elements are unique");
        }
        return self();
    }

    public void containsExactly(Object... expectedElements) {
        isNotNull();
        List<T> remaining = new ArrayList<>(actualList());
        List<Object> expected = new ArrayList<>(Arrays.asList(expectedElements));
        removeIntersections(remaining, expected);
        if (!expected.isEmpty() || !remaining.isEmpty()) {
            String missedElements = expected.isEmpty()
                    ? null
                    : "does not contain: " + StringUtil.toString(expected);
            String unexpectedElements = remaining.isEmpty()
                    ? null
                    : "contains unexpected elements: " + StringUtil.toString(remaining);
            String actualMessage;
            if (missedElements != null && unexpectedElements == null) {
                actualMessage = missedElements;
            } else if (missedElements == null) {
                actualMessage = unexpectedElements;
            } else {
                actualMessage = missedElements + "\nbut " + unexpectedElements;
            }
            fail(actualMessage, "contains exactly elements");
        }
    }

    @CheckReturnValue
    protected LinkedCheck<T, E, Self> at(int index) {
        isNotNull();
        if (index < 0 || index >= size()) {
            fail(SIZE_IS + size(), "has element at index " + index);
        }
        T element = get(index);
        List<String> elementLevels = new ArrayList<>(levels());
        elementLevels.add("[" + index + "]");
        E check = elementCheck.apply(element, elementLevels);
        return new LinkedCheck<>(check, self());
    }

    protected ElementOperations<T> operations() {
        isNotNull();
        return new ElementOperations<>(actualList(), levels(), this::fail);
    }

    protected void checkElementsType(Class<?> type) {
        ElementOperations<T> operations = operations();
        int index = operations.firstNotInstanceOf(type);
        if (index >= 0) {
            fail(ELEMENT_AT_INDEX + index + " is " + operations.get(index).getClass().getName(),
                    "all elements are " + type.getName());
        }
    }

    @SuppressWarnings("unchecked")
    private Self self() {
        return (Self) this;
    }

    private void removeIntersections(List<T> actualElements, List<Object> expectedElements) {
        Iterator<T> actualIterator = actualElements.iterator();
        while (actualIterator.hasNext()) {
            T actualElement = actualIterator.next();
            Iterator<Object> expectedIterator = expectedElements.iterator();
            while (expectedIterator.hasNext()) {
                Object expectedElement = expectedIterator.next();
                if (matches(actualElement, expectedElement)) {
                    actualIterator.remove();
                    expectedIterator.remove();
                    break;
                }
            }
        }
    }

    private boolean matches(T element, Object value) {
        if (value instanceof String) {
            return predicate(element, levels(), ObjectCheck::new, c -> c.isString(value.toString()));
        }
        return predicate(element, levels(), ObjectCheck::new, c -> c.isLike(value));
    }
}
