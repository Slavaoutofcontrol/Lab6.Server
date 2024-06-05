import collectionClasses.Movie;
import collectionManagers.CollectionManager;
import collectionManagers.StorageManager;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import utils.Server;

import java.util.HashSet;
import java.util.Scanner;

public class Main {
    private final static Integer serverPort = 23548;


    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Пожалуйста, введите путь к файлу с коллекцией:");
        String filePath = scanner.nextLine();

        HashSet<Movie> collection;
        try {
            StorageManager storageManager = new StorageManager(filePath);
            storageManager.setFilename(filePath);
            collection = storageManager.readCollection();
        } catch (JsonIOException | JsonSyntaxException e) {
            System.err.println("Не удалось прочитать коллекцию из файла: " + e.getMessage());
            System.err.println("Создана новая коллекция.");
            collection = new HashSet<>();
        }

        CollectionManager collectionManager = new CollectionManager();
        collectionManager.setCollection(collection);
        StorageManager storageManager = new StorageManager(filePath);
        Server server = new Server(collectionManager, serverPort, storageManager);
        server.run();
    }
}