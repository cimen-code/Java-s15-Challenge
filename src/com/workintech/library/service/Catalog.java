package com.workintech.library.service;

import com.workintech.library.model.Author;
import com.workintech.library.model.Book;
import com.workintech.library.model.Category;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class Catalog {

    private final Map<Integer, Book> booksById;

    public Catalog() {
        booksById = new LinkedHashMap<>();
    }

    public void addBook(Book book) {
        if (booksById.containsKey(book.getId())) {
            throw new IllegalArgumentException(
                    "A book with this id already exists."
            );
        }

        booksById.put(book.getId(), book);
    }

    public Optional<Book> findById(int id) {
        return Optional.ofNullable(booksById.get(id));
    }

    public List<Book> findByTitle(String title) {
        List<Book> result = new ArrayList<>();

        for (Book book : booksById.values()) {
            if (book.getTitle()
                    .toLowerCase(Locale.ROOT)
                    .contains(title.toLowerCase(Locale.ROOT))) {

                result.add(book);
            }
        }

        return result;
    }

    public List<Book> findByAuthor(String authorName) {
        List<Book> result = new ArrayList<>();

        for (Book book : booksById.values()) {
            if (book.getAuthor()
                    .getName()
                    .equalsIgnoreCase(authorName)) {

                result.add(book);
            }
        }

        return result;
    }

    public List<Book> listByCategory(Category category) {
        List<Book> result = new ArrayList<>();

        for (Book book : booksById.values()) {
            if (book.getCategory() == category) {
                result.add(book);
            }
        }

        return result;
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(booksById.values());
    }

    public Book updateBook(
            int id,
            String title,
            Author author,
            Category category,
            double borrowFee) {

        Book book = requireBook(id);
        book.updateDetails(title, author, category, borrowFee);

        return book;
    }

    public Book deleteBook(int id) {
        Book book = requireBook(id);

        if (!book.isAvailable()) {
            throw new IllegalStateException(
                    "A borrowed book cannot be deleted."
            );
        }

        booksById.remove(id);
        return book;
    }

    private Book requireBook(int id) {
        Book book = booksById.get(id);

        if (book == null) {
            throw new IllegalArgumentException("Book not found: " + id);
        }

        return book;
    }
}
