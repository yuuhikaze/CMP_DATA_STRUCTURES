package com.yuuhikaze.ed202510.E.E03._02;

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

class MountainController {
    private Graph<MountainPoint, Integer> corridorNetwork;
    private Map<String, Vertex<MountainPoint>> roomVertices;

    public MountainController() {
        this.corridorNetwork = new AdjacencyMapGraph<>(false); // undirected
        this.roomVertices = new UnsortedTableMap<>();
    }

    public void addMountainPoint(MountainPoint room) {
        if (roomVertices.get(room.getName()) == null) {
            Vertex<MountainPoint> vertex = corridorNetwork.insertVertex(room);
            roomVertices.put(room.getName(), vertex);
        }
    }

    public void addTravelPath(String room1Name, String room2Name, int travelTime) {
        Vertex<MountainPoint> v1 = roomVertices.get(room1Name);
        Vertex<MountainPoint> v2 = roomVertices.get(room2Name);

        if (v1 != null && v2 != null) {
            corridorNetwork.insertEdge(v1, v2, travelTime);
        }
    }

    public void displayNetwork() {
        System.out.println("\n=== Hospital Layout ===");
        System.out.println("MountainPoints: " + corridorNetwork.numVertices());
        System.out.println("Corridors: " + corridorNetwork.numEdges());

        System.out.println("\nMountainPoint list:");
        for (Vertex<MountainPoint> v : corridorNetwork.vertices()) {
            MountainPoint r = v.getElement();
            System.out.println("  - " + r);
        }

        System.out.println("\nCorridors (with travel times in seconds):");
        for (Edge<Integer> e : corridorNetwork.edges()) {
            Vertex<MountainPoint>[] endpoints = corridorNetwork.endVertices(e);
            System.out.println("  " + endpoints[0].getElement().getName() +
                             " <--[" + e.getElement() + "s]--> " +
                             endpoints[1].getElement().getName());
        }
    }

    public void findFastestRoute(String fromName, String toName) {
        Vertex<MountainPoint> from = roomVertices.get(fromName);
        Vertex<MountainPoint> to = roomVertices.get(toName);

        if (from == null || to == null) {
            System.out.println("One or both rooms not found");
            return;
        }

        System.out.println("\n=== Fastest Route: " + fromName + " → " + toName + " ===");

        // Use Dijkstra's algorithm
        Map<Vertex<MountainPoint>, Integer> distances = GraphAlgorithms.shortestPathDistances(
            corridorNetwork, from);

        Integer totalTime = distances.get(to);

        if (totalTime == null || totalTime == Integer.MAX_VALUE) {
            System.out.println("No path exists between these rooms");
            return;
        }

        System.out.println("Shortest travel time: " + totalTime + " seconds");

        // Reconstruct path using shortest path tree
        Map<Vertex<MountainPoint>, Edge<Integer>> tree = GraphAlgorithms.shortestPathTree(
            corridorNetwork, from, distances);

        PositionalList<Edge<Integer>> path = GraphAlgorithms.constructPath(
            corridorNetwork, from, to, tree);

        // Display path
        System.out.print("Route: " + fromName);
        Vertex<MountainPoint> current = from;
        for (Position<Edge<Integer>> pos = path.first(); pos != null; pos = path.after(pos)) {
            Edge<Integer> edge = pos.getElement();
            current = corridorNetwork.opposite(current, edge);
            System.out.print(" → " + current.getElement().getName());
        }
        System.out.println();
    }

    public void visitAllMountainPoints(String startName) {
        Vertex<MountainPoint> start = roomVertices.get(startName);
        if (start == null) {
            System.out.println("Starting room not found");
            return;
        }

        System.out.println("\n=== Complete Tour from " + startName + " ===");

        // Calculate shortest paths from start to all rooms
        Map<Vertex<MountainPoint>, Integer> distances = GraphAlgorithms.shortestPathDistances(
            corridorNetwork, start);

        int totalTime = 0;
        System.out.println("\nOptimal visiting order (by shortest distance):");

        PositionalList<Vertex<MountainPoint>> visitOrder = new LinkedPositionalList<>();
        visitOrder.addLast(start);

        // Sort remaining vertices by distance
        for (Vertex<MountainPoint> v : corridorNetwork.vertices()) {
            if (v != start) {
                visitOrder.addLast(v);
            }
        }

        int roomNum = 1;
        for (Position<Vertex<MountainPoint>> pos = visitOrder.first();
             pos != null; pos = visitOrder.after(pos)) {
            Vertex<MountainPoint> room = pos.getElement();
            Integer dist = distances.get(room);
            if (dist != null && dist != Integer.MAX_VALUE) {
                System.out.println("  " + (roomNum++) + ". " + room.getElement().getName() +
                                 " (distance from start: " + dist + "s)");
                totalTime += dist;
            }
        }

        System.out.println("\nTotal estimated time: " + totalTime + " seconds");
    }

