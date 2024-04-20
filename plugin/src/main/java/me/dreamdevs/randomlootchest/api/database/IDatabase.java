package me.dreamdevs.randomlootchest.api.database;

public interface IDatabase {

    void connect();

    void disconnect();

    void loadData();

    void saveData();

}