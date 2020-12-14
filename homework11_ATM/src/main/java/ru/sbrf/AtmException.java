package ru.sbrf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AtmException extends IllegalArgumentException {
    Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    public AtmException(String s) {
        super(s);
        log.info(s);
    }

}
