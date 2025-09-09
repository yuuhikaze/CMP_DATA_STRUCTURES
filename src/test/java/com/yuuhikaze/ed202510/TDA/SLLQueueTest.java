import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.NoSuchElementException;

class SSLQueueTest {

    @Test
    void testEnqueueDequeue() {
        SLLQueue<Integer> queue = new SLLQueue<>();

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
        SLLQueue<Integer> queue = new SLLQueue<>();

        // Test dequeue on empty queue
        assertThrows(NoSuchElementException.class, queue::dequeue);
    }

    @Test
    void testFirstOnEmptyQueue() {
        SLLQueue<Integer> queue = new SLLQueue<>();

        // Test first on empty queue
        assertThrows(NoSuchElementException.class, queue::first);
    }
}

