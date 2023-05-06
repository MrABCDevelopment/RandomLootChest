package me.dreamdevs.github.randomlootchest.api.database;

import java.sql.Connection;
import java.util.UUID;

public abstract class DatabaseConnector {

    public Connection connection;

    public abstract void connect();

    public abstract void disconnect();

    public abstract void saveData();

    public abstract void loadData();

    public abstract boolean isAccount(UUID account);

    public abstract void insertData(UUID account);

}