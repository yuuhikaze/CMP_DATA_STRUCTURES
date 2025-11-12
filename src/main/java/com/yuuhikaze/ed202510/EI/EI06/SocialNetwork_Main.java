package com.yuuhikaze.ed202510.EI.EI06;

public class SocialNetwork_Main {
    public static void main(String[] args) {
        System.out.println("=== SOCIAL NETWORK GRAPH TRAVERSALS (DFS & BFS) ===\n");

        SocialNetworkController network = new SocialNetworkController();

        // Create people
        network.addPerson(new Person("Alice", 28, "New York"));
        network.addPerson(new Person("Bob", 32, "San Francisco"));
        network.addPerson(new Person("Carol", 25, "Boston"));
        network.addPerson(new Person("David", 30, "Chicago"));
        network.addPerson(new Person("Eve", 27, "Seattle"));
        network.addPerson(new Person("Frank", 35, "Austin"));
        network.addPerson(new Person("Grace", 29, "Portland"));
        network.addPerson(new Person("Henry", 26, "Denver"));

        // Create friendships (undirected edges)
        // Component 1: Alice, Bob, Carol, David, Eve
        network.addFriendship("Alice", "Bob", "college friends");
        network.addFriendship("Alice", "Carol", "work colleagues");
        network.addFriendship("Bob", "David", "roommates");
        network.addFriendship("Carol", "David", "gym buddies");
        network.addFriendship("David", "Eve", "neighbors");

        // Component 2: Frank, Grace, Henry
        network.addFriendship("Frank", "Grace", "siblings");
        network.addFriendship("Grace", "Henry", "teammates");

        network.displayNetwork();

        // Demonstrate DFS from Alice
        network.performDFS("Alice");

        // Demonstrate BFS from Alice
        network.performBFS("Alice");

        // Find path using BFS
        network.findPath("Alice", "Eve");
        network.findPath("Bob", "Carol");

        // Demonstrate complete DFS (showing the forest structure)
        network.performDFSComplete();

        // Try path between disconnected components
        network.findPath("Alice", "Frank");
    }
}
