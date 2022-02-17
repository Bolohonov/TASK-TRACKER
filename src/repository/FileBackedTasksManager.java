package repository;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileBackedTasksManager extends InMemoryTasksManager implements TaskManager{

    private File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public FileBackedTasksManager() {
    }

    public void save() {

    }

    private static List<String> loadFromFile() {
        InMemoryTasksManager.singleTaskRepository
        List<String> words = new ArrayList<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(filename, StandardCharsets.UTF_8))) {
            while(fileReader.ready()) {
                String s = fileReader.readLine();
                words.add(s);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Произошла ошибка во время чтения файла.");
        } catch (IOException e) {
            System.out.println("Произошла ошибка во время чтения файла.");
        }
        return words;
    }
}
