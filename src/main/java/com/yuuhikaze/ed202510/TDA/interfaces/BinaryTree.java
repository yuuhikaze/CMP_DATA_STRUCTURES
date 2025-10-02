package com.yuuhikaze.ed202510.TDA.interfaces;

public interface BinaryTree<E> extends Tree<E> {

    // O(n)
    /**
     * Returns the position of the left child from queried node
     *
     * @param position is the node to query
     * @return position of the left child from queried node, or {@code null} if no child exists
     * @throws IllegalArgumentException TODO
     */
    Position<E> left(Position<E> position) throws IllegalArgumentException;

    // O(n)
    /**
     * Returns the position of the right child from queried node
     *
     * @param position is the node to query
     * @return position of the right child from queried node, or {@code null} if no child exists
     * @throws IllegalArgumentException TODO
     */
    Position<E> right(Position<E> position) throws IllegalArgumentException;

    /**
     * Returns the position of the sibling from queried node
     *
     * @param position is the node to query
     * @return position of the sibling from queried node, or {@code null} if no sibling exists
     * @throws IllegalArgumentException TODO
     */
    Position<E> sibling(Position<E> position) throws IllegalArgumentException;
}
