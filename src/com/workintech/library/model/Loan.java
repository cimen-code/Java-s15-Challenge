package com.workintech.library.model;

import java.time.LocalDateTime;

public class Loan {

    private final long id;
    private final int memberId;
    private final int bookId;
    private final LocalDateTime borrowedAt;
    private LocalDateTime returnedAt;
    private LoanStatus status;
    private final Invoice invoice;

    public Loan(
            long id,
            int memberId,
            int bookId,
            Invoice invoice) {

        this.id = id;
        this.memberId = memberId;
        this.bookId = bookId;
        this.invoice = invoice;
        this.borrowedAt = LocalDateTime.now();
        this.status = LoanStatus.ACTIVE;
    }

    public long getId() {
        return id;
    }

    public int getMemberId() {
        return memberId;
    }

    public int getBookId() {
        return bookId;
    }

    public LocalDateTime getBorrowedAt() {
        return borrowedAt;
    }

    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public boolean isActive() {
        return status == LoanStatus.ACTIVE;
    }

    public void close() {
        if (!isActive()) {
            throw new IllegalStateException("Loan is already closed.");
        }

        status = LoanStatus.RETURNED;
        returnedAt = LocalDateTime.now();
        invoice.refund();
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", bookId=" + bookId +
                ", status=" + status +
                ", invoice=" + invoice +
                '}';
    }
}
