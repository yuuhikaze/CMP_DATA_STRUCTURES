package com.yuuhikaze.ed202510.TDA;

import java.util.NoSuchElementException;

/**
 * A stack implementation inheriting from SinglyLinkedList and implementing Stack interface.
 */
public class LinkedStack<E> extends SinglyLinkedList<E> implements Stack<E> {

    @Override
    public void push(E element) {
        addFirst(element);
    }

    @Override
    public E pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return removeFirst();
    }

    @Override
    public E top() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return first();
    }
}
