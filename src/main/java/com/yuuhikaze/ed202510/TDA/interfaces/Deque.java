package com.yuuhikaze.ed202510.TDA.interfaces;

/**
 * A generic Double-ended queue interface defining standard stack operations.
 */
public interface Deque<E> extends Iterable<E> {

    void addFirst(E element);

    void addLast(E element);

    E removeFirst();

    E removeLast();

    E first();

    E last();

    int size();

    boolean isEmpty();
}
