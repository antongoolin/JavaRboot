import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/*
ДЗ •
На существующем проекте сделать программу, которая будет запрашивать у пользователя тип фигуры
(треугольник, квадрат, прямоугольник), нужные параметры и считать периметр и площадь.
 */
public class App {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        try (Scanner in = new Scanner(System.in)) {
            boolean scannerClosed = false;
            while (!scannerClosed) {

                System.out.println("Выбери фигуру (треугольник - Т, прямоугольник - П, квадрат - К), " +
                        "чтобы выйти из программы нажми - X:");
                String chosenFigure = in.next();

                try {
                    switch (chosenFigure.toLowerCase()) {
                        case "к":
                        case "k":
                        case "квадрат":
                            System.out.println("Введите длину строны:");
                            list.add(in.nextInt());
                            break;
                        case "п":
                        case "прямоугольник":
                            System.out.println("Введите длину строны 1:");
                            list.add(in.nextInt());
                            System.out.println("Введите длину строны 2:");
                            list.add(in.nextInt());
                            break;
                        case "т":
                        case "t":
                        case "треугольник":
                            System.out.println("Введите длину строны 1:");
                            list.add(in.nextInt());
                            System.out.println("Введите длину строны 2:");
                            list.add(in.nextInt());
                            System.out.println("Введите длину строны 3:");
                            list.add(in.nextInt());
                            break;
                        case "x":
                        case "х":
                            System.out.println("|||| ... Закрытие сессии ... |||");
                            in.close();
                            scannerClosed = true;
                            break;
                        default:
                            System.out.println("¯\\_(ツ)_/¯ Я такое пока считать не умею, выбери фигуру из предложенных ранее");
                    }

                } catch (InputMismatchException ex) {
                    System.out.println("¯\\_(ツ)_/¯ Для ввода длины доступен только ввод чисел");
                    list.clear();
                    in.next();
                    continue;
                }

                if (list.stream().anyMatch(a -> a <= 0)) {
                    System.out.println("¯\\_(ツ)_/¯ Длина стороны должна быть больше нуля. Фигура невалидна.");
                } else {
                    switch (list.size()) {
                        case 1:
                            Square square = new Square(list.get(0));
                            square.printResults();
                            break;
                        case 2:
                            Rectangle rectangle = new Rectangle(list.get(0), list.get(1));
                            rectangle.printResults();
                            break;
                        case 3:
                            Triangle triangle = new Triangle(list.get(0), list.get(1), list.get(2));
                            if (triangle.isTriangleRight())
                                triangle.printResults();
                            else
                                System.out.println("¯\\_(ツ)_/¯ Треугольник невалидный, проверь введенные стороны.");
                    }
                }
                list.clear();
            }
        }

    }

}