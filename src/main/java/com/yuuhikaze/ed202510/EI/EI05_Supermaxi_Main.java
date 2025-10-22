package com.yuuhikaze.ed202510.EI;

import com.yuuhikaze.ed202510.TDA.Vector;

/**
 * Main class demonstrating the Supermaxi supermarket system using MultiLinkedList.
 *
 * This demo showcases how MultiLinkedList can model complex relationships:
 * - Multiple clients with different ages
 * - Multiple products in different categories
 * - Clients' shopping carts with multiple items
 * - Multi-dimensional queries:
 *   * Get all clients who bought products in a specific category (e.g., "meat")
 *   * Show carts of clients in a specific age range
 *   * Find which clients bought a specific product
 */
public class EI05_Supermaxi_Main {

    public static void main(String[] args) {
        EI05_Supermaxi_Controller controller = new EI05_Supermaxi_Controller();

        System.out.println("=".repeat(60));
        System.out.println("SUPERMAXI - Multi-Linked List Demonstration");
        System.out.println("=".repeat(60));

        // ===== Step 1: Add Clients =====
        System.out.println("\n>>> Step 1: Adding Clients\n");

        controller.addClient(new EI05_Client_Entity("C001", "Juan Pérez", 25));
        controller.addClient(new EI05_Client_Entity("C002", "María García", 35));
        controller.addClient(new EI05_Client_Entity("C003", "Carlos López", 28));
        controller.addClient(new EI05_Client_Entity("C004", "Ana Martínez", 42));
        controller.addClient(new EI05_Client_Entity("C005", "Luis Rodríguez", 19));
        controller.addClient(new EI05_Client_Entity("C006", "Sofia Torres", 31));

        // ===== Step 2: Add Products =====
        System.out.println("\n>>> Step 2: Adding Products\n");

        // Meat products
        controller.addProduct(new EI05_Product_Entity("P001", "Beef Steak", "meat", 12.99));
        controller.addProduct(new EI05_Product_Entity("P002", "Chicken Breast", "meat", 8.50));
        controller.addProduct(new EI05_Product_Entity("P003", "Pork Chops", "meat", 10.75));

        // Dairy products
        controller.addProduct(new EI05_Product_Entity("P004", "Milk", "dairy", 3.25));
        controller.addProduct(new EI05_Product_Entity("P005", "Cheese", "dairy", 5.99));
        controller.addProduct(new EI05_Product_Entity("P006", "Yogurt", "dairy", 4.50));

        // Fruits
        controller.addProduct(new EI05_Product_Entity("P007", "Apples", "fruit", 2.99));
        controller.addProduct(new EI05_Product_Entity("P008", "Bananas", "fruit", 1.99));
        controller.addProduct(new EI05_Product_Entity("P009", "Oranges", "fruit", 3.50));

        // Vegetables
        controller.addProduct(new EI05_Product_Entity("P010", "Tomatoes", "vegetable", 2.25));
        controller.addProduct(new EI05_Product_Entity("P011", "Lettuce", "vegetable", 1.75));

        // ===== Step 3: Add Items to Carts =====
        System.out.println("\n>>> Step 3: Adding Items to Shopping Carts\n");

        // Juan (25) - buys meat and fruits
        controller.addToCart("C001", "P001", 2); // Beef Steak
        controller.addToCart("C001", "P007", 3); // Apples
        controller.addToCart("C001", "P004", 1); // Milk

        // María (35) - buys meat, dairy and vegetables
        controller.addToCart("C002", "P002", 1); // Chicken Breast
        controller.addToCart("C002", "P005", 2); // Cheese
        controller.addToCart("C002", "P010", 3); // Tomatoes
        controller.addToCart("C002", "P011", 1); // Lettuce

        // Carlos (28) - buys meat and fruits
        controller.addToCart("C003", "P001", 1); // Beef Steak
        controller.addToCart("C003", "P003", 2); // Pork Chops
        controller.addToCart("C003", "P008", 5); // Bananas

        // Ana (42) - buys dairy and vegetables
        controller.addToCart("C004", "P004", 2); // Milk
        controller.addToCart("C004", "P006", 4); // Yogurt
        controller.addToCart("C004", "P010", 2); // Tomatoes

        // Luis (19) - buys meat and fruits
        controller.addToCart("C005", "P002", 2); // Chicken Breast
        controller.addToCart("C005", "P007", 2); // Apples
        controller.addToCart("C005", "P009", 3); // Oranges

        // Sofia (31) - buys everything
        controller.addToCart("C006", "P001", 1); // Beef Steak
        controller.addToCart("C006", "P004", 1); // Milk
        controller.addToCart("C006", "P007", 2); // Apples
        controller.addToCart("C006", "P010", 1); // Tomatoes

        // ===== Step 4: Show All Clients and Their Carts =====
        controller.showAllClientsAndCarts();

        // ===== Step 5: Query - Get All Clients Who Bought Meat =====
        System.out.println("\n" + "=".repeat(60));
        System.out.println(">>> Query 1: Clients who bought MEAT");
        System.out.println("=".repeat(60));

        Vector<String> meatClients = controller.getClientsByProductCategory("meat");
        System.out.println("\nClients who bought meat:");
        for (int i = 0; i < meatClients.size(); i++) {
            System.out.println("  - " + meatClients.get(i));
        }

        // ===== Step 6: Query - Get All Clients Who Bought Dairy =====
        System.out.println("\n" + "=".repeat(60));
        System.out.println(">>> Query 2: Clients who bought DAIRY");
        System.out.println("=".repeat(60));

        Vector<String> dairyClients = controller.getClientsByProductCategory("dairy");
        System.out.println("\nClients who bought dairy:");
        for (int i = 0; i < dairyClients.size(); i++) {
            System.out.println("  - " + dairyClients.get(i));
        }

        // ===== Step 7: Query - Show Carts of Clients Aged 20-30 =====
        System.out.println("\n" + "=".repeat(60));
        System.out.println(">>> Query 3: Carts of clients aged 20-30");
        System.out.println("=".repeat(60));

        controller.showCartsByAgeRange(20, 30);

        // ===== Step 8: Query - Show Carts of Clients Aged 30-45 =====
        System.out.println("\n" + "=".repeat(60));
        System.out.println(">>> Query 4: Carts of clients aged 30-45");
        System.out.println("=".repeat(60));

        controller.showCartsByAgeRange(30, 45);

        // ===== Step 9: Query - Show Which Clients Bought Specific Products =====
        System.out.println("\n" + "=".repeat(60));
        System.out.println(">>> Query 5: Who bought Beef Steak?");
        System.out.println("=".repeat(60));

        controller.showClientsByProduct("P001");

        System.out.println("\n" + "=".repeat(60));
        System.out.println(">>> Query 6: Who bought Chicken Breast?");
        System.out.println("=".repeat(60));

        controller.showClientsByProduct("P002");

        System.out.println("\n" + "=".repeat(60));
        System.out.println(">>> Query 7: Who bought Tomatoes?");
        System.out.println("=".repeat(60));

        controller.showClientsByProduct("P010");

        // ===== Step 10: Show Statistics =====
        controller.showStatistics();

        // ===== Step 11: Show Internal Structure =====
        System.out.println("\n" + "=".repeat(60));
        System.out.println(">>> Internal MultiLinkedList Structure");
        System.out.println("=".repeat(60));
        System.out.println();

        controller.printStructure();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("END OF DEMONSTRATION");
        System.out.println("=".repeat(60));
    }
}
