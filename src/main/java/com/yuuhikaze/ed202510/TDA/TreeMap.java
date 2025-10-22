package com.yuuhikaze.ed202510.TDA;

import com.yuuhikaze.ed202510.TDA.interfaces.Entry;
import com.yuuhikaze.ed202510.TDA.interfaces.Position;
import java.util.Comparator;

public class TreeMap<K, V> extends AbstractSortedMap<K, V> {

    protected BalanceableBinaryTree<K, V> tree = new BalanceableBinaryTree<>();

    public TreeMap() {
        super();
        tree.addRoot(null);
    }

    public TreeMap(Comparator<K> comp) {
        super(comp);
        tree.addRoot(null);
    }

    public int size() {
        return (tree.size() - 1) / 2;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    private void expandExternal(Position<Entry<K, V>> p, Entry<K, V> entry) {
        tree.set(p, entry);
        tree.addLeft(p, null);
        tree.addRight(p, null);
    }

    // SHORTHANDS
    protected Position<Entry<K, V>> root() {
        return tree.root();
    }

    protected Position<Entry<K, V>> parent(Position<Entry<K, V>> p) {
        return tree.parent(p);
    }

    protected Position<Entry<K, V>> left(Position<Entry<K, V>> p) {
        return tree.left(p);
    }

    protected Position<Entry<K, V>> right(Position<Entry<K, V>> p) {
        return tree.right(p);
    }

    protected Position<Entry<K, V>> sibling(Position<Entry<K, V>> p) {
        return tree.sibling(p);
    }

    protected boolean isRoot(Position<Entry<K, V>> p) {
        return tree.isRoot(p);
    }

    protected boolean isExternal(Position<Entry<K, V>> p) {
        return tree.isExternal(p);
    }

    protected boolean isInternal(Position<Entry<K, V>> p) {
        return tree.isInternal(p);
    }

    protected void set(Position<Entry<K, V>> p, Entry<K, V> e) {
        tree.set(p, e);
    }

    protected void rotate(Position<Entry<K, V>> p) {
        tree.rotate(p);
    }

    protected Position<Entry<K, V>> restructure(Position<Entry<K, V>> x) {
        return tree.restructure(x);
    }

    // SELECTORS
    /**
     * Returns position with the minimal key in the subtree rooted at Position p.
     *
     * @param p a Position of the tree serving as root of a subtree
     * @return Position with minimal key in subtree
     */
    protected Position<Entry<K, V>> treeMin(Position<Entry<K, V>> p) {
        Position<Entry<K, V>> walk = p;
        while (isInternal(walk))
            walk = left(walk);
        return parent(walk); // we want the parent of the leaf
    }

    /**
     * Returns the position with the maximum key in the subtree rooted at p.
     *
     * @param p a Position of the tree serving as root of a subtree
     * @return Position with maximum key in subtree
     */
    protected Position<Entry<K, V>> treeMax(Position<Entry<K, V>> p) {
        Position<Entry<K, V>> walk = p;
        while (isInternal(walk))
            walk = right(walk);
        return parent(walk); // we want the parent of the leaf
    }

    /**
     * Returns entry of p's nearest ancestor with key that is smaller/larger than p. Returns null if no
     * such ancestor exists.
     */
    protected Entry<K, V> oneSidedAncestor(Position<Entry<K, V>> p, boolean smaller) {
        while (!isRoot(p)) {
            if (smaller == (p == right(parent(p)))) // parent has smaller key if p is right child of
                // parent
                return parent(p).getElement();
            else
                p = parent(p);
        }
        return null;
    }

    // O(log(n))
    // Binary search
    private Position<Entry<K, V>> treeSearch(Position<Entry<K, V>> p, K key) {
        if (isExternal(p))
            return p;
        int comp = compare(key, p.getElement());
        if (comp == 0)
            return p;
        else if (comp < 0)
            return treeSearch(left(p), key);
        else
            return treeSearch(right(p), key);
    }

    // GOODRICH's
    public V get(K key) throws IllegalArgumentException {
        Position<Entry<K, V>> p = treeSearch(root(), key);
        rebalanceAccess(p);
        if (isExternal(p))
            return null;
        return p.getElement().getValue();
    }

    public V put(K key, V value) throws IllegalArgumentException {
        Entry<K, V> newEntry = new MapEntry<K, V>(key, value);
        Position<Entry<K, V>> p = treeSearch(root(), key);
        if (isExternal(p)) {
            expandExternal(p, newEntry);
            rebalanceInsert(p); // TODO (stub)
            return null;
        } else {
            V old = p.getElement().getValue();
            set(p, newEntry);
            rebalanceAccess(p); // TODO (stub)
            return old;
        }
    }

    public V remove(K key) throws IllegalArgumentException {
        Position<Entry<K, V>> p = treeSearch(root(), key);
        if (isExternal(p)) {
            rebalanceAccess(p); // TODO (stub)
            return null;
        } else {
            V old = p.getElement().getValue();
            if (isInternal(left(p)) && isInternal(right(p))) {
                Position<Entry<K, V>> replacement = treeMax(left(p));
                set(p, replacement.getElement());
                p = replacement;
            }
            Position<Entry<K, V>> leaf = (isExternal(left(p)) ? left(p) : right(p));
            Position<Entry<K, V>> sib = sibling(leaf);
            tree.remove(leaf);
            tree.remove(p);
            rebalanceDelete(sib);
            return old;
        }
    }

    @Override
    public Entry<K, V> firstEntry() {
        if (isEmpty())
            return null;
        return treeMin(root()).getElement();
    }

    @Override
    public Entry<K, V> lastEntry() {
        if (isEmpty())
            return null;
        return treeMax(root()).getElement();
    }

    @Override
    public Entry<K, V> ceilingEntry(K key) throws IllegalArgumentException {
        Position<Entry<K, V>> p = treeSearch(root(), key);
        if (isInternal(p)) // an exact match
            return p.getElement();
        return oneSidedAncestor(p, false); // find nearest LARGER ancestor of leaf
    }

    @Override
    public Entry<K, V> floorEntry(K key) throws IllegalArgumentException {
        Position<Entry<K, V>> p = treeSearch(root(), key);
        if (isInternal(p))
            return p.getElement();
        return oneSidedAncestor(p, true);
    }

    @Override
    public Entry<K, V> lowerEntry(K key) throws IllegalArgumentException {
        Position<Entry<K, V>> p = treeSearch(root(), key);
        if (isInternal(p) && isInternal(left(p))) // if p has left subtree,
            return treeMax(left(p)).getElement(); // predecessor is max of that subtree
        return oneSidedAncestor(p, true); // otherwise predecessor is a SMALLER ancestor
    }

    @Override
    public Entry<K, V> higherEntry(K key) throws IllegalArgumentException {
        Position<Entry<K, V>> p = treeSearch(root(), key);
        if (isInternal(p) && isInternal(right(p))) // if p has right subtree,
            return treeMin(right(p)).getElement(); // successor is min of that subtree
        return oneSidedAncestor(p, false); // otherwise successor is a LARGER ancestor
    }

    @Override
    public Iterable<Entry<K, V>> subMap(K fromKey, K toKey) throws IllegalArgumentException {
        Vector<Entry<K, V>> buffer = new Vector<>();
        if (compare(fromKey, toKey) < 0) // ensure that fromKey < toKey
            subMapRecurse(fromKey, toKey, root(), buffer);
        return buffer;
    }

    // Stubs for balanced search tree operations (subclasses can override)
    /**
     * Rebalances the tree after an access of specified position. This version of the method does not do
     * anything, but it can be overridden by a subclasses.
     *
     * @param p the Position which was recently accessed (possibly a leaf)
     */
    protected void rebalanceAccess(Position<Entry<K, V>> p) {}

    @Override
    public Iterable<Entry<K, V>> entrySet() {
        Vector<Entry<K, V>> buffer = new Vector<>();
        for (Position<Entry<K, V>> p : tree.inorder())
            if (isInternal(p))
                buffer.add(p.getElement());
        return buffer;
    }

    // UTILITIES
    private void subMapRecurse(
            K fromKey, K toKey, Position<Entry<K, V>> p, Vector<Entry<K, V>> buffer) {
        if (isInternal(p))
            if (compare(p.getElement(), fromKey) < 0)
                // p's key is less than fromKey, so any relevant entries are to the right
                subMapRecurse(fromKey, toKey, right(p), buffer);
            else {
                subMapRecurse(fromKey, toKey, left(p), buffer); // first consider left subtree
                if (compare(p.getElement(), toKey) < 0) { // p is within range
                    buffer.add(p.getElement()); // so add it to buffer, and consider
                    subMapRecurse(fromKey, toKey, right(p), buffer); // right subtree as well
                }
            }
    }

    // REBALANCING LOGIC
    private int height(Position<Entry<K, V>> position) {
        return tree.getAux(position);
    }

    private void recomputeHeight(Position<Entry<K, V>> position) {
        tree.setAux(position, 1 + Math.max(height(left(position)), height(right(position))));
    }

    private boolean isBalanced(Position<Entry<K, V>> position) {
        return Math.abs(height(left(position)) - height(right(position))) <= 1;
    }

    private Position<Entry<K, V>> tallerChild(Position<Entry<K, V>> position) {
        if (height(left(position)) > height(right(position)))
            return left(position);
        if (height(left(position)) < height(right(position)))
            return right(position);
        if (isRoot(position))
            return left(position);
        if (position == left(parent(position)))
            return left(position);
        else
            return right(position);
    }

    private void rebalance(Position<Entry<K, V>> position) {
        int oldHeight, newHeight;
        do {
            oldHeight = height(position);
            if (!isBalanced(position)) {
                position = restructure(tallerChild(tallerChild(position)));
                recomputeHeight(left(position));
                recomputeHeight(right(position));
            }
            recomputeHeight(position);
            newHeight = height(position);
            position = parent(position);
        } while (oldHeight != newHeight && position != null);
    }
    
    /**
     * Rebalances the tree after an insertion of specified position. This version of the method does not
     * do anything, but it can be overridden by subclasses.
     *
     * @param p the position which was recently inserted
     */
    private void rebalanceInsert(Position<Entry<K, V>> p) {
        rebalance(p);
    }

    /**
     * Rebalances the tree after a child of specified position has been removed. This version of the
     * method does not do anything, but it can be overridden by subclasses.
     *
     * @param p the position of the sibling of the removed leaf
     */
    private void rebalanceDelete(Position<Entry<K, V>> p) {
        if (!isRoot(p))
            rebalance(parent(p));
    }
}
