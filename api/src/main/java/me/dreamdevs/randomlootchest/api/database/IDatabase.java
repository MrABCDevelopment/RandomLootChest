package me.dreamdevs.randomlootchest.api.database;

public interface IDatabase {


    /**
     * Makes a connection to the database.
     */
    void connect();

    void disconnect();

    void loadData();

    void saveData();

}