package com.yuuhikaze.ed202510.EXTRA;

import com.yuuhikaze.ed202510.TDA.MultiLinkedList;
import com.yuuhikaze.ed202510.TDA.Vector;
import com.yuuhikaze.ed202510.TDA.HashMap;

/**
 * Controller class for managing the Supermaxi supermarket using MultiLinkedList.
 * This demonstrates how MultiLinkedList can model complex relationships:
 * - Clients linked to their cart items
 * - Products linked to clients who bought them
 * - Age groups linked to clients in that range
 */
public class EI05_Supermaxi_Controller {

    private MultiLinkedList<Object> supermarket;
    private HashMap<String, MultiLinkedList<Object>.Node> clientNodes;
    private HashMap<String, MultiLinkedList<Object>.Node> productNodes;

    // Dimension names
    private static final String DIM_CLIENT_TO_CART = "client_cart";
    private static final String DIM_PRODUCT_TO_CLIENTS = "product_clients";
    private static final String DIM_AGE_GROUP = "age_group";

    public EI05_Supermaxi_Controller() {
        this.supermarket = new MultiLinkedList<>();
        this.clientNodes = new HashMap<>();
        this.productNodes = new HashMap<>();
    }

    /**
     * Adds a new client to the supermarket system
     */
    public void addClient(EI05_Client_Entity client) {
        MultiLinkedList<Object>.Node clientNode = supermarket.createNode(client);
        clientNode.setMetadata("type", "client");
        clientNode.setMetadata("age", client.getAge());
        clientNode.setMetadata("name", client.getName());

        clientNodes.put(client.getId(), clientNode);

        // Add to age group header
        supermarket.addToHeader(DIM_AGE_GROUP, clientNode);

        System.out.println("Added client: " + client);
    }

    /**
     * Adds a new product to the supermarket system
     */
    public void addProduct(EI05_Product_Entity product) {
        MultiLinkedList<Object>.Node productNode = supermarket.createNode(product);
        productNode.setMetadata("type", "product");
        productNode.setMetadata("category", product.getCategory());
        productNode.setMetadata("name", product.getName());

        productNodes.put(product.getProductId(), productNode);

        System.out.println("Added product: " + product);
    }

    /**
     * Adds an item to a client's cart
     * Creates multi-dimensional links:
     * - Client -> CartItem (client_cart dimension)
     * - Product -> Client (product_clients dimension)
     */
    public void addToCart(String clientId, String productId, int quantity) {
        MultiLinkedList<Object>.Node clientNode = clientNodes.get(clientId);
        MultiLinkedList<Object>.Node productNode = productNodes.get(productId);

        if (clientNode == null) {
            System.out.println("Error: Client not found with ID: " + clientId);
            return;
        }

        if (productNode == null) {
            System.out.println("Error: Product not found with ID: " + productId);
            return;
        }

        EI05_Product_Entity product = (EI05_Product_Entity) productNode.getElement();
        EI05_CartItem_Entity cartItem = new EI05_CartItem_Entity(product, quantity);

        // Create cart item node
        MultiLinkedList<Object>.Node cartItemNode = supermarket.createNode(cartItem);
        cartItemNode.setMetadata("type", "cart_item");
        cartItemNode.setMetadata("client_id", clientId);
        cartItemNode.setMetadata("product_id", productId);

        // Link client to cart item
        clientNode.addLink(DIM_CLIENT_TO_CART, cartItemNode);

        // Link product to client (for reverse lookup)
        productNode.addLink(DIM_PRODUCT_TO_CLIENTS, clientNode);

        System.out.println("Added to cart: " + cartItem + " for client " + clientId);
    }

