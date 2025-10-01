package com.example.dungeon.core;

import com.example.dungeon.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Game {
    private final GameState state = new GameState();
    private final Map<String, Command> commands = new LinkedHashMap<>();

    static {
        WorldInfo.touch("Game");
    }

    public Game() {
        registerCommands();
        bootstrapWorld();
    }

    private void registerCommands() {
        commands.put("help", (ctx, a) -> System.out.println("Команды: " + String.join(", ", commands.keySet())));
        commands.put("gc-stats", (ctx, a) -> {
            Runtime rt = Runtime.getRuntime();
            long free = rt.freeMemory(), total = rt.totalMemory(), used = total - free;
            System.out.println("Память: used=" + used + " free=" + free + " total=" + total);
        });
        commands.put("look", (ctx, a) -> System.out.println(ctx.getCurrent().describe()));
        commands.put("move", (ctx, a) -> {
            if (a.isEmpty()) {
                throw new InvalidCommandException("Укажите направление (north, south, east, west)");
            }
            
            String direction = a.getFirst().toLowerCase(Locale.ROOT);
            Room currentRoom = ctx.getCurrent();
            Map<String, Room> neighbors = currentRoom.getNeighbors();
            
            if (!neighbors.containsKey(direction)) {
                throw new InvalidCommandException("Нет пути в направлении: " + direction);
            }
            
            Room nextRoom = neighbors.get(direction);
            ctx.setCurrent(nextRoom);
            System.out.println("Вы переместились в: " + nextRoom.getName());
            System.out.println(nextRoom.describe());
        });
        commands.put("take", (ctx, a) -> {
            if (a.isEmpty()) {
                throw new InvalidCommandException("Укажите название предмета для взятия");
            }
            
            String itemName = String.join(" ", a);
            Room currentRoom = ctx.getCurrent();
            Player player = ctx.getPlayer();
            
            // Ищем предмет в комнате (регистронезависимо с учетом локали)
            Optional<Item> foundItem = currentRoom.getItems().stream()
                    .filter(item -> item.getName().toLowerCase(Locale.ROOT).equals(itemName.toLowerCase(Locale.ROOT)))
                    .findFirst();
            
            if (foundItem.isPresent()) {
                Item item = foundItem.get();
                // Удаляем из комнаты и добавляем в инвентарь
                currentRoom.getItems().remove(item);
                player.getInventory().add(item);
                System.out.println("Вы подобрали: " + item.getName());
            } else {
                // Покажем доступные предметы для помощи пользователю
                if (currentRoom.getItems().isEmpty()) {
                    throw new InvalidCommandException("В этой комнате нет предметов");
                } else {
                    String availableItems = currentRoom.getItems().stream()
                            .map(Item::getName)
                            .collect(Collectors.joining(", "));
                    throw new InvalidCommandException("Предмет '" + itemName + "' не найден. Доступные предметы: " + availableItems);
                }
            }
});
        commands.put("inventory", (ctx, a) -> {
            Player player = ctx.getPlayer();
            List<Item> inventory = player.getInventory();
            
            if (inventory.isEmpty()) {
                System.out.println("Инвентарь пуст");
                return;
            }
            
            System.out.println("=== ИНВЕНТАРЬ ===");
            
            // Группируем предметы по типу и сортируем по названию
            Map<String, List<Item>> groupedItems = inventory.stream()
                    .collect(Collectors.groupingBy(
                        item -> item.getClass().getSimpleName(),
                        TreeMap::new, // Сортировка по названию типа
                        Collectors.collectingAndThen(
                            Collectors.toList(),
                            list -> {
                                list.sort(Comparator.comparing(Item::getName));
                                return list;
                            }
                        )
                    ));
            
            // Выводим сгруппированные предметы (без специфичной информации о Potion)
            groupedItems.forEach((type, items) -> {
                System.out.println(type + ":");
                items.forEach(item -> System.out.println("  - " + item.getName() + 
                    (item instanceof Potion ? " (зелье)" : "")));
            });
            
            // Альтернативный вариант: простой список с подсчетом количества
            System.out.println("\n--- Сводка ---");
            inventory.stream()
                    .collect(Collectors.groupingBy(
                        Item::getClass,
                        Collectors.counting()
                    ))
                    .forEach((itemClass, count) -> {
                        System.out.println(itemClass.getSimpleName() + ": " + count + " шт.");
                    });
        });
        commands.put("use", (ctx, a) -> {
    if (a.isEmpty()) {
        throw new InvalidCommandException("Укажите название предмета для использования");
    }
    
    String searchName = String.join(" ", a).toLowerCase(Locale.ROOT);
    Player player = ctx.getPlayer();
    List<Item> inventory = player.getInventory();
    
    // Ищем предмет в инвентаре по частичному совпадению
    List<Item> matchingItems = inventory.stream()
            .filter(item -> item.getName().toLowerCase(Locale.ROOT).contains(searchName))
            .collect(Collectors.toList());
    
    if (matchingItems.isEmpty()) {
        throw new InvalidCommandException("Предмет '" + String.join(" ", a) + "' не найден в инвентаре");
    } else if (matchingItems.size() == 1) {
        Item item = matchingItems.get(0);
        item.apply(ctx); // Полиморфизм через метод apply()
    } else {
        // Если найдено несколько предметов
        String matchingNames = matchingItems.stream()
                .map(Item::getName)
                .collect(Collectors.joining(", "));
        throw new InvalidCommandException("Найдено несколько предметов: " + matchingNames + ". Уточните название.");
    }
});
        commands.put("fight", (ctx, a) -> {
    Room currentRoom = ctx.getCurrent();
    Player player = ctx.getPlayer();
    Monster monster = currentRoom.getMonster();
    
    if (monster == null) {
        throw new InvalidCommandException("В этой комнате нет монстров для боя");
    }
    
    if (monster.getHp() <= 0) {
        throw new InvalidCommandException(monster.getName() + " уже побежден");
    }
    
    System.out.println("=== НАЧАЛО БОЯ ===");
    System.out.println("Игрок: " + player.getName() + " (HP: " + player.getHp() + ", Атака: " + player.getAttack() + ")");
    System.out.println("Монстр: " + monster.getName() + " (HP: " + monster.getHp() + ", Уровень: " + monster.getLevel() + ")");
    System.out.println();
    
    int round = 1;
    Random random = new Random();
    
    while (player.getHp() > 0 && monster.getHp() > 0) {
        System.out.println("--- Раунд " + round + " ---");
        
        // Ход игрока
        int playerDamage = player.getAttack();
        // Добавляем небольшой случайный элемент (80%-120% от базового урона)
        playerDamage = (int)(playerDamage * (0.8 + random.nextDouble() * 0.4));
        System.out.println(player.getName() + " атакует " + monster.getName() + " и наносит " + playerDamage + " урона");
        
        monster.setHp(monster.getHp() - playerDamage);
        if (monster.getHp() <= 0) {
            monster.setHp(0);
            System.out.println(monster.getName() + " повержен!");
            break;
        }
        System.out.println(monster.getName() + " HP: " + monster.getHp());
        
        // Ход монстра (если еще жив)
        if (monster.getHp() > 0) {
            // Урон монстра зависит от его уровня
            int monsterDamage = Math.max(1, monster.getLevel() * 2);
            // Случайный элемент для урона монстра
            monsterDamage = (int)(monsterDamage * (0.7 + random.nextDouble() * 0.6));
            
            System.out.println(monster.getName() + " атакует " + player.getName() + " и наносит " + monsterDamage + " урона");
            
            player.setHp(player.getHp() - monsterDamage);
            if (player.getHp() <= 0) {
                player.setHp(0);
                System.out.println(player.getName() + " пал в бою!");
                break;
            }
            System.out.println(player.getName() + " HP: " + player.getHp());
        }
        
        round++;
        System.out.println();
        
        // Пауза для читаемости (опционально)
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // Игнорируем
        }
    }
    
    // Обработка результатов боя
    if (player.getHp() <= 0) {
        System.out.println("=== ГИБЕЛЬ ===");
        System.out.println("Вы погибли в бою с " + monster.getName());
        System.out.println("Игра завершена.");
        System.exit(0);
    } else {
        System.out.println("=== ПОБЕДА ===");
        System.out.println("Вы победили " + monster.getName() + "!");
        
        // Награда за победу
        int expGained = monster.getLevel() * 10;
        int goldGained = monster.getLevel() * 5;
        
        System.out.println("Получено опыта: " + expGained);
        System.out.println("Получено золота: " + goldGained);
        
        // Увеличиваем счет игрока
        state.addScore(expGained);
        
        // Монстр может выпасть как лут
        if (!monster.getLoot().isEmpty()) {
            System.out.println("Получен лут:");
            for (Item item : monster.getLoot()) {
                player.getInventory().add(item);
                System.out.println("  - " + item.getName());
            }
        } else {
            // Базовая награда, если у монстра нет специального лута
            if (random.nextDouble() < 0.6) { // 60% шанс
                Potion basicPotion = new Potion("Зелье здоровья", 5);
                player.getInventory().add(basicPotion);
                System.out.println("Получено: " + basicPotion.getName());
            }
        }
        
        // Убираем монстра из комнаты
        currentRoom.setMonster(null);
        System.out.println("Монстр исчез из комнаты.");
    }
});
        commands.put("save", (ctx, a) -> SaveLoad.save(ctx));
        commands.put("load", (ctx, a) -> SaveLoad.load(ctx));
        commands.put("scores", (ctx, a) -> SaveLoad.printScores());
        commands.put("exit", (ctx, a) -> {
            System.out.println("Пока!");
            System.exit(0);
        });
    }

    private void bootstrapWorld() {
        Player hero = new Player("Герой", 20, 5);
        state.setPlayer(hero);

        Room square = new Room("Площадь", "Каменная площадь с фонтаном.");
        Room forest = new Room("Лес", "Шелест листвы и птичий щебет.");
        Room cave = new Room("Пещера", "Темно и сыро.");
        square.getNeighbors().put("north", forest);
        forest.getNeighbors().put("south", square);
        forest.getNeighbors().put("east", cave);
        cave.getNeighbors().put("west", forest);

        forest.getItems().add(new Potion("Малое зелье", 5));
        forest.setMonster(new Monster("Волк", 1, 8));

        state.setCurrent(square);
    }

    public void run() {
        System.out.println("DungeonMini (TEMPLATE). 'help' — команды.");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.print("> ");
                String line = in.readLine();
                if (line == null) break;
                line = line.trim();
                if (line.isEmpty()) continue;
                List<String> parts = Arrays.asList(line.split("\s+"));
                String cmd = parts.getFirst().toLowerCase(Locale.ROOT);
                List<String> args = parts.subList(1, parts.size());
                Command c = commands.get(cmd);
                try {
                    if (c == null) throw new InvalidCommandException("Неизвестная команда: " + cmd);
                    c.execute(state, args);
                    state.addScore(1);
                } catch (InvalidCommandException e) {
                    System.out.println("Ошибка: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Непредвиденная ошибка: " + e.getClass().getSimpleName() + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка ввода/вывода: " + e.getMessage());
        }
    }
}
