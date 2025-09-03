package com.yuuhikaze.ed202510.TDA;

import java.util.NoSuchElementException;

/**
 * A queue implementation inheriting from SinglyLinkedList and implementing Queue interface.
 */
public class LinkedQueue<E> extends SinglyLinkedList<E> implements Queue<E> {

    @Override
    public void enqueue(E element) {
        addLast(element);
    }

    @Override
    public E dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return removeFirst();
    }

    @Override
    public E first() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return first();
    }
}
