package ru.sbrf;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AtmTest {

    @Test
    @DisplayName("Проверить внесение наличных")
    public void testAcceptMoney() {
        Save save = new Save();
        Atm atm = new AtmImpl(save);
        long balance = atm.showBalance();
        System.out.println(balance);

        List<Banknote> amount = new ArrayList<>();
        amount.add(Banknote.FIFTY);
        amount.add(Banknote.ONE_HUNDRED);
        amount.add(Banknote.ONE_THOUSAND);

        int expectedCountCell50 = save.getCell_50().getCount() + 1;
        int expectedCountCell100 = save.getCell_100().getCount() + 1;
        int expectedCountCell1000 = save.getCell_1_000().getCount() + 1;

        atm.acceptMoney(amount);

        assertEquals(atm.showBalance(), balance + 1150);
        assertEquals(expectedCountCell50, save.getCell_50().getCount());
        assertEquals(expectedCountCell100, save.getCell_100().getCount());
        assertEquals(expectedCountCell1000, save.getCell_1_000().getCount());
    }

    @Test
    @DisplayName("Проверить внесение фейковой купюры")
    public void testAcceptFakeMoney() {
        Atm atm = new AtmImpl(new Save());

        List<Banknote> amount = new ArrayList<>();
        amount.add(Banknote.TWO_HUNDRED);
        amount.add(Banknote.FAKE);

        assertThrows(AtmException.class, () -> atm.acceptMoney(amount), "Проверка на неизвестную купюру");
    }

    @Test
    @DisplayName("Снять наличные и проверить баланса")
    public void testWithdrawMoneySuccess() {
        Atm atm = new AtmImpl(new Save());
        long beforeBalance = atm.showBalance();
        List<Banknote> expectedCash = new ArrayList<>();
        expectedCash.add(Banknote.FIVE_THOUSAND);
        expectedCash.add(Banknote.FIVE_THOUSAND);
        expectedCash.add(Banknote.FIVE_THOUSAND);
        expectedCash.add(Banknote.TWO_HUNDRED);
        expectedCash.add(Banknote.TWO_HUNDRED);
        expectedCash.add(Banknote.FIFTY);

        List<Banknote> cash = atm.withdrawMoney(15450);
        assertEquals(atm.showBalance(), beforeBalance - 15450, "Проверка баланса");
        assertEquals(expectedCash, cash, "Проверка купюр");
    }

    @Test
    @DisplayName("Снять больше наличных, чем в АТМ, баланс АТМ не изменился")
    public void testWithdrawMoneyMoreThanAtmHas() {
        Save save = new Save();
        save.getCell_50().setCount(1);
        save.getCell_100().setCount(1);
        save.getCell_200().setCount(1);
        save.getCell_500().setCount(1);
        save.getCell_1_000().setCount(0);
        save.getCell_2_000().setCount(0);
        save.getCell_5_000().setCount(0);

        Atm atm = new AtmImpl(save);
        long beforeBalance = atm.showBalance();
        assertThrows(AtmException.class, () -> atm.withdrawMoney(6700));
        assertEquals(beforeBalance, atm.showBalance(), "Проверка баланса");
    }

    @Test
    @DisplayName("Снять сумму не кратную 50, 100, 200, 500, 1000, 2000, 5000, баланс АТМ не изменился")
    public void testWithdrawMoneyIncorrectAmount() {
        Atm atm = new AtmImpl(new Save());
        long beforeBalance = atm.showBalance();
        assertThrows(AtmException.class, () -> atm.withdrawMoney(6330));
        assertEquals(beforeBalance, atm.showBalance(), "Проверка баланса");
    }

    @Test
    @DisplayName("Снять сумму меньше нуля")
    public void testWithdrawMoneyNegativeAmount() {
        Atm atm = new AtmImpl(new Save());
        long beforeBalance = atm.showBalance();
        assertThrows(AtmException.class, () -> atm.withdrawMoney(-100));
        assertEquals(beforeBalance, atm.showBalance(), "Проверка баланса");
    }

    @Test
    @DisplayName("Снять сумму кратную 50, если закончились купюры 50")
    public void testWithdrawMoneyFinishedBanknote50() {
        Save save = new Save();
        save.getCell_50().setCount(0);
        Atm atm = new AtmImpl(save);
        long beforeBalance = atm.showBalance();
        assertThrows(AtmException.class, () -> atm.withdrawMoney(1350));
        assertEquals(beforeBalance, atm.showBalance(), "Проверка баланса");
    }

    @Test
    @DisplayName("Негативный сценарий. Снять сумму кратную 100, если закончились купюры 100")
    public void testWithdrawMoneyNegativeFinishedBanknote100() {
        Save save = new Save();
        save.getCell_100().setCount(0);
        save.getCell_200().setCount(1);
        save.getCell_50().setCount(2);
        save.getCell_500().setCount(0);
        Atm atm = new AtmImpl(save);
        long beforeBalance = atm.showBalance();
        assertThrows(AtmException.class, () -> atm.withdrawMoney(700));
        assertEquals(beforeBalance, atm.showBalance(), "Проверка баланса");
    }

    @Test
    @DisplayName("Позитивный сценарий. Снять сумму кратную 100, если закончились купюры 100")
    public void testWithdrawMoneyPositiveFinishedBanknote100() {
        Save save = new Save();
        save.getCell_100().setCount(0);
        save.getCell_200().setCount(3);
        save.getCell_50().setCount(2);
        save.getCell_500().setCount(0);
        Atm atm = new AtmImpl(save);
        long beforeBalance = atm.showBalance();

        List<Banknote> expectedCash = new ArrayList<>();
        expectedCash.add(Banknote.TWO_HUNDRED);
        expectedCash.add(Banknote.TWO_HUNDRED);
        expectedCash.add(Banknote.TWO_HUNDRED);
        expectedCash.add(Banknote.FIFTY);
        expectedCash.add(Banknote.FIFTY);

        assertEquals(expectedCash, atm.withdrawMoney(700), "Проверка купюр");
        assertEquals(beforeBalance - 700, atm.showBalance(), "Проверка баланса");

    }



}
