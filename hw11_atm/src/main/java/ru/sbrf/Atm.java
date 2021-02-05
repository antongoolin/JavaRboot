package ru.sbrf;

import java.util.List;

public interface Atm {
    //принимать банкноты разных номиналов (на каждый номинал должна быть своя ячейка)
    void acceptMoney(List<Banknote> amount);

    //выдавать запрошенную сумму минимальным количеством банкнот или ошибку если сумму нельзя выдать
    List<Banknote> withdrawMoney(long amount);

    //выдавать сумму остатка денежных средств
    long showBalance();
}
