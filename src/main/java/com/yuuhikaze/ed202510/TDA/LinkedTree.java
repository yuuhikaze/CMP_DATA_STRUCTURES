package com.yuuhikaze.ed202510.TDA;

import com.yuuhikaze.ed202510.TDA.interfaces.Position;
import com.yuuhikaze.ed202510.TDA.interfaces.PositionalList;

public class LinkedTree<E> extends AbstractTree<E> {

    protected static class Node<E> implements Position<E> {
        private E element;
        private Node<E> parent;
        private PositionalList<Node<E>> children;

        public Node(E element, Node<E> parent, PositionalList<Node<E>> children) {
            this.element = element;
            this.parent = parent;
            this.children = children;
        }

        @Override
        public E getElement() throws IllegalStateException {
            if (children == null && parent != null && parent.equals(this))
                throw new IllegalStateException("Position is defunct");
            return element;
        }

        public Node<E> getParent() {
            return parent;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public void setParent(Node<E> parent) {
            this.parent = parent;
        }
        
        public void cleanChildren() {
            this.children = null;
        }

        public void setChildNode(Node<E> newChild, Node<E> target) {
            for (Position<Node<E>> position : children) {
                if (position.getElement().equals(target)) {
                    children.set(position, newChild);
                    return;
                }
            }
        }

        public void addFirst(Node<E> node) {
            children.addFirst(node);
        }

        public void addLast(Node<E> node) {
            children.addLast(node);
        }

        public PositionalList<Node<E>> getChildren() {
            return children;
        }
    }

    protected Node<E> createNode(E element, Node<E> parent, PositionalList<Node<E>> children) {
        return new Node<E>(element, parent, children);
    }

    protected Node<E> root;
    private int size;

    public LinkedTree() {
        this.root = null;
        this.size = 0;
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
        if (node.getParent() != null && node.getParent().equals(node)) // checks for defunct node
            throw new IllegalArgumentException("Position is no longer in the list");
        return node;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Position<E> root() {
        return root;
    }

    @Override
    public Position<E> parent(Position<E> position) throws IllegalArgumentException {
        Node<E> node = validate(position);
        return node.getParent();
    }

    @Override
    public Iterable<Position<E>> children(Position<E> position) throws IllegalArgumentException {
        Node<E> node = validate(position);
        PositionalList<Node<E>> children = node.getChildren();
        Vector<Position<E>> snapshot = new Vector<>(children.size());
        for (Position<Node<E>> child : children) {
            snapshot.add(child.getElement());
        }
        return snapshot;
    }
    
    @Override
    public int numChildren(Position<E> position) {
        Node<E> node = validate(position);
        return node.getChildren().size();
    }

    public Position<E> first(Position<E> position) throws IllegalArgumentException {
        Node<E> node = validate(position);
        return node.getChildren().first().getElement();
    }

    public Position<E> last(Position<E> position) throws IllegalArgumentException {
        Node<E> node = validate(position);
        return node.getChildren().last().getElement();
    }

    // O(1)
    public Position<E> addRoot(E element) throws IllegalStateException {
        if (!isEmpty())
            throw new IllegalStateException("Tree is not empty");
        root = createNode(element, null, new LinkedPositionalList<>());
        size = 1;
        return root;
    }

    // O(1)
    public Position<E> addFirst(Position<E> position, E element) throws IllegalArgumentException {
        Node<E> parent = validate(position);
        Node<E> child = createNode(element, parent, new LinkedPositionalList<>());
        parent.addFirst(child);
        this.size++;
        return child;
    }

    // O(1)
    public Position<E> addLast(Position<E> position, E element) throws IllegalArgumentException {
        Node<E> parent = validate(position);
        Node<E> child = createNode(element, parent, new LinkedPositionalList<>());
        parent.addLast(child);
        this.size++;
        return child;
    }

    // O(1)
    public E set(Position<E> position, E element) throws IllegalArgumentException {
        Node<E> node = validate(position);
        E tmp = node.getElement();
        node.setElement(element);
        return tmp;
    }

    // O(1)
    public void attachToLeft(Position<E> position, LinkedTree<E> tree)
            throws IllegalArgumentException {
        Node<E> node = validate(position);
        if (isInternal(position))
            throw new IllegalArgumentException("Specified position must be a leaf node");
        size += tree.size();
        if (!tree.isEmpty()) {
            tree.root.setParent(node);
            node.addFirst(tree.root);
            tree.root = null;
            tree.size = 0;
        }
    }

    // O(1)
    public void attachToRight(Position<E> position, LinkedTree<E> tree)
            throws IllegalArgumentException {
        Node<E> node = validate(position);
        if (isInternal(position))
            throw new IllegalArgumentException("Specified position must be a leaf node");
        size += tree.size();
        if (!tree.isEmpty()) {
            tree.root.setParent(node);
            node.addLast(tree.root);
            tree.root = null;
            tree.size = 0;
        }
    }

    // O(1)
    public E remove(Position<E> position) throws IllegalArgumentException {
        Node<E> node = validate(position); // candidate for deletion
        if (numChildren(position) > 1)
            throw new IllegalArgumentException("Specified position has two children");
        Node<E> child = node.getChildren().isEmpty() ? null : node.getChildren().first().getElement();
        if (child != null) {
            child.setParent(node.getParent());
        }
        if (node == root) {
            root = child; // if targeted node for deletion is root, its child becomes root
        } else {
            Node<E> parent = node.getParent();
            parent.setChildNode(child, node);
        }
        this.size--;
        E tmp = node.getElement();
        // Free memory
        node.setElement(null);
        node.cleanChildren();
        node.setParent(node); // convention for defunct node - parent is set to himself
        return tmp;
    }
}
