package com.workintech.library.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Member extends User {

    public static final int MAX_BORROW_LIMIT = 5;

    private final Set<Integer> borrowedBookIds;

    public Member(int id, String name, String email) {
        super(id, name, email);
        borrowedBookIds = new HashSet<>();
    }

    @Override
    public String getRole() {
        return "MEMBER";
    }

    public boolean canBorrow() {
        return borrowedBookIds.size() < MAX_BORROW_LIMIT;
    }

    public void borrowBook(int bookId) {
        if (!canBorrow()) {
            throw new IllegalStateException(
                    "Member reached the maximum 5-book limit."
            );
        }

        if (!borrowedBookIds.add(bookId)) {
            throw new IllegalStateException(
                    "Member already borrowed this book."
            );
        }
    }

    public void returnBook(int bookId) {
        if (!borrowedBookIds.remove(bookId)) {
            throw new IllegalStateException(
                    "This member does not have the book."
            );
        }
    }

    public Set<Integer> getBorrowedBookIds() {
        return Collections.unmodifiableSet(borrowedBookIds);
    }
}
