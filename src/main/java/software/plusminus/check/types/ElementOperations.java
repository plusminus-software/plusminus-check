package software.plusminus.check.types;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;

/**
 * The element-level work behind {@link AbstractArrayCheck}: deriving new element lists
 * ({@code map}, {@code filter}, {@code flatMap}, {@code distinct}, {@code sort}) and
 * locating the element an assertion should blame. It holds a snapshot of the elements
 * and a failure sink, so the check classes are left with assertions rather than loops.
 *
 * <p>Index-returning methods answer with {@code -1} when nothing matches, leaving the
 * decision of whether that is a failure to the caller.
 */
@AllArgsConstructor
class ElementOperations<T> {

    public static final String ELEMENT_AT_INDEX = "element at index ";
    private List<T> elements;
    private List<String> levels;
    private BiConsumer<Object, Object> failure;

    <R> List<R> map(Function<T, R> mapper) {
        List<R> mapped = new ArrayList<>(elements.size());
        for (int i = 0; i < elements.size(); i++) {
            mapped.add(apply(mapper, elements.get(i), i));
        }
        return mapped;
    }

    List<T> filter(Predicate<T> predicate) {
        List<T> filtered = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++) {
            T element = elements.get(i);
            if (Boolean.TRUE.equals(apply(predicate::test, element, i))) {
                filtered.add(element);
            }
        }
        return filtered;
    }

    <R> List<R> flatMap(Function<T, ? extends Collection<R>> mapper) {
        List<R> flattened = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++) {
            Collection<R> mapped = apply(mapper, elements.get(i), i);
            if (mapped == null) {
                failure.accept(ELEMENT_AT_INDEX + i + " has no collection",
                        "all elements have a collection");
            } else {
                flattened.addAll(mapped);
            }
        }
        return flattened;
    }

    List<T> distinct() {
        List<T> distinct = new ArrayList<>();
        for (T element : elements) {
            if (!containsSame(distinct, element)) {
                distinct.add(element);
            }
        }
        return distinct;
    }

    List<T> sort(@Nullable Comparator<? super T> comparator) {
        List<T> sorted = new ArrayList<>(elements);
        if (comparator == null) {
            int nullIndex = firstNull();
            if (nullIndex >= 0) {
                failure.accept(ELEMENT_AT_INDEX + nullIndex + " is null", "all elements are non-null");
            }
        }
        try {
            sorted.sort(comparator);
        } catch (ClassCastException e) {
            failure.accept("elements are not mutually comparable", "all elements are comparable");
        }
        return sorted;
    }

    int firstNotMatching(Predicate<T> predicate) {
        for (int i = 0; i < elements.size(); i++) {
            if (Boolean.FALSE.equals(apply(predicate::test, elements.get(i), i))) {
                return i;
            }
        }
        return -1;
    }

    int firstMatching(Predicate<T> predicate) {
        for (int i = 0; i < elements.size(); i++) {
            if (Boolean.TRUE.equals(apply(predicate::test, elements.get(i), i))) {
                return i;
            }
        }
        return -1;
    }

    int firstNotInstanceOf(Class<?> type) {
        for (int i = 0; i < elements.size(); i++) {
            T element = elements.get(i);
            if (element != null && !type.isInstance(element)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Locates the first element repeating an earlier one.
     *
     * @return {@code {laterIndex, earlierIndex}}, or null when all elements are unique
     */
    @Nullable
    int[] firstDuplicate() {
        for (int i = 0; i < elements.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (isSame(elements.get(j), elements.get(i))) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    T get(int index) {
        return elements.get(index);
    }

    /**
     * Applies {@code function} to one element, reporting a {@link NullPointerException}
     * raised on a null element as an assertion failure naming the index. Anything else
     * propagates untouched, so a genuine bug inside the function is not mislabelled.
     */
    @SuppressWarnings({"PMD.AvoidCatchingNPE", "java:S1696"})
    private <R> R apply(Function<T, R> function, @Nullable T element, int index) {
        try {
            return function.apply(element);
        } catch (NullPointerException e) {
            if (element != null) {
                throw e;
            }
            failure.accept(ELEMENT_AT_INDEX + index + " is null", "all elements are non-null");
            return null;
        }
    }

    private boolean containsSame(List<T> candidates, @Nullable T element) {
        for (T candidate : candidates) {
            if (isSame(candidate, element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Compares structurally, the way {@code contains} compares elements, so that types
     * without {@code equals} still deduplicate sensibly.
     */
    private boolean isSame(@Nullable T left, @Nullable T right) {
        if (left == null || right == null) {
            return left == right;
        }
        try {
            new ObjectCheck<>(left, levels).isLike(right);
            return true;
        } catch (AssertionError e) {
            return false;
        }
    }

    private int firstNull() {
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i) == null) {
                return i;
            }
        }
        return -1;
    }
}
