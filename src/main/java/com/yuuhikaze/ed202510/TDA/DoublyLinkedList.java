package com.yuuhikaze.ed202510.TDA;

import java.util.Iterator;

public class DoublyLinkedList<E> implements Iterable<E> {
    private class Node<I> {
        private E element;
        private Node<I> previous;
        private Node<I> next;

        public Node(E element, Node<I> previous, Node<I> next) {
            this.element = element;
            this.previous = previous;
            this.next = next;
        }

        public E getElement() {
            return element;
        }

        public Node<I> getPrevious() {
            return previous;
        }

        public Node<I> getNext() {
            return next;
        }

        public void setPrevious(Node<I> previous) {
            this.previous = previous;
        }

        public void setNext(Node<I> next) {
            this.next = next;
        }
    }

    private Node<E> header;
    private Node<E> trailer;
    private int size;

    public DoublyLinkedList() {
        this.header = new Node<E>(null, null, null);
        this.trailer = new Node<E>(null, this.header, null);
        this.header.setNext(this.trailer);
    }

    private void addBetween(E element, Node<E> predecessor, Node<E> successor) {
        Node<E> node = new Node<>(element, predecessor, successor);
        predecessor.setNext(node);
        successor.setPrevious(node);
        this.size++;
    }

    private E remove(Node<E> candidate) {
        E element = candidate.getElement();
        Node<E> predecessor = candidate.getPrevious();
        Node<E> successor = candidate.getNext();
        predecessor.setNext(successor);
        successor.setPrevious(predecessor);
        this.size--;
        return element;
    }

    public void addFirst(E element) {
        addBetween(element, this.header, this.header.getNext());
    }

    public void addLast(E element) {
        addBetween(element, this.trailer.getPrevious(), this.trailer);
    }

    public E removeFirst() {
        if (isEmpty())
            return null;
        return remove(this.header.getNext());
    }

    public E removeLast() {
        if (isEmpty())
            return null;
        return remove(this.trailer.getPrevious());
    }

    public E first() {
        if (isEmpty())
            return null;
        return this.header.getNext().getElement();
    }

    public E last() {
        if (isEmpty())
            return null;
        return this.trailer.getPrevious().getElement();
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    private class DLLIterator implements Iterator<E> {
        private Node<E> current;
        private final boolean reverse;

        public DLLIterator(boolean reverse) {
            this.reverse = reverse;
            this.current = reverse ? trailer.getPrevious() : header.getNext();
        }

        @Override
        public boolean hasNext() {
            return reverse ? current != header : current != trailer;
        }

        @Override
        public E next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            E element = current.getElement();
            current = reverse ? current.getPrevious() : current.getNext();
            return element;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new DLLIterator(false);
    }

    public Iterator<E> reverseIterator() {
        return new DLLIterator(true);
    }
}
