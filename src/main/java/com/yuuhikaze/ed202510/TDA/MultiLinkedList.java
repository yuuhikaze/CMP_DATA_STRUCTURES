package com.yuuhikaze.ed202510.TDA;

import java.util.Iterator;

/**
 * A Multi-Linked List implementation that allows nodes to have multiple pointers
 * connecting different structures. This is useful for modeling sparse matrices,
 * complex relationships, and multi-dimensional data structures.
 *
 * Each node can have multiple links to other nodes in different "dimensions" or "categories".
 * For example, in a supermarket:
 * - A client node can link to multiple cart items (dimension 1)
 * - A product node can link to all clients who bought it (dimension 2)
 * - An age group node can link to all clients in that range (dimension 3)
 *
 * Properties:
 * - Integrated list of related structures
 * - Nodes are connected using links of pointers
 * - Linked nodes are connected with related data
 * - Nodes contain pointers from one structure to the other
 *
 * @param <E> the type of elements held in the nodes
 */
public class MultiLinkedList<E> {

    /**
     * Node class that can have multiple named links to other nodes
     */
    public class Node {
        private E element;
        private HashMap<String, Vector<Node>> links; // Multiple links by dimension
        private HashMap<String, Object> metadata; // Additional metadata

        public Node(E element) {
            this.element = element;
            this.links = new HashMap<>();
            this.metadata = new HashMap<>();
        }

        public E getElement() {
            return element;
        }

        public void setElement(E element) {
            this.element = element;
        }

        /**
         * Adds a link from this node to another node in a specific dimension
         *
         * @param dimension the name of the dimension/category
         * @param target the target node
         */
        public void addLink(String dimension, Node target) {
            Vector<Node> dimensionLinks = links.get(dimension);
            if (dimensionLinks == null) {
                dimensionLinks = new Vector<>();
                links.put(dimension, dimensionLinks);
            }
            dimensionLinks.add(target);
        }

        /**
         * Removes a link from this node to another node in a specific dimension
         *
         * @param dimension the name of the dimension/category
         * @param target the target node
         * @return true if removed, false otherwise
         */
        public boolean removeLink(String dimension, Node target) {
            Vector<Node> dimensionLinks = links.get(dimension);
            if (dimensionLinks != null) {
                for (int i = 0; i < dimensionLinks.size(); i++) {
                    if (dimensionLinks.get(i) == target) {
                        dimensionLinks.remove(i);
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * Gets all nodes linked in a specific dimension
         *
         * @param dimension the dimension name
         * @return list of linked nodes (returns empty list if none)
         */
        public Vector<Node> getLinks(String dimension) {
            Vector<Node> dimensionLinks = links.get(dimension);
            if (dimensionLinks == null) {
                return new Vector<>();
            }
            return dimensionLinks;
        }

        /**
         * Gets all dimension names for this node
         *
         * @return Vector of dimension names
         */
        public Vector<String> getDimensions() {
            Vector<String> dimensions = new Vector<>();
            for (String key : links.keySet()) {
                dimensions.add(key);
            }
            return dimensions;
        }

        /**
         * Sets metadata for this node
         *
         * @param key metadata key
         * @param value metadata value
         */
        public void setMetadata(String key, Object value) {
            metadata.put(key, value);
        }

        /**
         * Gets metadata from this node
         *
         * @param key metadata key
         * @return metadata value or null
         */
        public Object getMetadata(String key) {
            return metadata.get(key);
        }

        /**
         * Checks if this node has a specific metadata key
         *
         * @param key metadata key
         * @return true if exists, false otherwise
         */
        public boolean hasMetadata(String key) {
            return metadata.containsKey(key);
        }

        /**
         * Removes all links in a specific dimension
         *
         * @param dimension the dimension name
         */
        public void clearLinks(String dimension) {
            links.remove(dimension);
        }

        /**
         * Removes all links in all dimensions
         */
        public void clearAllLinks() {
            links.clear();
        }

        /**
         * Gets the number of links in a specific dimension
         *
         * @param dimension the dimension name
         * @return number of links
         */
        public int getLinkCount(String dimension) {
            Vector<Node> dimensionLinks = links.get(dimension);
            return dimensionLinks != null ? dimensionLinks.size() : 0;
        }

        @Override
        public String toString() {
            return "Node{" + element + "}";
        }
    }

    private Vector<Node> nodes; // All nodes in the multi-linked list
    private HashMap<String, Vector<Node>> headers; // Header nodes for each dimension

    /**
     * Constructs an empty MultiLinkedList
     */
    public MultiLinkedList() {
        this.nodes = new Vector<>();
        this.headers = new HashMap<>();
    }

    /**
     * Creates a new node with the given element
     *
     * @param element the element to store in the node
     * @return the created node
     */
    public Node createNode(E element) {
        Node node = new Node(element);
        nodes.add(node);
        return node;
    }

    /**
     * Removes a node from the multi-linked list
     *
     * @param node the node to remove
     * @return true if removed, false otherwise
     */
    public boolean removeNode(Node node) {
        // Remove all links to this node from other nodes
        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);
            Vector<String> dimensions = n.getDimensions();
            for (int j = 0; j < dimensions.size(); j++) {
                n.removeLink(dimensions.get(j), node);
            }
        }

        // Remove from headers
        for (Vector<Node> headerList : headers.values()) {
            for (int i = 0; i < headerList.size(); i++) {
                if (headerList.get(i) == node) {
                    headerList.remove(i);
                    break;
                }
            }
        }

        // Remove the node itself
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i) == node) {
                nodes.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a node to a dimension header list
     *
     * @param dimension the dimension name
     * @param node the node to add
     */
    public void addToHeader(String dimension, Node node) {
        Vector<Node> headerList = headers.get(dimension);
        if (headerList == null) {
            headerList = new Vector<>();
            headers.put(dimension, headerList);
        }
        headerList.add(node);
    }

    /**
     * Gets all nodes in a dimension header
     *
     * @param dimension the dimension name
     * @return list of nodes
     */
    public Vector<Node> getHeader(String dimension) {
        Vector<Node> headerList = headers.get(dimension);
        if (headerList == null) {
            return new Vector<>();
        }
        return headerList;
    }

    /**
     * Gets all nodes in the multi-linked list
     *
     * @return list of all nodes
     */
    public Vector<Node> getAllNodes() {
        Vector<Node> result = new Vector<>();
        for (int i = 0; i < nodes.size(); i++) {
            result.add(nodes.get(i));
        }
        return result;
    }

    /**
     * Gets the number of nodes
     *
     * @return number of nodes
     */
    public int size() {
        return nodes.size();
    }

    /**
     * Checks if the multi-linked list is empty
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return nodes.isEmpty();
    }

    /**
     * Clears all nodes and headers
     */
    public void clear() {
        nodes = new Vector<>();
        headers.clear();
    }

    /**
     * Finds nodes by element value
     *
     * @param element the element to search for
     * @return list of nodes containing the element
     */
    public Vector<Node> findNodes(E element) {
        Vector<Node> result = new Vector<>();
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            E nodeElement = node.getElement();
            if ((element == null && nodeElement == null) ||
                (element != null && element.equals(nodeElement))) {
                result.add(node);
            }
        }
        return result;
    }

