package ru.sbrf.cu.rest;

import ru.sbrf.cu.LibraryException;

public class NotFoundException extends LibraryException {
    public NotFoundException() {
        super("404: Not Found");
    }

}
