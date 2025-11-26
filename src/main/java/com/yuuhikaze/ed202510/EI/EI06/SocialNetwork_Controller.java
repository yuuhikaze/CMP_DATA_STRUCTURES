package com.yuuhikaze.ed202510.EI.EI06;

import com.yuuhikaze.ed202510.TDA.AdjacencyMapGraph;
import com.yuuhikaze.ed202510.TDA.UnsortedTableMap;
import com.yuuhikaze.ed202510.TDA.interfaces.Edge;
import com.yuuhikaze.ed202510.TDA.interfaces.Graph;
import com.yuuhikaze.ed202510.TDA.interfaces.Map;
import com.yuuhikaze.ed202510.TDA.interfaces.Vertex;
import com.yuuhikaze.ed202510.TDA.misc.GraphAlgorithms;
import com.yuuhikaze.ed202510.TDA.interfaces.PositionalList;
import java.util.HashSet;
import java.util.Set;

class SocialNetworkController {
    private Graph<Person, String> network;
    private Map<String, Vertex<Person>> personVertices;

    public SocialNetworkController() {
        this.network = new AdjacencyMapGraph<>(false); // undirected graph
        this.personVertices = new UnsortedTableMap<>();
    }

    public void addPerson(Person person) {
        if (personVertices.get(person.getName()) == null) {
            Vertex<Person> vertex = network.insertVertex(person);
            personVertices.put(person.getName(), vertex);
        }
    }

    public void addFriendship(String person1Name, String person2Name, String relationshipType) {
        Vertex<Person> v1 = personVertices.get(person1Name);
        Vertex<Person> v2 = personVertices.get(person2Name);

        if (v1 != null && v2 != null) {
            network.insertEdge(v1, v2, relationshipType);
        } else {
            System.out.println("One or both persons not found in the network");
        }
    }

    public void displayNetwork() {
        System.out.println("\n=== Social Network ===");
        System.out.println("Total people: " + network.numVertices());
        System.out.println("Total friendships: " + network.numEdges());

        System.out.println("\nPeople:");
        for (Vertex<Person> v : network.vertices()) {
            Person p = v.getElement();
            System.out.println("  - " + p);
        }

        System.out.println("\nFriendships:");
        for (Edge<String> e : network.edges()) {
            Vertex<Person>[] endpoints = network.endVertices(e);
            System.out.println("  - " + endpoints[0].getElement().getName() +
                             " <--[" + e.getElement() + "]--> " +
                             endpoints[1].getElement().getName());
        }
    }

    public void performDFS(String startPersonName) {
        Vertex<Person> startVertex = personVertices.get(startPersonName);
        if (startVertex == null) {
            System.out.println("Person not found: " + startPersonName);
            return;
        }

        System.out.println("\n=== Depth-First Search (DFS) starting from " + startPersonName + " ===");
        Set<Vertex<Person>> known = new HashSet<>();
        Map<Vertex<Person>, Edge<String>> forest = new UnsortedTableMap<>();

        System.out.println("Traversal order:");
        dfsWithPrint(network, startVertex, known, forest);

        System.out.println("\n\nDFS Tree (discovery edges from parent to child):");
        for (Vertex<Person> v : network.vertices()) {
            Edge<String> discoveryEdge = forest.get(v);
            if (discoveryEdge != null) {
                Vertex<Person> parent = network.opposite(v, discoveryEdge);
                System.out.println("  " + parent.getElement().getName() +
                                 " --> " + v.getElement().getName());
            } else if (v == startVertex) {
                System.out.println("  " + v.getElement().getName() + " (root of DFS tree)");
            }
        }
    }

    private void dfsWithPrint(Graph<Person, String> graph, Vertex<Person> u,
                             Set<Vertex<Person>> known, Map<Vertex<Person>, Edge<String>> forest) {
        known.add(u);
        System.out.println("  -> Visiting: " + u.getElement().getName());

        for (Edge<String> e : graph.outgoingEdges(u)) {
            Vertex<Person> v = graph.opposite(u, e);
            if (!known.contains(v)) {
                forest.put(v, e); // v discovered via edge e from u
                dfsWithPrint(graph, v, known, forest);
            }
        }
    }

