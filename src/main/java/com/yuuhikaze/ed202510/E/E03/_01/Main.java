package com.yuuhikaze.ed202510.E.E03._01;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== OFFICE EXERCISE ===\n");
        // Implementation is done; test code not, due to time constraints

        System.out.println("=== HASH TABLE COLLISION RESOLUTION COMPARISON ===\n");
        // Construct values
        int[] keys = {12, 44, 13, 88, 23, 94, 11, 39, 20, 16, 5};
        int capacity = 11;
        // Display constructed values
        System.out.print("Keys to insert: {");
        for (int i = 0; i < keys.length; i++) {
            if (i != keys.length)
                System.out.print(keys[i] + ", ");
            else
                System.out.print(keys[i]);
        }
        System.out.print("}");
        // Demonstrate separate chaining
        System.out.println("\n" + "=".repeat(60));
        System.out.println("METHOD 1: SEPARATE CHAINING");
        System.out.println("=".repeat(60));
        demonstrateSeparateChaining(keys, capacity);
        // Demonstrate linear probing
        System.out.println("\n" + "=".repeat(60));
        System.out.println("METHOD 2: LINEAR PROBING");
        System.out.println("=".repeat(60));
        demonstrateLinearProbing(keys, capacity);
    }

    private static void demonstrateSeparateChaining(int[] keys, int capacity) {
        System.out.println("\nInsertion process:");
        System.out.println("(Each position can hold multiple values in a chain)\n");
        HashUtils<Integer> hu = new HashUtils<>();
        for (int key : keys) {
            int hash = hu.hashValue(key, capacity);
            System.out.println(
                    "Key " + key + " -> hash(" + key + ") = " + key + " % " + capacity + " = " + hash);
        }
        ChainHashMap_C<Integer, String> chainMap = new ChainHashMap_C<>(capacity);
        for (int key : keys) {
            chainMap.put(key, "Value_" + key);
        }
        System.out.println("\nResulting hash table:");
        printChainHashTable(chainMap, keys, capacity);
    }

    // Due to time constraints, all logic was implemented here
    private static void demonstrateLinearProbing(int[] keys, int capacity) {
        System.out.println("\nInsertion process:");
        System.out.println("(Collision resolution: try next slot until empty slot found)\n");
        Integer[] table = new Integer[capacity];
        HashUtils<Integer> hu = new HashUtils<>();

        for (int key : keys) {
            int originalHash = hu.hashValue(key, capacity);
            int hash = originalHash;
            int probe = 0;

            while (table[hash] != null) {
                probe++;
                hash = (originalHash + probe) % capacity;
            }

            table[hash] = key;

            if (probe > 0) {
                System.out.println(
                        "Key " + key + " -> original position " + originalHash + " (COLLISION) -> probed " + probe + " time(s) -> final position "
                                + hash);
            } else {
                System.out.println("Key " + key + " -> position " + hash);
            }
        }

        System.out.println("\nResulting hash table:");
        printLinearTable(table);
    }

    private static void printChainHashTable(
            ChainHashMap_C<Integer, String> map, int[] keys, int size) {
        System.out.println("Index | Keys");
        System.out.println("------|"
                + "-".repeat(40));

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
