package com.github.tachesimazzoca.android.example.preferences;

public class ArrayUtils {
    public static final int INDEX_NOT_FOUND = -1;

    public static <T> int indexOf(T[] keys, T key) {
        for (int i = 0; i < keys.length; i++) {
            if (key.equals(keys[i]))
                return i;
        }
        return INDEX_NOT_FOUND;
    }
}
