package com.yuuhikaze.ed202510.EI;

import com.yuuhikaze.ed202510.TDA.LinkedTree;
import com.yuuhikaze.ed202510.TDA.interfaces.Position;
import com.yuuhikaze.ed202510.utils.ANSICodes;

class Employee {
    private String name;
    private String role;
    private int id;

    public Employee(String name, String role, int id) {
        this.name = name;
        this.role = role;
        this.id = id;
    }

    @Override
    public String toString() {
        return name + " (" + role + ", ID: " + id + ")";
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}


class CompanyHierarchyController {
    private LinkedTree<Employee> hierarchy;
    private Position<Employee> root;

    public CompanyHierarchyController(Employee ceo) {
        this.hierarchy = new LinkedTree<>();
        this.root = hierarchy.addRoot(ceo);
    }

    public Position<Employee> getRoot() {
        return root;
    }

    public Position<Employee> addEmployee(Position<Employee> manager, Employee employee) {
        return hierarchy.addLast(manager, employee);
    }

    public void displayPreorder() {
        System.out.println(
                ANSICodes.BOLD + "\n=== PREORDER TRAVERSAL (Root → Children) ===" + ANSICodes.RESET);
        System.out.println("Shows top-down hierarchy:\n");
        for (Position<Employee> pos : hierarchy.preorder()) {
            int depth = hierarchy.depth(pos);
            System.out.println("  ".repeat(depth) + "└─ " + pos.getElement());
        }
    }

    public void displayPostorder() {
        System.out.println(
                ANSICodes.BOLD + "\n=== POSTORDER TRAVERSAL (Children → Root) ===" + ANSICodes.RESET);
        System.out.println("Shows bottom-up hierarchy (useful for processing leaves first):\n");
        int count = 0;
        for (Position<Employee> pos : hierarchy.postorder()) {
            System.out.println((++count) + ". " + pos.getElement());
        }
    }

    public void displayBreadthFirst() {
        System.out.println(
                ANSICodes.BOLD + "\n=== BREADTH-FIRST TRAVERSAL (Level by Level) ===" + ANSICodes.RESET);
        System.out.println("Shows organizational levels:\n");
        int level = 0;
        Position<Employee> prev = null;
        for (Position<Employee> pos : hierarchy.breadthFirst()) {
            int currentDepth = hierarchy.depth(pos);
            if (prev == null || hierarchy.depth(prev) != currentDepth) {
                if (prev != null)
                    System.out.println();
                System.out.print("Level " + currentDepth + ": ");
            } else {
                System.out.print(" | ");
            }
            System.out.print(pos.getElement().getName());
            prev = pos;
        }
        System.out.println("\n");
    }

    public void printStatistics() {
        System.out.println(ANSICodes.BOLD + "\n=== COMPANY STATISTICS ===" + ANSICodes.RESET);
        System.out.println("Total employees: " + hierarchy.size());
        System.out.println("CEO: " + root.getElement());
        System.out.println("Organizational depth: " + hierarchy.height(root));
    }
}


public class EI04_CompanyTree {
    public static void main(String[] args) {
        System.out.println(
                ANSICodes.BOLD + "╔══════════════════════════════════════╗" + ANSICodes.RESET);
        System.out.println(
                ANSICodes.BOLD + "║   COMPANY ORGANIZATIONAL HIERARCHY   ║" + ANSICodes.RESET);
        System.out.println(
                ANSICodes.BOLD + "╚══════════════════════════════════════╝" + ANSICodes.RESET);

        Employee ceo = new Employee("Alice Chen", "CEO", 1);
        CompanyHierarchyController company = new CompanyHierarchyController(ceo);

        Position<Employee> ctoPos =
                company.addEmployee(company.getRoot(), new Employee("Bob Smith", "CTO", 2));
        Position<Employee> cfoPos =
                company.addEmployee(company.getRoot(), new Employee("Carol Davis", "CFO", 3));
        Position<Employee> cooPos =
                company.addEmployee(company.getRoot(), new Employee("David Lee", "COO", 4));

        company.addEmployee(ctoPos, new Employee("Eve Wilson", "Engineering Manager", 5));
        company.addEmployee(ctoPos, new Employee("Frank Brown", "DevOps Lead", 6));

        Position<Employee> engManagerPos =
                company.addEmployee(ctoPos, new Employee("Grace Kim", "Senior Engineer", 7));
        company.addEmployee(engManagerPos, new Employee("Henry Zhang", "Junior Developer", 8));
        company.addEmployee(engManagerPos, new Employee("Iris Patel", "Junior Developer", 9));

        company.addEmployee(cfoPos, new Employee("Jack Miller", "Accountant", 10));
        company.addEmployee(cfoPos, new Employee("Karen White", "Financial Analyst", 11));

        company.addEmployee(cooPos, new Employee("Leo Martinez", "Operations Manager", 12));

        company.displayPreorder();
        company.displayPostorder();
        company.displayBreadthFirst();
        company.printStatistics();
    }
}
