package com.yuuhikaze.ed202510.TDA.interfaces;

public interface Map<K, V> {
    /**
     * Returns size of map
     *
     * @return size of map
     */
    int size();

    /**
     * Checks whether map is empty
     *
     * @return {@code true} if map is empty, {@code false} otherwise
     */
    boolean isEmpty();

    /**
     * Returns the value associated with the given key
     *
     * @param key is the key to query
     * @return the value queried
     */
    V get(K key);

    /**
     * Creates an entry, if the key is already set, override and return previous value
     *
     * @param key is the target key
     * @param value is the value to place at the target key
     * @return returns the previous value is key exists
     */
    V put(K key, V value);

    /**
     * Removes the entry from the given key and returns the value of the removed entry
     *
     * @param key is the target key
     * @return the value from the deleted entry of target key
     */
    V remove(K key);

    /**
     * Returns iterable over keys
     *
     * @return iterable over keys
     */
    Iterable<K> keySet();

    /**
     * Returns iterable over values
     *
     * @return iterable over values
     */
    Iterable<V> values();

    /**
     * Returns iterable over entries
     *
     * @return iterable over entries
     */
    Iterable<Entry<K, V>> entrySet();

    /**
     * Removes all entries from the map
     */
    void clear();
}
