package task6;

import java.util.*;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Чтение данных о покупателях
        System.out.println("Введите список покупателей:");
        String personsInput = scanner.nextLine();
        Map<String, Person> persons = parsePersons(personsInput);

        // Чтение данных о продуктах
        System.out.println("Введите список продуктов:");
        String productsInput = scanner.nextLine();
        Map<String, Product> products = parseProducts(productsInput);

        // Обработка покупок
        System.out.println("Введите покупки (формат: Имя - Продукт, END для завершения):");
        while (true) {
            String purchase = scanner.nextLine();
            if (purchase.equals("END")) {
                break;
            }

            String[] parts = purchase.split(" - ");
            if (parts.length != 2) {
                System.out.println("Неверный формат покупки: " + purchase);
                continue;
            }

            String personName = parts[0].trim();
            String productName = parts[1].trim();

            if (!persons.containsKey(personName)) {
                System.out.println("Покупатель не найден: " + personName);
                continue;
            }

            if (!products.containsKey(productName)) {
                System.out.println("Продукт не найден: " + productName);
                continue;
            }

            Person person = persons.get(personName);
            Product product = products.get(productName);

            if (person.canAfford(product)) {
                person.buyProduct(product);
            } else {
                System.out.println(personName + " не может позволить себе " + productName);
            }
        }

        // Вывод результатов
        System.out.println("\nРезультаты покупок:");
        for (Person person : persons.values()) {
            System.out.println(person);
        }

        scanner.close();
    }

    private static Map<String, Person> parsePersons(String input) {
        Map<String, Person> persons = new HashMap<>();
        String[] entries = input.split("; ");

        for (String entry : entries) {
            String[] parts = entry.split(" = ");
            if (parts.length != 2) {
                System.out.println("Неверный формат записи покупателя: " + entry);
                continue;
            }

            try {
                String name = parts[0].trim();
                int money = Integer.parseInt(parts[1].trim());
                persons.put(name, new Person(name, money));
            } catch (NumberFormatException e) {
                System.out.println("Неверный формат денег: " + entry);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + ": " + entry);
            }
        }

        return persons;
    }

    private static Map<String, Product> parseProducts(String input) {
        Map<String, Product> products = new HashMap<>();
        String[] entries = input.split("; ");

        for (String entry : entries) {
            String[] parts = entry.split(" = ");
            if (parts.length != 2) {
                System.out.println("Неверный формат записи продукта: " + entry);
                continue;
            }

            try {
                String name = parts[0].trim();
                int price = Integer.parseInt(parts[1].trim());
                products.put(name, new Product(name, price));
            } catch (NumberFormatException e) {
                System.out.println("Неверный формат цены: " + entry);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + ": " + entry);
            }
        }

        return products;
    }
}
