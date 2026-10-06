# Library Management System - Design

## Class Diagram

```mermaid
classDiagram
    direction LR

    class User {
        <<abstract>>
        -int id
        -String name
        -String email
        +getRole() String
    }

    class Member {
        +MAX_BORROW_LIMIT = 5
        -Set~Integer~ borrowedBookIds
        +borrowBook(int)
        +returnBook(int)
    }

    class Librarian
    class Author
    class Book
    class Catalog {
        -Map~Integer, Book~ booksById
    }

    class BorrowingOperations {
        <<interface>>
        +borrowBook(int, int) Loan
        +returnBook(int, int) Loan
    }

    class LibraryService {
        -Catalog catalog
        -Map~Integer, User~ usersById
        -List~Loan~ loanHistory
    }

    class Loan {
        -Invoice invoice
    }

    class Invoice

    User <|-- Member
    User <|-- Librarian
    BorrowingOperations <|.. LibraryService

    LibraryService *-- Catalog
    LibraryService o-- User
    LibraryService o-- Loan
    Catalog *-- Book
    Book --> Author
    Loan *-- Invoice
```

## Design Notes

- **Encapsulation:** domain fields are private and state changes are controlled by methods.
- **Inheritance:** `Member` and `Librarian` extend abstract `User`.
- **Polymorphism:** different user types are stored as `Map<Integer, User>`.
- **Abstraction:** `User` is abstract and `BorrowingOperations` is an interface.
- **Composition:** `LibraryService` has a `Catalog`; `Loan` owns an `Invoice`.
- **Collections:** `Map` stores books/users, `Set` prevents duplicate active borrowed books, and `List<Loan>` stores loan history.

## Reference Diagram Adaptation

The challenge allows a custom OOP design. The reference UML shows book types such as Journals, StudyBooks and Magazines as subclasses. Because no different behavior is defined for those types, this implementation represents book types with `Category`. Inheritance is used where behavior differs: `User -> Member` and `User -> Librarian`.
