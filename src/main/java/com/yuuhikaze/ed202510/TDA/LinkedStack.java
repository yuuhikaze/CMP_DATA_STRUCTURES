package com.yuuhikaze.ed202510.TDA;

import java.util.NoSuchElementException;

/**
 * A stack implementation inheriting from SinglyLinkedList and implementing Stack interface.
 */
public class LinkedStack<E> implements Stack<E> {
    private final SinglyLinkedList<E> list = new SinglyLinkedList<>();

    @Override
    public void push(E element) {
        list.addFirst(element);
    }

    @Override
    public E pop() {
        if (list.isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return list.removeFirst();
    }

    @Override
    public E top() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return list.first();
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }
}
