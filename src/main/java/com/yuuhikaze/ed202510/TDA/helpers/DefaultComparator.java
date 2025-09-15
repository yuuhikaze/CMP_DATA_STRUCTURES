package com.yuuhikaze.ed202510.TDA.helpers;

import java.util.Comparator;

public class DefaultComparator<E extends Comparable<E>> implements Comparator<E> {

    @Override
    public int compare(E arg0, E arg1) {
        return ((Comparable<E>) arg0).compareTo(arg1);
    }
}
