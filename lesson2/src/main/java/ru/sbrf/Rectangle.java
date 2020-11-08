package ru.sbrf;

    public class Rectangle implements Calculator {
        private int a;
        private int b;

        public Rectangle(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public double getSquare() {
            return a * b;
        }

        @Override
        public String getName() {
            return "прямоугольник";
        }

        @Override
        public double getPerimeter() {
            return 2 * (a + b);
        }
}
