package com.workintech.library.service;

import com.workintech.library.model.Book;
import com.workintech.library.model.Invoice;
import com.workintech.library.model.Loan;
import com.workintech.library.model.Member;
import com.workintech.library.model.User;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LibraryService implements BorrowingOperations {

    private final Catalog catalog;
    private final Map<Integer, User> usersById;
    private final List<Loan> loanHistory;

    private long nextLoanId;
    private long nextInvoiceId;

    public LibraryService(Catalog catalog) {
        this.catalog = catalog;
        this.usersById = new LinkedHashMap<>();
        this.loanHistory = new ArrayList<>();
        this.nextLoanId = 1;
        this.nextInvoiceId = 1;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void registerUser(User user) {

        if (usersById.containsKey(user.getId())) {
            throw new IllegalArgumentException(
                    "A user with this id already exists."
            );
        }

        usersById.put(user.getId(), user);
    }

    public void registerMember(Member member) {
        registerUser(member);
    }

    public User getUser(int id) {

        User user = usersById.get(id);

        if (user == null) {
            throw new IllegalArgumentException(
                    "User not found: " + id
            );
        }

        return user;
    }

    public Member getMember(int id) {

        User user = getUser(id);

        if (!(user instanceof Member)) {
            throw new IllegalArgumentException(
                    "User is not a library member: " + id
            );
        }

        return (Member) user;
    }

    public List<User> getUsers() {
        return new ArrayList<>(usersById.values());
    }

    public List<Member> getMembers() {

        List<Member> members = new ArrayList<>();

        for (User user : usersById.values()) {
            if (user instanceof Member) {
                members.add((Member) user);
            }
        }

        return members;
    }

    public List<Loan> getLoanHistory() {
        return new ArrayList<>(loanHistory);
    }

    @Override
    public Loan borrowBook(int memberId, int bookId) {

        Member member = getMember(memberId);

        Book book = catalog.findById(bookId)
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "Book not found: " + bookId
                        )
                );

        if (!book.isAvailable()) {
            throw new IllegalStateException(
                    "Book is currently borrowed by member "
                            + book.getBorrowedByMemberId()
            );
        }

        if (!member.canBorrow()) {
            throw new IllegalStateException(
                    "Member reached the maximum 5-book limit."
            );
        }

        member.borrowBook(bookId);
        book.markBorrowed(memberId);

        Invoice invoice = new Invoice(
                nextInvoiceId++,
                memberId,
                bookId,
                book.getBorrowFee()
        );

        Loan loan = new Loan(
                nextLoanId++,
                memberId,
                bookId,
                invoice
        );

        loanHistory.add(loan);

        return loan;
    }

    @Override
    public Loan returnBook(int memberId, int bookId) {

        Member member = getMember(memberId);

        Book book = catalog.findById(bookId)
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "Book not found: " + bookId
                        )
                );

        if (book.isAvailable()) {
            throw new IllegalStateException(
                    "Book is not currently borrowed."
            );
        }

        if (book.getBorrowedByMemberId() == null
                || book.getBorrowedByMemberId() != memberId) {

            throw new IllegalStateException(
                    "This book is borrowed by another member."
            );
        }

        Loan activeLoan =
                findActiveLoan(memberId, bookId);

        member.returnBook(bookId);
        book.markReturned();
        activeLoan.close();

        return activeLoan;
    }

    private Loan findActiveLoan(
            int memberId,
            int bookId) {

        for (Loan loan : loanHistory) {

            if (loan.getMemberId() == memberId
                    && loan.getBookId() == bookId
                    && loan.isActive()) {

                return loan;
            }
        }

        throw new IllegalStateException(
                "Active loan record could not be found."
        );
    }
}
