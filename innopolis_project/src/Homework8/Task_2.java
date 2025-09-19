package Homework8;

import java.util.Arrays;

public class Task_2 {
    public static boolean isRussianAnagram(String s, String t) {
        // Приводим к нижнему регистру и удаляем все не-буквы
        s = s.toLowerCase().replaceAll("[^а-яё]", "");
        t = t.toLowerCase().replaceAll("[^а-яё]", "");

        if (s.length() != t.length()) {
            return false;
        }

        char[] sArray = s.toCharArray();
        char[] tArray = t.toCharArray();

        Arrays.sort(sArray);
        Arrays.sort(tArray);

        return Arrays.equals(sArray, tArray);
    }

    public static void main(String[] args) {
        String[][] russianExamples = {
                {"Бейсбол", "бобслей"},
                {"Героин", "регион"},
                {"Клоака", "околка"}
        };

        for (String[] pair : russianExamples) {
            boolean result = isRussianAnagram(pair[0], pair[1]);
            System.out.printf("%s - %s: %s%n", pair[0], pair[1], result);
        }
    }
}
