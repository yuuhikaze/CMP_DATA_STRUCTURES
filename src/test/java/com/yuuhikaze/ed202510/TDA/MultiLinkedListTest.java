package com.yuuhikaze.ed202510.TDA;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MultiLinkedList - a multi-dimensional linked structure
 */
class MultiLinkedListTest {

    private MultiLinkedList<String> mll;

    @BeforeEach
    void setUp() {
        mll = new MultiLinkedList<>();
    }

    @Test
    void testEmptyList() {
        assertTrue(mll.isEmpty(), "New list should be empty");
        assertEquals(0, mll.size(), "Size should be 0");
    }

    @Test
    void testCreateNode() {
        MultiLinkedList<String>.Node node = mll.createNode("Node1");

        assertNotNull(node, "Node should not be null");
        assertEquals("Node1", node.getElement(), "Element should match");
        assertEquals(1, mll.size(), "Size should be 1");
        assertFalse(mll.isEmpty(), "List should not be empty");
    }

    @Test
    void testCreateMultipleNodes() {
        MultiLinkedList<String>.Node node1 = mll.createNode("Node1");
        MultiLinkedList<String>.Node node2 = mll.createNode("Node2");
        MultiLinkedList<String>.Node node3 = mll.createNode("Node3");

        assertEquals(3, mll.size(), "Size should be 3");
    }

    @Test
    void testAddLink() {
        MultiLinkedList<String>.Node node1 = mll.createNode("Node1");
        MultiLinkedList<String>.Node node2 = mll.createNode("Node2");

        node1.addLink("next", node2);

        Vector<MultiLinkedList<String>.Node> links = node1.getLinks("next");
        assertEquals(1, links.size(), "Should have 1 link");
        assertEquals(node2, links.get(0), "Link should point to node2");
    }

    @Test
    void testAddMultipleLinksInSameDimension() {
        MultiLinkedList<String>.Node node1 = mll.createNode("Node1");
        MultiLinkedList<String>.Node node2 = mll.createNode("Node2");
        MultiLinkedList<String>.Node node3 = mll.createNode("Node3");

        node1.addLink("children", node2);
        node1.addLink("children", node3);

        Vector<MultiLinkedList<String>.Node> children = node1.getLinks("children");
        assertEquals(2, children.size(), "Should have 2 children");
    }

    @Test
    void testAddLinksInDifferentDimensions() {
        MultiLinkedList<String>.Node node1 = mll.createNode("Node1");
        MultiLinkedList<String>.Node node2 = mll.createNode("Node2");
        MultiLinkedList<String>.Node node3 = mll.createNode("Node3");

        node1.addLink("horizontal", node2);
        node1.addLink("vertical", node3);

        Vector<String> dimensions = node1.getDimensions();
        assertEquals(2, dimensions.size(), "Should have 2 dimensions");
        assertEquals(1, node1.getLinkCount("horizontal"), "Should have 1 horizontal link");
        assertEquals(1, node1.getLinkCount("vertical"), "Should have 1 vertical link");
    }

    @Test
    void testRemoveLink() {
        MultiLinkedList<String>.Node node1 = mll.createNode("Node1");
        MultiLinkedList<String>.Node node2 = mll.createNode("Node2");

        node1.addLink("next", node2);
        assertTrue(node1.removeLink("next", node2), "Should remove link successfully");
        assertEquals(0, node1.getLinkCount("next"), "Should have no links");
    }

    @Test
    void testRemoveNonExistentLink() {
        MultiLinkedList<String>.Node node1 = mll.createNode("Node1");
        MultiLinkedList<String>.Node node2 = mll.createNode("Node2");

        assertFalse(node1.removeLink("next", node2), "Should return false for non-existent link");
    }

    @Test
    void testNodeMetadata() {
        MultiLinkedList<String>.Node node = mll.createNode("Node1");

        node.setMetadata("age", 25);
        node.setMetadata("name", "John");

        assertEquals(25, node.getMetadata("age"), "Age metadata should match");
        assertEquals("John", node.getMetadata("name"), "Name metadata should match");
        assertTrue(node.hasMetadata("age"), "Should have age metadata");
        assertFalse(node.hasMetadata("city"), "Should not have city metadata");
    }