    public void performBFS(String startPersonName) {
        Vertex<Person> startVertex = personVertices.get(startPersonName);
        if (startVertex == null) {
            System.out.println("Person not found: " + startPersonName);
            return;
        }

        System.out.println("\n=== Breadth-First Search (BFS) starting from " + startPersonName + " ===");
        Set<Vertex<Person>> known = new HashSet<>();
        Map<Vertex<Person>, Edge<String>> forest = new UnsortedTableMap<>();

        GraphAlgorithms.BFS(network, startVertex, known, forest);

        System.out.println("Traversal by levels:");
        displayBFSLevels(startVertex, forest);

        System.out.println("\nBFS Tree (shortest path tree from root):");
        for (Vertex<Person> v : network.vertices()) {
            Edge<String> discoveryEdge = forest.get(v);
            if (discoveryEdge != null) {
                Vertex<Person> parent = network.opposite(v, discoveryEdge);
                System.out.println("  " + parent.getElement().getName() +
                                 " --> " + v.getElement().getName() +
                                 " (distance: " + getDistance(v, forest, startVertex) + ")");
            } else if (v == startVertex) {
                System.out.println("  " + startVertex.getElement().getName() + " (root - distance 0)");
            }
        }
    }

    private void displayBFSLevels(Vertex<Person> start, Map<Vertex<Person>, Edge<String>> forest) {
        Map<Vertex<Person>, Integer> levels = new UnsortedTableMap<>();
        levels.put(start, 0);

        for (Vertex<Person> v : network.vertices()) {
            if (v != start && forest.get(v) != null) {
                int level = getDistance(v, forest, start);
                levels.put(v, level);
            }
        }

        int maxLevel = 0;
        for (Vertex<Person> v : network.vertices()) {
            Integer level = levels.get(v);
            if (level != null && level > maxLevel) {
                maxLevel = level;
            }
        }

        for (int i = 0; i <= maxLevel; i++) {
            System.out.print("  Level " + i + ": ");
            boolean first = true;
            for (Vertex<Person> v : network.vertices()) {
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

    private int getDistance(Vertex<Person> v, Map<Vertex<Person>, Edge<String>> forest,
                           Vertex<Person> root) {
        int distance = 0;
        Vertex<Person> current = v;

        while (current != root) {
            Edge<String> edge = forest.get(current);
            if (edge == null) break;
            current = network.opposite(current, edge);
            distance++;
        }

        return distance;
    }

    public void findPath(String fromName, String toName) {
        Vertex<Person> from = personVertices.get(fromName);
        Vertex<Person> to = personVertices.get(toName);

        if (from == null || to == null) {
            System.out.println("One or both persons not found");
            return;
        }

        Set<Vertex<Person>> known = new HashSet<>();
        Map<Vertex<Person>, Edge<String>> forest = new UnsortedTableMap<>();
        GraphAlgorithms.BFS(network, from, known, forest);

        PositionalList<Edge<String>> path = GraphAlgorithms.constructPath(network, from, to, forest);

        System.out.println("\n=== Path from " + fromName + " to " + toName + " ===");
        if (path.isEmpty()) {
            System.out.println("No path exists between these people");
        } else {
            System.out.print(fromName);
            Vertex<Person> current = from;
            for (com.yuuhikaze.ed202510.TDA.interfaces.Position<Edge<String>> pos = path.first();
                 pos != null; pos = path.after(pos)) {
                Edge<String> edge = pos.getElement();
                current = network.opposite(current, edge);
                System.out.print(" --[" + edge.getElement() + "]--> " + current.getElement().getName());
            }
            System.out.println();
        }
    }

    public void performDFSComplete() {
        System.out.println("\n=== Complete DFS (Full Forest) ===");
        System.out.println("Running DFS on all connected components...\n");

        Map<Vertex<Person>, Edge<String>> forest = GraphAlgorithms.DFSComplete(network);

        Set<Vertex<Person>> roots = new HashSet<>();
        for (Vertex<Person> v : network.vertices()) {
            if (forest.get(v) == null) {
                roots.add(v);
            }
        }

        System.out.println("Number of connected components (trees in forest): " + roots.size());
        System.out.println("\nForest structure:");

        int treeNum = 1;
        for (Vertex<Person> root : roots) {
            System.out.println("\nTree " + treeNum + " (rooted at " + root.getElement().getName() + "):");
            displayTree(root, forest, 0);
            treeNum++;
        }
    }

    private void displayTree(Vertex<Person> v, Map<Vertex<Person>, Edge<String>> forest, int depth) {
        String indent = "  ".repeat(depth);
        System.out.println(indent + "- " + v.getElement().getName());

        for (Vertex<Person> child : network.vertices()) {
            Edge<String> edge = forest.get(child);
            if (edge != null) {
                Vertex<Person> parent = network.opposite(child, edge);
                if (parent == v) {
                    displayTree(child, forest, depth + 1);
                }
            }
        }
    }
}
