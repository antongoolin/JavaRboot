package ru.sbrf.cu.service;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sbrf.cu.dao.LibraryDao;
import ru.sbrf.cu.dao.LibraryDaoException;
import ru.sbrf.cu.model.Author;
import ru.sbrf.cu.model.Book;
import ru.sbrf.cu.model.Comment;
import ru.sbrf.cu.model.Genre;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryServiceImpl implements LibraryService {

    private final LibraryDao libraryDao;

    public LibraryServiceImpl(LibraryDao libraryDao) {
        this.libraryDao = libraryDao;
    }

    @Override
    @Transactional
    public Book upsertBook(Book book) {
        if (book.getName().equals("")) {
            throw (new LibraryServiceException("The name of book is not set"));
        }
        if (book.getAuthors().size() == 0) {
            throw (new LibraryServiceException("The author of book is not set"));
        }
        if (book.getId() == 0) {
            libraryDao.findByName(Book.class, book.getName()).stream().filter(b -> b.equals(book)).findAny().ifPresent(b -> {
                throw new LibraryServiceException("The book already exists");
            });
        } else {
            book.setComments(libraryDao.findById(Book.class, book.getId()).orElseThrow(() -> new LibraryDaoException("The book not found")).getComments());
        }
        return libraryDao.saveEntity(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return libraryDao.getListOfEntity(Book.class);
    }

    @Transactional
    @Override
    public Genre upsertGenre(Genre genre) {
        if (genre.getName().equals("")) {
            throw (new LibraryServiceException("The name of genre is not set"));
        }
        if (genre.getId() == 0) {
            libraryDao.findByName(Genre.class, genre.getName()).stream().findAny().ifPresent(g -> {
                throw new LibraryServiceException("The genre already exists");
            });
        } else {
            genre.setBooks(libraryDao.findById(Author.class, genre.getId()).get().getBooks());
        }
        return libraryDao.saveEntity(genre);
    }

    @Override
    public List<Genre> getAllGenres() {
        return libraryDao.getListOfEntity(Genre.class);
    }

    @Transactional
    @Override
    public Author upsertAuthor(Author author) {
        if (author.getName().equals("")) {
            throw (new LibraryServiceException("The name of author is not set"));
        }
        if (author.getId() == 0) {
            libraryDao.findByName(Author.class, author.getName()).stream().findAny().ifPresent(g -> {
                throw new LibraryServiceException("This author already exists");
            });
        } else {
            author.setBooks(libraryDao.findById(Author.class, author.getId()).get().getBooks());
        }
        return libraryDao.saveEntity(author);
    }

    @Override
    public List<Author> getAllAuthors() {
        return libraryDao.getListOfEntity(Author.class);
    }

    @Transactional
    @Override
    public void addComment(Comment comment) {
        Book book = libraryDao.findById(Book.class, comment.getBook().getId()).orElseThrow(() -> new LibraryDaoException("The book not found"));
        comment.setBook(book);
        libraryDao.saveEntity(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getAllComments(long bookId) {
        Book book = libraryDao.findById(Book.class, bookId).orElseThrow(() -> new LibraryDaoException("The book not found"));
        Hibernate.initialize(book.getComments());
        return book.getComments();
    }

    @Transactional
    @Override
    public void deleteBook(long bookId) {
        libraryDao.deleteById(Book.class, bookId);
    }

    @Override
    public Optional<Genre> getGenreById(long id) {
        return libraryDao.findById(Genre.class, id);
    }

    @Override
    public Optional<Book> getBookById(long id) {
        return libraryDao.findById(Book.class, id);
    }

    @Override
    public Optional<Author> getAuthorById(long id) {
        return libraryDao.findById(Author.class, id);
    }

    @Transactional
    @Override
    public void deleteAuthor(long authorId) {
        Optional<Author> author = libraryDao.findById(Author.class, authorId);
        if (author.isPresent()) {
            if (author.get().getBooks().size() == 0) {
                libraryDao.deleteById(Author.class, authorId);
            } else throw (new LibraryDaoException("The library has a book with such an author"));
        } else throw (new LibraryDaoException("No author found with this ID"));
    }

    @Transactional
    @Override
    public void deleteGenre(long genreId) {
        Optional<Genre> genre = libraryDao.findById(Genre.class, genreId);
        if (genre.isPresent()) {
            if (genre.get().getBooks().size() == 0) {
                libraryDao.deleteById(Genre.class, genreId);
            } else throw (new LibraryDaoException("The library has a book with such genre"));
        } else throw (new LibraryDaoException("No genre found with this Id"));
    }
}