    @Test
    void testClearLinks() {
        MultiLinkedList<String>.Node node1 = mll.createNode("Node1");
        MultiLinkedList<String>.Node node2 = mll.createNode("Node2");
        MultiLinkedList<String>.Node node3 = mll.createNode("Node3");

        node1.addLink("children", node2);
        node1.addLink("children", node3);

        node1.clearLinks("children");
        assertEquals(0, node1.getLinkCount("children"), "Should have no children after clear");
    }

    @Test
    void testClearAllLinks() {
        MultiLinkedList<String>.Node node1 = mll.createNode("Node1");
        MultiLinkedList<String>.Node node2 = mll.createNode("Node2");
        MultiLinkedList<String>.Node node3 = mll.createNode("Node3");

        node1.addLink("horizontal", node2);
        node1.addLink("vertical", node3);

        node1.clearAllLinks();
        assertEquals(0, node1.getDimensions().size(), "Should have no dimensions");
    }

    @Test
    void testAddToHeader() {
        MultiLinkedList<String>.Node node1 = mll.createNode("Node1");
        MultiLinkedList<String>.Node node2 = mll.createNode("Node2");

        mll.addToHeader("category_A", node1);
        mll.addToHeader("category_A", node2);

        Vector<MultiLinkedList<String>.Node> header = mll.getHeader("category_A");
        assertEquals(2, header.size(), "Header should have 2 nodes");
    }

    @Test
    void testGetEmptyHeader() {
        Vector<MultiLinkedList<String>.Node> header = mll.getHeader("non_existent");
        assertNotNull(header, "Should return non-null vector");
        assertEquals(0, header.size(), "Should be empty");
    }

    @Test
    void testRemoveNode() {
        MultiLinkedList<String>.Node node1 = mll.createNode("Node1");
        MultiLinkedList<String>.Node node2 = mll.createNode("Node2");
        MultiLinkedList<String>.Node node3 = mll.createNode("Node3");

        node1.addLink("next", node2);
        node2.addLink("next", node3);

        assertTrue(mll.removeNode(node2), "Should remove node successfully");
        assertEquals(2, mll.size(), "Size should be 2");
        assertEquals(0, node1.getLinks("next").size(), "Links to removed node should be gone");
    }

    @Test
    void testFindNodes() {
        mll.createNode("Alice");
        mll.createNode("Bob");
        mll.createNode("Alice");

        Vector<MultiLinkedList<String>.Node> found = mll.findNodes("Alice");
        assertEquals(2, found.size(), "Should find 2 Alice nodes");
    }

    @Test
    void testFindNodesByMetadata() {
        MultiLinkedList<String>.Node node1 = mll.createNode("Person1");
        MultiLinkedList<String>.Node node2 = mll.createNode("Person2");
        MultiLinkedList<String>.Node node3 = mll.createNode("Person3");

        node1.setMetadata("age", 25);
        node2.setMetadata("age", 30);
        node3.setMetadata("age", 25);

        Vector<MultiLinkedList<String>.Node> found = mll.findNodesByMetadata("age", 25);
        assertEquals(2, found.size(), "Should find 2 nodes with age 25");
    }

    @Test
    void testFindNodesByMetadataRange() {
        MultiLinkedList<String>.Node node1 = mll.createNode("Person1");
        MultiLinkedList<String>.Node node2 = mll.createNode("Person2");
        MultiLinkedList<String>.Node node3 = mll.createNode("Person3");
        MultiLinkedList<String>.Node node4 = mll.createNode("Person4");

        node1.setMetadata("age", 20);
        node2.setMetadata("age", 25);
        node3.setMetadata("age", 30);
        node4.setMetadata("age", 35);

        Vector<MultiLinkedList<String>.Node> found = mll.findNodesByMetadataRange("age", 23, 32);
        assertEquals(2, found.size(), "Should find 2 nodes in age range 23-32");
    }

    @Test
    void testTraverse() {
        MultiLinkedList<String>.Node node1 = mll.createNode("A");
        MultiLinkedList<String>.Node node2 = mll.createNode("B");
        MultiLinkedList<String>.Node node3 = mll.createNode("C");
        MultiLinkedList<String>.Node node4 = mll.createNode("D");

        node1.addLink("path", node2);
        node2.addLink("path", node3);
        node2.addLink("path", node4);

        Vector<MultiLinkedList<String>.Node> traversed = mll.traverse(node1, "path");
        assertEquals(4, traversed.size(), "Should traverse all 4 nodes");
    }

