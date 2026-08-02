package software.plusminus.check.types;

/**
 * Implemented by checks over a value made of parts — object fields, map entries,
 * collection or array elements. {@code allChecked} asserts every part was touched by
 * an assertion, so a part added later fails the test until it is checked too.
 *
 * <p>A part counts as checked when the check descended into it by name, key or position
 * ({@code field}, {@code valueAt}, {@code at}, {@code first}, {@code last}), when an
 * assertion compared the value as a whole ({@code is}, {@code isLike},
 * {@code containsExactly}), or when {@code contains} matched it.
 *
 * @author Taras Shpek
 */
public interface CoverageCheck {

    void allChecked();
}
