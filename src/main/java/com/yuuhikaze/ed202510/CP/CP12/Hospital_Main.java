package com.yuuhikaze.ed202510.CP.CP12;

public class Hospital_Main {
    public static void main(String[] args) {
        System.out.println("=== CP12: HOSPITAL METROPOLITANO PATHFINDING SYSTEM ===\n");

        HospitalController hospital = new HospitalController();

        // Add rooms (vertices)
        hospital.addRoom(new Room("Emergency", "Emergency Department", 50));
        hospital.addRoom(new Room("Cardiology", "Heart Center", 30));
        hospital.addRoom(new Room("Radiology", "Imaging", 20));
        hospital.addRoom(new Room("Surgery1", "Operating Rooms", 15));
        hospital.addRoom(new Room("Surgery2", "Operating Rooms", 15));
        hospital.addRoom(new Room("ICU", "Intensive Care", 25));
        hospital.addRoom(new Room("Pediatrics", "Children's Ward", 40));
        hospital.addRoom(new Room("Pharmacy", "Medication", 10));
        hospital.addRoom(new Room("Lab", "Laboratory", 15));
        hospital.addRoom(new Room("Reception", "Main Entrance", 20));

        // Add corridors with travel times (edges with weights)
        // Emergency connections
        hospital.addCorridor("Emergency", "Reception", 45);
        hospital.addCorridor("Emergency", "Cardiology", 60);
        hospital.addCorridor("Emergency", "Radiology", 50);
        hospital.addCorridor("Emergency", "Surgery1", 80);

        // Cardiology connections
        hospital.addCorridor("Cardiology", "ICU", 40);
        hospital.addCorridor("Cardiology", "Radiology", 35);

        // Radiology connections
        hospital.addCorridor("Radiology", "Lab", 30);
        hospital.addCorridor("Radiology", "Surgery1", 55);

        // Surgery connections
        hospital.addCorridor("Surgery1", "Surgery2", 20);
        hospital.addCorridor("Surgery1", "ICU", 45);
        hospital.addCorridor("Surgery2", "ICU", 35);

        // ICU connections
        hospital.addCorridor("ICU", "Pharmacy", 50);

        // Pediatrics connections
        hospital.addCorridor("Pediatrics", "Reception", 70);
        hospital.addCorridor("Pediatrics", "Pharmacy", 55);

        // Pharmacy connections
        hospital.addCorridor("Pharmacy", "Lab", 40);

        // Reception connections
        hospital.addCorridor("Reception", "Lab", 65);

        // Display network
        hospital.displayNetwork();

        // Exercise b: Find fastest routes for emergency situations
        System.out.println("\n" + "=".repeat(60));
        System.out.println("EXERCISE b: EMERGENCY ROUTING (Dijkstra's Algorithm)");
        System.out.println("=".repeat(60));

        hospital.findFastestRoute("Emergency", "ICU");
        hospital.findFastestRoute("Emergency", "Pharmacy");
        hospital.findFastestRoute("Emergency", "Pediatrics");
        hospital.findFastestRoute("Cardiology", "Surgery2");

        // Exercise c: Complete tour from Emergency
        System.out.println("\n" + "=".repeat(60));
        System.out.println("EXERCISE c: VISITING ALL ROOMS FROM EMERGENCY");
        System.out.println("=".repeat(60));
        hospital.visitAllRooms("Emergency");

        // Exercise d: Alternative round trip routes
        System.out.println("\n" + "=".repeat(60));
        System.out.println("EXERCISE d: ROUND TRIP ROUTES");
        System.out.println("=".repeat(60));
        hospital.findAlternativeRoute("Emergency", "Cardiology");
        hospital.findAlternativeRoute("Emergency", "Surgery1");

        // Exercise e: DFS and BFS traversals
        System.out.println("\n" + "=".repeat(60));
        System.out.println("EXERCISE e: GRAPH TRAVERSALS");
        System.out.println("=".repeat(60));
        hospital.dfsTraversal("Emergency");
        hospital.bfsTraversal("Emergency");

        System.out.println("\n" + "=".repeat(60));
        System.out.println("SUMMARY");
        System.out.println("=".repeat(60));
        System.out.println("\nThe hospital navigation system successfully:");
        System.out.println("✓ Models corridors as weighted edges (travel time)");
        System.out.println("✓ Finds fastest routes using Dijkstra's algorithm");
        System.out.println("✓ Calculates complete tours to visit all rooms");
        System.out.println("✓ Computes round-trip routes");
        System.out.println("✓ Supports DFS and BFS traversal strategies");
        System.out.println("\n(See CP12_Drawing.tex for visual graph representation)");
    }
}
