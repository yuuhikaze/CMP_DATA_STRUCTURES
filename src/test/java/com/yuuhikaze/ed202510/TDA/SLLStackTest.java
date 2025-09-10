package com.yuuhikaze.ed202510.TDA;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.NoSuchElementException;

class SSLStackTest {

    @Test
    void testPushPop() {
        SLLStack<Integer> stack = new SLLStack<>();

        // Test push
        stack.push(10);
        stack.push(20);
        stack.push(30);
        assertEquals(30, stack.top());
        assertFalse(stack.isEmpty());
        assertEquals(3, stack.size());

        // Test pop
        assertEquals(30, stack.pop());
        assertEquals(20, stack.pop());
        assertEquals(10, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void testPopFromEmptyStack() {
        SLLStack<Integer> stack = new SLLStack<>();

        // Test pop on empty stack
        assertThrows(NoSuchElementException.class, stack::pop);
    }

    @Test
    void testTopOnEmptyStack() {
        SLLStack<Integer> stack = new SLLStack<>();

        // Test top on empty stack
        assertThrows(NoSuchElementException.class, stack::top);
    }
}

