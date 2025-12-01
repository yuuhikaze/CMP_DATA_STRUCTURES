package com.yuuhikaze.ed202510.CP.CP08;

import com.yuuhikaze.ed202510.TDA.ChainHashMap;
import com.yuuhikaze.ed202510.TDA.ProbeHashMap;
import com.yuuhikaze.ed202510.TDA.DoubleHashMap;

public class HashTableComparison_Main {
    public static void main(String[] args) {
        System.out.println("=== HASH TABLE COLLISION RESOLUTION COMPARISON ===\n");

        int[] keys = {12, 44, 13, 88, 23, 94, 11, 39, 20, 16, 5};
        int tableSize = 11;

        System.out.println("Keys to insert: ");
        for (int key : keys) {
            System.out.print(key + " ");
        }
        System.out.println("\nTable size: " + tableSize);
        System.out.println("Hash function: h(k) = k % " + tableSize);

        // Method 1: Separate Chaining
        System.out.println("\n" + "=".repeat(60));
        System.out.println("METHOD 1: SEPARATE CHAINING");
        System.out.println("=".repeat(60));
        demonstrateSeparateChaining(keys, tableSize);

        // Method 2: Linear Probing
        System.out.println("\n" + "=".repeat(60));
        System.out.println("METHOD 2: LINEAR PROBING");
        System.out.println("=".repeat(60));
        demonstrateLinearProbing(keys, tableSize);

        // Method 3: Double Hashing
        System.out.println("\n" + "=".repeat(60));
        System.out.println("METHOD 3: DOUBLE HASHING");
        System.out.println("=".repeat(60));
        demonstrateDoubleHashing(keys, tableSize);
    }

    private static void demonstrateSeparateChaining(int[] keys, int tableSize) {
        System.out.println("\nInsertion process:");
        System.out.println("(Each position can hold multiple values in a chain)\n");

        for (int key : keys) {
            int hash = key % tableSize;
            System.out.println("Key " + key + " -> hash(" + key + ") = " + key + " % " + tableSize + " = " + hash);
        }

        // Using ChainHashMap
        ChainHashMap<Integer, String> chainMap = new ChainHashMap<>(tableSize);
        for (int key : keys) {
            chainMap.put(key, "Value_" + key);
        }

        System.out.println("\nResulting hash table:");
        printChainHashTable(chainMap, keys, tableSize);
    }

    private static void demonstrateLinearProbing(int[] keys, int tableSize) {
        System.out.println("\nInsertion process:");
        System.out.println("(Collision resolution: try next slot until empty slot found)\n");

        Integer[] table = new Integer[tableSize];

        for (int key : keys) {
            int originalHash = key % tableSize;
            int hash = originalHash;
            int probe = 0;

            while (table[hash] != null) {
                probe++;
                hash = (originalHash + probe) % tableSize;
            }

            table[hash] = key;

            if (probe > 0) {
                System.out.println("Key " + key + " -> original position " + originalHash +
                                 " (COLLISION) -> probed " + probe + " time(s) -> final position " + hash);
            } else {
                System.out.println("Key " + key + " -> position " + hash);
            }
        }

        System.out.println("\nResulting hash table:");
        printLinearTable(table);
    }

    private static void demonstrateDoubleHashing(int[] keys, int tableSize) {
        System.out.println("\nInsertion process:");
        System.out.println("(Collision resolution: h(k,i) = [h1(k) + i*h2(k)] % M)");
        System.out.println("  where h1(k) = k % " + tableSize);
        System.out.println("  and h2(k) = 7 - (k % 7)\n");

        Integer[] table = new Integer[tableSize];

        for (int key : keys) {
            int h1 = key % tableSize;
            int h2 = 7 - (key % 7);
            int hash = h1;
            int probe = 0;

            while (table[hash] != null) {
                probe++;
                hash = (h1 + probe * h2) % tableSize;
            }

            table[hash] = key;

            if (probe > 0) {
                System.out.println("Key " + key + " -> h1=" + h1 + ", h2=" + h2 +
                                 " (COLLISION) -> probed " + probe + " time(s) -> final position " + hash);
            } else {
                System.out.println("Key " + key + " -> h1=" + h1 + " -> position " + hash);
            }
        }

        System.out.println("\nResulting hash table:");
        printLinearTable(table);
    }

    private static void printChainHashTable(ChainHashMap<Integer, String> map, int[] keys, int size) {
        System.out.println("Index | Keys");
        System.out.println("------|" + "-".repeat(40));

        for (int i = 0; i < size; i++) {
            System.out.print(String.format("%5d | ", i));
            boolean found = false;
            for (int key : keys) {
                if (key % size == i) {
                    System.out.print(key + " -> ");
                    found = true;
                }
            }
            if (found) {
                System.out.print("null");
            }
            System.out.println();
        }
    }

    private static void printLinearTable(Integer[] table) {
        System.out.println("Index | Key");
        System.out.println("------|-----");
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                System.out.println(String.format("%5d | %d", i, table[i]));
            } else {
                System.out.println(String.format("%5d | (empty)", i));
            }
        }
    }
}
