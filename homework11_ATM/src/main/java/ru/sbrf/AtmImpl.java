package ru.sbrf;

import java.util.ArrayList;
import java.util.List;

public class AtmImpl implements Atm {
    //    private final Save save = new Save();
    private final Save save;

    public AtmImpl(Save save) {
        this.save = save;
    }

    @Override
    public void acceptMoney(List<Banknote> cash) {
        for (Banknote banknote : cash) {
            switch (banknote) {
                case FIFTY:
                    save.getCell_50().add();
                    break;
                case ONE_HUNDRED:
                    save.getCell_100().add();
                    break;
                case TWO_HUNDRED:
                    save.getCell_200().add();
                    break;
                case FIVE_HUNDRED:
                    save.getCell_500().add();
                    break;
                case ONE_THOUSAND:
                    save.getCell_1_000().add();
                    break;
                case TWO_THOUSAND:
                    save.getCell_2_000().add();
                    break;
                case FIVE_THOUSAND:
                    save.getCell_5_000().add();
                    break;
                default:
                    throw new AtmException("Неизвестная купюра. Проверите корректность вставленных купюр.");
            }
        }
    }

    @Override
    public List<Banknote> withdrawMoney(long amount) {
        if (!isValidAmount(amount))
            throw new AtmException("Сумма должна быть кратна 50, 100, 200, 500, 1000, 2000, 5000");
        else {
            if (save.getCell_50().isEmpty() && amount / 100 != 0) {
                throw new AtmException("Сумма должна быть кратна 100, 200, 500, 1000, 2000, 5000");
            }
        }
        if (amount > save.getBalance())
            throw new AtmException("В данный момент есть некоторые проблемы. Уменьшите сумму и повторите попытку.");

        List<Banknote> cash = new ArrayList<>();

        StringBuilder errorText = new StringBuilder();
        errorText.append("В данный момент не можем выдать сумму ").append(amount)
                .append(". Недоступны купюры номиналом: ");

        List<Cell> cells = new ArrayList<>();
        cells.add(0, save.getCell_5_000());
        cells.add(1, save.getCell_2_000());
        cells.add(2, save.getCell_1_000());
        cells.add(3, save.getCell_500());
        cells.add(4, save.getCell_200());
        cells.add(5, save.getCell_100());
        cells.add(6, save.getCell_50());

        for (Cell cell : cells) {
            amount = countBanknotes(cash, amount, cell);
            if (cell.isEmpty()) {
                errorText.append(cell.getBanknote().getValue()).append("P ");
            }
        }
        if (amount > 0) {
            throw new AtmException(errorText.toString());
        }

        for (Banknote banknote : cash) {
            cells.stream().filter(c ->
                    c.getBanknote().getValue() == banknote.getValue()).forEach(Cell::remove);
        }

        return cash;
    }


    private long countBanknotes(List<Banknote> cash, long amount, Cell cell) {
        long i = (int) amount / cell.getBanknote().getValue();
        int tempCellCount = cell.getCount();
        if (i >= 1)
            if (tempCellCount >= i) {
                while (i > 0) {
                    cash.add(cell.getBanknote());
                    //cell.remove();
                    amount -= cell.getBanknote().getValue();
                    i--;
                }
            } else {
                while (tempCellCount > 0) {
                    cash.add(cell.getBanknote());
                    //cell.remove();
                    amount -= cell.getBanknote().getValue();
                    tempCellCount--;
                }
            }
        return amount;
    }

    private boolean isValidAmount(long amount) {
        return (amount > 0 && (amount % Banknote.FIFTY.getValue() == 0
                || amount % Banknote.ONE_HUNDRED.getValue() == 0
                || amount % Banknote.TWO_HUNDRED.getValue() == 0
                || amount % Banknote.FIVE_HUNDRED.getValue() == 0
                || amount % Banknote.ONE_THOUSAND.getValue() == 0
                || amount % Banknote.TWO_THOUSAND.getValue() == 0
                || amount % Banknote.FIVE_THOUSAND.getValue() == 0));
    }

    @Override
    public long showBalance() {
        return save.getBalance();
    }
}
