package repository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileBackedTasksManager extends InMemoryTasksManager implements TaskManager{

    private File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public void save() {

    }

    private static void loadFromFile(File file) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            while(fileReader.ready()) {
                String s = fileReader.readLine();
                while (!s.isBlank()) {

                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Произошла ошибка во время чтения файла.");
        } catch (IOException e) {
            System.out.println("Произошла ошибка во время чтения файла.");
        }
    }
}
