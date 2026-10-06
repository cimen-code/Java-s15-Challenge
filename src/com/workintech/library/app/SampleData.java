package com.workintech.library.app;

import com.workintech.library.model.Author;
import com.workintech.library.model.Book;
import com.workintech.library.model.Category;
import com.workintech.library.model.Librarian;
import com.workintech.library.model.Member;
import com.workintech.library.service.Catalog;
import com.workintech.library.service.LibraryService;

public final class SampleData {

    private SampleData() {
    }

    public static LibraryService createLibrary() {

        Catalog catalog = new Catalog();

        Author georgeOrwell = new Author(1, "George Orwell");
        Author yuvalHarari = new Author(2, "Yuval Noah Harari");
        Author robertMartin = new Author(3, "Robert C. Martin");

        catalog.addBook(
                new Book(
                        101,
                        "1984",
                        georgeOrwell,
                        Category.FICTION,
                        20.0
                )
        );

        catalog.addBook(
                new Book(
                        102,
                        "Animal Farm",
                        georgeOrwell,
                        Category.FICTION,
                        15.0
                )
        );

        catalog.addBook(
                new Book(
                        103,
                        "Sapiens",
                        yuvalHarari,
                        Category.HISTORY,
                        25.0
                )
        );

        catalog.addBook(
                new Book(
                        104,
                        "Clean Code",
                        robertMartin,
                        Category.TECHNOLOGY,
                        30.0
                )
        );

        catalog.addBook(
                new Book(
                        105,
                        "Clean Architecture",
                        robertMartin,
                        Category.TECHNOLOGY,
                        30.0
                )
        );

        catalog.addBook(
                new Book(
                        106,
                        "Homo Deus",
                        yuvalHarari,
                        Category.SCIENCE,
                        25.0
                )
        );

        LibraryService library = new LibraryService(catalog);

        library.registerMember(
                new Member(
                        1,
                        "Hakan Cimen",
                        "hakan@example.com"
                )
        );

        library.registerMember(
                new Member(
                        2,
                        "Demo Member",
                        "demo@example.com"
                )
        );


        library.registerUser(
                new Librarian(
                        900,
                        "Library Admin",
                        "admin@example.com"
                )
        );

        return library;
    }
}
