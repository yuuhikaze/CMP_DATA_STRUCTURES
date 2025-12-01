package com.yuuhikaze.ed202510.CP.CP10;

import com.yuuhikaze.ed202510.TDA.AdjacencyMapGraph;
import com.yuuhikaze.ed202510.TDA.UnsortedTableMap;
import com.yuuhikaze.ed202510.TDA.interfaces.Edge;
import com.yuuhikaze.ed202510.TDA.interfaces.Graph;
import com.yuuhikaze.ed202510.TDA.interfaces.Map;
import com.yuuhikaze.ed202510.TDA.interfaces.Vertex;
import com.yuuhikaze.ed202510.TDA.misc.GraphAlgorithms;
import java.util.HashSet;
import java.util.Set;

public class AdjacencyListGraph_Main {
    public static void main(String[] args) {
        System.out.println("=== CP10 EXERCISE 2: GRAPH FROM ADJACENCY LIST ===\n");

        // Build the graph based on the adjacency list
        Graph<Integer, Integer> graph = buildGraphFromAdjacencyList();

        // Map to access vertices by value
        Map<Integer, Vertex<Integer>> vertices = new UnsortedTableMap<>();
        for (Vertex<Integer> v : graph.vertices()) {
            vertices.put(v.getElement(), v);
        }

        System.out.println("Graph constructed from adjacency list:");
        displayGraph(graph);

        // Exercise 2a: Graph is already drawn in CP10_Drawings.tex

        // Exercise 2b: DFS traversal starting at vertex 1
        System.out.println("\n" + "=".repeat(60));
        System.out.println("EXERCISE 2.b: DFS Traversal starting at vertex 1");
        System.out.println("=".repeat(60));
        performDFS(graph, vertices.get(1));

        // Exercise 2c: BFS traversal starting at vertex 1
        System.out.println("\n" + "=".repeat(60));
        System.out.println("EXERCISE 2.c: BFS Traversal starting at vertex 1");
        System.out.println("=".repeat(60));
        performBFS(graph, vertices.get(1));
    }

    private static Graph<Integer, Integer> buildGraphFromAdjacencyList() {
        Graph<Integer, Integer> g = new AdjacencyMapGraph<>(false);

        // Create vertices 1-8
        Vertex<Integer> v1 = g.insertVertex(1);
        Vertex<Integer> v2 = g.insertVertex(2);
        Vertex<Integer> v3 = g.insertVertex(3);
        Vertex<Integer> v4 = g.insertVertex(4);
        Vertex<Integer> v5 = g.insertVertex(5);
        Vertex<Integer> v6 = g.insertVertex(6);
        Vertex<Integer> v7 = g.insertVertex(7);
        Vertex<Integer> v8 = g.insertVertex(8);

        // Add edges based on adjacency list
        // vertex 1 — (2, 3, 4)
        g.insertEdge(v1, v2, 1);
        g.insertEdge(v1, v3, 1);
        g.insertEdge(v1, v4, 1);

        // vertex 2 — (1, 3, 4) - only add 2-3 and 2-4 (1-2 already exists)
        g.insertEdge(v2, v3, 1);
        g.insertEdge(v2, v4, 1);

        // vertex 3 — (1, 2, 4) - only add 3-4 (others already exist)
        g.insertEdge(v3, v4, 1);

        // vertex 4 — (1, 2, 3, 6) - only add 4-6 (others already exist)
        g.insertEdge(v4, v6, 1);

        // vertex 5 — (6, 7, 8)
        g.insertEdge(v5, v6, 1);
        g.insertEdge(v5, v7, 1);
        g.insertEdge(v5, v8, 1);

        // vertex 6 — (4, 5, 7) - only add 6-7 (others already exist)
        g.insertEdge(v6, v7, 1);

        // vertex 7 — (5, 6, 8) - only add 7-8 (others already exist)
        g.insertEdge(v7, v8, 1);

        // vertex 8 — (5, 7) - all already exist

        return g;
    }

    private static void displayGraph(Graph<Integer, Integer> graph) {
        System.out.println("Vertices: " + graph.numVertices());
        System.out.println("Edges: " + graph.numEdges());

        System.out.println("\nAdjacency representation:");
        for (Vertex<Integer> v : graph.vertices()) {
            System.out.print("  " + v.getElement() + " — (");
            boolean first = true;
            for (Edge<Integer> e : graph.outgoingEdges(v)) {
                Vertex<Integer> opposite = graph.opposite(v, e);
                if (!first) System.out.print(", ");
                System.out.print(opposite.getElement());
                first = false;
            }
            System.out.println(")");
        }
    }

