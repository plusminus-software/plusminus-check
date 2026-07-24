package software.plusminus.check.util;

import org.junit.Test;
import software.plusminus.check.fixtures.TestObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("java:S2699")
public class TypeUtilTest {

    @Test
    public void nullIsSimple() {
        assertTrue(TypeUtil.isSimpleType(null));
    }

    @Test
    public void scalarsAreSimple() {
        assertTrue(TypeUtil.isSimpleType("string"));
        assertTrue(TypeUtil.isSimpleType(42));
        assertTrue(TypeUtil.isSimpleType(LocalDate.now()));
    }

    @Test
    public void containersAreNotSimple() {
        assertFalse(TypeUtil.isSimpleType(new ArrayList<>()));
        assertFalse(TypeUtil.isSimpleType(new HashMap<>()));
        assertFalse(TypeUtil.isSimpleType(Optional.empty()));
    }

    @Test
    public void arrayIsNotSimple() {
        assertFalse(TypeUtil.isSimpleType(new int[]{1}));
    }

    @Test
    public void userTypeIsNotSimple() {
        assertFalse(TypeUtil.isSimpleType(new TestObject("a", 1)));
    }
}
