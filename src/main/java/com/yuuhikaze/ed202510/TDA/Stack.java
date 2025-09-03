package com.yuuhikaze.ed202510.TDA;

/**
 * A generic Stack interface defining standard stack operations.
 */
public interface Stack<E> {
    /**
     * Inserts an element at the top of the stack.
     */
    void push(E element);

    /**
     * Removes and returns the element from the top of the stack.
     */
    E pop();

    /**
     * Returns, but does not remove, the element at the top of the stack.
     */
    E top();

    /**
     * Returns the number of elements in the stack.
     */
    int size();

    /**
     * Tests whether the stack is empty.
     */
    boolean isEmpty();
}

