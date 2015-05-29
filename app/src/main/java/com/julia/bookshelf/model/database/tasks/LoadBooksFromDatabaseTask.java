package com.julia.bookshelf.model.database.tasks;

import android.os.AsyncTask;

import com.julia.bookshelf.model.dao.BookDAO;
import com.julia.bookshelf.model.data.Book;

import java.util.List;

public class LoadBooksFromDatabaseTask extends AsyncTask<Void, Void, List<Book>> {

    @Override
    protected List<Book> doInBackground(Void... params) {
        BookDAO bookDAO = new BookDAO();
        return bookDAO.getAllBooks();
    }
}
