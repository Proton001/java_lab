
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

//Задача 1. Составить программу вывода на экран в одну строку сообщения
//«Привет, имя_пользователя», где «имя_пользователя» - это введёное в консоль
//имя, для выполнения данного задания нужно использовать класс Scanner.
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {

        // Создаем объект Scanner для чтения ввода с консоли
        Scanner scanner = new Scanner(System.in);

        // Запрашиваем имя пользователя
        System.out.print("Введите ваше имя: ");
        String userName = scanner.nextLine();

        // Выводим приветственное сообщение
        System.out.println("Привет, " + userName);

        // Закрываем Scanner
        scanner.close();
    }
}