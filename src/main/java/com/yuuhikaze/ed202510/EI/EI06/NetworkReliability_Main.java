package com.yuuhikaze.ed202510.EI.EI06;

public class NetworkReliability_Main {
    public static void main(String[] args) {
        System.out.println("=== NETWORK RELIABILITY ANALYSIS (BICONNECTED COMPONENTS) ===\n");

        NetworkReliabilityController network = new NetworkReliabilityController();

        // Build the network from the report diagram
        // Biconnected component 1: S1, S2, S3 (triangle)
        network.addServer(new Server("S1", "West Coast", "Web Server"));
        network.addServer(new Server("S2", "West Coast", "Load Balancer"));
        network.addServer(new Server("S3", "West Coast", "Database"));

        // Biconnected component 2: S4, S5, S6, S7 (complex cluster)
        network.addServer(new Server("S4", "Central", "Gateway"));
        network.addServer(new Server("S5", "Central", "Application Server"));
        network.addServer(new Server("S6", "Central", "Cache Server"));
        network.addServer(new Server("S7", "Central", "API Gateway"));

        // Biconnected component 3: S8, S9, S10 (triangle)
        network.addServer(new Server("S8", "East Coast", "Database"));
        network.addServer(new Server("S9", "East Coast", "Web Server"));
        network.addServer(new Server("S10", "East Coast", "Backup Server"));

        // Component 1 connections (forms a triangle - biconnected)
        network.addConnection("S1", "S2", "Fiber");
        network.addConnection("S1", "S3", "Fiber");
        network.addConnection("S2", "S3", "Fiber");

        // Bridge between components 1 and 2
        network.addConnection("S2", "S4", "Satellite");

        // Component 2 connections (complex mesh - biconnected)
        network.addConnection("S4", "S5", "Fiber");
        network.addConnection("S4", "S6", "Fiber");
        network.addConnection("S5", "S6", "Fiber");
        network.addConnection("S5", "S7", "Fiber");
        network.addConnection("S6", "S7", "Fiber");

        // Bridge between components 2 and 3
        network.addConnection("S7", "S8", "Microwave");

        // Component 3 connections (forms a triangle - biconnected)
        network.addConnection("S8", "S9", "Fiber");
        network.addConnection("S8", "S10", "Fiber");
        network.addConnection("S9", "S10", "Fiber");

        network.displayNetwork();

        // Find articulation points and bridges
        network.findArticulationPointsAndBridges();

        // Analyze overall robustness
        network.analyzeRobustness();

        // Simulate failures of critical components
        network.simulateServerFailure("S2");
        network.simulateServerFailure("S4");
        network.simulateServerFailure("S1"); // Not critical

        network.simulateConnectionFailure("S2", "S4");
        network.simulateConnectionFailure("S7", "S8");
        network.simulateConnectionFailure("S1", "S3"); // Not a bridge

        System.out.println("\n=== CONCLUSION ===");
        System.out.println("The network has 3 biconnected components connected by bridges.");
        System.out.println("Articulation points (S2, S4, S7) are single points of failure.");
        System.out.println("To improve robustness: add redundant connections across components.");
    }
}
