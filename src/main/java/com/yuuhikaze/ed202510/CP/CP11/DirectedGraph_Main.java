package com.yuuhikaze.ed202510.CP.CP11;

import com.yuuhikaze.ed202510.TDA.AdjacencyMapGraph;
import com.yuuhikaze.ed202510.TDA.UnsortedTableMap;
import com.yuuhikaze.ed202510.TDA.interfaces.Edge;
import com.yuuhikaze.ed202510.TDA.interfaces.Graph;
import com.yuuhikaze.ed202510.TDA.interfaces.Map;
import com.yuuhikaze.ed202510.TDA.interfaces.Vertex;
import com.yuuhikaze.ed202510.TDA.interfaces.PositionalList;
import com.yuuhikaze.ed202510.TDA.interfaces.Position;
import com.yuuhikaze.ed202510.TDA.misc.GraphAlgorithms;

public class DirectedGraph_Main {
    public static void main(String[] args) {
        System.out.println("=== CP11 EXERCISE 1: DIRECTED GRAPH ANALYSIS ===\n");

        // Build directed graph from Fig. 1 with edges: BM, BA, BJ, AM, AF
        Graph<String, Integer> graph = buildDirectedGraph();

        // Map to access vertices by name
        Map<String, Vertex<String>> vertices = new UnsortedTableMap<>();
        for (Vertex<String> v : graph.vertices()) {
            vertices.put(v.getElement(), v);
        }

        System.out.println("Directed Graph G constructed:");
        displayGraph(graph);

        // Exercise 1.a: Determine if G is a DAG
        System.out.println("\n" + "=".repeat(60));
        System.out.println("EXERCISE 1.a: Is G a DAG (Directed Acyclic Graph)?");
        System.out.println("=".repeat(60));
        checkIfDAG(graph);

        // Exercise 1.b: Print topological order
        System.out.println("\n" + "=".repeat(60));
        System.out.println("EXERCISE 1.b: Topological Order of G");
        System.out.println("=".repeat(60));
        printTopologicalOrder(graph);

        // Exercise 1.c: Transitive closure
        System.out.println("\n" + "=".repeat(60));
        System.out.println("EXERCISE 1.c: Transitive Closure of G");
        System.out.println("=".repeat(60));
        computeAndPrintTransitiveClosure(graph);
    }

    private static Graph<String, Integer> buildDirectedGraph() {
        // Create directed graph
        Graph<String, Integer> g = new AdjacencyMapGraph<>(true);

        // Insert vertices
        Vertex<String> B = g.insertVertex("B");
        Vertex<String> M = g.insertVertex("M");
        Vertex<String> A = g.insertVertex("A");
        Vertex<String> J = g.insertVertex("J");
        Vertex<String> F = g.insertVertex("F");

        // Insert directed edges: BM, BA, BJ, AM, AF
        // Note: The transcript shows BM, BA, BJ, AM, AF but the diagram shows:
        // B->M, M->A, B->A, B->J, A->J, A->F
        g.insertEdge(B, M, 1);
        g.insertEdge(M, A, 1);
        g.insertEdge(B, A, 1);
        g.insertEdge(B, J, 1);
        g.insertEdge(A, J, 1);
        g.insertEdge(A, F, 1);

        return g;
    }

    private static void displayGraph(Graph<String, Integer> graph) {
        System.out.println("Vertices: " + graph.numVertices());
        System.out.println("Edges: " + graph.numEdges());

        System.out.println("\nDirected edges:");
        for (Edge<Integer> e : graph.edges()) {
            Vertex<String>[] endpoints = graph.endVertices(e);
            System.out.println("  " + endpoints[0].getElement() + " -> " +
                             endpoints[1].getElement());
        }

        System.out.println("\nAdjacency list:");
        for (Vertex<String> v : graph.vertices()) {
            System.out.print("  " + v.getElement() + " -> {");
            boolean first = true;
            for (Edge<Integer> e : graph.outgoingEdges(v)) {
                Vertex<String> dest = graph.opposite(v, e);
                if (!first) System.out.print(", ");
                System.out.print(dest.getElement());
                first = false;
            }
            System.out.println("}");
        }
    }

    private static void checkIfDAG(Graph<String, Integer> graph) {
        boolean isDAG = GraphAlgorithms.isDAG(graph);

        if (isDAG) {
            System.out.println("✓ YES - Graph G is a DAG (Directed Acyclic Graph)");
            System.out.println("\nExplanation:");
            System.out.println("A DAG is a directed graph with no directed cycles.");
            System.out.println("This graph has no cycles because all edges point in a");
            System.out.println("consistent direction without forming any loops.");
        } else {
            System.out.println("✗ NO - Graph G is NOT a DAG");
            System.out.println("\nThe graph contains at least one directed cycle.");
        }

        System.out.println("\nMethod used:");
        System.out.println("Topological sort algorithm. If all vertices can be ordered,");
        System.out.println("then the graph is acyclic (DAG). Otherwise, it has cycles.");
    }

    private static void printTopologicalOrder(Graph<String, Integer> graph) {
        PositionalList<Vertex<String>> topoOrder = GraphAlgorithms.topologicalSort(graph);

        System.out.println("Topological order:");
        System.out.print("  ");
        boolean first = true;
        for (Position<Vertex<String>> pos = topoOrder.first();
             pos != null; pos = topoOrder.after(pos)) {
            if (!first) System.out.print(" → ");
            System.out.print(pos.getElement().getElement());
            first = false;
        }
        System.out.println();

        System.out.println("\nExplanation:");
        System.out.println("Topological order is a linear ordering of vertices such that");
        System.out.println("for every directed edge (u, v), vertex u comes before v.");
        System.out.println("\nAlgorithm used: Kahn's algorithm");
        System.out.println("1. Find vertices with no incoming edges");
        System.out.println("2. Remove them and add to order");
        System.out.println("3. Repeat until all vertices processed");

        // Verify the order
        System.out.println("\nVerification:");
        for (Position<Vertex<String>> pos = topoOrder.first();
             pos != null; pos = topoOrder.after(pos)) {
            Vertex<String> v = pos.getElement();
            System.out.print("  " + v.getElement() + ": ");

            boolean hasOutgoing = false;
            for (Edge<Integer> e : graph.outgoingEdges(v)) {
                hasOutgoing = true;
                Vertex<String> dest = graph.opposite(v, e);
                System.out.print("points to " + dest.getElement() + " ");
            }
            if (!hasOutgoing) {
                System.out.print("(no outgoing edges)");
            }
            System.out.println();
        }
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
