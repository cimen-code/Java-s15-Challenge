package com.workintech.library.model;

import java.time.LocalDateTime;

public class Invoice {

    private final long id;
    private final int memberId;
    private final int bookId;
    private final double amount;
    private final LocalDateTime createdAt;
    private InvoiceStatus status;

    public Invoice(
            long id,
            int memberId,
            int bookId,
            double amount) {

        this.id = id;
        this.memberId = memberId;
        this.bookId = bookId;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
        this.status = InvoiceStatus.CHARGED;
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

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void refund() {
        if (status == InvoiceStatus.REFUNDED) {
            throw new IllegalStateException("Invoice is already refunded.");
        }

        status = InvoiceStatus.REFUNDED;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", bookId=" + bookId +
                ", amount=" + amount +
                ", status=" + status +
                '}';
    }
}
