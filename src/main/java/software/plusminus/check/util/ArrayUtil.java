package software.plusminus.check.util;

import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;

@UtilityClass
public class ArrayUtil {

    @Nullable
    public Boolean[] box(@Nullable boolean[] actual) {
        if (actual == null) {
            return null;
        }
        Boolean[] boxed = new Boolean[actual.length];
        for (int i = 0; i < actual.length; i++) {
            boxed[i] = actual[i];
        }
        return boxed;
    }

    @Nullable
    public Character[] box(@Nullable char[] actual) {
        if (actual == null) {
            return null;
        }
        Character[] boxed = new Character[actual.length];
        for (int i = 0; i < actual.length; i++) {
            boxed[i] = actual[i];
        }
        return boxed;
    }

    @Nullable
    public Short[] box(@Nullable short[] actual) {
        if (actual == null) {
            return null;
        }
        Short[] boxed = new Short[actual.length];
        for (int i = 0; i < actual.length; i++) {
            boxed[i] = actual[i];
        }
        return boxed;
    }

    @Nullable
    public Integer[] box(@Nullable int[] actual) {
        if (actual == null) {
            return null;
        }
        Integer[] boxed = new Integer[actual.length];
        for (int i = 0; i < actual.length; i++) {
            boxed[i] = actual[i];
        }
        return boxed;
    }

    @Nullable
    public Long[] box(@Nullable long[] actual) {
        if (actual == null) {
            return null;
        }
        Long[] boxed = new Long[actual.length];
        for (int i = 0; i < actual.length; i++) {
            boxed[i] = actual[i];
        }
        return boxed;
    }

    @Nullable
    public Float[] box(@Nullable float[] actual) {
        if (actual == null) {
            return null;
        }
        Float[] boxed = new Float[actual.length];
        for (int i = 0; i < actual.length; i++) {
            boxed[i] = actual[i];
        }
        return boxed;
    }

    @Nullable
    public Double[] box(@Nullable double[] actual) {
        if (actual == null) {
            return null;
        }
        Double[] boxed = new Double[actual.length];
        for (int i = 0; i < actual.length; i++) {
            boxed[i] = actual[i];
        }
        return boxed;
    }
}
