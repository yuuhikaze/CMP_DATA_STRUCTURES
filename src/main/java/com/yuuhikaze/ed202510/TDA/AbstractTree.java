package com.yuuhikaze.ed202510.TDA;

import com.yuuhikaze.ed202510.TDA.interfaces.Position;
import com.yuuhikaze.ed202510.TDA.interfaces.Queue;
import com.yuuhikaze.ed202510.TDA.interfaces.Tree;
import java.util.Iterator;

public class AbstractTree<E> implements Tree<E> {

    private class ElementIterator implements Iterator<E> {
        Iterator<Position<E>> positionIterator = positions().iterator();

        public boolean hasNext() {
            return positionIterator.hasNext();
        }

        public E next() {
            return positionIterator.next().getElement();
        }

        public void remove() {
            positionIterator.remove();
        }
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'size'");
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public Position<E> root() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'root'");
    }

    @Override
    public Position<E> parent(Position<E> position) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parent'");
    }

    @Override
    public Iterable<Position<E>> children(Position<E> position) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'children'");
    }

    @Override
    public int numChildren(Position<E> position) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'numChildren'");
    }

    // O(1)
    @Override
    public boolean isInternal(Position<E> position) throws IllegalArgumentException {
        return numChildren(position) > 0;
    }

    // O(1)
    @Override
    public boolean isExternal(Position<E> position) throws IllegalArgumentException {
        return numChildren(position) == 0;
    }

    @Override
    public boolean isRoot(Position<E> position) throws IllegalArgumentException {
        return position == root();
    }

    @Override
    public Iterator<E> iterator() {
        return new ElementIterator();
    }

    @Override
    public Iterable<Position<E>> positions() {
        return postorder();
    }

    // O(n)
    public int depth(Position<E> position) {
        if (isRoot(position))
            return 0;
        else
            return 1 + depth(parent(position));
    }

    // O(n)
    public int height(Position<E> position) {
        int height = 0;
        for (Position<E> child : children(position))
            height = Math.max(height, 1 + height(child));
        return height;
    }

    public Iterable<Position<E>> preorder() {
        Queue<Position<E>> snapshot = new SLLQueue<>();
        if (!isEmpty())
            preorderSubtree(root(), snapshot);
        return snapshot;
    }

    private void preorderSubtree(Position<E> position, Queue<Position<E>> snapshot) {
        snapshot.enqueue(position);
        for (Position<E> child : children(position)) {
            preorderSubtree(child, snapshot);
        }
    }

    public Iterable<Position<E>> postorder() {
        Queue<Position<E>> snapshot = new SLLQueue<>();
        if (!isEmpty())
            postorderSubtree(root(), snapshot);
        return snapshot;
    }

    private void postorderSubtree(Position<E> position, Queue<Position<E>> snapshot) {
        for (Position<E> child : children(position)) {
            postorderSubtree(child, snapshot);
        }
        snapshot.enqueue(position);
    }

    public Iterable<Position<E>> breadthFirst() {
        Queue<Position<E>> snapshot = new SLLQueue<>();
        if (!isEmpty())
            breadthFirstSubtree(root(), snapshot);
        return snapshot;
    }

    // Tree must be balanced (hypothesis)
    private void breadthFirstSubtree(Position<E> position, Queue<Position<E>> snapshot) {
        Queue<Position<E>> layers = new SLLQueue<Position<E>>();
        layers.enqueue(position);
        while (!layers.isEmpty()) {
            Position<E> currentLayer = layers.dequeue();
            snapshot.enqueue(currentLayer);
            for (Position<E> child : children(currentLayer)) {
                layers.enqueue(child);
            }
        }
    }
}
