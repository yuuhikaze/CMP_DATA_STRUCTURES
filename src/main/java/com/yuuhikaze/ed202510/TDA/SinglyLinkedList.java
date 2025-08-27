package com.yuuhikaze.ed202510.TDA;

public class SinglyLinkedList<E> {
    private Node<E> head = null;
    private Node<E> tail = null;
    private int size = 0;

    public SinglyLinkedList() { }

    /**
     * Inserts an element to the start of the Singly Linked List
     */
    public void addFirst(E element) {
        this.head = new Node<>(element, this.head);
        if (isEmpty()) this.tail = this.head;
        this.size++;
    }

    /**
     * Inserts an element to the end of the Singly Linked List
     */
    public void addLast(E element) {
        Node<E> tail = new Node<>(element, null);
        if (isEmpty()) {
            this.head = tail;
        } else {
            this.tail.setNext(tail);
        }
        this.tail = tail;
        this.size++;
    }

    /**
     * Returns the size of the collection
     */
    public int size() {
        return this.size;
    }

    /**
     * Returns true if the collection is empty, otherwise, false
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Returns the first element
     */
    public E first() {
        if (isEmpty()) return null;
        return this.head.getElement();
    }

    /**
     * Returns the last element
     */
    public E last() {
        if (isEmpty()) return null;
        return this.tail.getElement();
    }

    /**
     * Removes and returns the first element from the collection
     */
    public E removeFirst() {
        if(isEmpty()) return null;
        E element = this.head.getElement();
        this.head = this.head.getNext();
        this.size--;
        if(isEmpty()) this.tail = null;
        return element;
        // GC automatically disposes the first node since it no longer is referenced
    }

    private class Node<I> {
        private I element;
        private Node<I> next;

        public Node(I element, Node<I> next) {
            this.element = element;
            this.next = next;
        }

        /**
         * Returns next node
         */
        public Node<I> getNext() {
            return this.next;
        }

        /**
         * Sets next node
         */
        public void setNext(Node<I> next) {
            this.next = next;
        }

        /**
         * Gets element/value of node
         */
        public I getElement() {
            return this.element;
        }
    }
}
