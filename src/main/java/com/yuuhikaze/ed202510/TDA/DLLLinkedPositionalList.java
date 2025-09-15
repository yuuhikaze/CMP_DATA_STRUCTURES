package com.yuuhikaze.ed202510.TDA;

import com.yuuhikaze.ed202510.TDA.interfaces.Position;
import com.yuuhikaze.ed202510.TDA.interfaces.PositionalList;

public class DLLLinkedPositionalList<E> implements PositionalList<E> {
    private static class Node<E> implements Position<E> {
        private E element;
        private Node<E> previous;
        private Node<E> next;

        public Node(E element, Node<E> previous, Node<E> next) {
            this.element = element;
            this.previous = previous;
            this.next = next;
        }

        @Override
        public E getElement() throws IllegalStateException {
            if (next == null)
                throw new IllegalArgumentException("Position no longer valid");
            return element;
        }

        public Node<E> getPrevious() {
            return previous;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public void setPrevious(Node<E> previous) {
            this.previous = previous;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }
    }

    private Node<E> header;
    private Node<E> trailer;
    private int size;

    public DLLLinkedPositionalList() {
        this.header = new Node<>(null, null, null);
        this.trailer = new Node<>(null, header, null);
        header.setNext(trailer);
        this.size = 0;
    }

    private Node<E> validate(Position<E> position) throws IllegalArgumentException {
        if (!(position instanceof Node))
            throw new IllegalArgumentException("Invalid position");
        Node<E> node = (Node<E>) position;
        if (node.getNext() == null)
            throw new IllegalArgumentException("Position is no longer in the list");
        return node;
    }

    private Position<E> position(Node<E> node) {
        if (node == header || node == trailer)
            return null;
        return node;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Position<E> first() {
        return position(this.header.getNext());
    }

    @Override
    public Position<E> last() {
        return position(this.trailer.getPrevious());
    }

    @Override
    public Position<E> before(Position<E> position) throws IllegalArgumentException {
        Node<E> node = validate(position);
        return position(node.getPrevious());
    }

    @Override
    public Position<E> after(Position<E> position) throws IllegalArgumentException {
        Node<E> node = validate(position);
        return position(node.getNext());
    }

    private Position<E> addBetween(E element, Node<E> predecessor, Node<E> successor) {
        Node<E> newest = new Node<>(element, predecessor, successor);
        predecessor.setNext(newest);
        successor.setPrevious(newest);
        this.size++;
        return newest;
    }

    @Override
    public Position<E> addFirst(E element) {
        return addBetween(element, this.header, this.header.getNext());
    }

    @Override
    public Position<E> addLast(E element) {
        return addBetween(element, this.trailer.getPrevious(), this.trailer);
    }

    @Override
    public Position<E> addBefore(Position<E> position, E element) throws IllegalArgumentException {
        return addBetween(element, this.header, this.header.getNext());
    }

    @Override
    public Position<E> addAfter(Position<E> position, E element) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addAfter'");
    }

    @Override
    public E set(Position<E> position, E element) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'set'");
    }

    @Override
    public E remove(Position<E> position, E element) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }
}
