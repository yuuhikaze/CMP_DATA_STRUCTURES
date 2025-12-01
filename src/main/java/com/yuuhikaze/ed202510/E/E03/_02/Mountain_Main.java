package com.yuuhikaze.ed202510.E.E03._02;

import com.yuuhikaze.ed202510.TDA.interfaces.Graph;

public class Mountain_Main {
    public static void main(String[] args) {
        System.out.println("=== CP12: HOSPITAL METROPOLITANO PATHFINDING SYSTEM ===\n");

        MountainController mountain = new MountainController();

        // Add mountain points (vertices)
        mountain.addMountainPoint(new MountainPoint("C3", "", 50));
        mountain.addMountainPoint(new MountainPoint("C4", "", 30));
        mountain.addMountainPoint(new MountainPoint("PC4", "", 20));
        mountain.addMountainPoint(new MountainPoint("PC3", "", 15));
        mountain.addMountainPoint(new MountainPoint("C2", "", 15));
        mountain.addMountainPoint(new MountainPoint("PC2", "", 25));
        mountain.addMountainPoint(new MountainPoint("PC1", "", 40));
        mountain.addMountainPoint(new MountainPoint("C1", "", 10));
        // Add edges (time to travel) [TODO] due to time constraints edges were not added
        // hospital.addTravelPath("", "", x);
        // hospital.addTravelPath("", "", x);
        // ...

        // Display network
        mountain.displayNetwork();

        // Find fastest routes for optimum "ascenso" (time) to mountain
        System.out.println("\n" + "=".repeat(60));
        System.out.println("DJISKTRA");
        System.out.println("=".repeat(60));
        mountain.findFastestRoute("<X point>", "<Y POINT>");
        // ...
        // Transitive closure
        System.out.println("\n" + "=".repeat(60));
        System.out.println("EXERCISE 1.c: Transitive Closure of G");
        System.out.println("=".repeat(60));
        computeAndPrintTransitiveClosure(mountain);
    }
    
    private static void computeAndPrintTransitiveClosure(Graph<String, Integer> graph) {
        System.out.println("Original graph edges:");
        int originalEdges = graph.numEdges();
        for (Edge<Integer> e : graph.edges()) {
            Vertex<String>[] endpoints = graph.endVertices(e);
            System.out.println("  " + endpoints[0].getElement() + " -> " +
                             endpoints[1].getElement());
        }

        // Compute transitive closure
        Graph<String, Integer> closure = GraphAlgorithms.computeTransitiveClosure(graph);

        System.out.println("\nTransitive Closure G' edges:");
        for (Edge<Integer> e : closure.edges()) {
            Vertex<String>[] endpoints = closure.endVertices(e);
            System.out.println("  " + endpoints[0].getElement() + " -> " +
                             endpoints[1].getElement());
        }

        System.out.println("\nStatistics:");
        System.out.println("  Original edges: " + originalEdges);
        System.out.println("  Closure edges:  " + closure.numEdges());
        System.out.println("  New edges added: " + (closure.numEdges() - originalEdges));

        System.out.println("\nExplanation:");
        System.out.println("Transitive closure adds edges for all reachable pairs.");
        System.out.println("If there's a path from u to v, we add edge (u, v).");
        System.out.println("\nAlgorithm: Floyd-Warshall");
        System.out.println("For each vertex k, if edges (i,k) and (k,j) exist,");
        System.out.println("add edge (i,j) if it doesn't already exist.");

        System.out.println("\nNew reachability:");
        for (Vertex<String> u : closure.vertices()) {
            System.out.print("  From " + u.getElement() + " can reach: {");
            boolean first = true;
            for (Edge<Integer> e : closure.outgoingEdges(u)) {
                Vertex<String> v = closure.opposite(u, e);
                if (!first) System.out.print(", ");
                System.out.print(v.getElement());
                first = false;
            }
            System.out.println("}");
        }

        System.out.println("\n(See CP11_Drawings.tex for visual representation)");
    }
}
