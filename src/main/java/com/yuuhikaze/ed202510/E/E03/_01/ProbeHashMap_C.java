package com.yuuhikaze.ed202510.E.E03._01;

import com.yuuhikaze.ed202510.TDA.interfaces.Entry;
import java.util.ArrayList;

public class ProbeHashMap_C<K, V> extends AbstractHashMap_C<K, V> {
    private MapEntry<K, V>[] table;
    private MapEntry<K, V> DEFUNCT = new MapEntry<>(null, null);

    public ProbeHashMap_C() {
        super();
    }

    public ProbeHashMap_C(int cap) {
        super(cap);
    }

    protected void createTable() {
        table = (MapEntry<K, V>[]) new MapEntry[capacity];
    }

    private boolean isAvailable(int j) {
        return (table[j] == null || table[j] == DEFUNCT);
    }

    private int findSlot(int h, K k) {
        int avail = -1;
        int j = h;
        do {
            if (isAvailable(j)) {
                if (avail == -1)
                    avail = j;
                if (table[j] == null)
                    break;
            } else if (table[j].getKey().equals(k))
                return j;
            j = (j + 1) % capacity;
        } while (j != h);
        return -(avail + 1);
    }

    protected V bucketGet(int h, K k) {
        int j = findSlot(h, k);
        if (j < 0)
            return null;
        // no match found
        return table[j].getValue();
    }

    protected V bucketPut(int h, K k, V v) {
        int j = findSlot(h, k);
        if (j >= 0)
            return table[j].setValue(v);
        table[-(j + 1)] = new MapEntry<>(k, v);
        n++;
        return null;
    }

    protected V bucketRemove(int h, K k) {
        int j = findSlot(h, k);
        if (j < 0)
            return null;
        V answer = table[j].getValue();
        table[j] = DEFUNCT;
        n--;
        return answer;
    }

    public Iterable<Entry<K, V>> entrySet() {
        ArrayList<Entry<K, V>> buffer = new ArrayList<>();
        for (int h = 0; h < capacity; h++)
            if (!isAvailable(h))
                buffer.add(table[h]);
        return buffer;
    }
}
