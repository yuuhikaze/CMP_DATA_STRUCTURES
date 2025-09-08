package com.yuuhikaze.ed202510.TDA;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.NoSuchElementException;

class LinkedQueueTest {

    @Test
    void testEnqueueDequeue() {
        LinkedQueue<Integer> queue = new LinkedQueue<>();

        // Test enqueue
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        assertEquals(10, queue.first());
        assertFalse(queue.isEmpty());
        assertEquals(3, queue.size());

        // Test dequeue
        assertEquals(10, queue.dequeue());
        assertEquals(20, queue.dequeue());
        assertEquals(30, queue.dequeue());
        assertTrue(queue.isEmpty());
    }

    @Test
    void testDequeueFromEmptyQueue() {
        LinkedQueue<Integer> queue = new LinkedQueue<>();

        // Test dequeue on empty queue
        assertThrows(NoSuchElementException.class, queue::dequeue);
    }

    @Test
    void testFirstOnEmptyQueue() {
        LinkedQueue<Integer> queue = new LinkedQueue<>();

        // Test first on empty queue
        assertThrows(NoSuchElementException.class, queue::first);
    }
}

