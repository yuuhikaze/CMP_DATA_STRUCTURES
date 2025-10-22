package com.yuuhikaze.ed202510.TDA;

import com.yuuhikaze.ed202510.TDA.interfaces.Entry;
import java.util.Iterator;

/**
 * HashMap implementation using separate chaining for collision resolution.
 * This implementation uses an array of ArrayList buckets to store entries.
 *
 * @param <K> the type of keys
 * @param <V> the type of values
 */
public class HashMap<K, V> extends AbstractMap<K, V> {

    private Vector<MapEntry<K, V>>[] table; // Array of buckets
    private int size; // Number of entries in the map
    private int capacity; // Capacity of the table
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    /**
     * Constructs an empty HashMap with default capacity
     */
    public HashMap() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructs an empty HashMap with specified capacity
     *
     * @param capacity initial capacity
     */
    @SuppressWarnings("unchecked")
    public HashMap(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.table = (Vector<MapEntry<K, V>>[]) new Vector[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new Vector<>();
        }
    }

    /**
     * Hash function to compute bucket index
     */
    private int hashFunction(K key) {
        if (key == null) {
            return 0;
        }
        return Math.abs(key.hashCode()) % capacity;
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
    public V get(K key) {
        int bucket = hashFunction(key);
        Vector<MapEntry<K, V>> bucketList = table[bucket];

        for (int i = 0; i < bucketList.size(); i++) {
            MapEntry<K, V> entry = bucketList.get(i);
            if (keysEqual(entry.getKey(), key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        int bucket = hashFunction(key);
        Vector<MapEntry<K, V>> bucketList = table[bucket];

        // Check if key already exists
        for (int i = 0; i < bucketList.size(); i++) {
            MapEntry<K, V> entry = bucketList.get(i);
            if (keysEqual(entry.getKey(), key)) {
                V oldValue = entry.getValue();
                entry.setValue(value);
                return oldValue;
            }
        }

        // Add new entry
        bucketList.add(new MapEntry<>(key, value));
        size++;

        // Check if we need to resize
        if ((double) size / capacity > LOAD_FACTOR) {
            resize();
        }

        return null;
    }

    @Override
    public V remove(K key) {
        int bucket = hashFunction(key);
        Vector<MapEntry<K, V>> bucketList = table[bucket];

        for (int i = 0; i < bucketList.size(); i++) {
            MapEntry<K, V> entry = bucketList.get(i);
            if (keysEqual(entry.getKey(), key)) {
                V value = entry.getValue();
                bucketList.remove(i);
                size--;
                return value;
            }
        }
        return null;
    }

    /**
     * Checks if two keys are equal
     */
    private boolean keysEqual(K key1, K key2) {
        if (key1 == null && key2 == null) {
            return true;
        }
        if (key1 == null || key2 == null) {
            return false;
        }
        return key1.equals(key2);
    }

    /**
     * Resizes the hash table when load factor is exceeded
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = capacity * 2;
        Vector<MapEntry<K, V>>[] oldTable = table;

        // Create new table
        table = (Vector<MapEntry<K, V>>[]) new Vector[newCapacity];
        for (int i = 0; i < newCapacity; i++) {
            table[i] = new Vector<>();
        }

        capacity = newCapacity;
        size = 0;

        // Rehash all entries
        for (int i = 0; i < oldTable.length; i++) {
            Vector<MapEntry<K, V>> bucket = oldTable[i];
            for (int j = 0; j < bucket.size(); j++) {
                MapEntry<K, V> entry = bucket.get(j);
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public Iterable<Entry<K, V>> entrySet() {
        return new EntryIterable();
    }

    private class EntryIterable implements Iterable<Entry<K, V>> {
        @Override
        public Iterator<Entry<K, V>> iterator() {
            return new EntryIterator();
        }
    }

    private class EntryIterator implements Iterator<Entry<K, V>> {
        private int bucketIndex = 0;
        private int entryIndex = 0;
        private int count = 0;

        public EntryIterator() {
            advanceToNextEntry();
        }

        private void advanceToNextEntry() {
            while (bucketIndex < capacity) {
                if (entryIndex < table[bucketIndex].size()) {
                    return;
                }
                bucketIndex++;
                entryIndex = 0;
            }
        }

        @Override
        public boolean hasNext() {
            return count < size;
        }

        @Override
        public Entry<K, V> next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            MapEntry<K, V> entry = table[bucketIndex].get(entryIndex);
            entryIndex++;
            count++;
            advanceToNextEntry();

            return entry;
        }
    }

    /**
     * Checks if the map contains the specified key
     *
     * @param key the key to check
     * @return true if key exists, false otherwise
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Clears all entries from the map
     */
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            table[i] = new Vector<>();
        }
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (Entry<K, V> entry : entrySet()) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue());
            first = false;
        }
        sb.append("}");
        return sb.toString();
    }
}
