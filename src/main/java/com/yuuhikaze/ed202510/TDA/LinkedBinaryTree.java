package com.yuuhikaze.ed202510.TDA;

import com.yuuhikaze.ed202510.TDA.interfaces.Position;

public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {

    protected static class Node<E> implements Position<E> {
        private E element;
        private Node<E> parent;
        private Node<E> left;
        private Node<E> right;

        public Node(E element, Node<E> parent, Node<E> left, Node<E> right) {
            this.element = element;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        @Override
        public E getElement() throws IllegalStateException {
            return element;
        }

        public Node<E> getParent() {
            return parent;
        }

        public Node<E> getLeft() {
            return left;
        }

        public Node<E> getRight() {
            return right;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public void setParent(Node<E> parent) {
            this.parent = parent;
        }

        public void setLeft(Node<E> left) {
            this.left = left;
        }

        public void setRight(Node<E> right) {
            this.right = right;
        }
    }

    protected Node<E> createNode(E element, Node<E> parent, Node<E> left, Node<E> right) {
        return new Node<E>(element, parent, left, right);
    }

    protected Node<E> root;
    private int size;

    public LinkedBinaryTree() {
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
        if (node.getParent() == null)
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
    public Position<E> left(Position<E> position) throws IllegalArgumentException {
        Node<E> node = validate(position);
        return node.getLeft();
    }

    @Override
    public Position<E> right(Position<E> position) throws IllegalArgumentException {
        Node<E> node = validate(position);
        return node.getRight();
    }

    // O(1)
    public Position<E> addRoot(E element) throws IllegalStateException {
        if (!isEmpty())
            throw new IllegalStateException("Tree is not empty");
        root = createNode(element, null, null, null);
        size = 1;
        return root;
    }

    // O(1)
    public Position<E> addLeft(Position<E> position, E element) throws IllegalArgumentException {
        Node<E> parent = validate(position);
        if (parent.getLeft() != null)
            throw new IllegalArgumentException("Specified position already has a left child");
        Node<E> left = createNode(element, parent, null, null);
        parent.setLeft(left);
        this.size++;
        return left;
    }

    // O(1)
    public Position<E> addRight(Position<E> position, E element) throws IllegalArgumentException {
        Node<E> parent = validate(position);
        if (parent.getRight() != null)
            throw new IllegalArgumentException("Specified position already has a right child");
        Node<E> right = createNode(element, parent, null, null);
        parent.setRight(right);
        this.size++;
        return right;
    }

    // O(1)
    public E set(Position<E> position, E element) throws IllegalArgumentException {
        Node<E> node = validate(position);
        E tmp = node.getElement();
        node.setElement(element);
        return tmp;
    }

    // O(1)
    public void attach(
            Position<E> position, LinkedBinaryTree<E> leftTree, LinkedBinaryTree<E> rightTree)
            throws IllegalArgumentException {
        Node<E> node = validate(position);
        if (isInternal(position))
            throw new IllegalArgumentException("Specified position must be a leaf node");
        size += leftTree.size() + rightTree.size();
        if (!leftTree.isEmpty()) {
            leftTree.root.setParent(node);
            node.setLeft(leftTree.root);
            leftTree.root = null;
            leftTree.size = 0;
        }
        if (!rightTree.isEmpty()) {
            rightTree.root.setParent(node);
            node.setRight(rightTree.root);
            rightTree.root = null;
            rightTree.size = 0;
        }
    }

    // O(1)
    public E remove(Position<E> position) throws IllegalArgumentException {
        Node<E> node = validate(position); // candidate for deletion
        if (numChildren(position) == 2)
            throw new IllegalArgumentException("Specified position has two children");
        Node<E> child = (node.getLeft() != null ? node.getLeft() : node.getRight());
        if (child != null) {
            child.setParent(
                    node
                            .getParent()); // update the child's parent reference to the node above
                                           // the candidate for deletion
        }
        if (node == root) {
            root = child; // if targeted node for deletion is root, its child becomes root
        } else {
            Node<E> parent = node.getParent();
            // Update the relevant side
            // Is the current candidate for deletion on the left or right?
            if (node == parent.getLeft()) {
                parent.setLeft(child);
            } else {
                parent.setRight(child);
            }
        }
        this.size--;
        E tmp = node.getElement();
        // Free memory
        node.setElement(null);
        node.setLeft(null);
        node.setRight(null);
        node.setParent(node); // convention for defunct node - parent is set to himself
        return tmp;
    }
}
