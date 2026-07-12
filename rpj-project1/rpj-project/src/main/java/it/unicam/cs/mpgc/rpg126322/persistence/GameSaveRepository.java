package it.unicam.cs.mpgc.rpg126322.persistence;


public interface GameSaveRepository {

    void save(GameSaveData saveData, int slot);

    GameSaveData load(int slot);


    boolean exists(int slot);
}
