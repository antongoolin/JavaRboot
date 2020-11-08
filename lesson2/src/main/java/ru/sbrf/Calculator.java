package ru.sbrf;

public interface Calculator {

    String getName();
    double getSquare();
    double getPerimeter();

    default void printResults() {
        System.out.println("Периметр " + getName() + "a = " + getPerimeter());
        System.out.println("Площадь " + getName() + "a = " + getSquare());
    }

}
