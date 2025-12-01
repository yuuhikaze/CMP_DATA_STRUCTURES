package com.yuuhikaze.ed202510.E.E03._01;

public class HashUtils<K> {
    private static final int PRIME = 11;
    private static final long SCALE = 3, SHIFT = 5;

    public int hashValue(K key, int capacity) {
        return (int) ((Math.abs(key.hashCode() * SCALE + SHIFT) % PRIME) % capacity);
    }
}
