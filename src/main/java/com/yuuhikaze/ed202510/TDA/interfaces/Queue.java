package com.yuuhikaze.ed202510.TDA.interfaces;

/**
 * A generic Queue interface defining standard queue operations.
 */
public interface Queue<E> extends Iterable<E> {
    /**
     * Adds an element to the rear of the queue.
     */
    void enqueue(E element);

    /**
     * Removes and returns the first element of the queue.
     */
    E dequeue();

    /**
     * Returns, but does not remove, the first element of the queue.
     */
    E first();

    /**
     * Returns the number of elements in the queue.
     */
    int size();

    /**
     * Tests whether the queue is empty.
     */
    boolean isEmpty();
}
