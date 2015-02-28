package com.julia.bookshelf.model.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Julia on 24.02.2015.
 */
public class DatabaseManager {
    private AtomicInteger openCounter = new AtomicInteger();

    private static DatabaseManager instance;
    private static SQLiteOpenHelper databaseHelper; // TODO sDatabaseHelper
    private SQLiteDatabase database;

    public static synchronized void initializeInstance(SQLiteOpenHelper helper) {
        if (instance == null) {
            instance = new DatabaseManager();
            databaseHelper = helper;
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException(DatabaseManager.class.getSimpleName() +
                    " is not initialized, call initialize(..) method first.");
        }

        return instance;
    }

    public synchronized Database openDatabase() {
        if(openCounter.incrementAndGet() == 1) {
            database = databaseHelper.getWritableDatabase();
        }
        return new Database(database);
    }

    public synchronized void closeDatabase() {
        if(openCounter.decrementAndGet() == 0) {
            database.close();

        }
    }
}
