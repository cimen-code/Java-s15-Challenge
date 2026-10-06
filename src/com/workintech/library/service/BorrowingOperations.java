package com.workintech.library.service;

import com.workintech.library.model.Loan;

public interface BorrowingOperations {

    Loan borrowBook(int memberId, int bookId);

    Loan returnBook(int memberId, int bookId);
}