    public void findAlternativeRoute(String startName, String viaName) {
        Vertex<MountainPoint> start = roomVertices.get(startName);
        Vertex<MountainPoint> via = roomVertices.get(viaName);

        if (start == null || via == null) {
            System.out.println("One or both rooms not found");
            return;
        }

        System.out.println("\n=== Round Trip: " + startName + " → " + viaName + " → " + startName + " ===");

        // Find path from start to via
        Map<Vertex<MountainPoint>, Integer> distancesFrom = GraphAlgorithms.shortestPathDistances(
            corridorNetwork, start);
        Integer timeToVia = distancesFrom.get(via);

        // Find path from via back to start
        Map<Vertex<MountainPoint>, Integer> distancesBack = GraphAlgorithms.shortestPathDistances(
            corridorNetwork, via);
        Integer timeBack = distancesBack.get(start);

        if (timeToVia == null || timeBack == null ||
            timeToVia == Integer.MAX_VALUE || timeBack == Integer.MAX_VALUE) {
            System.out.println("No round trip path exists");
            return;
        }

        System.out.println("Outbound time: " + timeToVia + "s");
        System.out.println("Return time: " + timeBack + "s");
        System.out.println("Total round trip: " + (timeToVia + timeBack) + "s");
    }

    public void dfsTraversal(String startName) {
        Vertex<MountainPoint> start = roomVertices.get(startName);
        if (start == null) {
            System.out.println("Starting room not found");
            return;
        }

        System.out.println("\n=== DFS Traversal from " + startName + " ===");

        Set<Vertex<MountainPoint>> known = new HashSet<>();
        Map<Vertex<MountainPoint>, Edge<Integer>> forest = new UnsortedTableMap<>();

        GraphAlgorithms.DFS(corridorNetwork, start, known, forest);

        System.out.print("Visit order: " + startName);
        for (Vertex<MountainPoint> v : corridorNetwork.vertices()) {
            if (v != start && forest.get(v) != null) {
                System.out.print(" " + v.getElement().getName());
            }
        }
        System.out.println();
    }

    public void bfsTraversal(String startName) {
        Vertex<MountainPoint> start = roomVertices.get(startName);
        if (start == null) {
            System.out.println("Starting room not found");
            return;
        }

        System.out.println("\n=== BFS Traversal from " + startName + " ===");

        Set<Vertex<MountainPoint>> known = new HashSet<>();
        Map<Vertex<MountainPoint>, Edge<Integer>> forest = new UnsortedTableMap<>();

        GraphAlgorithms.BFS(corridorNetwork, start, known, forest);

        System.out.print("Visit order: " + startName);
        for (Vertex<MountainPoint> v : corridorNetwork.vertices()) {
            if (v != start && forest.get(v) != null) {
                System.out.print(" " + v.getElement().getName());
            }
        }
        System.out.println();

        // Display levels
        System.out.println("\nBy distance levels:");
        displayBFSLevels(start, forest);
    }

    private void displayBFSLevels(Vertex<MountainPoint> start, Map<Vertex<MountainPoint>, Edge<Integer>> forest) {
        Map<Vertex<MountainPoint>, Integer> levels = new UnsortedTableMap<>();
        levels.put(start, 0);

        for (Vertex<MountainPoint> v : corridorNetwork.vertices()) {
            if (v != start && forest.get(v) != null) {
                int level = getHopDistance(v, forest, start);
                levels.put(v, level);
            }
        }

        int maxLevel = 0;
        for (Vertex<MountainPoint> v : corridorNetwork.vertices()) {
            Integer level = levels.get(v);
            if (level != null && level > maxLevel) {
                maxLevel = level;
            }
        }

        for (int i = 0; i <= maxLevel; i++) {
            System.out.print("  Level " + i + ": ");
            boolean first = true;
            for (Vertex<MountainPoint> v : corridorNetwork.vertices()) {
                Integer level = levels.get(v);
                if (level != null && level == i) {
                    if (!first) System.out.print(", ");
                    System.out.print(v.getElement().getName());
                    first = false;
                }
            }
            System.out.println();
        }
    }

    private int getHopDistance(Vertex<MountainPoint> v, Map<Vertex<MountainPoint>, Edge<Integer>> forest,
                              Vertex<MountainPoint> root) {
        int distance = 0;
        Vertex<MountainPoint> current = v;

        while (current != root) {
            Edge<Integer> edge = forest.get(current);
            if (edge == null) break;
            current = corridorNetwork.opposite(current, edge);
            distance++;
        }

        return distance;
    }
}
