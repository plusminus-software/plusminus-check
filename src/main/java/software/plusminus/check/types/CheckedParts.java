package software.plusminus.check.types;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * Tracks which parts of a checked value — object fields, map keys, element indexes —
 * were touched by an assertion, so {@link CoverageCheck#allChecked()} can report the
 * ones that were not. Parts are identified by their rendered name; the owning check
 * decides how to render them, and must render them the same way in
 * {@link #mark(Object)} and {@link #verify(Collection, BiConsumer)}.
 */
class CheckedParts {

    private final String partsName;
    private final Set<String> checked = new LinkedHashSet<>();
    private boolean all;

    CheckedParts(String partsName) {
        this.partsName = partsName;
    }

    void mark(Object part) {
        checked.add(String.valueOf(part));
    }

    void markAll() {
        all = true;
    }

    void verify(Collection<String> required, BiConsumer<Object, Object> fail) {
        if (all) {
            return;
        }
        Set<String> notChecked = new LinkedHashSet<>(required);
        notChecked.removeAll(checked);
        if (!notChecked.isEmpty()) {
            fail.accept("there are not checked " + partsName + ": " + notChecked,
                    "all " + partsName + " were checked");
        }
    }
}
