import java.util.Scanner;

public class KeyboardLetters {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the letter in lowercase");
        String input = scanner.nextLine().trim();
        char c = input.charAt(0);

        // Замкнутая последовательность букв на клавиатуре
        String keyboard = "qwertyuiopasdfghjklzxcvbnm";

        int index = keyboard.indexOf(c);
        if (index == -1) {
            System.out.println("Invalid input");
            return;
        }

        int leftIndex = (index - 1 + keyboard.length()) % keyboard.length();
        char leftChar = keyboard.charAt(leftIndex);

        System.out.println(leftChar);
    }
}