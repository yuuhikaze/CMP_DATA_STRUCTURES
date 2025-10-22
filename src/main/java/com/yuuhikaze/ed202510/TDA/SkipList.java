package com.yuuhikaze.ed202510.TDA;

import java.util.Iterator;
import java.util.Random;

/**
 * A Skip List implementation that provides efficient search, insert, and remove operations.
 * Skip lists use multiple levels of linked lists to achieve O(log n) expected time complexity
 * for search, insert, and remove operations, while maintaining O(n) space complexity.
 *
 * @param <E> the type of elements held in this skip list
 */
public class SkipList<E> implements Iterable<E> {

    /**
     * Node class for Skip List with multiple forward pointers (levels)
     */
    protected class Node<I> {
        private I element;
        private Node<I>[] forward; // Array of forward pointers at different levels

        @SuppressWarnings("unchecked")
        public Node(I element, int level) {
            this.element = element;
            this.forward = (Node<I>[]) new Node[level + 1];
        }

        public I getElement() {
            return element;
        }

        public void setElement(I element) {
            this.element = element;
        }

        public Node<I> getForward(int level) {
            return forward[level];
        }

        public void setForward(int level, Node<I> node) {
            forward[level] = node;
        }
    }

    private static final int MAX_LEVEL = 16;
    private static final double PROBABILITY = 0.5;

    protected Node<E> head;
    protected int level; // Current maximum level
    protected int size;
    protected Random random;

    /**
     * Constructs an empty SkipList
     */
    public SkipList() {
        this.head = new Node<>(null, MAX_LEVEL);
        this.level = 0;
        this.size = 0;
        this.random = new Random();
    }

    /**
     * Generates a random level for a new node using geometric distribution
     */
    protected int randomLevel() {
        int lvl = 0;
        while (lvl < MAX_LEVEL && random.nextDouble() < PROBABILITY) {
            lvl++;
        }
        return lvl;
    }

    /**
     * Returns the number of elements in the skip list
     *
     * @return the size
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the skip list is empty
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Finds the node at the specified index using skip list traversal
     *
     * @param targetIndex the index to find
     * @return the node at the specified index or null if not found
     */
    protected Node<E> findNodeByIndex(int targetIndex) {
        if (targetIndex < 0 || targetIndex >= size) {
            return null;
        }

        Node<E> current = head;
        int currentIndex = -1;

        // Traverse from top level down
        for (int i = level; i >= 0; i--) {
            while (current.forward[i] != null) {
                if (i == 0) {
                    int nextIndex = currentIndex + 1;
                    if (nextIndex > targetIndex) {
                        break;
                    }
                    if (nextIndex == targetIndex) {
                        return current.forward[i];
                    }
                    currentIndex = nextIndex;
                    current = current.forward[i];
                } else {
                    // For higher levels, count nodes to estimate position
                    int span = countSpan(current, i);
                    if (currentIndex + span > targetIndex) {
                        break;
                    }
                    currentIndex += span;
                    current = current.forward[i];
                }
            }
        }
        return null;
    }

    /**
     * Calculates the span (number of nodes) between current node and its forward node at given level
     */
    protected int countSpan(Node<E> node, int lvl) {
        if (node.forward[lvl] == null) {
            return 0;
        }
        int count = 1;
        Node<E> temp = node;
        // Count nodes at level 0 between node and node.forward[lvl]
        while (temp.forward[0] != null && temp.forward[0] != node.forward[lvl]) {
            temp = temp.forward[0];
            count++;
        }
        return count;
    }

