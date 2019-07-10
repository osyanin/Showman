package library;

import java.util.List;

public interface Library { /* Регистрация новой книги */

    void addNewBook(Book book); /* Студент берет книгу */

    void borrowBook(Book book, String student); /* Студент возвращает книгу */

    void returnBook(Book book, String student); /* Получить список свободных книг */

    List<Book> findAvailableBooks();

}