    @Test
    void testGetAllDimensions() {
        MultiLinkedList<String>.Node node1 = mll.createNode("Node1");
        MultiLinkedList<String>.Node node2 = mll.createNode("Node2");

        node1.addLink("horizontal", node2);
        node1.addLink("vertical", node2);
        mll.addToHeader("category", node1);

        Vector<String> dimensions = mll.getAllDimensions();
        assertTrue(dimensions.size() >= 2, "Should have at least 2 dimensions");
    }

    @Test
    void testClear() {
        mll.createNode("Node1");
        mll.createNode("Node2");
        mll.createNode("Node3");

        mll.clear();
        assertEquals(0, mll.size(), "Size should be 0 after clear");
        assertTrue(mll.isEmpty(), "List should be empty after clear");
    }

    @Test
    void testComplexMultiDimensionalStructure() {
        // Create a structure similar to the Supermaxi demo
        MultiLinkedList<String>.Node client1 = mll.createNode("Client1");
        MultiLinkedList<String>.Node client2 = mll.createNode("Client2");
        MultiLinkedList<String>.Node product1 = mll.createNode("Product1");
        MultiLinkedList<String>.Node product2 = mll.createNode("Product2");
        MultiLinkedList<String>.Node cartItem1 = mll.createNode("CartItem1");
        MultiLinkedList<String>.Node cartItem2 = mll.createNode("CartItem2");

        // Client -> Cart items
        client1.addLink("cart", cartItem1);
        client1.addLink("cart", cartItem2);

        // Product -> Clients who bought it
        product1.addLink("buyers", client1);
        product1.addLink("buyers", client2);

        // Add metadata
        client1.setMetadata("age", 25);
        client2.setMetadata("age", 35);

        // Add to headers
        mll.addToHeader("clients", client1);
        mll.addToHeader("clients", client2);

        // Verify structure
        assertEquals(2, client1.getLinkCount("cart"), "Client1 should have 2 cart items");
        assertEquals(2, product1.getLinkCount("buyers"), "Product1 should have 2 buyers");
        assertEquals(2, mll.getHeader("clients").size(), "Should have 2 clients in header");

        Vector<MultiLinkedList<String>.Node> youngClients = mll.findNodesByMetadataRange("age", 20, 30);
        assertEquals(1, youngClients.size(), "Should find 1 client in age range");
    }

    @Test
    void testPrintStructure() {
        mll.createNode("Node1");
        mll.createNode("Node2");

        // Should not throw exception
        assertDoesNotThrow(() -> mll.printStructure(), "printStructure should not throw");
    }

    @Test
    void testToString() {
        mll.createNode("Node1");
        mll.createNode("Node2");

        String str = mll.toString();
        assertTrue(str.contains("2"), "toString should contain node count");
    }

    @Test
    void testGetAllNodes() {
        mll.createNode("Node1");
        mll.createNode("Node2");
        mll.createNode("Node3");

        Vector<MultiLinkedList<String>.Node> allNodes = mll.getAllNodes();
        assertEquals(3, allNodes.size(), "Should get all 3 nodes");
    }

    @Test
    void testSparseMatrixSimulation() {
        // Simulate a sparse matrix using MultiLinkedList
        // Matrix positions linked by row and column
        MultiLinkedList<Integer>.Node[][] matrix = new MultiLinkedList.Node[3][3];
        MultiLinkedList<Integer> sparseMatrix = new MultiLinkedList<>();

        // Only create nodes for non-zero values
        matrix[0][0] = sparseMatrix.createNode(5);
        matrix[0][2] = sparseMatrix.createNode(3);
        matrix[2][1] = sparseMatrix.createNode(7);

        // Link by rows
        matrix[0][0].addLink("row", matrix[0][2]);

        // Add metadata for position
        matrix[0][0].setMetadata("row", 0);
        matrix[0][0].setMetadata("col", 0);
        matrix[0][2].setMetadata("row", 0);
        matrix[0][2].setMetadata("col", 2);
        matrix[2][1].setMetadata("row", 2);
        matrix[2][1].setMetadata("col", 1);

        assertEquals(3, sparseMatrix.size(), "Should have 3 non-zero elements");
        assertEquals(1, matrix[0][0].getLinkCount("row"), "Row 0 should have 1 link");
    }
}
