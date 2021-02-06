package ru.sbrf.cu.service.formatters;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import ru.sbrf.cu.model.Genre;
import ru.sbrf.cu.rest.NotFoundException;
import ru.sbrf.cu.service.LibraryServiceImpl;

import java.text.ParseException;
import java.util.Locale;

@Component
public class GenreFormatter implements Formatter<Genre> {

    private final LibraryServiceImpl libraryService;

    public GenreFormatter(LibraryServiceImpl libraryService) {
        this.libraryService = libraryService;
    }

    @Override
    public Genre parse(String s, Locale locale) throws ParseException {
        final Integer genreId = Integer.valueOf(s);
        return libraryService.getGenreById(genreId).orElseThrow(NotFoundException::new);
    }

    @Override
    public String print(Genre genre, Locale locale) {
        return (genre != null ? String.valueOf(genre.getId()) : "");
    }
}
