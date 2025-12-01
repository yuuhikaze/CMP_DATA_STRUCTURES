package com.yuuhikaze.ed202510.TDA.misc;

import java.util.HashSet;
import java.util.Set;
import com.yuuhikaze.ed202510.TDA.HeapAdaptablePriorityQueue;
import com.yuuhikaze.ed202510.TDA.LinkedPositionalList;
import com.yuuhikaze.ed202510.TDA.ProbeHashMap;
import com.yuuhikaze.ed202510.TDA.UnsortedTableMap;
import com.yuuhikaze.ed202510.TDA.SLLStack;
import com.yuuhikaze.ed202510.TDA.interfaces.AdaptablePriorityQueue;
import com.yuuhikaze.ed202510.TDA.interfaces.Edge;
import com.yuuhikaze.ed202510.TDA.interfaces.Entry;
import com.yuuhikaze.ed202510.TDA.interfaces.Graph;
import com.yuuhikaze.ed202510.TDA.interfaces.Map;
import com.yuuhikaze.ed202510.TDA.interfaces.PositionalList;
import com.yuuhikaze.ed202510.TDA.interfaces.Stack;
import com.yuuhikaze.ed202510.TDA.interfaces.Vertex;
import com.yuuhikaze.ed202510.utils.IterUtils;

public class GraphAlgorithms<V, E> {

    public static <V, E> void DFS(
            Graph<V, E> graph, Vertex<V> u, Set<Vertex<V>> known, Map<Vertex<V>, Edge<E>> forest) {
        known.add(u);
        for (Edge<E> e : graph.outgoingEdges(u)) {
            Vertex<V> v = graph.opposite(u, e);
            if (!known.contains(v)) {
                forest.put(v, e);
                DFS(graph, v, known, forest);
            }
        }
    }

    public static <V, E> PositionalList<Edge<E>> constructPath(
            Graph<V, E> graph, Vertex<V> u, Vertex<V> v, Map<Vertex<V>, Edge<E>> forest) {
        PositionalList<Edge<E>> path = new LinkedPositionalList<>();
        if (forest.get(v) != null) {
            Vertex<V> walk = v;
            while (walk != u) {
                Edge<E> edge = forest.get(walk);
                path.addFirst(edge);
                walk = graph.opposite(walk, edge);
            }
        }

        return path;
    }

    public static <V, E> Map<Vertex<V>, Edge<E>> DFSComplete(Graph<V, E> graph) {
        Set<Vertex<V>> known = new HashSet<>();
        Map<Vertex<V>, Edge<E>> forest = new UnsortedTableMap<>();
        for (Vertex<V> u : graph.vertices()) {
            if (!known.contains(u)) {
                DFS(graph, u, known, forest);
            }
        }

        return forest;
    }

    public static <V, E> void BFS(
            Graph<V, E> graph, Vertex<V> s, Set<Vertex<V>> known, Map<Vertex<V>, Edge<E>> forest) {
        PositionalList<Vertex<V>> level = new LinkedPositionalList<>();
        known.add(s);
        level.addLast(s);
        while (!level.isEmpty()) {
            PositionalList<Vertex<V>> nextLevel = new LinkedPositionalList<>();
            for (Vertex<V> u : IterUtils.getIterableFromIterator(level.elementIterator())) {
                for (Edge<E> e : graph.outgoingEdges(u)) {
                    Vertex<V> v = graph.opposite(u, e);
                    if (!known.contains(v)) {
                        known.add(v);
                        forest.put(v, e);
                        nextLevel.addLast(v);
                    }
                }
            }
            level = nextLevel;
        }
    }

