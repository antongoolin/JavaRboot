package ru.sbrf;


import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cell {
    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    @Setter @Getter
    private int count = 100;
    @Getter
    private final Banknote banknote;

    public Cell(Banknote banknote) {
        this.banknote = banknote;
    }

    public void add() {
        log.info("add before " + count);
        count++;
        log.info("add after  " + count);
    }

    public void remove() {
        log.info("remove before " + count);
        count--;
        log.info("remove after  " + count);
    }

    public long getSum() {
        return (long) count * banknote.getValue();
    }

    public boolean isEmpty() {
        return getSum() == 0;

    }
}
