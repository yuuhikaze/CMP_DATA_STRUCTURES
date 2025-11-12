package com.yuuhikaze.ed202510.TDA.interfaces;

import java.util.Iterator;

/**
 * A generic interface representing a sequence of elements characterized by positions
 *
 * @param <E> type of elements in the list
 */
public interface PositionalList<E> extends Iterable<Position<E>> {

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
     * Returns the first position in the list
     *
     * @return first position in the list, or {@code null} if empty
     */
    Position<E> first();

    // O(1)
    /**
     * Returns the last position in the list
     *
     * @return last position in the list, or {@code null} if empty
     */
    Position<E> last();

    // O(1)
    /**
     * Returns position before given position
     *
     * @param p represents a position
     * @return position before p, or {@code null} if empty
     * @throws IllegalArgumentException TODO
     */
    Position<E> before(Position<E> position) throws IllegalArgumentException;

    // O(1)
    /**
     * Returns position after given position
     *
     * @param p represents a position
     * @return position after p, or {@code null} if empty
     * @throws IllegalArgumentException TODO
     */
    Position<E> after(Position<E> position) throws IllegalArgumentException;

    // O(1)
    /**
     * Adds element at the front of the list and returns its new position
     *
     * @param element is the entity to add
     * @return the new position of the added element
     */
    Position<E> addFirst(E element);

    // O(1)
    /**
     * Adds element at the back of the list and returns its new position
     *
     * @param element is the entity to add
     * @return the new position of the added element
     */
    Position<E> addLast(E element);

    // O(1)
    /**
     * Adds element before given position and returns its new position
     *
     * @param position is the given position
     * @param element is the entity to add
     * @return the new position of the added element
     */
    Position<E> addBefore(Position<E> position, E element);

    // O(1)
    /**
     * Adds element after given position and returns its new position
     *
     * @param position is the given position
     * @param element is the entity to add
     * @return the new position of the added element
     */
    Position<E> addAfter(Position<E> position, E element);

    // O(1)
    /**
     * Replaces the element at given position with provided element
     *
     * @param position is the given position
     * @param element is the element to be set in-place
     * @return the replaced element
     * @throws IllegalArgumentException TODO
     */
    E set(Position<E> position, E element) throws IllegalArgumentException;

    // O(1)
    /**
     * Removes element at given position
     *
     * @param position is the given position
     * @return the removed element
     * @throws IllegalArgumentException TODO
     */
    E remove(Position<E> position) throws IllegalArgumentException;

    // Iterate over elements of PositionalList instead of the positions itself
    public Iterator<E> elementIterator();
}
