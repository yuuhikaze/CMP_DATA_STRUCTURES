package com.yuuhikaze.ed202510.TDA.interfaces;

import java.util.Iterator;

public interface Tree<E> extends Iterable<E> {

    // O(1)
    /**
     * Returns the number of elements in tree
     *
     * @return number of elements in tree
     */
    int size();

    // O(1)
    /**
     * Checks whether tree contains no elements
     *
     * @return {@code true} if list empty, {@code false} otherwise
     */
    boolean isEmpty();

    // O(n)
    /**
     * Returns the position of the root node
     *
     * @return position of root node
     */
    Position<E> root();

    // O(n)
    /**
     * Returns the position of the parent node from queried node
     *
     * @param position is the node to query
     * @return position of the parent node from queried node
     * @throws IllegalArgumentException TODO
     */
    Position<E> parent(Position<E> position) throws IllegalArgumentException;

    /**
     * Returns an iterable collection containing the children of the queried node
     *
     * @param position is the node to query
     * @return iterable collection containing the children of the queried node
     * @throws IllegalArgumentException TODO
     */
    Iterable<Position<E>> children(Position<E> position) throws IllegalArgumentException;

    /**
     * Returns the number of children of the queried node
     *
     * @param position is the node to query
     * @return the number of children of the queried node
     * @throws IllegalArgumentException TODO
     */
    int numChildren(Position<E> position) throws IllegalArgumentException;

    /**
     * Checks whether the given node has children
     *
     * @param position is the node to query
     * @return {@code true} if node has at least one child, {@code false} otherwise
     * @throws IllegalArgumentException
     */
    boolean isInternal(Position<E> position) throws IllegalArgumentException;

    /**
     * Checks whether the given node does not have children
     *
     * @param position is the node to query
     * @return {@code true} if node does not have children, {@code false} otherwise
     * @throws IllegalArgumentException
     */
    boolean isExternal(Position<E> position) throws IllegalArgumentException;

    /**
     * Checks whether given node is root
     *
     * @param position is the node to query
     * @return {@code true} if given node is root, {@code false} otherwise
     * @throws IllegalArgumentException TODO
     */
    boolean isRoot(Position<E> position) throws IllegalArgumentException;

    /**
     * Returns an iterator to all the elements of the tree
     *
     * @return iterator to all the elements of the tree
     */
    Iterator<E> iterator();

    /**
     * Returns an iterator to all the nodes of the tree
     *
     * @return iterator to all the nodes of the tree
     */
    Iterable<Position<E>> positions();
}
