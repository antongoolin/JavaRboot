package ru.sbrf.cu.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.sbrf.cu.LibraryException;
import ru.sbrf.cu.model.Author;
import ru.sbrf.cu.model.Book;
import ru.sbrf.cu.model.Comment;
import ru.sbrf.cu.model.Genre;
import ru.sbrf.cu.service.LibraryServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/library")
public class LibraryController {
    private final LibraryServiceImpl libraryService;

    public LibraryController(LibraryServiceImpl libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/")
    public String startPage(Model model) {
        return ("start");
    }

    @GetMapping("/books")
    public String booksPage(@RequestParam(value = "author", required = false) Long authorId, @RequestParam(value = "genre", required = false) Long genreId, Model model) {
        List<Book> books = libraryService.getAllBooks();
        List<Genre> genres = libraryService.getAllGenres();
        List<Author> authors = libraryService.getAllAuthors();
        model.addAttribute("books", books.stream()
                .filter(book -> genreId == null || book.getGenre().getId() == genreId)
                .filter(book -> authorId == null || book.getAuthors().stream().anyMatch(author -> author.getId() == authorId))
                .collect(Collectors.toList()));
        model.addAttribute("genres", genres);
        model.addAttribute("authors", authors);
        return ("books");
    }

    @GetMapping("/authors")
    public String authorsPage(Model model) {
        List<Author> authors = libraryService.getAllAuthors();
        model.addAttribute("authors", authors);
        return ("authors");
    }

    @GetMapping("/genres")
    public String genresPage(Model model) {
        List<Genre> genres = libraryService.getAllGenres();
        model.addAttribute("genres", genres);
        return ("genres");
    }

    @GetMapping("/books/editBook")
    public String editBookPage(@RequestParam(value = "id") long id, Model model) {
        Book book = libraryService.getBookById(id).orElseThrow(NotFoundException::new);
        List<Genre> genres = libraryService.getAllGenres();
        List<Author> authors = libraryService.getAllAuthors();
        model.addAttribute("book", book);
        model.addAttribute("genres", genres);
        model.addAttribute("authors", authors);
        return ("editBook");
    }

    @PostMapping("/books/{value}")
    public String saveBook(Book book, @PathVariable(value = "value") String val, @RequestParam(value = "id", required = false, defaultValue = "0") long id, RedirectAttributes attributes, Model model) {
        try {
            Book updBook = libraryService.upsertBook(book);
        } catch (LibraryException e) {
            attributes.addFlashAttribute("error", e.getMessage());
            if (id != 0) {
                attributes.addAttribute("id", id);
            }
            return ("redirect:/api/library/books/" + val);
        }
        model.addAttribute("books", libraryService.getAllBooks());
        model.addAttribute("genres", libraryService.getAllGenres());
        model.addAttribute("authors", libraryService.getAllAuthors());
        return ("redirect:/api/library/books");
    }

    @GetMapping("/books/createBook")
    public String createBookPage(Model model) {
        Book book = new Book();
        List<Genre> genres = libraryService.getAllGenres();
        List<Author> authors = libraryService.getAllAuthors();
        model.addAttribute("book", book);
        model.addAttribute("genres", genres);
        model.addAttribute("authors", authors);
        return ("createBook");
    }

    @GetMapping("/authors/editAuthor")
    public String editAuthorPage(@RequestParam(value = "id") long id, Model model) {
        Author author = libraryService.getAuthorById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("author", author);
        return ("editAuthor");
    }

    @PostMapping("/authors/{value}")
    public String saveAuthor(Author author, @PathVariable(value = "value") String val, @RequestParam(value = "id", required = false, defaultValue = "0") long id, RedirectAttributes attributes, Model model) {
        try {
            Author updAuthor = libraryService.upsertAuthor(author);
        } catch (LibraryException e) {
            attributes.addFlashAttribute("error", e.getMessage());
            if (id != 0) {
                attributes.addAttribute("id", id);
            }
            return ("redirect:/api/library/authors/" + val);
        }
        model.addAttribute("authors", libraryService.getAllGenres());
        return ("redirect:/api/library/authors");
    }

    @GetMapping("/authors/createAuthor")
    public String createAuthorPage(Model model) {
        Author author = new Author();
        model.addAttribute("author", author);
        return ("createAuthor");
    }

    @PostMapping("/books/deleteBook")
    public String deleteBook(@RequestParam(value = "id") long id, Model model, RedirectAttributes attributes) {
        try {
            libraryService.deleteBook(id);
        } catch (LibraryException e) {
            attributes.addFlashAttribute("error", e.getMessage());
            return ("redirect:/api/library/books");
        }
        return ("redirect:/api/library/books");
    }

    @PostMapping("/authors/deleteAuthor")
    public String deleteAuthor(@RequestParam(value = "id") long id, Model model, RedirectAttributes attributes) {
        try {
            libraryService.deleteAuthor(id);
        } catch (LibraryException e) {
            attributes.addFlashAttribute("error", e.getMessage());
            return ("redirect:/api/library/authors");
        }
        return ("redirect:/api/library/authors");
    }

    @GetMapping("/genres/editGenre")
    public String editGenrePage(@RequestParam(value = "id") long id, Model model) {
        Genre genre = libraryService.getGenreById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("genre", genre);
        return ("editGenre");
    }

    @PostMapping(value = "/genres/{value}")
    public String saveGenre(Genre genre, @PathVariable(value = "value") String val, @RequestParam(value = "id", required = false, defaultValue = "0") long id, RedirectAttributes attributes, Model model) {
        try {
            Genre updGenre = libraryService.upsertGenre(genre);
        } catch (LibraryException e) {
            attributes.addFlashAttribute("error", e.getMessage());
            if (id != 0) {
                attributes.addAttribute("id", id);
            }
            return ("redirect:/api/library/genres/" + val);
        }
        model.addAttribute("genres", libraryService.getAllGenres());
        return ("redirect:/api/library/genres");
    }

    @GetMapping("/genres/createGenre")
    public String createGenrePage(Model model) {
        Genre genre = new Genre();
        model.addAttribute("genre", genre);
        return ("createGenre");
    }

    @PostMapping("/genres/deleteGenre")
    public String deleteGenre(@RequestParam(value = "id") long id, Model model, RedirectAttributes attributes) {
        try {
            libraryService.deleteGenre(id);
        } catch (LibraryException e) {
            attributes.addFlashAttribute("error", e.getMessage());
            return ("redirect:/api/library/genres");
        }
        return ("redirect:/api/library/genres");
    }

    @GetMapping("/books/book")
    public String bookPage(@RequestParam(value = "id") long id, Model model) {
        Book book = libraryService.getBookById(id).orElseThrow(NotFoundException::new);
        List<Comment> comments = libraryService.getAllComments(id);
        model.addAttribute("book", book);
        model.addAttribute("comments", comments);
        return ("book");
    }

    @GetMapping("/accessDenied")
    public String accessDeniedPage(Model model) {
        return ("accessDenied");
    }

    @PostMapping("/accessDenied")
    public String postAccessDeniedPage(Model model) {
        return ("accessDenied");
    }

    @GetMapping("/success")
    public String successPage(Model model) {
        return ("success");
    }

}
