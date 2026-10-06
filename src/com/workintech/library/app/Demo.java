package com.workintech.library.app;

import com.workintech.library.model.Book;
import com.workintech.library.model.Loan;
import com.workintech.library.model.User;
import com.workintech.library.service.LibraryService;

public class Demo {

    public static void main(String[] args) {

        LibraryService library =
                SampleData.createLibrary();

        System.out.println(
                "=== LIBRARY SYSTEM PRESENTATION DEMO ==="
        );

        System.out.println(
                "\n1) POLYMORPHISM - SYSTEM USERS"
        );

        for (User user : library.getUsers()) {
            System.out.println(
                    user.getRole()
                            + " -> "
                            + user.getName()
            );
        }

        System.out.println(
                "\n2) INITIAL CATALOG"
        );

        for (Book book :
                library.getCatalog().getAllBooks()) {

            System.out.println(book);
        }

        System.out.println(
                "\n3) BORROW BOOK 101"
        );

        Loan firstLoan =
                library.borrowBook(1, 101);

        System.out.println(firstLoan);

        System.out.println(
                library.getCatalog()
                        .findById(101)
                        .orElseThrow()
        );

        System.out.println(
                "\n4) SECOND MEMBER TRIES SAME BOOK"
        );

        try {

            library.borrowBook(2, 101);

        } catch (IllegalStateException exception) {

            System.out.println(
                    "Rejected correctly: "
                            + exception.getMessage()
            );
        }

        System.out.println(
                "\n5) RETURN BOOK 101"
        );

        Loan returnedLoan =
                library.returnBook(1, 101);

        System.out.println(returnedLoan);

        System.out.println(
                "Invoice status: "
                        + returnedLoan
                        .getInvoice()
                        .getStatus()
        );

        System.out.println(
                "Book status: "
                        + library
                        .getCatalog()
                        .findById(101)
                        .orElseThrow()
                        .getStatus()
        );

        System.out.println(
                "\n6) FIVE BOOK LIMIT"
        );

        int[] bookIds = {
                101,
                102,
                103,
                104,
                105
        };

        for (int bookId : bookIds) {

            library.borrowBook(
                    1,
                    bookId
            );

            System.out.println(
                    "Borrowed -> " + bookId
            );
        }

        System.out.println(
                "Member 1 active books: "
                        + library
                        .getMember(1)
                        .getBorrowedBookIds()
        );

        System.out.println(
                "\nTrying sixth book -> 106"
        );

        try {

            library.borrowBook(1, 106);

        } catch (IllegalStateException exception) {

            System.out.println(
                    "Rejected correctly: "
                            + exception.getMessage()
            );
        }

        System.out.println(
                "\n=== PRESENTATION DEMO COMPLETED ==="
        );
    }
}
