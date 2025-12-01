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

public class AirportGraph_Main {
    public static void main(String[] args) {
        System.out.println("=== CP11 R-14.18 & R-14.19: AIRPORT FLIGHT NETWORK ===\n");

        // Build the airport graph from the transcript
        Graph<String, String> flightGraph = buildAirportGraph();

        System.out.println("Flight Network Graph:");
        displayGraph(flightGraph);

        // R-14.18: Compute topological ordering
        System.out.println("\n" + "=".repeat(60));
        System.out.println("R-14.18: TOPOLOGICAL ORDERING");
        System.out.println("=".repeat(60));
        computeTopologicalOrder(flightGraph);

        // R-14.19: Draw transitive closure
        System.out.println("\n" + "=".repeat(60));
        System.out.println("R-14.19: TRANSITIVE CLOSURE");
        System.out.println("=".repeat(60));
        computeTransitiveClosure(flightGraph);
    }

    private static Graph<String, String> buildAirportGraph() {
        Graph<String, String> g = new AdjacencyMapGraph<>(true);

        // Create vertices for airports
        Vertex<String> BOS = g.insertVertex("BOS");
        Vertex<String> JFK = g.insertVertex("JFK");
        Vertex<String> MIA = g.insertVertex("MIA");
        Vertex<String> ORD = g.insertVertex("ORD");
        Vertex<String> DFW = g.insertVertex("DFW");
        Vertex<String> SFO = g.insertVertex("SFO");
        Vertex<String> LAX = g.insertVertex("LAX");

        // Add directed edges based on R-14.18 diagram
        g.insertEdge(ORD, DFW, "flight");
        g.insertEdge(DFW, LAX, "flight");
        g.insertEdge(DFW, SFO, "flight");
        g.insertEdge(MIA, DFW, "flight");
        g.insertEdge(MIA, LAX, "flight");
        g.insertEdge(JFK, DFW, "flight");
        g.insertEdge(JFK, SFO, "flight");
        g.insertEdge(JFK, MIA, "flight");
        g.insertEdge(BOS, JFK, "flight");
        g.insertEdge(BOS, MIA, "flight");
        g.insertEdge(BOS, SFO, "flight");

        return g;
    }

    private static void displayGraph(Graph<String, String> graph) {
        System.out.println("Airports: " + graph.numVertices());
        System.out.println("Direct flights: " + graph.numEdges());

        System.out.println("\nFlight routes:");
        for (Edge<String> e : graph.edges()) {
            Vertex<String>[] endpoints = graph.endVertices(e);
            System.out.println("  " + endpoints[0].getElement() + " → " +
                             endpoints[1].getElement());
        }

        System.out.println("\nOutgoing flights from each airport:");
        for (Vertex<String> airport : graph.vertices()) {
            System.out.print("  " + airport.getElement() + " → {");
            boolean first = true;
            for (Edge<String> e : graph.outgoingEdges(airport)) {
                Vertex<String> dest = graph.opposite(airport, e);
                if (!first) System.out.print(", ");
                System.out.print(dest.getElement());
                first = false;
            }
            System.out.println("}");
        }
    }

    private static void computeTopologicalOrder(Graph<String, String> graph) {
        PositionalList<Vertex<String>> topoOrder = GraphAlgorithms.topologicalSort(graph);

        System.out.println("\nTopological ordering of airports:");
        System.out.print("  ");
        boolean first = true;
        for (Position<Vertex<String>> pos = topoOrder.first();
             pos != null; pos = topoOrder.after(pos)) {
            if (!first) System.out.print(" → ");
            System.out.print(pos.getElement().getElement());
            first = false;
        }
        System.out.println();

        System.out.println("\nInterpretation:");
        System.out.println("This ordering shows a sequence where each airport appears");
        System.out.println("before all airports it has direct flights to.");
        System.out.println("\nFor example, if you were routing packages:");
        System.out.println("- Start from BOS (earliest in order)");
        System.out.println("- Can route through intermediate hubs");
        System.out.println("- End at LAX or SFO (latest in order)");

        System.out.println("\nVerification - each airport's outgoing flights:");
        for (Position<Vertex<String>> pos = topoOrder.first();
             pos != null; pos = topoOrder.after(pos)) {
            Vertex<String> airport = pos.getElement();
            System.out.print("  " + airport.getElement() + ": flies to ");

            boolean hasFlights = false;
            for (Edge<String> e : graph.outgoingEdges(airport)) {
                hasFlights = true;
                Vertex<String> dest = graph.opposite(airport, e);
                System.out.print(dest.getElement() + " ");
            }
            if (!hasFlights) {
                System.out.print("(destination - no outgoing flights)");
            }
            System.out.println();
        }
    }

    private static void computeTransitiveClosure(Graph<String, String> graph) {
        System.out.println("\nOriginal graph:");
        System.out.println("  Direct flights: " + graph.numEdges());

        // Compute transitive closure
        Graph<String, String> closure = GraphAlgorithms.computeTransitiveClosure(graph);

        System.out.println("\nTransitive closure:");
        System.out.println("  Total reachability edges: " + closure.numEdges());
        System.out.println("  New edges added: " + (closure.numEdges() - graph.numEdges()));

        System.out.println("\nAll airports reachable from each airport:");
        System.out.println("(includes both direct and connecting flights)\n");

        for (Vertex<String> from : closure.vertices()) {
            System.out.print(from.getElement() + " can reach: {");
            boolean first = true;
            for (Edge<String> e : closure.outgoingEdges(from)) {
                Vertex<String> to = closure.opposite(from, e);
                if (!first) System.out.print(", ");
                System.out.print(to.getElement());
                first = false;
            }
            System.out.println("}");
        }

        System.out.println("\nComparison with original:");
        System.out.println("\nNew reachable destinations (via connections):");
        for (Vertex<String> from : graph.vertices()) {
            // Find destinations in closure but not in original
            boolean foundNew = false;
            for (Edge<String> e : closure.outgoingEdges(from)) {
                Vertex<String> to = closure.opposite(from, e);

                // Check if this edge exists in original graph
                boolean existsInOriginal = false;
                for (Vertex<String> origFrom : graph.vertices()) {
                    if (origFrom.getElement().equals(from.getElement())) {
                        for (Edge<String> origEdge : graph.outgoingEdges(origFrom)) {
                            Vertex<String> origTo = graph.opposite(origFrom, origEdge);
                            if (origTo.getElement().equals(to.getElement())) {
                                existsInOriginal = true;
                                break;
                            }
                        }
                    }
                }

                if (!existsInOriginal) {
                    if (!foundNew) {
                        System.out.print("  " + from.getElement() + " → ");
                        foundNew = true;
                    } else {
                        System.out.print(", ");
                    }
                    System.out.print(to.getElement());
                }
            }
            if (foundNew) System.out.println();
        }

        System.out.println("\n(See CP11_Drawings.tex for visual representation)");
    }
}
