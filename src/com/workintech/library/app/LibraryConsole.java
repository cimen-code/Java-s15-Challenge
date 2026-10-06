package com.workintech.library.app;

import com.workintech.library.model.Author;
import com.workintech.library.model.Book;
import com.workintech.library.model.Category;
import com.workintech.library.model.Loan;
import com.workintech.library.model.User;
import com.workintech.library.service.LibraryService;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class LibraryConsole {

    private final Scanner scanner;
    private final LibraryService library;

    public LibraryConsole(Scanner scanner, LibraryService library) {
        this.scanner = scanner;
        this.library = library;
    }

    public static void main(String[] args) {

        LibraryService library = SampleData.createLibrary();

        LibraryConsole console =
                new LibraryConsole(
                        new Scanner(System.in),
                        library
                );

        console.start();
    }

    public void start() {

        boolean running = true;

        System.out.println(
                "=== WORKINTECH LIBRARY SYSTEM ==="
        );

        while (running) {

            printMenu();

            int choice = readInt("Select: ");

            try {

                switch (choice) {

                    case 1:
                        printBooks(
                                library
                                        .getCatalog()
                                        .getAllBooks()
                        );
                        break;

                    case 2:
                        searchById();
                        break;

                    case 3:
                        searchByTitle();
                        break;

                    case 4:
                        searchByAuthor();
                        break;

                    case 5:
                        listByCategory();
                        break;

                    case 6:
                        addBook();
                        break;

                    case 7:
                        updateBook();
                        break;

                    case 8:
                        deleteBook();
                        break;

                    case 9:
                        borrowBook();
                        break;

                    case 10:
                        returnBook();
                        break;

                    case 11:
                        listUsers();
                        break;

                    case 12:
                        listLoans();
                        break;

                    case 0:
                        running = false;
                        break;

                    default:
                        System.out.println(
                                "Unknown menu option."
                        );
                }

            } catch (RuntimeException exception) {

                System.out.println(
                        "ERROR: "
                                + exception.getMessage()
                );
            }
        }

        System.out.println(
                "Library system closed."
        );
    }

    private void printMenu() {

        System.out.println();
        System.out.println("------------------------------");
        System.out.println("1  - List all books");
        System.out.println("2  - Search book by ID");
        System.out.println("3  - Search book by title");
        System.out.println("4  - Search book by author");
        System.out.println("5  - List books by category");
        System.out.println("6  - Add book");
        System.out.println("7  - Update book");
        System.out.println("8  - Delete book");
        System.out.println("9  - Borrow book");
        System.out.println("10 - Return book");
        System.out.println("11 - List users");
        System.out.println("12 - Loan history");
        System.out.println("0  - Exit");
        System.out.println("------------------------------");
    }

    private void searchById() {

        int id = readInt("Book ID: ");

        Book book = library
                .getCatalog()
                .findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "Book not found."
                        )
                );

        System.out.println(book);
    }

    private void searchByTitle() {

        String title =
                readLine("Title contains: ");

        printBooks(
                library
                        .getCatalog()
                        .findByTitle(title)
        );
    }

    private void searchByAuthor() {

        String author =
                readLine("Author name: ");

        printBooks(
                library
                        .getCatalog()
                        .findByAuthor(author)
        );
    }

    private void listByCategory() {

        Category category =
                readCategory();

        printBooks(
                library
                        .getCatalog()
                        .listByCategory(category)
        );
    }

    private void addBook() {

        int id =
                readInt("Book ID: ");

        String title =
                readLine("Title: ");

        int authorId =
                readInt("Author ID: ");

        String authorName =
                readLine("Author name: ");

        Category category =
                readCategory();

        double fee =
                readDouble("Borrow fee: ");

        Book book =
                new Book(
                        id,
                        title,
                        new Author(
                                authorId,
                                authorName
                        ),
                        category,
                        fee
                );

        library
                .getCatalog()
                .addBook(book);

        System.out.println(
                "Book added: " + book
        );
    }

    private void updateBook() {

        int id =
                readInt("Book ID to update: ");

        String title =
                readLine("New title: ");

        int authorId =
                readInt("New author ID: ");

        String authorName =
                readLine("New author name: ");

        Category category =
                readCategory();

        double fee =
                readDouble("New borrow fee: ");

        Book book =
                library
                        .getCatalog()
                        .updateBook(
                                id,
                                title,
                                new Author(
                                        authorId,
                                        authorName
                                ),
                                category,
                                fee
                        );

        System.out.println(
                "Book updated: " + book
        );
    }

    private void deleteBook() {

        int id =
                readInt("Book ID to delete: ");

        Book book =
                library
                        .getCatalog()
                        .deleteBook(id);

        System.out.println(
                "Book deleted: " + book
        );
    }

    private void borrowBook() {

        int memberId =
                readInt("Member ID: ");

        int bookId =
                readInt("Book ID: ");

        Loan loan =
                library.borrowBook(
                        memberId,
                        bookId
                );

        System.out.println(
                "Borrow successful:"
        );

        System.out.println(loan);

        System.out.println(
                "Invoice: "
                        + loan.getInvoice()
        );
    }

    private void returnBook() {

        int memberId =
                readInt("Member ID: ");

        int bookId =
                readInt("Book ID: ");

        Loan loan =
                library.returnBook(
                        memberId,
                        bookId
                );

        System.out.println(
                "Return successful:"
        );

        System.out.println(loan);

        System.out.println(
                "Refund status: "
                        + loan
                        .getInvoice()
                        .getStatus()
        );
    }

    private void listUsers() {

        for (User user :
                library.getUsers()) {

            System.out.println(
                    user.getRole()
                            + " -> "
                            + user
            );
        }
    }

    private void listLoans() {

        for (Loan loan :
                library.getLoanHistory()) {

            System.out.println(loan);
        }
    }

    private void printBooks(
            List<Book> books) {

        if (books.isEmpty()) {

            System.out.println(
                    "No books found."
            );

            return;
        }

        for (Book book : books) {
            System.out.println(book);
        }
    }

    private Category readCategory() {

        while (true) {

            System.out.println(
                    "Categories: "
                            + Arrays.toString(
                            Category.values()
                    )
            );

            String input =
                    readLine("Category: ");

            try {

                return Category.valueOf(
                        input.toUpperCase(
                                Locale.ROOT
                        )
                );

            } catch (
                    IllegalArgumentException exception) {

                System.out.println(
                        "Invalid category."
                );
            }
        }
    }

    private int readInt(
            String prompt) {

        while (true) {

            try {

                return Integer.parseInt(
                        readLine(prompt)
                );

            } catch (
                    NumberFormatException exception) {

                System.out.println(
                        "Please enter an integer."
                );
            }
        }
    }

    private double readDouble(
            String prompt) {

        while (true) {

            try {

                return Double.parseDouble(
                        readLine(prompt)
                );

            } catch (
                    NumberFormatException exception) {

                System.out.println(
                        "Please enter a number."
                );
            }
        }
    }

    private String readLine(
            String prompt) {

        System.out.print(prompt);

        return scanner
                .nextLine()
                .trim();
    }
}
