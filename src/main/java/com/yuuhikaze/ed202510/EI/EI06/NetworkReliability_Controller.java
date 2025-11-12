package com.yuuhikaze.ed202510.EI.EI06;

import com.yuuhikaze.ed202510.TDA.AdjacencyMapGraph;
import com.yuuhikaze.ed202510.TDA.UnsortedTableMap;
import com.yuuhikaze.ed202510.TDA.interfaces.Edge;
import com.yuuhikaze.ed202510.TDA.interfaces.Graph;
import com.yuuhikaze.ed202510.TDA.interfaces.Map;
import com.yuuhikaze.ed202510.TDA.interfaces.Vertex;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

class NetworkReliabilityController {
    private Graph<Server, String> network;
    private Map<String, Vertex<Server>> serverVertices;
    private Map<Vertex<Server>, Integer> discoveryTime;
    private Map<Vertex<Server>, Integer> lowValue;
    private Set<Vertex<Server>> articulationPoints;
    private Set<Edge<String>> bridges;
    private int time;

    public NetworkReliabilityController() {
        this.network = new AdjacencyMapGraph<>(false); // undirected graph
        this.serverVertices = new UnsortedTableMap<>();
        this.discoveryTime = new UnsortedTableMap<>();
        this.lowValue = new UnsortedTableMap<>();
        this.articulationPoints = new HashSet<>();
        this.bridges = new HashSet<>();
        this.time = 0;
    }

    public void addServer(Server server) {
        if (serverVertices.get(server.getId()) == null) {
            Vertex<Server> vertex = network.insertVertex(server);
            serverVertices.put(server.getId(), vertex);
        }
    }

    public void addConnection(String server1Id, String server2Id, String connectionType) {
        Vertex<Server> v1 = serverVertices.get(server1Id);
        Vertex<Server> v2 = serverVertices.get(server2Id);

        if (v1 != null && v2 != null) {
            network.insertEdge(v1, v2, connectionType);
        } else {
            System.out.println("One or both servers not found in the network");
        }
    }

    public void displayNetwork() {
        System.out.println("\n=== Network Topology ===");
        System.out.println("Total servers: " + network.numVertices());
        System.out.println("Total connections: " + network.numEdges());

        System.out.println("\nServers:");
        for (Vertex<Server> v : network.vertices()) {
            Server s = v.getElement();
            System.out.println("  - " + s);
        }

        System.out.println("\nConnections:");
        for (Edge<String> e : network.edges()) {
            Vertex<Server>[] endpoints = network.endVertices(e);
            System.out.println("  - " + endpoints[0].getElement().getId() +
                             " <--[" + e.getElement() + "]--> " +
                             endpoints[1].getElement().getId());
        }
    }

    public void findArticulationPointsAndBridges() {
        System.out.println("\n=== Finding Critical Components ===");

        // Reset for new analysis
        articulationPoints.clear();
        bridges.clear();
        discoveryTime.clear();
        lowValue.clear();
        time = 0;

        Set<Vertex<Server>> visited = new HashSet<>();

        // Run DFS from each unvisited vertex to handle disconnected components
        for (Vertex<Server> v : network.vertices()) {
            if (!visited.contains(v)) {
                dfsArticulationAndBridges(v, null, visited);
            }
        }

        // Display results
        System.out.println("\n--- Articulation Points (Cut Vertices) ---");
        if (articulationPoints.isEmpty()) {
            System.out.println("No articulation points found - network is biconnected!");
        } else {
            System.out.println("Found " + articulationPoints.size() + " critical server(s):");
            for (Vertex<Server> v : articulationPoints) {
                System.out.println("  ! " + v.getElement().getId() +
                                 " - Removing this server would fragment the network");
            }
        }

        System.out.println("\n--- Bridges (Cut Edges) ---");
        if (bridges.isEmpty()) {
            System.out.println("No bridges found - network has redundant paths!");
        } else {
            System.out.println("Found " + bridges.size() + " critical connection(s):");
            for (Edge<String> e : bridges) {
                Vertex<Server>[] endpoints = network.endVertices(e);
                System.out.println("  ! " + endpoints[0].getElement().getId() +
                                 " <--> " + endpoints[1].getElement().getId() +
                                 " - Removing this connection would fragment the network");
            }
        }
    }

