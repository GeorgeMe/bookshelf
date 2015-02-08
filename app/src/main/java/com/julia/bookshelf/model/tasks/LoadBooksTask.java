package com.julia.bookshelf.model.tasks;

import android.os.AsyncTask;

import com.julia.bookshelf.model.data.Book;
import com.julia.bookshelf.model.http.HTTPClient;
import com.julia.bookshelf.model.http.HTTPResponse;
import com.julia.bookshelf.model.parsers.JSONParser;

import java.util.List;

public class LoadBooksTask extends AsyncTask<Void, Void, List<Book>> {
    private String url;

    public LoadBooksTask(String url) {
        this.url = url;
    }

    @Override
    protected List<Book> doInBackground(Void... params) {
        HTTPResponse response = HTTPClient.get(url);
        return JSONParser.parseBooks(response.getJson());
    }
}