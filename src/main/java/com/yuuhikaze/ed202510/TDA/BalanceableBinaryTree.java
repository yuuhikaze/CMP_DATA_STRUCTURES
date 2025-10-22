package com.yuuhikaze.ed202510.TDA;

import com.yuuhikaze.ed202510.TDA.interfaces.Entry;
import com.yuuhikaze.ed202510.TDA.interfaces.Position;

public class BalanceableBinaryTree<K, V> extends LinkedBinaryTree<Entry<K, V>> {

    protected static class BSTNode<E> extends Node<E> {
        int aux = 0;

        public BSTNode(E element, Node<E> parent, Node<E> left, Node<E> right) {
            super(element, parent, left, right);
        }

        public int getAux() {
            return aux;
        }

        public void setAux(int aux) {
            this.aux = aux;
        }
    }

    public int getAux(Position<Entry<K, V>> position) {
        return ((BSTNode<Entry<K, V>>) position).getAux();
    }

    public void setAux(Position<Entry<K, V>> position, int value) {
        ((BSTNode<Entry<K, V>>) position).setAux(value);
    }

    @Override
    protected Node<Entry<K, V>> createNode(
            Entry<K, V> element,
            Node<Entry<K, V>> parent,
            Node<Entry<K, V>> left,
            Node<Entry<K, V>> right) {
        return new BSTNode<>(element, parent, left, right);
    }

    private void relink(Node<Entry<K, V>> parent, Node<Entry<K, V>> child, boolean makeLeftChild) {
        child.setParent(parent);
        if (makeLeftChild)
            parent.setLeft(child);
        else
            parent.setRight(child);
    }

    public void rotate(Position<Entry<K, V>> position) {
        Node<Entry<K, V>> x = validate(position);
        Node<Entry<K, V>> y = x.getParent();
        Node<Entry<K, V>> z = y.getParent();
        if (z == null) {
            root = x;
            x.setParent(null);
        } else {
            relink(z, x, y == z.getLeft());
        }
        if (x == y.getLeft()) {
            relink(x, y.getRight(), true);
            relink(x, y, false);
        } else {
            relink(y, x.getLeft(), false);
            relink(x, y, true);
        }
    }

    public Position<Entry<K, V>> restructure(Position<Entry<K, V>> x) {
        Position<Entry<K, V>> y = parent(x);
        Position<Entry<K, V>> z = parent(y);
        if ((x == right(y)) == (y == right(z))) {
            rotate(y);
            return y;
        } else {
            rotate(x);
            rotate(x);
            return x;
        }
    }
}
