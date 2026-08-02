package software.plusminus.check.util;

import org.junit.Test;

import static software.plusminus.check.Checks.check;

@SuppressWarnings("java:S2699")
public class ArrayUtilTest {

    @Test
    public void boxBooleans() {
        check(ArrayUtil.box(new boolean[]{true, false})).is(true, false);
    }

    @Test
    public void boxCharacters() {
        check(ArrayUtil.box(new char[]{'a', 'b'})).is('a', 'b');
    }

    @Test
    public void boxShorts() {
        check(ArrayUtil.box(new short[]{1, 2})).is((short) 1, (short) 2);
    }

    @Test
    public void boxIntegers() {
        check(ArrayUtil.box(new int[]{1, 2})).is(1, 2);
    }

    @Test
    public void boxLongs() {
        check(ArrayUtil.box(new long[]{1L, 2L})).is(1L, 2L);
    }

    @Test
    public void boxFloats() {
        check(ArrayUtil.box(new float[]{1.5f, 2.5f})).is(1.5f, 2.5f);
    }

    @Test
    public void boxDoubles() {
        check(ArrayUtil.box(new double[]{1.5d, 2.5d})).is(1.5d, 2.5d);
    }

    @Test
    public void boxEmpty() {
        check(ArrayUtil.box(new int[0])).isEmpty();
    }

    @Test
    public void boxNull() {
        check(ArrayUtil.box((int[]) null)).isNull();
    }

    @Test
    public void keepsComponentTypeSoDerivedChecksStayTyped() {
        check(ArrayUtil.box(new int[]{3, 1, 2}))
                .sorted()
                .isType(Integer[].class)
                .is(1, 2, 3);
    }
}
