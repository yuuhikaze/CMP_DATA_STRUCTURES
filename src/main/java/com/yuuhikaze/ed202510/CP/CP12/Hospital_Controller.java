package com.yuuhikaze.ed202510.CP.CP12;

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
import com.yuuhikaze.ed202510.utils.IterUtils;
import java.util.HashSet;
import java.util.Set;

class HospitalController {
    private Graph<Room, Integer> corridorNetwork;
    private Map<String, Vertex<Room>> roomVertices;

    public HospitalController() {
        this.corridorNetwork = new AdjacencyMapGraph<>(false); // undirected
        this.roomVertices = new UnsortedTableMap<>();
    }

    public void addRoom(Room room) {
        if (roomVertices.get(room.getName()) == null) {
            Vertex<Room> vertex = corridorNetwork.insertVertex(room);
            roomVertices.put(room.getName(), vertex);
        }
    }

    public void addCorridor(String room1Name, String room2Name, int travelTime) {
        Vertex<Room> v1 = roomVertices.get(room1Name);
        Vertex<Room> v2 = roomVertices.get(room2Name);

        if (v1 != null && v2 != null) {
            corridorNetwork.insertEdge(v1, v2, travelTime);
        }
    }

    public void displayNetwork() {
        System.out.println("\n=== Hospital Layout ===");
        System.out.println("Rooms: " + corridorNetwork.numVertices());
        System.out.println("Corridors: " + corridorNetwork.numEdges());

        System.out.println("\nRoom list:");
        for (Vertex<Room> v : corridorNetwork.vertices()) {
            Room r = v.getElement();
            System.out.println("  - " + r);
        }

        System.out.println("\nCorridors (with travel times in seconds):");
        for (Edge<Integer> e : corridorNetwork.edges()) {
            Vertex<Room>[] endpoints = corridorNetwork.endVertices(e);
            System.out.println("  " + endpoints[0].getElement().getName() +
                             " <--[" + e.getElement() + "s]--> " +
                             endpoints[1].getElement().getName());
        }
    }

    public void findFastestRoute(String fromName, String toName) {
        Vertex<Room> from = roomVertices.get(fromName);
        Vertex<Room> to = roomVertices.get(toName);

        if (from == null || to == null) {
            System.out.println("One or both rooms not found");
            return;
        }

        System.out.println("\n=== Fastest Route: " + fromName + " → " + toName + " ===");

        // Use Dijkstra's algorithm
        Map<Vertex<Room>, Integer> distances = GraphAlgorithms.shortestPathLengths(
            corridorNetwork, from);

        Integer totalTime = distances.get(to);

        if (totalTime == null || totalTime == Integer.MAX_VALUE) {
            System.out.println("No path exists between these rooms");
            return;
        }

        System.out.println("Shortest travel time: " + totalTime + " seconds");

        // Reconstruct path
        reconstructPath(from, to, distances);
    }

    private void reconstructPath(Vertex<Room> from, Vertex<Room> to,
                                 Map<Vertex<Room>, Integer> distances) {
        PositionalList<Vertex<Room>> path = new LinkedPositionalList<>();
        Vertex<Room> current = to;
        path.addFirst(current);

        while (current != from) {
            int currentDist = distances.get(current);
            boolean found = false;

            for (Edge<Integer> e : corridorNetwork.incomingEdges(current)) {
                Vertex<Room> prev = corridorNetwork.opposite(current, e);
                int prevDist = distances.get(prev);

                if (prevDist + e.getElement() == currentDist) {
                    path.addFirst(prev);
                    current = prev;
                    found = true;
                    break;
                }
            }

            if (!found) break;
        }

        // Display path
        System.out.print("Route: ");
        boolean first = true;
        for (Position<Vertex<Room>> pos = path.first();
             pos != null; pos = path.after(pos)) {
            if (!first) System.out.print(" → ");
            System.out.print(pos.getElement().getElement().getName());
            first = false;
        }
        System.out.println();
    }

