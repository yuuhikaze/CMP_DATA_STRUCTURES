package com.yuuhikaze.ed202510.TDA;

import java.util.Iterator;
import com.yuuhikaze.ed202510.TDA.interfaces.List;

public class Vector<E> implements List<E> {

    private static final int DEFAULT_CAPACITY = 16;
    private E[] data;
    private int size;

    public Vector() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public Vector(int capacity) {
        this.size = 0;
        data = (E[]) new Object[capacity];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E get(int i) throws IndexOutOfBoundsException {
        return data[i];
    }

    @Override
    public E set(int i, E element) throws IndexOutOfBoundsException {
        E tmp = data[i];
        data[i] = element;
        return tmp;
    }

    @Override
    public void insert(int i, E element) throws IndexOutOfBoundsException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public void add(E element) {
        if (size == data.length)
            resize(2 * data.length);
        else if (size > data.length)
            throw new IllegalStateException("Size can't be greater than capacity");
        data[size] = element;
        this.size++;
    }
    
    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        E[] tmp = (E[]) new Object[capacity];
        for (int k = 0; k < size; k++)
            tmp[k] = data[k];
        data = tmp;
    }

    @Override
    public E remove(int i) throws IndexOutOfBoundsException {
        E tmp = data[i];
        for (int k = i; k < size; k++)
            data[k] = data[k + 1];
        data[size - 1] = null;
        this.size--;
        return tmp;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index != size - 1;
            }

            @Override
            public E next() {
                E element = data[index];
                this.index++;
                return element;
            }
            
        };
    }
}
