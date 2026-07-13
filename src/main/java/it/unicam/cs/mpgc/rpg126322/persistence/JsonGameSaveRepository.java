package it.unicam.cs.mpgc.rpg126322.persistence;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonGameSaveRepository implements GameSaveRepository {
    private final Gson gson = new Gson();

    @Override
    public void save(GameSaveData saveData, int slot) {
        String fileName = getFileName(slot);
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(saveData, writer);
            System.out.println("Stato salvato correttamente in: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public GameSaveData load(int slot) {
        String fileName = getFileName(slot);
        try (FileReader reader = new FileReader(fileName)) {
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            if (jsonObject == null) {
                return null;
            }


            String heroName = readString(jsonObject, "heroName", readString(jsonObject, "name", "Arthur"));
            String heroType = readString(jsonObject, "heroType",
                    readString(jsonObject, "type", inferHeroType(heroName)));
            int heroHealth = readInt(jsonObject, "heroHealth", readInt(jsonObject, "health", 100));


            int currentLevel = readInt(jsonObject, "currentLevel", 1);
            String defaultEnemyType = (currentLevel == 1) ? "golem" : (currentLevel == 2) ? "mage" : "warrior";
            String defaultEnemyName = (currentLevel == 1) ? "Roccia" : (currentLevel == 2) ? "Malekith" : "Arthur Oscuro";
            int defaultEnemyHp = (currentLevel == 1) ? 100 : (currentLevel == 2) ? 90 : 120;

            String enemyType = readString(jsonObject, "enemyType", defaultEnemyType);
            String enemyName = readString(jsonObject, "enemyName", defaultEnemyName);
            int enemyHealth = readInt(jsonObject, "enemyHealth", defaultEnemyHp);
            int enemyMaxHealth = readInt(jsonObject, "enemyMaxHealth", defaultEnemyHp);

            // --- LETTURA DATI STATO ---
            int potionQuantity = readInt(jsonObject, "potionQuantity", 3);
            int specialCooldown = readInt(jsonObject, "specialCooldown", 0);


            return new GameSaveData(
                    heroType, heroName, heroHealth,
                    enemyType, enemyName, enemyHealth, enemyMaxHealth,
                    currentLevel, potionQuantity, specialCooldown
            );
        } catch (Exception e) {
            System.err.println("Errore durante la ricostruzione del salvataggio: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean exists(int slot) {
        File file = new File(getFileName(slot));
        return file.exists() && file.isFile();
    }

    private String getFileName(int slot) {
        return "save_slot_" + slot + ".json";
    }

    private String inferHeroType(String heroName) {
        return "Shadow".equalsIgnoreCase(heroName) ? "rogue" : "warrior";
    }

    private String readString(JsonObject jsonObject, String property, String defaultValue) {
        if (!jsonObject.has(property) || jsonObject.get(property).isJsonNull()) {
            return defaultValue;
        }
        return jsonObject.get(property).getAsString();
    }

    private int readInt(JsonObject jsonObject, String property, int defaultValue) {
        if (!jsonObject.has(property) || jsonObject.get(property).isJsonNull()) {
            return defaultValue;
        }

        try {
            JsonElement element = jsonObject.get(property);
            if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
                String value = element.getAsString().trim();
                if (value.contains("/")) {
                    value = value.split("/")[0].trim();
                }
                if (value.contains(" ")) {
                    value = value.split(" ")[0].trim();
                }
                return Integer.parseInt(value);
            }
            return element.getAsInt();
        } catch (RuntimeException e) {
            return defaultValue;
        }
    }
}