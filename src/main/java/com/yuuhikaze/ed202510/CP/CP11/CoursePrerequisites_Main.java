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

public class CoursePrerequisites_Main {
    public static void main(String[] args) {
        System.out.println("=== CP11 R-14.17: BOB'S COURSE SCHEDULE ===\n");

        System.out.println("Bob wants to take 9 language courses:");
        System.out.println("LA15, LA16, LA22, LA31, LA32, LA126, LA127, LA141, LA169\n");

        // Build prerequisite graph
        Graph<String, Integer> prereqGraph = buildPrerequisiteGraph();

        System.out.println("Prerequisites:");
        displayPrerequisites(prereqGraph);

        // Compute topological order
        System.out.println("\n" + "=".repeat(60));
        System.out.println("COURSE ORDERING (Topological Sort)");
        System.out.println("=".repeat(60));

        PositionalList<Vertex<String>> order = GraphAlgorithms.topologicalSort(prereqGraph);

        System.out.println("\nBob can take the courses in this order:");
        int semester = 1;
        Map<Integer, String> coursesBySemester = new UnsortedTableMap<>();

        // Group courses by their level in the topological order
        System.out.println("\nSuggested semester-by-semester plan:\n");

        // Simple approach: take courses as soon as prerequisites are met
        Map<Vertex<String>, Integer> semesterMap = new UnsortedTableMap<>();
        for (Position<Vertex<String>> pos = order.first();
             pos != null; pos = order.after(pos)) {
            Vertex<String> course = pos.getElement();

            // Find the maximum semester of all prerequisites
            int maxPrereqSemester = 0;
            for (Edge<Integer> e : prereqGraph.incomingEdges(course)) {
                Vertex<String> prereq = prereqGraph.opposite(course, e);
                Integer prereqSem = semesterMap.get(prereq);
                if (prereqSem != null && prereqSem > maxPrereqSemester) {
                    maxPrereqSemester = prereqSem;
                }
            }

            semesterMap.put(course, maxPrereqSemester + 1);
        }

        // Display by semester
        int maxSemester = 0;
        for (Vertex<String> v : prereqGraph.vertices()) {
            Integer sem = semesterMap.get(v);
            if (sem != null && sem > maxSemester) {
                maxSemester = sem;
            }
        }

        for (int sem = 1; sem <= maxSemester; sem++) {
            System.out.println("Semester " + sem + ":");
            for (Position<Vertex<String>> pos = order.first();
                 pos != null; pos = order.after(pos)) {
                Vertex<String> course = pos.getElement();
                if (semesterMap.get(course) != null &&
                    semesterMap.get(course) == sem) {
                    System.out.println("  - " + course.getElement());
                }
            }
            System.out.println();
        }

        System.out.println("Complete ordering (one valid sequence):");
        System.out.print("  ");
        boolean first = true;
        for (Position<Vertex<String>> pos = order.first();
             pos != null; pos = order.after(pos)) {
            if (!first) System.out.print(" → ");
            System.out.print(pos.getElement().getElement());
            first = false;
        }
        System.out.println("\n");

        System.out.println("Note: Multiple valid orderings exist. Any ordering that");
        System.out.println("respects the prerequisite constraints is acceptable.");
    }

    private static Graph<String, Integer> buildPrerequisiteGraph() {
        Graph<String, Integer> g = new AdjacencyMapGraph<>(true);

        // Create vertices for all courses
        Vertex<String> LA15 = g.insertVertex("LA15");
        Vertex<String> LA16 = g.insertVertex("LA16");
        Vertex<String> LA22 = g.insertVertex("LA22");
        Vertex<String> LA31 = g.insertVertex("LA31");
        Vertex<String> LA32 = g.insertVertex("LA32");
        Vertex<String> LA126 = g.insertVertex("LA126");
        Vertex<String> LA127 = g.insertVertex("LA127");
        Vertex<String> LA141 = g.insertVertex("LA141");
        Vertex<String> LA169 = g.insertVertex("LA169");

        // Add prerequisite edges (prerequisite -> course)
        // LA15: (none)
        // LA16: LA15
        g.insertEdge(LA15, LA16, 1);

        // LA22: (none)
        // LA31: LA15
        g.insertEdge(LA15, LA31, 1);

        // LA32: LA16, LA31
        g.insertEdge(LA16, LA32, 1);
        g.insertEdge(LA31, LA32, 1);

        // LA126: LA22, LA32
        g.insertEdge(LA22, LA126, 1);
        g.insertEdge(LA32, LA126, 1);

        // LA127: LA16
        g.insertEdge(LA16, LA127, 1);

        // LA141: LA22, LA16
        g.insertEdge(LA22, LA141, 1);
        g.insertEdge(LA16, LA141, 1);

        // LA169: LA32
        g.insertEdge(LA32, LA169, 1);

        return g;
    }

    private static void displayPrerequisites(Graph<String, Integer> graph) {
        for (Vertex<String> course : graph.vertices()) {
            System.out.print("  " + course.getElement() + ": ");

            boolean hasPrereq = false;
            int count = 0;
            for (Edge<Integer> e : graph.incomingEdges(course)) {
                count++;
            }

            if (count == 0) {
                System.out.println("(no prerequisites)");
            } else {
                System.out.print("requires ");
                boolean first = true;
                for (Edge<Integer> e : graph.incomingEdges(course)) {
                    Vertex<String> prereq = graph.opposite(course, e);
                    if (!first) System.out.print(", ");
                    System.out.print(prereq.getElement());
                    first = false;
                }
                System.out.println();
            }
        }
    }
}
