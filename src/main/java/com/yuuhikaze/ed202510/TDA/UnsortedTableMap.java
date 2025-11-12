package com.yuuhikaze.ed202510.TDA;

import com.yuuhikaze.ed202510.TDA.interfaces.Entry;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A Map implementation adapting ArrayList
 *
 * <p>
 * Performance is crap, O(n) for `get`; use for educational purposes only
 */
public class UnsortedTableMap<K, V> extends AbstractMap<K, V> {

    private Vector<MapEntry<K, V>> table = new Vector<>();

    public UnsortedTableMap() {}

    private int findIndex(K key) {
        for (int j = 0; j < table.size(); j++)
            if (table.get(j).getKey().equals(key))
                return j;
        return -1;
    }

    @Override
    public int size() {
        return table.size();
    }

    @Override
    public V get(K key) {
        int j = findIndex(key);
        if (j == -1)
            return null;
        return table.get(j).getValue();
    }

    @Override
    public V put(K key, V value) {
        int j = findIndex(key);
        if (j == -1) {
            table.add(new MapEntry<>(key, value));
            return null;
        } else {
            return table.get(j).setValue(value);
        }
    }

    public V remove(K key) {
        int j = findIndex(key);
        int n = size();
        if (j == -1)
            return null;
        V tmp = table.get(j).getValue();
        if (j != n - 1)
            table.set(j, table.get(n - 1)); // relocate last entry to vacated slot
        table.remove(n - 1);
        return tmp;
    }

    private class EntryIterator implements Iterator<Entry<K, V>> {
        private int j = 0;

        public boolean hasNext() {
            return j < table.size();
        }

        public Entry<K, V> next() {
            if (j == table.size())
                throw new NoSuchElementException();
            return table.get(j++);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class EntryIterable implements Iterable<Entry<K, V>> {
        @Override
        public Iterator<Entry<K, V>> iterator() {
            return new EntryIterator();
        }
    }

    /**
     * Define default iterator (this)
     */
    public Iterable<Entry<K, V>> entrySet() {
        return new EntryIterable();
    }

    @Override
    public void clear() {
        table = new Vector<>();
    }
}
