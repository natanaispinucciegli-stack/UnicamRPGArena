package it.unicam.cs.mpgc.rpg126322.persistence;

public class GameSaveData {

    private String heroType;
    private String heroName;
    private int heroHealth;


    private String enemyType;
    private String enemyName;
    private int enemyHealth;
    private int enemyMaxHealth;


    private int currentLevel;
    private int potionQuantity;
    private int specialCooldown;



    public GameSaveData(String heroType, String heroName, int heroHealth,
                        String enemyType, String enemyName, int enemyHealth, int enemyMaxHealth,
                        int currentLevel, int potionQuantity, int specialCooldown) {
        this.heroType = heroType;
        this.heroName = heroName;
        this.heroHealth = heroHealth;
        this.enemyType = enemyType;
        this.enemyName = enemyName;
        this.enemyHealth = enemyHealth;
        this.enemyMaxHealth = enemyMaxHealth;
        this.currentLevel = currentLevel;
        this.potionQuantity = potionQuantity;
        this.specialCooldown = specialCooldown;
    }


    public String getHeroType() { return heroType; }
    public String getHeroName() { return heroName; }
    public int getHeroHealth() { return heroHealth; }


    public String getEnemyType() { return enemyType; }
    public String getEnemyName() { return enemyName; }
    public int getEnemyHealth() { return enemyHealth; }



    public int getCurrentLevel() { return currentLevel; }
    public int getPotionQuantity() { return potionQuantity; }
    public int getSpecialCooldown() { return specialCooldown; }
}