package com.yuuhikaze.ed202510.TDA;

import java.util.Iterator;
import com.yuuhikaze.ed202510.TDA.interfaces.List;

public class ArrayList<E> implements List<E> {

    private static final int CAPACITY = 16; // default capacity
    private E[] data;
    private int size;

    public ArrayList() {
        this(CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public ArrayList(int capacity) {
        this.size = 0;
        data = (E[]) new Object[capacity]; // safe cast
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
        checkIndex(i, size);
        return data[i];
    }

    @Override
    public E set(int i, E element) throws IndexOutOfBoundsException {
        checkIndex(i, size);
        E tmp = data[i];
        data[i] = element;
        return tmp;
    }

    @Override
    public void insert(int i, E element) throws IndexOutOfBoundsException {
        checkIndex(i, size + 1);
        if (size == data.length)
            throw new IllegalArgumentException("Array is full");
        for (int k = size - 1; k >= i; k--)
            data[k + 1] = data[k];
        data[i] = element;
        this.size++;
    }

    @Override
    public void add(E element) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public E remove(int i) throws IndexOutOfBoundsException {
        checkIndex(i, size);
        E tmp = data[i];
        for (int k = i; k < size; k++)
            data[k] = data[k + 1];
        data[size - 1] = null;
        this.size--;
        return tmp;
    }

    protected void checkIndex(int i, int n) throws IndexOutOfBoundsException {
        if (i < 0 || i >= n)
            throw new IndexOutOfBoundsException("Illegal index");
    }

    @Override
    public Iterator<E> iterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'iterator'");
    }
}
