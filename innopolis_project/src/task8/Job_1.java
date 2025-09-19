package task8;
import java.util.ArrayList;
import java.util.Set;

import java.util.HashSet;

public class Job_1 {

    public static <T> Set<T> getUniqueElements(ArrayList<T> list) {
        if (list == null) {
            return new HashSet<>();
        }
        return new HashSet<>(list);
    }

    // Тестирование
    public static void main(String[] args) {
        // Тест с целыми числами
        ArrayList<Integer> intList = new ArrayList<>();
        intList.add(1);
        intList.add(2);
        intList.add(3);
        intList.add(2);
        intList.add(1);

        Set<Integer> uniqueInts = getUniqueElements(intList);
        System.out.println("Целые числа: " + intList);
        System.out.println("Уникальные: " + uniqueInts);

        // Тест со строками
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("Java");
        stringList.add("Python");
        stringList.add("Java");
        stringList.add("C++");

        Set<String> uniqueStrings = getUniqueElements(stringList);
        System.out.println("\nСтроки: " + stringList);
        System.out.println("Уникальные: " + uniqueStrings);

        // Тест с пустым списком
        ArrayList<String> emptyList = new ArrayList<>();
        Set<String> emptyUnique = getUniqueElements(emptyList);
        System.out.println("\nПустой список: " + emptyList);
        System.out.println("Уникальные: " + emptyUnique);
    }
}
