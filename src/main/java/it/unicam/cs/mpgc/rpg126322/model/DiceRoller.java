package it.unicam.cs.mpgc.rpg126322.model;

import java.util.Random;

public class DiceRoller {
    private static final Random random = new Random();


    public static int roll(int faces) {
        return random.nextInt(faces) + 1;
    }
}