package com.yuuhikaze.ed202510.TDA;

import com.yuuhikaze.ed202510.TDA.interfaces.BinaryTree;
import com.yuuhikaze.ed202510.TDA.interfaces.List;
import com.yuuhikaze.ed202510.TDA.interfaces.Position;

public class AbstractBinaryTree<E> extends AbstractTree<E> implements BinaryTree<E> {

    @Override
    public Position<E> left(Position<E> position) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'left'");
    }

    @Override
    public Position<E> right(Position<E> position) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'right'");
    }

    @Override
    public Position<E> sibling(Position<E> position) throws IllegalArgumentException {
        Position<E> parent = parent(position);
        if (parent == null)
            return null;
        if (position == left(parent))
            return right(parent); // might be null
        else
            return left(parent); // might be null
    }

    @Override
    public int numChildren(Position<E> position) {
        int count = 0;
        if (left(position) != null)
            count++;
        if (right(position) != null)
            count++;
        return count;
    }

    public Iterable<Position<E>> children(Position<E> position) {
        List<Position<E>> snapshot = new Vector<>(2);
        if (left(position) != null)
            snapshot.add(left(position));
        if (right(position) != null)
            snapshot.add(left(position));
        return snapshot;
    }
}
