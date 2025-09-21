package Homework9;

import java.util.*;
import java.util.stream.Collectors;

// Главный класс
public class Main {
    public static void main(String[] args) {
        // Создаем список автомобилей
        List<Автомобиль> автомобили = Arrays.asList(
                new Автомобиль("a123me", "Mercedes", "White", 0, 8300000),
                new Автомобиль("b873of", "Volga", "Black", 0, 673000),
                new Автомобиль("w487mn", "Lexus", "Grey", 76000, 900000),
                new Автомобиль("p987hj", "Volga", "Red", 610, 704340),
                new Автомобиль("c987ss", "Toyota", "White", 254000, 761000),
                new Автомобиль("o983op", "Toyota","Black",698000,740000),
                new Автомобиль("p146op","BMW","White",271000,850000),
                new Автомобиль( "u893ii", "Toyota","Purple",210900,440000),
                new Автомобиль("l097df","Toyota","Black",108000,780000),
                new Автомобиль("y876wd","Toyota","Black",160000,1000000)
        );

        // Заданные параметры для поиска
        String colorToFind = "Black";
        int mileageToFind = 0;
        double n = 700000; // нижняя граница цены
        double m = 800000; // верхняя граница цены
        String modelToFind = "Toyota";

        System.out.println("=== Все автомобили ===");
        автомобили.forEach(System.out::println);
        System.out.println();

        // 1) Номера всех автомобилей, имеющих заданный цвет или нулевой пробег
        System.out.println("1) Автомобили с цветом '" + colorToFind + "' или пробегом " + mileageToFind + ":");
        List<String> номера = автомобили.stream()
                .filter(авто -> авто.getЦвет().equals(colorToFind) || авто.getПробег() == mileageToFind)
                .map(Автомобиль::getНомер)
                .collect(Collectors.toList());
        System.out.println("Номера: " + номера);
        System.out.println();

        // 2) Количество уникальных моделей в ценовом диапазоне от n до m тыс.
        System.out.println("2) Уникальные модели в ценовом диапазоне от " + n/1000 + " до " + m/1000 + " тыс.:");
        long количествоУникальныхМоделей = автомобили.stream()
                .filter(авто -> авто.getСтоимость() >= n && авто.getСтоимость() <= m)
                .map(Автомобиль::getМодель)
                .distinct()
                .count();
        System.out.println("Количество уникальных моделей: " + количествоУникальныхМоделей);
        System.out.println();

        // 3) Цвет автомобиля с минимальной стоимостью
        System.out.println("3) Цвет автомобиля с минимальной стоимостью:");
        Optional<String> цветМинимальнойСтоимости = автомобили.stream()
                .min(Comparator.comparingDouble(Автомобиль::getСтоимость))
                .map(Автомобиль::getЦвет);
        цветМинимальнойСтоимости.ifPresent(цвет ->
                System.out.println("Цвет: " + цвет));
        System.out.println();

        // 4) Средняя стоимость искомой модели
        System.out.println("4) Средняя стоимость модели '" + modelToFind + "':");
        OptionalDouble средняяСтоимость = автомобили.stream()
                .filter(авто -> авто.getМодель().equals(modelToFind))
                .mapToDouble(Автомобиль::getСтоимость)
                .average();

        if (средняяСтоимость.isPresent()) {
            System.out.println("Средняя стоимость: " + средняяСтоимость.getAsDouble());
        } else {
            System.out.println("Модель '" + modelToFind + "' не найдена");
        }
    }
}