    /**
     * Gets the element at the specified index
     *
     * @param i the index
     * @return the element at the index
     * @throws IndexOutOfBoundsException if index is out of bounds
     */
    public E get(int i) throws IndexOutOfBoundsException {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size: " + size);
        }
        Node<E> node = findNodeByIndex(i);
        return node != null ? node.getElement() : null;
    }

    /**
     * Sets the element at the specified index
     *
     * @param i the index
     * @param element the new element
     * @return the old element
     * @throws IndexOutOfBoundsException if index is out of bounds
     */
    public E set(int i, E element) throws IndexOutOfBoundsException {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size: " + size);
        }
        Node<E> node = findNodeByIndex(i);
        if (node != null) {
            E oldElement = node.getElement();
            node.setElement(element);
            return oldElement;
        }
        return null;
    }

    /**
     * Inserts an element at the specified index
     *
     * @param i the index
     * @param element the element to insert
     * @throws IndexOutOfBoundsException if index is out of bounds
     */
    public void insert(int i, E element) throws IndexOutOfBoundsException {
        if (i < 0 || i > size) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size: " + size);
        }

        @SuppressWarnings("unchecked")
        Node<E>[] update = (Node<E>[]) new Node[MAX_LEVEL + 1];
        Node<E> current = head;
        int currentIndex = -1;

        // Find the position to insert
        for (int lvl = level; lvl >= 0; lvl--) {
            while (current.forward[lvl] != null) {
                if (lvl == 0) {
                    int nextIndex = currentIndex + 1;
                    if (nextIndex >= i) {
                        break;
                    }
                    currentIndex = nextIndex;
                    current = current.forward[lvl];
                } else {
                    int span = countSpan(current, lvl);
                    if (currentIndex + span >= i) {
                        break;
                    }
                    currentIndex += span;
                    current = current.forward[lvl];
                }
            }
            update[lvl] = current;
        }

        // Generate random level for new node
        int newLevel = randomLevel();
        if (newLevel > level) {
            for (int lvl = level + 1; lvl <= newLevel; lvl++) {
                update[lvl] = head;
            }
            level = newLevel;
        }

        // Create new node and update pointers
        Node<E> newNode = new Node<>(element, newLevel);
        for (int lvl = 0; lvl <= newLevel; lvl++) {
            newNode.forward[lvl] = update[lvl].forward[lvl];
            update[lvl].forward[lvl] = newNode;
        }

        size++;
    }

    /**
     * Adds an element to the end of the skip list
     *
     * @param element the element to add
     */
    public void add(E element) {
        insert(size, element);
    }

    /**
     * Removes the element at the specified index
     *
     * @param i the index
     * @return the removed element
     * @throws IndexOutOfBoundsException if index is out of bounds
     */
    public E remove(int i) throws IndexOutOfBoundsException {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size: " + size);
        }

        @SuppressWarnings("unchecked")
        Node<E>[] update = (Node<E>[]) new Node[MAX_LEVEL + 1];
        Node<E> current = head;
        int currentIndex = -1;

        // Find the node to remove
        for (int lvl = level; lvl >= 0; lvl--) {
            while (current.forward[lvl] != null) {
                if (lvl == 0) {
                    int nextIndex = currentIndex + 1;
                    if (nextIndex > i) {
                        break;
                    }
                    if (nextIndex == i) {
                        break;
                    }
                    currentIndex = nextIndex;
                    current = current.forward[lvl];
                } else {
                    int span = countSpan(current, lvl);
                    if (currentIndex + span > i) {
                        break;
                    }
                    currentIndex += span;
                    current = current.forward[lvl];
                }
            }
            update[lvl] = current;
        }

        Node<E> nodeToRemove = current.forward[0];
        if (nodeToRemove == null) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size: " + size);
        }

        E element = nodeToRemove.getElement();

        // Update forward pointers
        for (int lvl = 0; lvl <= level; lvl++) {
            if (update[lvl].forward[lvl] != nodeToRemove) {
                break;
            }
            update[lvl].forward[lvl] = nodeToRemove.forward[lvl];
        }

        // Update level if necessary
        while (level > 0 && head.forward[level] == null) {
            level--;
        }

        size--;
        return element;
    }

    /**
     * Checks if the skip list contains the specified element
     *
     * @param element the element to search for
     * @return true if found, false otherwise
     */
    public boolean contains(E element) {
        return indexOf(element) != -1;
    }

    /**
     * Returns the index of the first occurrence of the specified element
     *
     * @param element the element to search for
     * @return the index or -1 if not found
     */
    public int indexOf(E element) {
        Node<E> current = head.forward[0];
        int index = 0;
        while (current != null) {
            if ((element == null && current.getElement() == null) ||
                (element != null && element.equals(current.getElement()))) {
                return index;
            }
            current = current.forward[0];
            index++;
        }
        return -1;
    }

    /**
     * Removes all elements from the skip list
     */
    public void clear() {
        head = new Node<>(null, MAX_LEVEL);
        level = 0;
        size = 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = head.forward[0];

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new java.util.NoSuchElementException();
                }
                E element = current.getElement();
                current = current.forward[0];
                return element;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> current = head.forward[0];
        while (current != null) {
            sb.append(current.getElement());
            current = current.forward[0];
            if (current != null) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Returns the current maximum level of the skip list (for debugging)
     *
     * @return the current level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Prints the skip list structure at all levels (for debugging)
     */
    public void printStructure() {
        for (int lvl = level; lvl >= 0; lvl--) {
            System.out.print("Level " + lvl + ": ");
            Node<E> current = head.forward[lvl];
            while (current != null) {
                System.out.print(current.getElement() + " -> ");
                current = current.forward[lvl];
            }
            System.out.println("null");
        }
    }
}
