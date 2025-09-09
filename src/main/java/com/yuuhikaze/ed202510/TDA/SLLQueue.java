package com.yuuhikaze.ed202510.TDA;

import java.util.NoSuchElementException;
import com.yuuhikaze.ed202510.TDA.interfaces.Queue;

/**
 * A queue implementation adapting SinglyLinkedList and implementing Queue interface.
 */
public class SLLQueue<E> implements Queue<E> {
    private final SinglyLinkedList<E> list = new SinglyLinkedList<>();

    @Override
    public void enqueue(E element) {
        list.addLast(element);
    }

    @Override
    public E dequeue() {
        if (list.isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return list.removeFirst();
    }

    @Override
    public E first() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return list.first();
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }
}
