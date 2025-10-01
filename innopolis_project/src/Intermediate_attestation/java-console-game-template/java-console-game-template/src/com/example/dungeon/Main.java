package com.example.dungeon;

import com.example.dungeon.core.Game;

public class Main {
    public static void main(String[] args) {
        System.setProperty("console.encoding", "Cp866");
        System.setProperty("file.encoding", "Cp866");
        new Game().run();
    }
}
