package com.yuuhikaze.ed202510.TDA;

import com.yuuhikaze.ed202510.TDA.interfaces.Entry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for TreeMap - a balanced binary search tree implementation
 */
class TreeMapTest {

    private TreeMap<Integer, String> map;

    @BeforeEach
    void setUp() {
        map = new TreeMap<>();
    }

    @Test
    void testEmptyMap() {
        assertTrue(map.isEmpty(), "New map should be empty");
        assertEquals(0, map.size(), "Size should be 0");
    }

    @Test
    void testPutAndGet() {
        map.put(5, "five");
        map.put(3, "three");
        map.put(7, "seven");

        assertEquals("five", map.get(5), "Should get correct value for key 5");
        assertEquals("three", map.get(3), "Should get correct value for key 3");
        assertEquals("seven", map.get(7), "Should get correct value for key 7");
        assertEquals(3, map.size(), "Size should be 3");
    }

    @Test
    void testPutOverwrite() {
        map.put(5, "five");
        String oldValue = map.put(5, "FIVE");

        assertEquals("five", oldValue, "Should return old value");
        assertEquals("FIVE", map.get(5), "Should have new value");
        assertEquals(1, map.size(), "Size should still be 1");
    }

    @Test
    void testGetNonExistent() {
        map.put(5, "five");

        assertNull(map.get(10), "Should return null for non-existent key");
    }

    @Test
    void testRemove() {
        map.put(5, "five");
        map.put(3, "three");
        map.put(7, "seven");

        String removed = map.remove(5);
        assertEquals("five", removed, "Should return removed value");
        assertEquals(2, map.size(), "Size should be 2");
        assertNull(map.get(5), "Removed key should not exist");
    }

    @Test
    void testRemoveNonExistent() {
        map.put(5, "five");

        assertNull(map.remove(10), "Should return null for non-existent key");
        assertEquals(1, map.size(), "Size should remain 1");
    }

    @Test
    void testRemoveWithTwoChildren() {
        map.put(5, "five");
        map.put(3, "three");
        map.put(7, "seven");
        map.put(2, "two");
        map.put(4, "four");
        map.put(6, "six");
        map.put(8, "eight");

        String removed = map.remove(5);
        assertEquals("five", removed, "Should return removed value");
        assertEquals(6, map.size(), "Size should be 6");

        // Tree should still be valid
        assertEquals("three", map.get(3), "Should still find 3");
        assertEquals("seven", map.get(7), "Should still find 7");
    }

    @Test
    void testFirstEntry() {
        map.put(5, "five");
        map.put(3, "three");
        map.put(7, "seven");

        Entry<Integer, String> first = map.firstEntry();
        assertNotNull(first, "First entry should not be null");
        assertEquals(3, first.getKey(), "First key should be 3");
        assertEquals("three", first.getValue(), "First value should be 'three'");
    }

    @Test
    void testLastEntry() {
        map.put(5, "five");
        map.put(3, "three");
        map.put(7, "seven");

        Entry<Integer, String> last = map.lastEntry();
        assertNotNull(last, "Last entry should not be null");
        assertEquals(7, last.getKey(), "Last key should be 7");
        assertEquals("seven", last.getValue(), "Last value should be 'seven'");
    }

    @Test
    void testFirstEntryEmpty() {
        assertNull(map.firstEntry(), "First entry should be null for empty map");
    }

    @Test
    void testLastEntryEmpty() {
        assertNull(map.lastEntry(), "Last entry should be null for empty map");
    }

    @Test
    void testCeilingEntry() {
        map.put(2, "two");
        map.put(4, "four");
        map.put(6, "six");
        map.put(8, "eight");

        Entry<Integer, String> ceiling5 = map.ceilingEntry(5);
        assertNotNull(ceiling5, "Ceiling should not be null");
        assertEquals(6, ceiling5.getKey(), "Ceiling of 5 should be 6");

        Entry<Integer, String> ceiling4 = map.ceilingEntry(4);
        assertEquals(4, ceiling4.getKey(), "Ceiling of 4 should be 4 (exact match)");

        Entry<Integer, String> ceiling10 = map.ceilingEntry(10);
        assertNull(ceiling10, "Ceiling of 10 should be null");
    }

    @Test
    void testFloorEntry() {
        map.put(2, "two");
        map.put(4, "four");
        map.put(6, "six");
        map.put(8, "eight");

        Entry<Integer, String> floor5 = map.floorEntry(5);
        assertNotNull(floor5, "Floor should not be null");
        assertEquals(4, floor5.getKey(), "Floor of 5 should be 4");

        Entry<Integer, String> floor4 = map.floorEntry(4);
        assertEquals(4, floor4.getKey(), "Floor of 4 should be 4 (exact match)");

        Entry<Integer, String> floor1 = map.floorEntry(1);
        assertNull(floor1, "Floor of 1 should be null");
    }

    @Test
    void testLowerEntry() {
        map.put(2, "two");
        map.put(4, "four");
        map.put(6, "six");
        map.put(8, "eight");

        Entry<Integer, String> lower5 = map.lowerEntry(5);
        assertNotNull(lower5, "Lower should not be null");
        assertEquals(4, lower5.getKey(), "Lower of 5 should be 4");

        Entry<Integer, String> lower4 = map.lowerEntry(4);
        assertEquals(2, lower4.getKey(), "Lower of 4 should be 2 (not exact match)");

        Entry<Integer, String> lower2 = map.lowerEntry(2);
        assertNull(lower2, "Lower of 2 should be null");
    }

