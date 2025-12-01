package com.yuuhikaze.ed202510.CP.CP10;

import com.yuuhikaze.ed202510.TDA.AdjacencyMapGraph;
import com.yuuhikaze.ed202510.TDA.UnsortedTableMap;
import com.yuuhikaze.ed202510.TDA.LinkedPositionalList;
import com.yuuhikaze.ed202510.TDA.interfaces.Edge;
import com.yuuhikaze.ed202510.TDA.interfaces.Graph;
import com.yuuhikaze.ed202510.TDA.interfaces.Map;
import com.yuuhikaze.ed202510.TDA.interfaces.Vertex;
import com.yuuhikaze.ed202510.TDA.interfaces.PositionalList;
import com.yuuhikaze.ed202510.TDA.interfaces.Position;
import com.yuuhikaze.ed202510.TDA.misc.GraphAlgorithms;
import java.util.HashSet;
import java.util.Set;

public class GraphTraversal_Main {
    public static void main(String[] args) {
        System.out.println("=== CP10 EXERCISE 1: GRAPH G TRAVERSALS ===\n");

        // Create the graph from Figure 1
        Graph<String, Integer> graph = buildGraphG();

        // Map to access vertices by name
        Map<String, Vertex<String>> vertices = new UnsortedTableMap<>();
        for (Vertex<String> v : graph.vertices()) {
            vertices.put(v.getElement(), v);
        }

        System.out.println("Graph G constructed:");
        displayGraph(graph);

        // Exercise 1a: DFS and BFS traversals
        System.out.println("\n" + "=".repeat(60));
        System.out.println("EXERCISE 1.a: DFS and BFS Traversals");
        System.out.println("=".repeat(60));

        performDFS(graph, vertices.get("A"));
        performBFS(graph, vertices.get("A"));

        // Exercise 1b: Shortest path from A to H
        System.out.println("\n" + "=".repeat(60));
        System.out.println("EXERCISE 1.b: Shortest Path from A to H");
        System.out.println("=".repeat(60));
        findShortestPath(graph, vertices.get("A"), vertices.get("H"));

        // Exercise 1c: Spanning tree
        System.out.println("\n" + "=".repeat(60));
        System.out.println("EXERCISE 1.c: Spanning Tree of G");
        System.out.println("=".repeat(60));
        printSpanningTree(graph);

        // Exercise 1d: Spanning forest without red edges
        System.out.println("\n" + "=".repeat(60));
        System.out.println("EXERCISE 1.d: Spanning Forest (without red edges)");
        System.out.println("=".repeat(60));
        Graph<String, Integer> graphWithoutRed = buildGraphGWithoutRedEdges();
        printSpanningForest(graphWithoutRed);
    }

    private static Graph<String, Integer> buildGraphG() {
        Graph<String, Integer> g = new AdjacencyMapGraph<>(false);

        // Insert vertices
        Vertex<String> A = g.insertVertex("A");
        Vertex<String> B = g.insertVertex("B");
        Vertex<String> C = g.insertVertex("C");
        Vertex<String> D = g.insertVertex("D");
        Vertex<String> E = g.insertVertex("E");
        Vertex<String> F = g.insertVertex("F");
        Vertex<String> G = g.insertVertex("G");
        Vertex<String> H = g.insertVertex("H");

        // Insert black edges (weight 1)
        g.insertEdge(A, C, 1);
        g.insertEdge(C, B, 1);
        g.insertEdge(B, A, 1);
        g.insertEdge(A, D, 1);
        g.insertEdge(D, G, 1);
        g.insertEdge(B, G, 1);
        g.insertEdge(E, F, 1);
        g.insertEdge(E, H, 1);
        g.insertEdge(F, H, 1);

        // Insert red edges (weight 2 to distinguish)
        g.insertEdge(A, E, 2);
        g.insertEdge(B, E, 2);
        g.insertEdge(D, E, 2);

        return g;
    }

    private static Graph<String, Integer> buildGraphGWithoutRedEdges() {
        Graph<String, Integer> g = new AdjacencyMapGraph<>(false);

        // Insert vertices
        Vertex<String> A = g.insertVertex("A");
        Vertex<String> B = g.insertVertex("B");
        Vertex<String> C = g.insertVertex("C");
        Vertex<String> D = g.insertVertex("D");
        Vertex<String> E = g.insertVertex("E");
        Vertex<String> F = g.insertVertex("F");
        Vertex<String> G = g.insertVertex("G");
        Vertex<String> H = g.insertVertex("H");

        // Insert only black edges
        g.insertEdge(A, C, 1);
        g.insertEdge(C, B, 1);
        g.insertEdge(B, A, 1);
        g.insertEdge(A, D, 1);
        g.insertEdge(D, G, 1);
        g.insertEdge(B, G, 1);
        g.insertEdge(E, F, 1);
        g.insertEdge(E, H, 1);
        g.insertEdge(F, H, 1);

        return g;
    }

    private static void displayGraph(Graph<String, Integer> graph) {
        System.out.println("Vertices: " + graph.numVertices());
        System.out.println("Edges: " + graph.numEdges());

        System.out.println("\nEdge list:");
        for (Edge<Integer> e : graph.edges()) {
            Vertex<String>[] endpoints = graph.endVertices(e);
            String type = e.getElement() == 2 ? " (RED)" : "";
            System.out.println("  " + endpoints[0].getElement() + " -- " +
                             endpoints[1].getElement() + type);
        }
    }