    public void visitAllRooms(String startName) {
        Vertex<Room> start = roomVertices.get(startName);
        if (start == null) {
            System.out.println("Starting room not found");
            return;
        }

        System.out.println("\n=== Complete Tour from " + startName + " ===");

        // Calculate shortest paths from start to all rooms
        Map<Vertex<Room>, Integer> distances = GraphAlgorithms.shortestPathLengths(
            corridorNetwork, start);

        int totalTime = 0;
        System.out.println("\nOptimal visiting order (by shortest distance):");

        PositionalList<Vertex<Room>> visitOrder = new LinkedPositionalList<>();
        visitOrder.addLast(start);

        // Sort remaining vertices by distance
        for (Vertex<Room> v : corridorNetwork.vertices()) {
            if (v != start) {
                visitOrder.addLast(v);
            }
        }

        int roomNum = 1;
        for (Position<Vertex<Room>> pos = visitOrder.first();
             pos != null; pos = visitOrder.after(pos)) {
            Vertex<Room> room = pos.getElement();
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
        Vertex<Room> start = roomVertices.get(startName);
        Vertex<Room> via = roomVertices.get(viaName);

        if (start == null || via == null) {
            System.out.println("One or both rooms not found");
            return;
        }

        System.out.println("\n=== Round Trip: " + startName + " → " + viaName + " → " + startName + " ===");

        // Find path from start to via
        Map<Vertex<Room>, Integer> distancesFrom = GraphAlgorithms.shortestPathLengths(
            corridorNetwork, start);
        Integer timeToVia = distancesFrom.get(via);

        // Find path from via back to start
        Map<Vertex<Room>, Integer> distancesBack = GraphAlgorithms.shortestPathLengths(
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
        Vertex<Room> start = roomVertices.get(startName);
        if (start == null) {
            System.out.println("Starting room not found");
            return;
        }

        System.out.println("\n=== DFS Traversal from " + startName + " ===");
        System.out.print("Visit order: ");

        Set<Vertex<Room>> known = new HashSet<>();
        Map<Vertex<Room>, Edge<Integer>> forest = new UnsortedTableMap<>();

        dfsWithPrint(start, known, forest);
        System.out.println();
    }

    private void dfsWithPrint(Vertex<Room> u, Set<Vertex<Room>> known,
                             Map<Vertex<Room>, Edge<Integer>> forest) {
        known.add(u);
        System.out.print(u.getElement().getName() + " ");

        for (Edge<Integer> e : corridorNetwork.outgoingEdges(u)) {
            Vertex<Room> v = corridorNetwork.opposite(u, e);
            if (!known.contains(v)) {
                forest.put(v, e);
                dfsWithPrint(v, known, forest);
            }
        }
    }

    public void bfsTraversal(String startName) {
        Vertex<Room> start = roomVertices.get(startName);
        if (start == null) {
            System.out.println("Starting room not found");
            return;
        }

        System.out.println("\n=== BFS Traversal from " + startName + " ===");

        Set<Vertex<Room>> known = new HashSet<>();
        Map<Vertex<Room>, Edge<Integer>> forest = new UnsortedTableMap<>();

        GraphAlgorithms.BFS(corridorNetwork, start, known, forest);

        System.out.print("Visit order: " + startName);
        for (Vertex<Room> v : corridorNetwork.vertices()) {
            if (v != start && forest.get(v) != null) {
                System.out.print(" " + v.getElement().getName());
            }
        }
        System.out.println();

        // Display levels
        System.out.println("\nBy distance levels:");
        displayBFSLevels(start, forest);
    }

    private void displayBFSLevels(Vertex<Room> start, Map<Vertex<Room>, Edge<Integer>> forest) {
        Map<Vertex<Room>, Integer> levels = new UnsortedTableMap<>();
        levels.put(start, 0);

        for (Vertex<Room> v : corridorNetwork.vertices()) {
            if (v != start && forest.get(v) != null) {
                int level = getHopDistance(v, forest, start);
                levels.put(v, level);
            }
        }

        int maxLevel = 0;
        for (Vertex<Room> v : corridorNetwork.vertices()) {
            Integer level = levels.get(v);
            if (level != null && level > maxLevel) {
                maxLevel = level;
            }
        }

        for (int i = 0; i <= maxLevel; i++) {
            System.out.print("  Level " + i + ": ");
            boolean first = true;
            for (Vertex<Room> v : corridorNetwork.vertices()) {
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

    private int getHopDistance(Vertex<Room> v, Map<Vertex<Room>, Edge<Integer>> forest,
                              Vertex<Room> root) {
        int distance = 0;
        Vertex<Room> current = v;

        while (current != root) {
            Edge<Integer> edge = forest.get(current);
            if (edge == null) break;
            current = corridorNetwork.opposite(current, edge);
            distance++;
        }

        return distance;
    }
}
