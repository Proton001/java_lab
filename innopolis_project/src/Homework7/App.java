package Homework7;

import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        Map<String, BaseProduct> products = parseProducts(productsInput);

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
            BaseProduct product = products.get(productName);

            if (person.canAfford(product)) {
                person.buyProduct(product);
                System.out.println(personName + " купил(а) " + productName + " за " + product.getPrice() + " руб.");
            } else {
                System.out.println(personName + " не может позволить себе " + productName);
            }
        }

        // Вывод результатов
        System.out.println("\nРезультаты покупок:");
        for (Person person : persons.values()) {
            System.out.println(person);
        }

        // Вывод информации о продуктах
        System.out.println("\nИнформация о продуктах:");
        for (BaseProduct product : products.values()) {
            System.out.println(product);
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

    private static Map<String, BaseProduct> parseProducts(String input) {
        Map<String, BaseProduct> products = new HashMap<>();
        String[] entries = input.split("; ");

        for (String entry : entries) {
            try {
                if (entry.contains("(") && entry.contains(")")) {
                    // Обработка скидочного продукта
                    products.putAll(parseDiscountProduct(entry));
                } else {
                    // Обработка обычного продукта
                    products.putAll(parseRegularProduct(entry));
                }
            } catch (Exception e) {
                System.out.println("Ошибка при обработке продукта '" + entry + "': " + e.getMessage());
            }
        }

        return products;
    }

    private static Map<String, BaseProduct> parseRegularProduct(String entry) {
        Map<String, BaseProduct> result = new HashMap<>();
        String[] parts = entry.split(" = ");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Неверный формат записи продукта");
        }

        String name = parts[0].trim();
        int price = Integer.parseInt(parts[1].trim());
        result.put(name, new Product(name, price));

        return result;
    }

    private static Map<String, BaseProduct> parseDiscountProduct(String entry) {
        Map<String, BaseProduct> result = new HashMap<>();

        // Формат: Название = Цена (скидка, дата_окончания)
        String[] mainParts = entry.split(" = ");
        if (mainParts.length != 2) {
            throw new IllegalArgumentException("Неверный формат записи скидочного продукта");
        }

        String name = mainParts[0].trim();
        String rest = mainParts[1].trim();

        // Извлечение базовой цены и информации о скидке
        int basePriceEndIndex = rest.indexOf('(');
        if (basePriceEndIndex == -1) {
            throw new IllegalArgumentException("Неверный формат скидочного продукта");
        }

        int basePrice = Integer.parseInt(rest.substring(0, basePriceEndIndex).trim());
        String discountInfo = rest.substring(basePriceEndIndex + 1, rest.indexOf(')')).trim();

        String[] discountParts = discountInfo.split(",");
        if (discountParts.length != 2) {
            throw new IllegalArgumentException("Неверный формат информации о скидке");
        }

        int discount = Integer.parseInt(discountParts[0].trim());
        LocalDate endDate = LocalDate.parse(discountParts[1].trim(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        result.put(name, new DiscountProduct(name, basePrice, discount, endDate));

        return result;
    }
}
