package com.yuuhikaze.ed202510.TDA;

import java.util.Comparator;
import com.yuuhikaze.ed202510.TDA.helpers.DefaultComparator;
import com.yuuhikaze.ed202510.TDA.interfaces.Entry;
import com.yuuhikaze.ed202510.TDA.interfaces.PriorityQueue;

public class AbstractPriorityQueue<K extends Comparable<K>, V> implements PriorityQueue<K, V> {
    protected static class PQEntry<K, V> implements Entry<K, V> {
        private K key;
        private V value;

        public PQEntry(K k, V v) {
            this.key = k;
            this.value = v;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }
        
        protected void setKey(K key) {
            this.key = key;
        }
        
        protected void setValue(V value) {
            this.value = value;
        }
    }
    private Comparator<K> comparator;

    protected AbstractPriorityQueue(Comparator<K> comparator) {
        this.comparator = comparator;
    }
    
    protected AbstractPriorityQueue() {
        this(new DefaultComparator<K>());
    }
    
    protected int compare(Entry<K,V> e0, Entry<K,V> e1) {
        return comparator.compare(e0.getKey(), e1.getKey());
    }
    
    protected boolean checkKey(K key) throws IllegalArgumentException {
        try {
            return(comparator.compare(key, key) == 0);
        } catch (Exception e) {
            throw new IllegalArgumentException("Incompatible key");
        }
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'size'");
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public Entry<K, V> min() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'min'");
    }

    @Override
    public Entry<K, V> removeMin() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeMin'");
    }
}
