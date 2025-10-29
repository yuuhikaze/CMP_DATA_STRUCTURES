package com.yuuhikaze.ed202510.EI.EI05;

import com.yuuhikaze.ed202510.TDA.ChainHashMap;

class LibraryController {
    private ChainHashMap<String, Book> bookCatalog;

    public LibraryController() {
        this.bookCatalog = new ChainHashMap<>();
    }

    public void addBook(Book book) {
        bookCatalog.put(book.getIsbn(), book);
    }

    public Book findBook(String isbn) {
        return bookCatalog.get(isbn);
    }

    public void borrowBook(String isbn) {
        Book book = bookCatalog.get(isbn);
        if (book != null) {
            if (book.isAvailable()) {
                book.setAvailable(false);
                System.out.println("Book borrowed: " + book.getTitle());
            } else {
                System.out.println("Book already borrowed: " + book.getTitle());
            }
        } else {
            System.out.println("Book not found with ISBN: " + isbn);
        }
    }

    public void returnBook(String isbn) {
        Book book = bookCatalog.get(isbn);
        if (book != null) {
            if (!book.isAvailable()) {
                book.setAvailable(true);
                System.out.println("Book returned: " + book.getTitle());
            } else {
                System.out.println("Book was not borrowed: " + book.getTitle());
            }
        } else {
            System.out.println("Book not found with ISBN: " + isbn);
        }
    }

    public void displayCatalog() {
        System.out.println("\n=== Library Catalog ===");
        for (Book book : bookCatalog.values()) {
            System.out.println("ISBN " + book.getIsbn() + ": " + book);
        }
    }
}
