package ru.sbrf.cu.service;

import ru.sbrf.cu.model.Author;
import ru.sbrf.cu.model.Book;
import ru.sbrf.cu.model.Comment;
import ru.sbrf.cu.model.Genre;

import java.util.List;
import java.util.Optional;

public interface LibraryService {
    Book upsertBook(Book book);

    List<Book> getAllBooks();

    Genre upsertGenre(Genre genre);

    List<Genre> getAllGenres();

    Author upsertAuthor(Author author);

    List<Author> getAllAuthors();

    void addComment(Comment comment);

    List<Comment> getAllComments(long bookId);

    void deleteBook(long bookId);

    Optional<Genre> getGenreById(long id);

    Optional<Book> getBookById(long id);

    Optional<Author> getAuthorById(long id);

    void deleteAuthor(long authorId);

    void deleteGenre(long genreId);
}
