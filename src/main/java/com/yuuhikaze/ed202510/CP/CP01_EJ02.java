package com.yuuhikaze.ed202510.CP;

import com.yuuhikaze.ed202510.TDA.SinglyLinkedList;
import com.yuuhikaze.ed202510.utils.ANSICodes;
import com.yuuhikaze.ed202510.utils.ChecksumUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Controller {
    /**
     * 0: Success
     *
     * <p>
     * 1: Attempts reservation on non available book (quantity)
     *
     * <p>
     * 2: Attempts reservation on non available book (existence)
     */
    public static final int TRIGGER_CASE = 2;
}


class ReservationRequest {
    String solicitor;
    Book book;

    public ReservationRequest(String solicitor, Book book) {
        this.solicitor = solicitor;
        this.book = book;
    }

    public Book getBook() {
        return book;
    }

    public String getSolicitor() {
        return solicitor;
    }
}


class Library {
    // operate as queue =>
    // insert at tail
    // remove from head
    private Map<Long, BookInstance> books;
    private SinglyLinkedList<ReservationRequest> rRequests;
    private List<Book> unattendedRequests;
    private boolean problemsOccurred;

    public Library(BookInstance[] books) {
        this.books = new HashMap<Long, BookInstance>();
        for (BookInstance book : books) {
            Long checksum = ChecksumUtil.computeCRC32(book);
            this.books.put(checksum, book);
        }
        this.rRequests = new SinglyLinkedList<>();
        this.unattendedRequests = new ArrayList<Book>();
        this.problemsOccurred = false;
    }

    class RequestHandlingException extends Exception {
        public RequestHandlingException(String message) {
            super(message);
        }
    }

    void request_book(ReservationRequest rRequest) {
        this.rRequests.addLast(rRequest);
    }

    void process_requests() throws RequestHandlingException {
        for (var rRequest : this.rRequests) {
            Long checksum = ChecksumUtil.computeCRC32(rRequest.getBook());
            if (this.books.containsKey(checksum)) {
                BookInstance book = this.books.get(checksum);
                try {
                    book.deduct();
                } catch (BookInstance.DeductionException e) {
                    this.unattendedRequests.add(book);
                }
            } else {
                this.problemsOccurred = true;
                throw new RequestHandlingException(
                        "'" + rRequest.getSolicitor() + "' requested a book not present in the catalog\n" + rRequest.getBook());
            }
        }
        this.rRequests = null;
    }

    void printReservationReport() {
        if (!this.problemsOccurred && this.unattendedRequests.isEmpty()) {
            System.out.println(
                    ANSICodes.BOLD + "All requests handled successfully!" + ANSICodes.RESET);
        }
        if (!this.unattendedRequests.isEmpty()) {
            System.out.println(
                    ANSICodes.BOLD + "Some requests were not fulfilled due to insufficient stock" + ANSICodes.RESET);
            System.out.println("The following books need to be purchased");
            for (Book book : this.unattendedRequests) {
                System.out.println(book);
            }
        }
    }
}


class Book {
    protected String title;
    protected String author;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    @Override
    public String toString() {
        return ANSICodes.DIM + "Title: " + this.title + "\nAuthor: " + this.author + ANSICodes.RESET;
    }
}


class BookInstance extends Book {
    private int sample_q;

    class DeductionException extends Exception {
        public DeductionException(String message) {
            super(message);
        }
    }

    public BookInstance(String title, String author, int sample_q) {
        super(title, author);
        this.sample_q = sample_q;
    }

    void deduct() throws DeductionException {
        if (this.sample_q == 0)
            throw new DeductionException("Can't deduct non-existent quantity of books");
        this.sample_q--;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}


public class CP01_EJ02 {

    public static void main(String[] args) {
        // Create library
        Library library =
                new Library(
                        new BookInstance[] {
                                new BookInstance("1984", "George Orwell", 1),
                                new BookInstance("Brave New World", "Aldous Huxley", 0),
                                new BookInstance("Fahrenheit 451", "Ray Bradbury", 2)});

        // Submit requests
        library.request_book(new ReservationRequest("Alice", new Book("1984", "George Orwell")));
        library.request_book(
                new ReservationRequest("Charlie", new Book("Fahrenheit 451", "Ray Bradbury")));
        switch (Controller.TRIGGER_CASE) {
            case 1:
                library.request_book(
                        new ReservationRequest(
                                "Bob", new Book("Brave New World", "Aldous Huxley")));
                break;

            case 2:
                library.request_book(
                        new ReservationRequest("Dave", new Book("The Hobbit", "J.R.R. Tolkien")));
                break;
        }

        // Process requests
        try {
            library.process_requests();
        } catch (Library.RequestHandlingException e) {
            System.out.println(
                    ANSICodes.BOLD + "The following requests were problematic" + ANSICodes.RESET);
            System.out.println(e.getMessage());
        }
        library.printReservationReport();
    }
}
