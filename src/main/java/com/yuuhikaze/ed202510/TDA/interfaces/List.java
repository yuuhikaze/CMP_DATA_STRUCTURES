package com.yuuhikaze.ed202510.TDA.interfaces;

/**
 * A generic interface representing a sequence of elements
 *
 * @param <E> type of elements in the list
 */
public interface List<E> extends Iterable<E> {

    // O(1)
    /**
     * Returns the number of elements in list
     *
     * @return number of elements in list
     */
    int size();

    // O(1)
    /**
     * Checks whether list contains no elements
     *
     * @return {@code true} if list empty, {@code false} otherwise
     */
    boolean isEmpty();

    // O(1)
    /**
     * Returns the element at the specified index
     *
     * @param i is the index
     * @return element at position i
     * @throws IndexOutOfBoundsException if the index is not in range [0,size()-1]
     */
    E get(int i) throws IndexOutOfBoundsException;

    // O(1)
    /**
     * Replaces given element at the specified index
     *
     * @param i is the index
     * @param element is the entity to retrieve
     * @return replaced element at i
     * @throws IndexOutOfBoundsException if the index is not in range [0,size()-1]
     */
    E set(int i, E element) throws IndexOutOfBoundsException;

    // O(n)
    /**
     * Inserts element at the specified index
     *
     * @param i is the index
     * @param element is the entity to insert
     * @throws IndexOutOfBoundsException if the index is not in range [0,size()]
     */
    void insert(int i, E element) throws IndexOutOfBoundsException;

    // O(1) amortized
    /**
     * Adds element to the back of the list
     *
     * @param element is the entity to add
     */
    void add(E element);

    // O(n)
    /**
     * Removes element at the specified index
     *
     * @param i is the index
     * @return removed element at i
     * @throws IndexOutOfBoundsException if the index is not in range [0,size()-1]
     */
    E remove(int i) throws IndexOutOfBoundsException;
}
