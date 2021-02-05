package ru.sbrf;

public class Triangle implements Calculator {
    private int a;
    private int b;
    private int c;

    public Triangle(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public boolean isTriangleRight() {
        return (a + b) > c && (a + c) > b && (b + c) > a;
    }

    @Override
    public String getName() {
        return "треугольник";
    }

    @Override
    public double getSquare() {
        double p = (getPerimeter() + .0) / 2;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

    @Override
    public double getPerimeter() {
        return (double) a + b + c;
    }
}
