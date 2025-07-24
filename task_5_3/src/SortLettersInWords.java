import java.util.Arrays;
import java.util.Scanner;

public class SortLettersInWords {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();

        String[] words = input.split(" ");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            char[] letters = word.toLowerCase().toCharArray();
            Arrays.sort(letters);
            result.append(new String(letters)).append(" ");
        }

        System.out.println(result.toString().trim());
    }
}