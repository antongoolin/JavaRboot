package ru.sbrf;

import lombok.Getter;

@Getter
public class Save {
    private final Cell cell_50;
    private final Cell cell_100;
    private final Cell cell_200;
    private final Cell cell_500;
    private final Cell cell_1_000;
    private final Cell cell_2_000;
    private final Cell cell_5_000;

    public Save() {
        cell_50 = new Cell(Banknote.FIFTY);
        cell_100 = new Cell(Banknote.ONE_HUNDRED);
        cell_200 = new Cell(Banknote.TWO_HUNDRED);
        cell_500 = new Cell(Banknote.FIVE_HUNDRED);
        cell_1_000 = new Cell(Banknote.ONE_THOUSAND);
        cell_2_000 = new Cell(Banknote.TWO_THOUSAND);
        cell_5_000 = new Cell(Banknote.FIVE_THOUSAND);
    }

    public long getBalance() {
        return getCell_50().getSum()
                + getCell_100().getSum()
                + getCell_200().getSum()
                + getCell_500().getSum()
                + getCell_1_000().getSum()
                + getCell_2_000().getSum()
                + getCell_5_000().getSum();

    }
}
