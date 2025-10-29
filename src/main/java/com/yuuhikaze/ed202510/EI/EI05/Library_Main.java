package com.yuuhikaze.ed202510.EI.EI05;

public class Library_Main {
    public static void main(String[] args) {
        System.out.println("=== LIBRARY SYSTEM (ChainHashMap - Separate Chaining) ===\n");

        LibraryController library = new LibraryController();

        library.addBook(new Book("978-0134685991", "Effective Java", "Joshua Bloch", 2018));
        library.addBook(new Book("978-0596009205", "Head First Design Patterns", "Freeman & Robson", 2004));
        library.addBook(new Book("978-0132350884", "Clean Code", "Robert Martin", 2008));
        library.addBook(new Book("978-0201633610", "Design Patterns", "Gang of Four", 1994));

        library.displayCatalog();

        System.out.println("\n--- Borrowing books ---");
        library.borrowBook("978-0134685991");
        library.borrowBook("978-0132350884");

        library.displayCatalog();

        System.out.println("\n--- Attempting to borrow already borrowed book ---");
        library.borrowBook("978-0134685991");

        System.out.println("\n--- Returning books ---");
        library.returnBook("978-0134685991");

        library.displayCatalog();
    }
}
