package com.julia.bookshelf.model.dao;

import android.database.Cursor;

import com.julia.bookshelf.model.database.Database;
import com.julia.bookshelf.model.database.DatabaseManager;

public abstract class AbsDAO {
    protected Database openDatabase() {
        return DatabaseManager.getInstance().openDatabase();
    }

    protected void closeDatabase() {
        DatabaseManager.getInstance().closeDatabase();
    }

    protected void closeCursor(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
    }

    protected void execute(Query query) {
        Database database = openDatabase();
        query.execute(database);
        closeDatabase();
    }

    public static interface Query {
        public void execute(Database database);
    }
}
