package ru.sbrf.cu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import ru.sbrf.cu.dao.LibraryDaoException;
import ru.sbrf.cu.dao.LibraryDaoImpl;
import ru.sbrf.cu.model.Author;
import ru.sbrf.cu.model.Book;
import ru.sbrf.cu.model.Comment;
import ru.sbrf.cu.model.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(LibraryDaoImpl.class)
public class LibraryDaoImplTest {

    private static final long FIRST_BOOK_ID = 1;

    private static final long NOT_EXISTING_BOOK_ID = 943;

    @Autowired
    private LibraryDaoImpl libraryDao;

    @Autowired
    private TestEntityManager entityManager;

    @DisplayName("Поиск по Id")
    @Test
    void testFindBookById() {
        Optional<Book> optionalActualBook = libraryDao.findById(Book.class, FIRST_BOOK_ID);
        Book expectedBook = entityManager.find(Book.class, FIRST_BOOK_ID);
        assertThat(optionalActualBook).isPresent().get().isEqualToComparingFieldByField(expectedBook);
    }

    @DisplayName("Поиск всех жанры")
    @Test
    void testFindAllGenres() {
        List<Genre> actualGenres = libraryDao.getListOfEntity(Genre.class);
        assertThat(actualGenres.stream().map(s -> s.getName())).containsExactlyInAnyOrder("фантастика", "детектив", "модернизм");
    }

    @DisplayName("Поиск всех авторов")
    @Test
    void testFindAllAuthors() {
        List<Author> actualAuthors = libraryDao.getListOfEntity(Author.class);
        assertThat(actualAuthors.stream().map(s -> s.getName())).containsExactlyInAnyOrder("Франц Кафка", "Агата Кристи", "Аркадий Стругацкий", "Борис Стругацкий");
    }

    @DisplayName("Поиск всех книги")
    @Test
    void testFindAllBook() {
        List<Book> actualBooks = libraryDao.getListOfEntity(Book.class);
        assertThat(actualBooks.stream().map(s -> s.getName())).containsExactlyInAnyOrder("Процесс", "Пикник на обочине", "Восточный экспресс");
    }

    @DisplayName("Добавляем жанр")
    @Transactional
    @Test
    void testAddGenre() {
        libraryDao.saveEntity(new Genre(0, "комедия"));
        List<Genre> actualGenres = libraryDao.getListOfEntity(Genre.class);
        assertThat(actualGenres.stream().map(s -> s.getName())).containsExactlyInAnyOrder("фантастика", "детектив", "модернизм", "комедия");
    }

    @DisplayName("Добавляем автора")
    @Transactional
    @Test
    void testAddAuthor() {
        libraryDao.saveEntity(new Author(0, "Уильям Шекспир"));
        List<Author> actualAuthors = libraryDao.getListOfEntity(Author.class);
        assertThat(actualAuthors.stream().map(s -> s.getName())).containsExactlyInAnyOrder("Франц Кафка", "Агата Кристи", "Аркадий Стругацкий", "Борис Стругацкий", "Уильям Шекспир");
    }

    @DisplayName("Добавлять книгу")
    @Transactional
    @Test
    void testAddBook() {
        List<Author> authors = List.of(new Author(0, "Уильям Шекспир"));
        authors.stream().forEach(a -> libraryDao.saveEntity(a));
        Genre genre = new Genre(0, "комедия");
        libraryDao.saveEntity(genre);
        libraryDao.saveEntity(new Book(0, "Двенадцатая ночь", authors, genre));
        List<Book> actualBooks = libraryDao.getListOfEntity(Book.class);
        assertThat(actualBooks.stream().map(s -> s.getName())).containsExactlyInAnyOrder("Процесс", "Пикник на обочине", "Восточный экспресс", "Двенадцатая ночь");
    }

    @DisplayName("Должен добавлять комментарий к книге")
    @Transactional
    @Test
    void testAddComment() {
        Book book = libraryDao.findById(Book.class, FIRST_BOOK_ID).get();
        libraryDao.saveEntity(new Comment(0, "Нравится", "Кот Мей", book));
        List<Comment> actualComments = book.getComments();
        assertThat(actualComments.stream().map(s -> s.getText())).containsExactlyInAnyOrder("Неплохо", "Прекрасно", "Удивительно");
    }

    @DisplayName("Удаляем книгу")
    @Transactional
    @Test
    void testDeleteBook() {
        libraryDao.deleteById(Book.class, FIRST_BOOK_ID);
        List<Book> actualBooks = libraryDao.getListOfEntity(Book.class);
        assertThat(actualBooks.stream().map(s -> s.getName())).containsExactlyInAnyOrder("Ералаш", "Бог");
    }

    @DisplayName("Поиск книги по названию")
    @Test
    void testGetBookByName() {
        List<Book> books = libraryDao.findByName(Book.class, "Ералаш");
        assertThat(books.stream().map(s -> s.getId())).containsExactlyInAnyOrder(2l);
    }

}