    /**
     * Finds nodes by metadata
     *
     * @param key metadata key
     * @param value metadata value
     * @return list of nodes with matching metadata
     */
    public Vector<Node> findNodesByMetadata(String key, Object value) {
        Vector<Node> result = new Vector<>();
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            Object metadata = node.getMetadata(key);
            if ((value == null && metadata == null) ||
                (value != null && value.equals(metadata))) {
                result.add(node);
            }
        }
        return result;
    }

    /**
     * Finds nodes by metadata in a range (for numeric values)
     *
     * @param key metadata key
     * @param min minimum value (inclusive)
     * @param max maximum value (inclusive)
     * @return list of nodes with metadata in range
     */
    public Vector<Node> findNodesByMetadataRange(String key, int min, int max) {
        Vector<Node> result = new Vector<>();
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            Object metadata = node.getMetadata(key);
            if (metadata instanceof Integer) {
                int value = (Integer) metadata;
                if (value >= min && value <= max) {
                    result.add(node);
                }
            }
        }
        return result;
    }

    /**
     * Traverses the multi-linked list from a starting node in a specific dimension
     *
     * @param startNode the starting node
     * @param dimension the dimension to traverse
     * @return list of nodes in traversal order
     */
    public Vector<Node> traverse(Node startNode, String dimension) {
        Vector<Node> result = new Vector<>();
        HashMap<Node, Boolean> visited = new HashMap<>();
        traverseHelper(startNode, dimension, result, visited);
        return result;
    }

    private void traverseHelper(Node node, String dimension, Vector<Node> result, HashMap<Node, Boolean> visited) {
        if (node == null || visited.get(node) != null) {
            return;
        }
        visited.put(node, true);
        result.add(node);

        Vector<Node> links = node.getLinks(dimension);
        for (int i = 0; i < links.size(); i++) {
            traverseHelper(links.get(i), dimension, result, visited);
        }
    }

    /**
     * Gets all dimension names used in the multi-linked list
     *
     * @return Vector of dimension names
     */
    public Vector<String> getAllDimensions() {
        HashMap<String, Boolean> dimensionSet = new HashMap<>();

        // Collect from nodes
        for (int i = 0; i < nodes.size(); i++) {
            Vector<String> nodeDimensions = nodes.get(i).getDimensions();
            for (int j = 0; j < nodeDimensions.size(); j++) {
                dimensionSet.put(nodeDimensions.get(j), true);
            }
        }

        // Collect from headers
        for (String key : headers.keySet()) {
            dimensionSet.put(key, true);
        }

        // Convert to Vector
        Vector<String> result = new Vector<>();
        for (String dimension : dimensionSet.keySet()) {
            result.add(dimension);
        }
        return result;
    }

    /**
     * Prints the structure of the multi-linked list (for debugging)
     */
    public void printStructure() {
        System.out.println("MultiLinkedList Structure:");
        System.out.println("Total nodes: " + nodes.size());
        Vector<String> dimensions = getAllDimensions();
        System.out.print("Dimensions: [");
        for (int i = 0; i < dimensions.size(); i++) {
            System.out.print(dimensions.get(i));
            if (i < dimensions.size() - 1) System.out.print(", ");
        }
        System.out.println("]");

        System.out.println("\nHeaders:");
        for (String dimension : headers.keySet()) {
            Vector<Node> headerList = headers.get(dimension);
            System.out.println("  " + dimension + ": " + headerList.size() + " nodes");
        }

        System.out.println("\nNodes:");
        for (int i = 0; i < Math.min(10, nodes.size()); i++) {
            Node node = nodes.get(i);
            System.out.println("  " + node);
            Vector<String> nodeDimensions = node.getDimensions();
            for (int j = 0; j < nodeDimensions.size(); j++) {
                String dim = nodeDimensions.get(j);
                System.out.println("    -> " + dim + ": " + node.getLinkCount(dim) + " links");
            }
        }
        if (nodes.size() > 10) {
            System.out.println("  ... (" + (nodes.size() - 10) + " more nodes)");
        }
    }

    @Override
    public String toString() {
        return "MultiLinkedList{nodes=" + nodes.size() + ", dimensions=" + getAllDimensions().size() + "}";
    }
}
