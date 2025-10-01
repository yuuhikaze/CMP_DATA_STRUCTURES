package com.yuuhikaze.ed202510.TDA.interfaces;

/**
 * A generic interface representing a position in a LinkedList
 *
 * @param <E> type of element the position references
 */
public interface Position<E> {

    /**
     * Returns the element stored at this position
     *
     * @return the stored element
     * @throws IllegalStateException if position is no longer valid
     */
    E getElement() throws IllegalStateException;
}
