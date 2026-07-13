package it.unicam.cs.mpgc.rpg126322.model;

public class Potion {
    private final String name;
    private final int healAmount;
    private int quantity;

    public Potion(String name, int healAmount, int quantity) {
        this.name = name;
        this.healAmount = healAmount;
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public int getHealAmount() { return healAmount; }
    public int getQuantity() { return quantity; }

    public boolean use() {
        if (quantity > 0) {
            quantity--;
            return true;
        }
        return false;
    }
}