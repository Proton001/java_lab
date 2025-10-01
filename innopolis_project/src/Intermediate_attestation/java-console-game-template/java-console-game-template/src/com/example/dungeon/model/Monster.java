package com.example.dungeon.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Monster extends Entity {
    private int level;
    private final List<Item> loot = new ArrayList<>();


    public Monster(String name, int level, int hp) {
        super(name, hp);
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<Item> getLoot() {
        return loot;
    }

    // Метод для расчета урона монстра (опционально)
    public int calculateDamage() {
        Random random = new Random();
        int baseDamage = Math.max(1, this.level * 2);
        return (int)(baseDamage * (0.7 + random.nextDouble() * 0.6));
    }
}
