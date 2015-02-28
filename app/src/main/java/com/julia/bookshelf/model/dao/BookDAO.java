package com.julia.bookshelf.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import com.julia.bookshelf.model.data.Book;
import com.julia.bookshelf.model.database.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julia on 23.02.2015.
 */
public class BookDAO extends AbsDAO {

    private interface Table {
        String NAME = "books";
        String ID = "id";
        String TITLE = "title";
        String AUTHOR = "author";
        String COVER = "cover";
        String GENRE = "genre";
        String ANNOTATION = "annotation";
    }

    public static String getCreateTable() {
        return "CREATE TABLE " + Table.NAME + "("
                + Table.ID + " TEXT," + Table.TITLE + " TEXT,"
                + Table.AUTHOR + " TEXT," + Table.COVER + " TEXT,"
                + Table.GENRE + " TEXT," + Table.ANNOTATION + " TEXT" + ")";
    }

    public static String getDropTable() {
        return "DROP TABLE IF EXISTS " + Table.NAME;
    }

    public void addBook(final Book book) {
        execute(new Query() {
            @Override
            public void execute(Database database) {
                ContentValues values = getValues(book);
                database.insert(Table.NAME, null, values);
            }
        });
    }

    public void addBookList(List<Book> bookList) {
        Database database = openDatabase();
        database.beginTransaction();
        try {
            for (int i = 0; i < bookList.size(); i++) {
                ContentValues values = getValues(bookList.get(i));
                database.insert(Table.NAME, null, values);
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
            closeDatabase();
        }
    }


    private ContentValues getValues(Book book) {
        ContentValues values = new ContentValues();
        values.put(Table.ID, book.getId());
        values.put(Table.TITLE, book.getTitle());
        values.put(Table.AUTHOR, book.getAuthor());
        values.put(Table.COVER, book.getCover());
        values.put(Table.GENRE, book.getGenre());
        values.put(Table.ANNOTATION, book.getAnnotation());
        return values;
    }

    @Nullable
    public Book getBook(String id) {
        Database database = openDatabase();
        String selection = Table.ID + "=?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = database.query(Table.NAME, null, selection,
                selectionArgs, null, null, null, null);
        Book book = null;
        if (cursor != null) {
            cursor.moveToFirst();
            book = getBook(cursor);
        }
        closeCursor(cursor);
        closeDatabase();
        return book;
    }


    private Book getBook(Cursor cursor) {//todo: create and return book object
        Book book = new Book();
        int idIndex = cursor.getColumnIndex(Table.ID);
        int titleIndex = cursor.getColumnIndex(Table.TITLE);
        int authorIndex = cursor.getColumnIndex(Table.AUTHOR);
        int coverIndex = cursor.getColumnIndex(Table.COVER);
        int genreIndex = cursor.getColumnIndex(Table.GENRE);
        int annotationIndex = cursor.getColumnIndex(Table.ANNOTATION);
        book.setId(cursor.getString(idIndex));
        book.setTitle(cursor.getString(titleIndex));
        book.setAuthor(cursor.getString(authorIndex));
        book.setCover(cursor.getString(coverIndex));
        book.setGenre(cursor.getString(genreIndex));
        book.setAnnotation(cursor.getString(annotationIndex));
        return book;
    }

    @Nullable
    public List<Book> getAllBooks() {
        List<Book> bookList = null;
        String selectQuery = "SELECT  * FROM " + Table.NAME;
        Database database = openDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            bookList = new ArrayList<Book>();
            do {
                Book book = getBook(cursor);
                bookList.add(book);
            } while (cursor.moveToNext());
        }
        closeCursor(cursor);
        closeDatabase();
        return bookList;
    }

    public int getBooksCount() {
        String countQuery = "SELECT  * FROM " + Table.NAME;
        Database database = openDatabase();
        Cursor cursor = database.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        closeDatabase();
        return count;
    }

    public int updateBook(Book book) {
        Database database = openDatabase();
        ContentValues values = getValues(book);
        int res = database.update(Table.NAME, values, Table.ID + " = ?",
                new String[]{String.valueOf(book.getId())});
        closeDatabase();
        return res;

    }

    public void deleteBook(Book book) {
        Database database = openDatabase();
        database.delete(Table.NAME, Table.ID + " = ?",
                new String[]{String.valueOf(book.getId())});
        closeDatabase();
    }
}
