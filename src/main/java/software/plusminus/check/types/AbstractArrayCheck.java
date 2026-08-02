package software.plusminus.check.types;

import software.plusminus.check.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@SuppressWarnings({"checkstyle:ClassTypeParameterName", "java:S2160", "java:S119"})
public abstract class AbstractArrayCheck<T, A, E extends AbstractCheck<T>,
        Self extends AbstractArrayCheck<T, A, E, Self>> extends AbstractObjectCheck<A> {

    private static final String SIZE_IS = "size is ";

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
            } else if (missedElements == null && unexpectedElements != null) {
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

    protected <R> List<R> mapToList(Function<T, R> mapper) {
        isNotNull();
        List<T> elements = actualList();
        List<R> mapped = new ArrayList<>(elements.size());
        for (int i = 0; i < elements.size(); i++) {
            T element = elements.get(i);
            mapped.add(applyToElement(mapper, element, i));
        }
        return mapped;
    }

    protected List<T> filterToList(Predicate<T> predicate) {
        isNotNull();
        List<T> elements = actualList();
        List<T> filtered = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++) {
            T element = elements.get(i);
            if (applyToElement(predicate::test, element, i)) {
                filtered.add(element);
            }
        }
        return filtered;
    }

    protected List<T> sortToList(@Nullable Comparator<? super T> comparator) {
        isNotNull();
        List<T> sorted = new ArrayList<>(actualList());
        if (comparator == null) {
            checkNoNullElements(sorted);
        }
        try {
            sorted.sort(comparator);
        } catch (ClassCastException e) {
            fail("elements are not mutually comparable", "all elements are comparable");
        }
        return sorted;
    }

    protected void checkElementsType(Class<?> type) {
        isNotNull();
        List<T> elements = actualList();
        for (int i = 0; i < elements.size(); i++) {
            T element = elements.get(i);
            if (element != null && !type.isInstance(element)) {
                fail("element at index " + i + " is " + element.getClass().getName(),
                        "all elements are " + type.getName());
            }
        }
    }

    private void checkNoNullElements(List<T> elements) {
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i) == null) {
                fail("element at index " + i + " is null", "all elements are non-null");
            }
        }
    }

    @SuppressWarnings({"PMD.AvoidCatchingNPE", "java:S1696"})
    private <R> R applyToElement(Function<T, R> function, @Nullable T element, int index) {
        try {
            return function.apply(element);
        } catch (NullPointerException e) {
            if (element != null) {
                throw e;
            }
            fail("element at index " + index + " is null", "all elements are non-null");
            return null;
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
