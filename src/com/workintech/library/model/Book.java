package com.workintech.library.model;

public class Book {

    private final int id;
    private String title;
    private Author author;
    private Category category;
    private double borrowFee;
    private BookStatus status;
    private Integer borrowedByMemberId;

    public Book(
            int id,
            String title,
            Author author,
            Category category,
            double borrowFee) {

        if (borrowFee < 0) {
            throw new IllegalArgumentException("Borrow fee cannot be negative.");
        }

        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.borrowFee = borrowFee;
        this.status = BookStatus.AVAILABLE;
        this.borrowedByMemberId = null;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public Category getCategory() {
        return category;
    }

    public double getBorrowFee() {
        return borrowFee;
    }

    public BookStatus getStatus() {
        return status;
    }

    public Integer getBorrowedByMemberId() {
        return borrowedByMemberId;
    }

    public boolean isAvailable() {
        return status == BookStatus.AVAILABLE;
    }

    public void updateDetails(
            String title,
            Author author,
            Category category,
            double borrowFee) {

        if (borrowFee < 0) {
            throw new IllegalArgumentException("Borrow fee cannot be negative.");
        }

        this.title = title;
        this.author = author;
        this.category = category;
        this.borrowFee = borrowFee;
    }

    public void markBorrowed(int memberId) {
        if (!isAvailable()) {
            throw new IllegalStateException("Book is already borrowed.");
        }

        status = BookStatus.BORROWED;
        borrowedByMemberId = memberId;
    }

    public void markReturned() {
        status = BookStatus.AVAILABLE;
        borrowedByMemberId = null;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + author.getName() +
                ", category=" + category +
                ", fee=" + borrowFee +
                ", status=" + status +
                ", borrowedBy=" + borrowedByMemberId +
                '}';
    }
}