    /**
     * Gets all clients who bought a specific product category
     */
    public Vector<String> getClientsByProductCategory(String category) {
        Vector<String> clientNames = new Vector<>();

        // Iterate through all product nodes
        for (String productId : productNodes.keySet()) {
            MultiLinkedList<Object>.Node productNode = productNodes.get(productId);
            String productCategory = (String) productNode.getMetadata("category");

            if (category.equals(productCategory)) {
                // Get all clients linked to this product
                Vector<MultiLinkedList<Object>.Node> clients = productNode.getLinks(DIM_PRODUCT_TO_CLIENTS);

                for (int i = 0; i < clients.size(); i++) {
                    MultiLinkedList<Object>.Node clientNode = clients.get(i);
                    EI05_Client_Entity client = (EI05_Client_Entity) clientNode.getElement();

                    // Avoid duplicates
                    boolean found = false;
                    for (int j = 0; j < clientNames.size(); j++) {
                        if (clientNames.get(j).equals(client.getName())) {
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        clientNames.add(client.getName());
                    }
                }
            }
        }

        return clientNames;
    }

    /**
     * Gets all carts of clients in a specific age range
     */
    public void showCartsByAgeRange(int minAge, int maxAge) {
        System.out.println("\n=== Carts of clients aged " + minAge + "-" + maxAge + " ===");

        Vector<MultiLinkedList<Object>.Node> ageGroupNodes = supermarket.getHeader(DIM_AGE_GROUP);

        for (int i = 0; i < ageGroupNodes.size(); i++) {
            MultiLinkedList<Object>.Node clientNode = ageGroupNodes.get(i);
            Integer age = (Integer) clientNode.getMetadata("age");

            if (age != null && age >= minAge && age <= maxAge) {
                EI05_Client_Entity client = (EI05_Client_Entity) clientNode.getElement();
                System.out.println("\nClient: " + client.getName() + " (Age: " + age + ")");
                System.out.println("Cart:");

                // Get cart items for this client
                Vector<MultiLinkedList<Object>.Node> cartItems = clientNode.getLinks(DIM_CLIENT_TO_CART);

                if (cartItems.size() == 0) {
                    System.out.println("  (empty)");
                } else {
                    for (int j = 0; j < cartItems.size(); j++) {
                        MultiLinkedList<Object>.Node cartItemNode = cartItems.get(j);
                        EI05_CartItem_Entity cartItem = (EI05_CartItem_Entity) cartItemNode.getElement();
                        System.out.println("  - " + cartItem);
                    }
                }
            }
        }
    }

    /**
     * Shows all clients and their carts
     */
    public void showAllClientsAndCarts() {
        System.out.println("\n=== All Clients and Their Carts ===");

        for (String clientId : clientNodes.keySet()) {
            MultiLinkedList<Object>.Node clientNode = clientNodes.get(clientId);
            EI05_Client_Entity client = (EI05_Client_Entity) clientNode.getElement();

            System.out.println("\n" + client);
            System.out.println("Cart:");

            Vector<MultiLinkedList<Object>.Node> cartItems = clientNode.getLinks(DIM_CLIENT_TO_CART);

            if (cartItems.size() == 0) {
                System.out.println("  (empty)");
            } else {
                double total = 0.0;
                for (int i = 0; i < cartItems.size(); i++) {
                    MultiLinkedList<Object>.Node cartItemNode = cartItems.get(i);
                    EI05_CartItem_Entity cartItem = (EI05_CartItem_Entity) cartItemNode.getElement();
                    System.out.println("  - " + cartItem);
                    total += cartItem.getTotalPrice();
                }
                System.out.println("  Total: $" + total);
            }
        }
    }

    /**
     * Shows which clients bought a specific product
     */
    public void showClientsByProduct(String productId) {
        MultiLinkedList<Object>.Node productNode = productNodes.get(productId);

        if (productNode == null) {
            System.out.println("Error: Product not found with ID: " + productId);
            return;
        }

        EI05_Product_Entity product = (EI05_Product_Entity) productNode.getElement();
        System.out.println("\n=== Clients who bought: " + product.getName() + " ===");

        Vector<MultiLinkedList<Object>.Node> clients = productNode.getLinks(DIM_PRODUCT_TO_CLIENTS);

        if (clients.size() == 0) {
            System.out.println("No clients have bought this product yet.");
        } else {
            for (int i = 0; i < clients.size(); i++) {
                MultiLinkedList<Object>.Node clientNode = clients.get(i);
                EI05_Client_Entity client = (EI05_Client_Entity) clientNode.getElement();
                System.out.println("  - " + client.getName() + " (Age: " + client.getAge() + ")");
            }
        }
    }

    /**
     * Gets statistics about the supermarket
     */
    public void showStatistics() {
        System.out.println("\n=== Supermaxi Statistics ===");
        System.out.println("Total nodes in system: " + supermarket.size());
        System.out.println("Total clients: " + clientNodes.size());
        System.out.println("Total products: " + productNodes.size());

        int totalCartItems = 0;
        for (String clientId : clientNodes.keySet()) {
            MultiLinkedList<Object>.Node clientNode = clientNodes.get(clientId);
            totalCartItems += clientNode.getLinkCount(DIM_CLIENT_TO_CART);
        }
        System.out.println("Total cart items: " + totalCartItems);

        Vector<String> dimensions = supermarket.getAllDimensions();
        System.out.print("Dimensions: ");
        for (int i = 0; i < dimensions.size(); i++) {
            System.out.print(dimensions.get(i));
            if (i < dimensions.size() - 1) System.out.print(", ");
        }
        System.out.println();
    }

    /**
     * Prints the internal structure of the MultiLinkedList (for debugging)
     */
    public void printStructure() {
        supermarket.printStructure();
    }
}
