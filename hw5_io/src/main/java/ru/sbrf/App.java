package ru.sbrf;

import lombok.var;

import java.io.*;

/**
 * Перенос "теста" ReadFromFile на чтение из консоли и записи в файл
 *
 */
public class App {
    public static void main(String[] args) throws IOException {

        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter out = new BufferedWriter(new FileWriter("homework5_IO/tmp/lorem_new.txt"))) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) {

                if (inputLine.equalsIgnoreCase("end")) {
                    System.out.print("Write Successful");
                    break;
                }

                out.write(inputLine);
                out.newLine();

            }
        }
    }
}