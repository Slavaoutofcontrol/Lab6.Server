package collectionManagers;


import collectionClasses.Movie;
import collectionClasses.MovieGenre;
import collectionClasses.MpaaRating;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * The {@code CollectionManager} class управляет коллекцией
 *

 */
public class CollectionManager implements Serializable {
    /**
     * Главная коллекция фильмов
     */
    private static HashSet<Movie> movies = new HashSet<>();
    /**
     * Дата инициализации коллекции
     */
    private static LocalDate localDate = LocalDate.now();

    /**
     * Название файл, содержащий коллекцию
     */
    private String filename;


    /**
     * Конструктор для класса
     */
    public CollectionManager() {
    }

    /**
     * Устанавливает главную коллекцию
     * @param movies movies
     */
    public void setCollection(HashSet<Movie> movies) {
        this.movies = movies;
    }

    /**
     * Устанавливает файл, содержащий коллекцию
     * @param filename filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Возвращает главную коллекцию
     * @return HashSet</Movie>
     */
    public static HashSet<Movie> getMovies() {
        return movies;
    }

    /**
     * Выводит информацию о коллекции
     */
    public static String info() {
         return  "Тип коллекции: " + movies.getClass().getSimpleName() + "\nДата инициализации: " + localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "\nКоличество элементов: " + movies.size();
    }

    /**
     * Полностью выводит коллекцию
     */
    public static String show() {
        StringBuilder result = new StringBuilder();
        if (!movies.isEmpty()) {
            movies.forEach(movie -> result.append(movie.toString()));
            return result.toString();
        } else {
           return "Коллекция не содержит элементов";
        }
    }

    /**
     * Добавляет элемент в коллекцию
     * @param movie movie
     */
    public static void add(Movie movie) {
        movies.add(movie);
    }

    /**
     * Обновляет элемент по переданному идентификатору
     * @param newMovie newMovie
     */
    public static String updateId(Movie newMovie) {
        boolean flag = movies.stream()
                .filter(movie -> movie.getId() == newMovie.getId())
                .findFirst()
                .map(movie -> {
                    movie.setName(newMovie.getName());
                    movie.setCoordinates(newMovie.getCoordinates());
                    movie.setOscarsCount(newMovie.getOscarsCount());
                    movie.setGenre(newMovie.getGenre());
                    movie.setMpaaRating(newMovie.getMpaaRating());
                    movie.setDirector(newMovie.getDirector());
                    return movie;
                })
                .isPresent();
        return !flag ? "Элемента с таким id нет в коллекции" : "Элемент успешно обновлен";
    }
    /**
     * Удаляет элемент по идентификатору
     * @param id id
     */
    public static String removeById(long id) {
        boolean flag = false;
        for (Movie movie : movies){
            if (movie.getId() == id){
                flag = true;
                movies.remove(movie);
                break;
            }
        }
        if (!flag){
            return "Элемента с таким id нет в коллекции";
        } else {
            return "Элемент удален из коллекции";
        }
    }

    /**
     * Очищает коллекцию
     */
    public static void clear() {
        movies.clear();
    }



    /**
     * Добавляет элемент, если он наибольший
     * @param newMovie newMovie
     */
    public static String addIfMax(Movie newMovie) {
        int maxOscarCount = movies.stream()
                .mapToInt(Movie::getOscarsCount)
                .max()
                .getAsInt();
        if (newMovie.getOscarsCount() > maxOscarCount) {
            add(newMovie);
            return "Элемент успешно добавлен в коллекцию";
        } else {
            return "Элемент не добавлен в коллекцию (не наибольший)";
        }
    }

    /**
     * Добавляет элемент, если он наименьший
     * @param newMovie newMovie
     */
    public static String addIfMin(Movie newMovie) {
        int minOscarCount = movies.stream()
                .mapToInt(Movie::getOscarsCount)
                .max()
                .getAsInt();
        if (newMovie.getOscarsCount() < minOscarCount) {
            add(newMovie);
            return "Элемент успешно добавлен в коллекцию";
        } else {
            return "Элемент не добавлен в коллекцию (не наибольший)";
        }
    }

    /**
     * Удаляет все элементы, больше переданного
     * @param newMovie newMovie
     */
    public static String removeGreater(Movie newMovie) {
        movies.removeIf(movie -> movie.getOscarsCount() > newMovie.getOscarsCount());
        movies.add(newMovie);
        return "Элементы,больше чем заданный, удалены";
    }

    /**
     * Группирует элементы коллекции по режиссеру
     */
    public static String  groupCountingByDirector() {
        Map<String, List<Movie>> groupedByDirector = new HashMap<>();
        for (Movie movie : movies) {
            groupedByDirector.computeIfAbsent(String.valueOf(movie.getDirector()), k -> new ArrayList<>()).add(movie);
        }

        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, List<Movie>> entry : groupedByDirector.entrySet()) {
            result.append("Режиссер: ").append(entry.getKey()).append("\n");
            for (Movie movie : entry.getValue()) {
                result.append("   - ").append(movie.getName()).append("\n");
            }
        }

        return result.toString();
    }

    /**
     * Выводит элементы коллекции, значение поля MpaaRating которых меньше переданного значения
     * @param mpaaRating mpaaRating
     */
    public static  String countLessThanMpaaRating(MpaaRating mpaaRating) {
        Map<MpaaRating, Integer> ratingCount = new HashMap<>();
        for (Movie movie : movies) {
            ratingCount.put(MpaaRating.valueOf(String.valueOf(movie.getMpaaRating())), ratingCount.getOrDefault(movie.getMpaaRating(), 0) + 1);
        }
        int i = ratingCount.get(mpaaRating);
        int result = 0;
        for (MpaaRating key: ratingCount.keySet())
        {
            if(key == mpaaRating){
                continue;
            }
            if(i > ratingCount.get(key)){
                result += ratingCount.get(key);
            }
        }
        return "Количество элементов, значение рейтинга которых меньше рейтинга " + mpaaRating +  " : " + result;
    }

    /**
     * Выводит элементы коллекции, значение поля Genre которых больше переданного значения
     * @param genre genre
     */
    public static String countGreaterThanGenre(MovieGenre genre) {
        Map<MovieGenre, Integer> genreCount = new HashMap<>();
        for (Movie movie : movies) {
           genreCount.put(MovieGenre.valueOf(String.valueOf(movie.getGenre())), genreCount.getOrDefault(movie.getGenre(), 0) + 1);
        }
        int i = genreCount.get(genre);
        int result = 0;
        for (MovieGenre key: genreCount.keySet())
        {
            if(key == genre){
                continue;
            }
            if(i < genreCount.get(key)){
                result += genreCount.get(key);
            }
        }
        return "Количество элементов, значение жанра которых больше жанра " + genre +  " : " + result;
    }
    public static boolean isContainsId(long id) {
        HashSet<Long> ids = new HashSet<>();
        movies.forEach(movie -> ids.add(movie.getId()));
        return (ids.contains(id));
    }
}

