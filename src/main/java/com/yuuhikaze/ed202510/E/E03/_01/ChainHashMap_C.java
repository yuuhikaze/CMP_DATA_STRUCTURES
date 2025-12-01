package com.yuuhikaze.ed202510.E.E03._01;

import com.yuuhikaze.ed202510.TDA.UnsortedTableMap;
import com.yuuhikaze.ed202510.TDA.interfaces.Entry;
import java.util.ArrayList;

public class ChainHashMap_C<K, V> extends AbstractHashMap_C<K, V> {
    private UnsortedTableMap<K, V>[] table;

    public ChainHashMap_C() {
        super();
    }

    public ChainHashMap_C(int cap) {
        super(cap);
    }

    protected void createTable() {
        table = (UnsortedTableMap<K, V>[]) new UnsortedTableMap[capacity];
    }

    protected V bucketGet(int h, K k) {
        UnsortedTableMap<K, V> bucket = table[h];
        if (bucket == null)
            return null;
        return bucket.get(k);
    }

    protected V bucketPut(int h, K k, V v) {
        UnsortedTableMap<K, V> bucket = table[h];
        if (bucket == null)
            bucket = table[h] = new UnsortedTableMap<>();
        int oldSize = bucket.size();
        V answer = bucket.put(k, v);
        n += (bucket.size() - oldSize);
        return answer;
    }

    protected V bucketRemove(int h, K k) {
        UnsortedTableMap<K, V> bucket = table[h];
        if (bucket == null)
            return null;
        int oldSize = bucket.size();
        V answer = bucket.remove(k);
        n -= (oldSize - bucket.size());
        return answer;
    }

    public Iterable<Entry<K, V>> entrySet() {
        ArrayList<Entry<K, V>> buffer = new ArrayList<>();
        for (int h = 0; h < capacity; h++)
            if (table[h] != null)
                for (Entry<K, V> entry : table[h].entrySet())
                    buffer.add(entry);
        return buffer;
    }
}