    public static <V> Map<Vertex<V>, Integer> shortestPathLengths(
            Graph<V, Integer> g, Vertex<V> src) {
        Map<Vertex<V>, Integer> d = new ProbeHashMap<>();
        Map<Vertex<V>, Integer> cloud = new ProbeHashMap<>();
        AdaptablePriorityQueue<Integer, Vertex<V>> pq;
        pq = new HeapAdaptablePriorityQueue<>();
        Map<Vertex<V>, Entry<Integer, Vertex<V>>> pqTokens;
        pqTokens = new ProbeHashMap<>();
        for (Vertex<V> v : g.vertices()) {
            if (v == src)
                d.put(v, 0);
            else
                d.put(v, Integer.MAX_VALUE);
            pqTokens.put(v, pq.insert(d.get(v), v));
        }
        while (!pq.isEmpty()) {
            Entry<Integer, Vertex<V>> entry = pq.removeMin();
            int key = entry.getKey();
            Vertex<V> u = entry.getValue();
            cloud.put(u, key);
            pqTokens.remove(u);
            for (Edge<Integer> e : g.outgoingEdges(u)) {
                Vertex<V> v = g.opposite(u, e);
                if (cloud.get(v) == null) {
                    int wgt = e.getElement();
                    if (d.get(u) + wgt < d.get(v)) {
                        d.put(v, d.get(u) + wgt);
                        pq.replaceKey(pqTokens.get(v), d.get(v));
                    }
                }
            }
        }
        return cloud;
    }

    /**
     * Returns a list of vertices of directed acyclic graph g in topological order.
     * If graph g has a cycle, the result will be incomplete.
     */
    public static <V, E> PositionalList<Vertex<V>> topologicalSort(Graph<V, E> g) {
        PositionalList<Vertex<V>> topo = new LinkedPositionalList<>();
        Stack<Vertex<V>> ready = new SLLStack<>();
        Map<Vertex<V>, Integer> inCount = new UnsortedTableMap<>();

        for (Vertex<V> u : g.vertices()) {
            inCount.put(u, g.inDegree(u));
            if (inCount.get(u) == 0) {
                ready.push(u);
            }
        }

        while (!ready.isEmpty()) {
            Vertex<V> u = ready.pop();
            topo.addLast(u);
            for (Edge<E> e : g.outgoingEdges(u)) {
                Vertex<V> v = g.opposite(u, e);
                inCount.put(v, inCount.get(v) - 1);
                if (inCount.get(v) == 0) {
                    ready.push(v);
                }
            }
        }

        return topo;
    }

    /**
     * Checks if a directed graph is a DAG (Directed Acyclic Graph).
     * Returns true if the graph has no cycles, false otherwise.
     */
    public static <V, E> boolean isDAG(Graph<V, E> g) {
        PositionalList<Vertex<V>> topo = topologicalSort(g);
        return topo.size() == g.numVertices();
    }

    /**
     * Converts graph g into its transitive closure using Floyd-Warshall algorithm.
     */
    public static <V, E> void transitiveClosure(Graph<V, E> g) {
        for (Vertex<V> k : g.vertices()) {
            for (Vertex<V> i : g.vertices()) {
                if (i != k && g.getEdge(i, k) != null) {
                    for (Vertex<V> j : g.vertices()) {
                        if (i != j && j != k && g.getEdge(k, j) != null) {
                            if (g.getEdge(i, j) == null) {
                                g.insertEdge(i, j, null);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Creates a copy of the transitive closure of graph g.
     * Returns a new graph that is the transitive closure.
     */
    public static <V, E> Graph<V, E> computeTransitiveClosure(Graph<V, E> g) {
        Graph<V, E> closure = new com.yuuhikaze.ed202510.TDA.AdjacencyMapGraph<>(true);
        Map<Vertex<V>, Vertex<V>> vertexMap = new UnsortedTableMap<>();

        // Copy all vertices
        for (Vertex<V> v : g.vertices()) {
            Vertex<V> newV = closure.insertVertex(v.getElement());
            vertexMap.put(v, newV);
        }

        // Copy all edges
        for (Edge<E> e : g.edges()) {
            Vertex<V>[] endpoints = g.endVertices(e);
            Vertex<V> u = vertexMap.get(endpoints[0]);
            Vertex<V> v = vertexMap.get(endpoints[1]);
            closure.insertEdge(u, v, e.getElement());
        }

        // Compute transitive closure
        transitiveClosure(closure);

        return closure;
    }
}