    @Test
    void testHigherEntry() {
        map.put(2, "two");
        map.put(4, "four");
        map.put(6, "six");
        map.put(8, "eight");

        Entry<Integer, String> higher5 = map.higherEntry(5);
        assertNotNull(higher5, "Higher should not be null");
        assertEquals(6, higher5.getKey(), "Higher of 5 should be 6");

        Entry<Integer, String> higher4 = map.higherEntry(4);
        assertEquals(6, higher4.getKey(), "Higher of 4 should be 6 (not exact match)");

        Entry<Integer, String> higher8 = map.higherEntry(8);
        assertNull(higher8, "Higher of 8 should be null");
    }

    @Test
    void testSubMap() {
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        map.put(4, "four");
        map.put(5, "five");
        map.put(6, "six");

        Iterable<Entry<Integer, String>> subMap = map.subMap(2, 5);

        int count = 0;
        for (Entry<Integer, String> entry : subMap) {
            assertTrue(entry.getKey() >= 2 && entry.getKey() < 5,
                    "Key should be in range [2, 5)");
            count++;
        }
        assertEquals(3, count, "Should have 3 entries (2, 3, 4)");
    }

    @Test
    void testSubMapEmpty() {
        map.put(1, "one");
        map.put(5, "five");

        Iterable<Entry<Integer, String>> subMap = map.subMap(2, 4);

        int count = 0;
        for (Entry<Integer, String> entry : subMap) {
            count++;
        }
        assertEquals(0, count, "SubMap should be empty");
    }

    @Test
    void testEntrySet() {
        map.put(5, "five");
        map.put(3, "three");
        map.put(7, "seven");
        map.put(2, "two");

        Iterable<Entry<Integer, String>> entries = map.entrySet();

        int count = 0;
        Integer previousKey = null;
        for (Entry<Integer, String> entry : entries) {
            if (previousKey != null) {
                assertTrue(entry.getKey() > previousKey,
                        "Entries should be in ascending order");
            }
            previousKey = entry.getKey();
            count++;
        }
        assertEquals(4, count, "Should have 4 entries");
    }

    @Test
    void testKeySet() {
        map.put(5, "five");
        map.put(3, "three");
        map.put(7, "seven");

        Iterable<Integer> keys = map.keySet();

        int count = 0;
        for (Integer key : keys) {
            assertTrue(key == 3 || key == 5 || key == 7, "Key should be valid");
            count++;
        }
        assertEquals(3, count, "Should have 3 keys");
    }

    @Test
    void testValues() {
        map.put(5, "five");
        map.put(3, "three");
        map.put(7, "seven");

        Iterable<String> values = map.values();

        int count = 0;
        for (String value : values) {
            assertTrue(value.equals("three") || value.equals("five") || value.equals("seven"),
                    "Value should be valid");
            count++;
        }
        assertEquals(3, count, "Should have 3 values");
    }

    @Test
    void testBalancing() {
        // Insert in ascending order - should trigger rebalancing
        for (int i = 1; i <= 10; i++) {
            map.put(i, "value" + i);
        }

        assertEquals(10, map.size(), "Size should be 10");

        // Verify all elements are accessible
        for (int i = 1; i <= 10; i++) {
            assertEquals("value" + i, map.get(i), "Should find value for key " + i);
        }
    }

    @Test
    void testLargeNumberOfElements() {
        int n = 1000;

        // Insert many elements
        for (int i = 0; i < n; i++) {
            map.put(i, "value" + i);
        }

        assertEquals(n, map.size(), "Size should be " + n);

        // Verify retrieval
        for (int i = 0; i < n; i++) {
            assertEquals("value" + i, map.get(i), "Should find value for key " + i);
        }

        // Test removal
        for (int i = 0; i < n; i += 2) {
            map.remove(i);
        }

        assertEquals(n / 2, map.size(), "Size should be " + (n / 2));
    }

    @Test
    void testWithNegativeKeys() {
        map.put(-5, "minus five");
        map.put(0, "zero");
        map.put(5, "five");

        assertEquals("minus five", map.get(-5), "Should handle negative keys");
        assertEquals("zero", map.get(0), "Should handle zero key");
        assertEquals("five", map.get(5), "Should handle positive keys");

        Entry<Integer, String> first = map.firstEntry();
        assertEquals(-5, first.getKey(), "First should be -5");
    }

    @Test
    void testWithCustomComparator() {
        // Create TreeMap with reverse order comparator
        TreeMap<Integer, String> reverseMap = new TreeMap<>((a, b) -> b.compareTo(a));

        reverseMap.put(5, "five");
        reverseMap.put(3, "three");
        reverseMap.put(7, "seven");

        Entry<Integer, String> first = reverseMap.firstEntry();
        assertEquals(7, first.getKey(), "First in reverse order should be 7");

        Entry<Integer, String> last = reverseMap.lastEntry();
        assertEquals(3, last.getKey(), "Last in reverse order should be 3");
    }

    @Test
    void testRandomOperations() {
        // Mix of puts, gets, and removes
        map.put(50, "fifty");
        map.put(25, "twenty-five");
        map.put(75, "seventy-five");
        map.put(10, "ten");
        map.put(30, "thirty");

        assertEquals("thirty", map.get(30), "Should get thirty");

        map.remove(25);
        assertNull(map.get(25), "25 should be removed");

        map.put(60, "sixty");
        assertEquals("sixty", map.get(60), "Should get sixty");

        assertEquals(5, map.size(), "Size should be 5");
    }

    @Test
    void testNullHandling() {
        // TreeMap should handle operations gracefully even with unusual inputs
        map.put(1, null);
        assertNull(map.get(1), "Should be able to store null values");

        map.put(2, "two");
        assertEquals("two", map.get(2), "Other values should work fine");
    }
}
