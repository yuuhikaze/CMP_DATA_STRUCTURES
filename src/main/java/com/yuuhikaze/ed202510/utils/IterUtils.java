package com.yuuhikaze.ed202510.utils;

import java.util.Iterator;

public class IterUtils {

    // @source: https://www.geeksforgeeks.org/java/convert-iterator-to-iterable-in-java
    public static <T> Iterable<T> getIterableFromIterator(Iterator<T> iterator) {
        return () -> iterator;
    }
}
