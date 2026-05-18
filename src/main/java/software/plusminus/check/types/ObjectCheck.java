package software.plusminus.check.types;

import software.plusminus.check.getter.AbstractGetter;
import software.plusminus.check.object.ObjectCheckField;
import software.plusminus.check.object.ObjectCheckType;
import software.plusminus.check.util.FieldCoverage;
import software.plusminus.check.util.ObjectUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiFunction;
import java.util.function.Function;

@SuppressWarnings({"unchecked", "java:S2160"})
public class ObjectCheck<T> extends AbstractObjectCheck<T> implements ObjectCheckType, ObjectCheckField<T> {

    private Set<String> checkedFields;

    public ObjectCheck(T actual) {
        super(actual);
        this.checkedFields = new HashSet<>();
    }

    public ObjectCheck(T actual, List<String> levels) {
        super(actual, levels);
        this.checkedFields = new HashSet<>();
    }

    @Override
    public <X, C extends AbstractCheck<X>> C isType(Class<X> type,
                                                    BiFunction<X, List<String>, C> checkBuilder) {
        checkInstanceOf(type);
        return checkBuilder.apply(type.cast(actual()), levels());
    }

    public <X> ObjectCheck<X> isInstanceOf(Class<X> expectedType) {
        checkInstanceOf(expectedType);
        return new ObjectCheck<>(expectedType.cast(actual()), levels());
    }

    @Override
    public void allFieldsChecked(FieldCoverage coverage) {
        if (actual() == null) {
            throw new AssertionError("Cannot verify checked fields: actual is null");
        }
        Class<?> clazz = actual().getClass();
        Set<String> required = new TreeSet<>(ObjectUtils.declaredFieldNames(clazz));
        if (coverage == FieldCoverage.WITH_GETTERS_ONLY) {
            required.removeIf(name -> !ObjectUtils.hasGetter(clazz, name));
        }
        required.removeAll(checkedFields);
        if (!required.isEmpty()) {
            fail("there are not checked fields: " + required, "all fields were checked");
        }
    }

    @Override
    public <X> LinkedCheck<X, ObjectCheck<X>, ObjectCheck<T>> field(String fieldName) {
        isNotNull();
        T actual = actual();
        Object value = ObjectUtils.readField(actual, fieldName);
        checkedFields.add(fieldName);
        return new LinkedCheck<>(new ObjectCheck<>((X) value, levels()), this);
    }

    @Override
    public <V, C extends AbstractCheck<V>> LinkedCheck<V, C, ObjectCheck<T>> fieldOf(
            AbstractGetter<T, V> getter,
            BiFunction<V, List<String>, C> checkBuilder) {
        isNotNull();
        V value = getter.apply(actual());
        C check = checkBuilder.apply(value, levels(getter));
        return new LinkedCheck<>(check, this);
    }

    @Override
    public <V, C extends AbstractCheck<V>> LinkedCheck<V, C, ObjectCheck<T>> fieldOf(
            Serializable getter,
            Function<T, V> valueProvider,
            BiFunction<V, List<String>, C> checkBuilder) {
        isNotNull();
        V value = valueProvider.apply(actual());
        C check = checkBuilder.apply(value, levels(getter));
        return new LinkedCheck<>(check, this);
    }

    private List<String> levels(Serializable getter) {
        Field field = ObjectUtils.toField(getter);
        checkedFields.add(field.getName());
        List<String> levels = new ArrayList<>(levels());
        levels.add("." + field.getName());
        return levels;
    }

    @SuppressWarnings("java:S2259")
    private void checkInstanceOf(Class<?> expectedType) {
        T actual = actual();
        if (actual == null) {
            fail("null", "an instance of " + expectedType.getName());
        }
        if (!expectedType.isAssignableFrom(actual.getClass())) {
            fail("class of the object is " + actual.getClass().getName(),
                    "class of the object is an instance of " + expectedType.getName());
        }
    }
}
