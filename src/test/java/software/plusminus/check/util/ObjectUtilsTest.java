package software.plusminus.check.util;

import org.junit.Test;
import software.plusminus.check.fixtures.TestObject;
import software.plusminus.check.getter.AbstractGetter;

import java.lang.reflect.Field;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("java:S2699")
public class ObjectUtilsTest {

    @Test
    public void toFieldFromGetter() {
        AbstractGetter<TestObject, String> getter = TestObject::getName;
        Field field = ObjectUtils.toField(getter);
        assertEquals("name", field.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void toFieldRejectsInlineLambda() {
        AbstractGetter<TestObject, String> getter = object -> object.getName().toUpperCase();
        ObjectUtils.toField(getter);
    }

    @Test
    public void declaredFieldNames() {
        Set<String> names = ObjectUtils.declaredFieldNames(TestObject.class);
        assertTrue(names.contains("name"));
        assertTrue(names.contains("count"));
    }

    @Test
    public void findField() {
        Field field = ObjectUtils.findField(TestObject.class, "count");
        assertEquals("count", field.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findFieldMissing() {
        ObjectUtils.findField(TestObject.class, "missing");
    }

    @Test
    public void readField() {
        Object value = ObjectUtils.readField(new TestObject("a", 1), "name");
        assertEquals("a", value);
    }

    @Test
    public void hasGetter() {
        assertTrue(ObjectUtils.hasGetter(TestObject.class, "name"));
        assertFalse(ObjectUtils.hasGetter(TestObject.class, "missing"));
        assertFalse(ObjectUtils.hasGetter(TestObject.class, ""));
    }
}
