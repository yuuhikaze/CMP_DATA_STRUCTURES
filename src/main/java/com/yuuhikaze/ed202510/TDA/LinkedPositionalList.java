package com.yuuhikaze.ed202510.TDA;

import com.yuuhikaze.ed202510.TDA.interfaces.Position;
import com.yuuhikaze.ed202510.TDA.interfaces.PositionalList;
import java.util.Iterator;

public class LinkedPositionalList<E> implements PositionalList<E> {

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
                throw new IllegalArgumentException("Position no longer valid"); // TODO
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
    private int size = 0;

    public LinkedPositionalList() {
        header = new Node<>(null, null, null);
        trailer = new Node<>(null, header, null);
        header.setNext(trailer);
    }

    /**
     * Validates position is in the list and that is of {@code Node} type
     *
     * @param position is the position to validate
     * @return position casted as node
     * @throws IllegalArgumentException when position fails the validation
     */
    private Node<E> validate(Position<E> position) throws IllegalArgumentException {
        if (!(position instanceof Node))
            throw new IllegalArgumentException("Position must be of Node type");
        Node<E> node = (Node<E>) position; // safe cast
        if (node.getNext() == null)
            throw new IllegalArgumentException("Position is no longer in the list");
        return node;
    }

    /**
     * Returns given node as position
     *
     * @param node TODO
     * @return node as position, or {@code null} if it is a sentinel
     */
    private Position<E> nodeAsPosition(Node<E> node) {
        if (node == header || node == trailer)
            return null;
        return node;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Position<E> first() {
        return nodeAsPosition(header.getNext());
    }

    @Override
    public Position<E> last() {
        return nodeAsPosition(trailer.getPrevious());
    }

    @Override
    public Position<E> before(Position<E> position) throws IllegalArgumentException {
        Node<E> node = validate(position);
        return nodeAsPosition(node.getPrevious());
    }

    @Override
    public Position<E> after(Position<E> position) throws IllegalArgumentException {
        Node<E> node = validate(position);
        return nodeAsPosition(node.getNext());
    }

    private Position<E> addBetween(E element, Node<E> predecessor, Node<E> successor) {
        Node<E> node = new Node<>(element, predecessor, successor);
        predecessor.setNext(node);
        successor.setPrevious(node);
        this.size++;
        return node;
    }

    @Override
    public Position<E> addFirst(E element) {
        return addBetween(element, header, header.getNext());
    }

    @Override
    public Position<E> addLast(E element) {
        return addBetween(element, trailer.getPrevious(), trailer);
    }

    @Override
    public Position<E> addBefore(Position<E> position, E element) {
        Node<E> node = validate(position);
        return addBetween(element, node.getPrevious(), node);
    }

    @Override
    public Position<E> addAfter(Position<E> position, E element) {
        Node<E> node = validate(position);
        return addBetween(element, node, node.getNext());
    }

    @Override
    public E set(Position<E> position, E element) throws IllegalArgumentException {
        Node<E> node = validate(position);
        E tmp = node.getElement();
        node.setElement(element);
        return tmp;
    }

    @Override
    public E remove(Position<E> position) throws IllegalArgumentException {
        Node<E> node = validate(position);
        Node<E> predecessor = node.getPrevious();
        Node<E> successor = node.getNext();
        predecessor.setNext(successor);
        successor.setPrevious(predecessor);
        this.size--;
        E tmp = node.getElement();
        // theoretically the following is not needed ↓
        node.setElement(null);
        node.setNext(null);
        node.setPrevious(null);
        return tmp;
    }

    @Override
    public Iterator<Position<E>> iterator() {
        return new Iterator<Position<E>>() {
            private Position<E> current = nodeAsPosition(header.getNext());

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Position<E> next() {
                if (current == null)
                    throw new java.util.NoSuchElementException();
                Position<E> tmp = current;
                current = after(current);
                return tmp;
            }
        };
    }
}
