package task8;


import java.util.HashSet;
import java.util.Set;

public class PowerfulSet {

    /**
     * Возвращает пересечение двух наборов (общие элементы)
     * @param set1 первый набор
     * @param set2 второй набор
     * @return набор, содержащий элементы, присутствующие в обоих наборах
     */
    public <T> Set<T> intersection(Set<T> set1, Set<T> set2) {
        Set<T> result = new HashSet<>(set1);
        result.retainAll(set2);
        return result;
    }

    /**
     * Возвращает объединение двух наборов (все уникальные элементы из обоих наборов)
     * @param set1 первый набор
     * @param set2 второй набор
     * @return набор, содержащий все элементы из обоих наборов без дубликатов
     */
    public <T> Set<T> union(Set<T> set1, Set<T> set2) {
        Set<T> result = new HashSet<>(set1);
        result.addAll(set2);
        return result;
    }

    /**
     * Возвращает относительное дополнение (элементы первого набора без общих со вторым)
     * @param set1 первый набор (из которого вычитаем)
     * @param set2 второй набор (который вычитаем)
     * @return набор, содержащий элементы из set1, которых нет в set2
     */
    public <T> Set<T> relativeComplement(Set<T> set1, Set<T> set2) {
        Set<T> result = new HashSet<>(set1);
        result.removeAll(set2);
        return result;
    }



    // Тестирование
    public static void main(String[] args) {
        PowerfulSet powerfulSet = new PowerfulSet();

        // Тестовые данные
        Set<Integer> set1 = Set.of(1, 2, 3);
        Set<Integer> set2 = Set.of(0, 1, 2, 4);

        System.out.println("Set1: " + set1);
        System.out.println("Set2: " + set2);
        System.out.println();

        // Тестирование пересечения
        Set<Integer> intersection = powerfulSet.intersection(set1, set2);
        System.out.println("Пересечение: " + intersection);
        System.out.println("Ожидаемый результат: [1, 2]");
        System.out.println();

        // Тестирование объединения
        Set<Integer> union = powerfulSet.union(set1, set2);
        System.out.println("Объединение: " + union);
        System.out.println("Ожидаемый результат: [0, 1, 2, 3, 4]");
        System.out.println();

        // Тестирование относительного дополнения
        Set<Integer> relativeComplement = powerfulSet.relativeComplement(set1, set2);
        System.out.println("Относительное дополнение (set1 \\ set2): " + relativeComplement);
        System.out.println("Ожидаемый результат: [3]");
        System.out.println();


    }
}
