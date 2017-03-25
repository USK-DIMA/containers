package ru.uskov.dmitry.common;

import java.util.Collection;
import java.util.List;

/**
 * Created by Dmitry on 11.03.2017.
 */
public class CollectionUtils {

    public static <T> T getFirst(List<T> collection) {
        if (collection != null && collection.size() > 0) {
            return collection.get(0);
        }
        return null;
    }

    public static boolean isEmoty(Collection collection) {
        return collection == null || collection.size() == 0;
    }
}
