package ru.sbrf;

public class Square implements Calculator {
    private int a;

    public Square(int a) {
        this.a = a;
    }

    @Override
    public String getName() {
        return "квадрат";
    }

    @Override
    public double getSquare() {
        return a * a;
    }

    @Override
    public double getPerimeter() {
        return 4 * a;
    }
}