    private void dfsArticulationAndBridges(Vertex<Server> u, Vertex<Server> parent,
                                          Set<Vertex<Server>> visited) {
        visited.add(u);
        discoveryTime.put(u, time);
        lowValue.put(u, time);
        time++;

        int childCount = 0;
        boolean isArticulation = false;

        for (Edge<String> e : network.outgoingEdges(u)) {
            Vertex<Server> v = network.opposite(u, e);

            if (!visited.contains(v)) {
                childCount++;
                dfsArticulationAndBridges(v, u, visited);

                // Update low value
                lowValue.put(u, Math.min(lowValue.get(u), lowValue.get(v)));

                // Check if u is an articulation point
                if (parent == null && childCount > 1) {
                    // u is root with multiple children
                    isArticulation = true;
                } else if (parent != null && lowValue.get(v) >= discoveryTime.get(u)) {
                    // u is not root and has a child v with no back edge to ancestor of u
                    isArticulation = true;
                }

                // Check if edge u-v is a bridge
                if (lowValue.get(v) > discoveryTime.get(u)) {
                    bridges.add(e);
                }
            } else if (v != parent) {
                // Back edge (v is already visited and not parent)
                lowValue.put(u, Math.min(lowValue.get(u), discoveryTime.get(v)));
            }
        }

        if (isArticulation) {
            articulationPoints.add(u);
        }
    }

    public void analyzeRobustness() {
        System.out.println("\n=== Network Robustness Analysis ===");

        int vertices = network.numVertices();
        int edges = network.numEdges();
        int apCount = articulationPoints.size();
        int bridgeCount = bridges.size();

        System.out.println("\nStatistics:");
        System.out.println("  Servers: " + vertices);
        System.out.println("  Connections: " + edges);
        System.out.println("  Articulation Points: " + apCount);
        System.out.println("  Bridges: " + bridgeCount);

        // Calculate edge density
        int maxEdges = (vertices * (vertices - 1)) / 2;
        double density = (double) edges / maxEdges * 100;
        System.out.println("  Network Density: " + String.format("%.1f%%", density));

        // Robustness assessment
        System.out.println("\nRobustness Assessment:");
        if (apCount == 0 && bridgeCount == 0) {
            System.out.println("  Status: HIGHLY ROBUST");
            System.out.println("  The network is biconnected with no single points of failure.");
        } else if (apCount <= 2 && bridgeCount <= 2) {
            System.out.println("  Status: MODERATELY ROBUST");
            System.out.println("  The network has few critical points but may benefit from redundancy.");
        } else {
            System.out.println("  Status: VULNERABLE");
            System.out.println("  The network has multiple single points of failure.");
        }

        // Recommendations
        System.out.println("\nRecommendations:");
        if (bridgeCount > 0) {
            System.out.println("  - Add alternative connections to eliminate bridges");
        }
        if (apCount > 0) {
            System.out.println("  - Add redundant servers or backup systems for critical nodes");
        }
        if (density < 30) {
            System.out.println("  - Increase network connectivity for better fault tolerance");
        }
        if (apCount == 0 && bridgeCount == 0) {
            System.out.println("  - Network design is optimal for single-failure resilience");
        }
    }

    public void simulateServerFailure(String serverId) {
        Vertex<Server> failedServer = serverVertices.get(serverId);
        if (failedServer == null) {
            System.out.println("Server not found: " + serverId);
            return;
        }

        System.out.println("\n=== Simulating Failure of Server " + serverId + " ===");

        boolean isArticulation = articulationPoints.contains(failedServer);

        if (isArticulation) {
            System.out.println("WARNING: This is a critical server (articulation point)!");
            System.out.println("Effect: Network will be fragmented into multiple components.");
        } else {
            System.out.println("INFO: This server is not critical.");
            System.out.println("Effect: Network will remain connected after failure.");
        }

        // Count affected connections
        int affectedConnections = 0;
        for (Edge<String> e : network.outgoingEdges(failedServer)) {
            affectedConnections++;
        }
        System.out.println("Connections lost: " + affectedConnections);
    }

    public void simulateConnectionFailure(String server1Id, String server2Id) {
        Vertex<Server> v1 = serverVertices.get(server1Id);
        Vertex<Server> v2 = serverVertices.get(server2Id);

        if (v1 == null || v2 == null) {
            System.out.println("One or both servers not found");
            return;
        }

        Edge<String> connection = network.getEdge(v1, v2);
        if (connection == null) {
            System.out.println("No connection exists between these servers");
            return;
        }

        System.out.println("\n=== Simulating Failure of Connection " +
                         server1Id + " <--> " + server2Id + " ===");

        boolean isBridge = bridges.contains(connection);

        if (isBridge) {
            System.out.println("WARNING: This is a critical connection (bridge)!");
            System.out.println("Effect: Network will be fragmented into disconnected components.");
        } else {
            System.out.println("INFO: This connection is not critical.");
            System.out.println("Effect: Alternative paths exist; network remains connected.");
        }
    }
}
