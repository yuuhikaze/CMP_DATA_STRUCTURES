package com.yuuhikaze.ed202510.TDA;

import com.yuuhikaze.ed202510.TDA.interfaces.Position;
import com.yuuhikaze.ed202510.TDA.interfaces.PositionalList;

public class SortUtils {

    // O(n^2)
    public static void insertionSort(PositionalList<Integer> list) {
        Position<Integer> marker = list.first();
        while (marker != list.last()) {
            Position<Integer> pivot = list.after(marker);
            int value = pivot.getElement(); // number to place
            if (value > marker.getElement()) { // pivot is already sorted
                marker = pivot;
            } else { // pivot needs to be realocated
                Position<Integer> walk = marker; // find leftmost item greater than value
                while (walk != list.first() && list.before(walk).getElement() > value)
                    walk = list.before(walk);
                list.remove(pivot); // remove pivot entry
                list.addBefore(walk, value); // reinsert value in front of walk
            }
        }
    }
}
