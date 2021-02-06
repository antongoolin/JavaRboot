package ru.sbrf.cu.service.formatters;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import ru.sbrf.cu.model.Author;
import ru.sbrf.cu.rest.NotFoundException;
import ru.sbrf.cu.service.LibraryServiceImpl;

import java.text.ParseException;
import java.util.Locale;

@Component
public class AuthorFormatter implements Formatter<Author> {

    private final LibraryServiceImpl libraryService;

    public AuthorFormatter(LibraryServiceImpl libraryService) {
        this.libraryService = libraryService;
    }

    @Override
    public Author parse(String s, Locale locale) throws ParseException {
        final Integer authorId = Integer.valueOf(s);
        return libraryService.getAuthorById(authorId).orElseThrow(NotFoundException::new);
    }

    @Override
    public String print(Author author, Locale locale) {
        return (author != null ? String.valueOf(author.getId()) : "");
    }
}
