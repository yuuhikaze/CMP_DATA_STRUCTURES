package com.yuuhikaze.ed202510.E.E03._01;

import com.yuuhikaze.ed202510.TDA.interfaces.Entry;
import java.util.ArrayList;

// Custom AbstractHashMap
// Uses 3 as scale factor, 11 for prime number, 5 for shift
public abstract class AbstractHashMap_C<K, V> extends AbstractMap_O<K, V> {
    protected int n = 0;
    protected int capacity;
    private int prime = 11;
    private long scale = 3, shift = 5;

    public AbstractHashMap_C(int cap) {
        capacity = cap;
        createTable();
    }

    public AbstractHashMap_C() {
        this(17);
    }

    protected int hashValue(K key) {
        HashUtils<K> hu = new HashUtils<>();
        return hu.hashValue(key, capacity);
    }

    public int size() {
        return n;
    }

    public V get(K key) {
        return bucketGet(hashValue(key), key);
    }

    public V remove(K key) {
        return bucketRemove(hashValue(key), key);
    }

    public V put(K key, V value) {
        V answer = bucketPut(hashValue(key), key, value);
        if (n > capacity / 2)
            resize(2 * capacity - 1);
        return answer;
    }

    private void resize(int newCap) {
        ArrayList<Entry<K, V>> buﬀer = new ArrayList<>(n);
        for (Entry<K, V> e : entrySet())
            buﬀer.add(e);
        capacity = newCap;
        createTable();
        n = 0;
        for (Entry<K, V> e : buﬀer)
            put(e.getKey(), e.getValue());
    }

    protected abstract void createTable();

    protected abstract V bucketGet(int h, K k);

    protected abstract V bucketPut(int h, K k, V v);

    protected abstract V bucketRemove(int h, K k);

    @Override
    public void clear() {
        n = 0;
        createTable();
    }
}
