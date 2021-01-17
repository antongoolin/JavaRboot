package ru.sbrf;

import lombok.experimental.var;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@DisplayName("Нужно поработать с содержимым файлов")
class AppTest {
    @DisplayName("вывести на консоль lorem.txt")
    @Test
    public void readFileContext() throws IOException {
        // TODO 2
        File file = new File("tmp", "lorem.txt");
        if (file.exists()){
            try (var bufferedReader = new BufferedReader(new FileReader(file))) {
                System.out.println("text from the file" + file + ":");
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                }
            }
        }
        else { System.out.println("there isn't file " + file);}
    }

    @DisplayName("ввести строки в консоле и сохранить строчки одну за одной в файле")
    @Test
    public void writeFileContext() throws IOException {
        // TODO 2
        /*
        Не получается читать из консаоли в test, ухожу в main
         */


    }
}
