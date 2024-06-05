package collectionManagers;

import collectionClasses.Movie;
import collectionSupport.IdGenerator;
import collectionSupport.LocalDateAdapter;
import collectionSupport.Validator;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import utils.ServerLogger;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;


/**
 * the {@code StorageManager} class управляет хранением коллекции
 */
public class StorageManager {
    /**
     * Имя файла
     */
    String fileName;
    /**
     * Конструктор класса
     //* @param fileName fileName
     */
    public StorageManager(String fileName) {
        this.fileName = fileName;
    }


    public void setFilename(String filename) {
        this.fileName = filename;
    }
    public String getFilename(){
        return this.fileName;
    }


    /**
     * Записывает коллекцию в файл.
     */
    public void writeCollection() {
        HashSet<Movie> movies = CollectionManager.getMovies();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        Gson gson = gsonBuilder.create();
        String data = gson.toJson(movies);
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))){
            bufferedWriter.write(data);
            ServerLogger.getLogger().info("Коллекция сохранена в файл.");
        }catch (IOException e){
            if (!(new File(fileName)).canWrite()){
                ServerLogger.getLogger().warning("Нет прав на запись в файл");
            } else {
                ServerLogger.getLogger().warning("Что-то пошло не так. Данные не сохранены. ");
            }
        }
    }

    /**
     * Считывает коллекцию из файла.
     * @return HashSet<Movie>
     */
    public HashSet<Movie> readCollection() throws JsonSyntaxException, JsonIOException{
        HashSet<Movie> movies = new HashSet<>();
        ArrayList<Movie> buffer;
        Validator validator = new Validator();
        IdGenerator idGenerator = validator.getIdGenerator();
        try{
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            Type itemsArrayType = new TypeToken<ArrayList<Movie>>() {}.getType();
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
            Gson gson = gsonBuilder.create();
            String data = "";
            while (scanner.hasNextLine()){
                data = data.concat(scanner.nextLine());
            }
            buffer = gson.fromJson(data, itemsArrayType);
            for (Movie musicBand : buffer){
                Movie validatedMovie = validator.getValidatedElement(musicBand);
                if (!(validatedMovie == null) && !(idGenerator.getGeneratedIds().contains(validatedMovie.getId()))){
                    movies.add(validatedMovie);
                    idGenerator.addId(validatedMovie);
                }else{
                    ServerLogger.getLogger().warning("Пропущен некорректный элемент. ");
                }
            }
            return movies;
        }catch (JsonIOException | NullPointerException | JsonSyntaxException e){
            ServerLogger.getLogger().warning("Что-то не так с файлом или он пуст. Коллекция не содержит элементов. ");
            return movies;
        } catch (FileNotFoundException e) {
            if (!(new File(fileName).canRead())){
                ServerLogger.getLogger().warning("Нет прав на чтение данного файла.");
            } else {
                ServerLogger.getLogger().warning("Данный файл не найден.");
            }
            return movies;
        }
    }
}