    private static void performDFS(Graph<Integer, Integer> graph, Vertex<Integer> start) {
        System.out.println("\nDFS Traversal starting from vertex " + start.getElement() + ":");

        Set<Vertex<Integer>> known = new HashSet<>();
        Map<Vertex<Integer>, Edge<Integer>> forest = new UnsortedTableMap<>();

        System.out.print("Sequence: ");
        dfsWithPrint(graph, start, known, forest);
        System.out.println();

        System.out.println("\nExplanation:");
        System.out.println("DFS explores as deep as possible before backtracking.");
        System.out.println("Following the adjacency list order, DFS visits neighbors in sequence:");
        System.out.println("1 → 2 (first neighbor of 1)");
        System.out.println("2 → 3 (first unvisited neighbor of 2)");
        System.out.println("3 → 4 (first unvisited neighbor of 3)");
        System.out.println("4 → 6 (first unvisited neighbor of 4)");
        System.out.println("6 → 5 (first unvisited neighbor of 6)");
        System.out.println("5 → 7 (first unvisited neighbor of 5)");
        System.out.println("7 → 8 (first unvisited neighbor of 7)");
    }

    private static void dfsWithPrint(Graph<Integer, Integer> graph, Vertex<Integer> u,
                                    Set<Vertex<Integer>> known, Map<Vertex<Integer>, Edge<Integer>> forest) {
        known.add(u);
        System.out.print(u.getElement() + " ");

        for (Edge<Integer> e : graph.outgoingEdges(u)) {
            Vertex<Integer> v = graph.opposite(u, e);
            if (!known.contains(v)) {
                forest.put(v, e);
                dfsWithPrint(graph, v, known, forest);
            }
        }
    }

    private static void performBFS(Graph<Integer, Integer> graph, Vertex<Integer> start) {
        System.out.println("\nBFS Traversal starting from vertex " + start.getElement() + ":");

        Set<Vertex<Integer>> known = new HashSet<>();
        Map<Vertex<Integer>, Edge<Integer>> forest = new UnsortedTableMap<>();

        GraphAlgorithms.BFS(graph, start, known, forest);

        // Print sequence
        System.out.print("Sequence: " + start.getElement());
        for (Vertex<Integer> v : graph.vertices()) {
            if (v != start && forest.get(v) != null) {
                System.out.print(" " + v.getElement());
            }
        }
        System.out.println();

        // Print levels
        System.out.println("\nBFS by levels:");
        displayBFSLevels(graph, start, forest);

        System.out.println("\nExplanation:");
        System.out.println("BFS explores level by level:");
        System.out.println("Level 0: {1} - starting vertex");
        System.out.println("Level 1: {2, 3, 4} - neighbors of 1");
        System.out.println("Level 2: {6} - neighbor of 4 not yet visited");
        System.out.println("Level 3: {5, 7} - neighbors of 6 not yet visited");
        System.out.println("Level 4: {8} - neighbor of 5 or 7 not yet visited");
    }

    private static void displayBFSLevels(Graph<Integer, Integer> graph,
                                        Vertex<Integer> start, Map<Vertex<Integer>, Edge<Integer>> forest) {
        Map<Vertex<Integer>, Integer> levels = new UnsortedTableMap<>();
        levels.put(start, 0);

        for (Vertex<Integer> v : graph.vertices()) {
            if (v != start && forest.get(v) != null) {
                int level = getDistance(graph, v, forest, start);
                levels.put(v, level);
            }
        }

        int maxLevel = 0;
        for (Vertex<Integer> v : graph.vertices()) {
            Integer level = levels.get(v);
            if (level != null && level > maxLevel) {
                maxLevel = level;
            }
        }

        for (int i = 0; i <= maxLevel; i++) {
            System.out.print("  Level " + i + ": {");
            boolean first = true;
            for (Vertex<Integer> v : graph.vertices()) {
                Integer level = levels.get(v);
                if (level != null && level == i) {
                    if (!first) System.out.print(", ");
                    System.out.print(v.getElement());
                    first = false;
                }
            }
            System.out.println("}");
        }
    }

    private static int getDistance(Graph<Integer, Integer> graph, Vertex<Integer> v,
                                  Map<Vertex<Integer>, Edge<Integer>> forest, Vertex<Integer> root) {
        int distance = 0;
        Vertex<Integer> current = v;

        while (current != root) {
            Edge<Integer> edge = forest.get(current);
            if (edge == null) break;
            current = graph.opposite(current, edge);
            distance++;
        }

        return distance;
    }
}