    private static void performDFS(Graph<String, Integer> graph, Vertex<String> start) {
        System.out.println("\nDFS Traversal starting from " + start.getElement() + ":");

        Set<Vertex<String>> known = new HashSet<>();
        Map<Vertex<String>, Edge<Integer>> forest = new UnsortedTableMap<>();

        dfsWithPrint(graph, start, known, forest);

        System.out.println("\nDFS Tree edges:");
        for (Vertex<String> v : graph.vertices()) {
            Edge<Integer> edge = forest.get(v);
            if (edge != null) {
                Vertex<String> parent = graph.opposite(v, edge);
                System.out.println("  " + parent.getElement() + " -> " + v.getElement());
            }
        }
    }

    private static void dfsWithPrint(Graph<String, Integer> graph, Vertex<String> u,
                                    Set<Vertex<String>> known, Map<Vertex<String>, Edge<Integer>> forest) {
        known.add(u);
        System.out.print(u.getElement() + " ");

        for (Edge<Integer> e : graph.outgoingEdges(u)) {
            Vertex<String> v = graph.opposite(u, e);
            if (!known.contains(v)) {
                forest.put(v, e);
                dfsWithPrint(graph, v, known, forest);
            }
        }
    }

    private static void performBFS(Graph<String, Integer> graph, Vertex<String> start) {
        System.out.println("\nBFS Traversal starting from " + start.getElement() + ":");

        Set<Vertex<String>> known = new HashSet<>();
        Map<Vertex<String>, Edge<Integer>> forest = new UnsortedTableMap<>();

        GraphAlgorithms.BFS(graph, start, known, forest);

        System.out.print("Sequence: " + start.getElement());
        for (Vertex<String> v : graph.vertices()) {
            if (v != start && forest.get(v) != null) {
                System.out.print(" " + v.getElement());
            }
        }
        System.out.println();

        System.out.println("\nBFS Tree edges:");
        for (Vertex<String> v : graph.vertices()) {
            Edge<Integer> edge = forest.get(v);
            if (edge != null) {
                Vertex<String> parent = graph.opposite(v, edge);
                System.out.println("  " + parent.getElement() + " -> " + v.getElement());
            }
        }
    }

    private static void findShortestPath(Graph<String, Integer> graph,
                                        Vertex<String> start, Vertex<String> end) {
        Set<Vertex<String>> known = new HashSet<>();
        Map<Vertex<String>, Edge<Integer>> forest = new UnsortedTableMap<>();

        GraphAlgorithms.BFS(graph, start, known, forest);

        PositionalList<Edge<Integer>> path = GraphAlgorithms.constructPath(graph, start, end, forest);

        if (path.isEmpty()) {
            System.out.println("No path exists from " + start.getElement() + " to " + end.getElement());
        } else {
            System.out.print("Shortest path: " + start.getElement());
            Vertex<String> current = start;
            int distance = 0;

            for (Position<Edge<Integer>> pos = path.first(); pos != null; pos = path.after(pos)) {
                Edge<Integer> edge = pos.getElement();
                current = graph.opposite(current, edge);
                System.out.print(" -> " + current.getElement());
                distance++;
            }
            System.out.println("\nPath length: " + distance + " edges");
        }
    }

    private static void printSpanningTree(Graph<String, Integer> graph) {
        Map<Vertex<String>, Edge<Integer>> forest = GraphAlgorithms.DFSComplete(graph);

        System.out.println("Spanning Tree (using DFS):");
        System.out.println("\nTree edges:");

        int edgeCount = 0;
        for (Vertex<String> v : graph.vertices()) {
            Edge<Integer> edge = forest.get(v);
            if (edge != null) {
                Vertex<String> parent = graph.opposite(v, edge);
                System.out.println("  " + parent.getElement() + " -- " + v.getElement());
                edgeCount++;
            }
        }

        System.out.println("\nTotal tree edges: " + edgeCount);
        System.out.println("Expected for spanning tree: " + (graph.numVertices() - 1));
    }

    private static void printSpanningForest(Graph<String, Integer> graph) {
        Map<Vertex<String>, Edge<Integer>> forest = GraphAlgorithms.DFSComplete(graph);

        // Find roots (vertices with no parent in forest)
        Set<Vertex<String>> roots = new HashSet<>();
        for (Vertex<String> v : graph.vertices()) {
            if (forest.get(v) == null) {
                roots.add(v);
            }
        }

        System.out.println("Number of connected components (trees): " + roots.size());

        int treeNum = 1;
        for (Vertex<String> root : roots) {
            System.out.println("\nTree " + treeNum + " (rooted at " + root.getElement() + "):");

            Set<Vertex<String>> treeVertices = new HashSet<>();
            treeVertices.add(root);

            // Find all vertices in this tree
            for (Vertex<String> v : graph.vertices()) {
                Vertex<String> current = v;
                while (current != null) {
                    if (current == root) {
                        treeVertices.add(v);
                        break;
                    }
                    Edge<Integer> edge = forest.get(current);
                    if (edge == null) break;
                    current = graph.opposite(current, edge);
                }
            }

            System.out.println("  Vertices: " + treeVertices);

            System.out.println("  Edges:");
            for (Vertex<String> v : treeVertices) {
                Edge<Integer> edge = forest.get(v);
                if (edge != null) {
                    Vertex<String> parent = graph.opposite(v, edge);
                    System.out.println("    " + parent.getElement() + " -- " + v.getElement());
                }
            }

            treeNum++;
        }
    }
}
