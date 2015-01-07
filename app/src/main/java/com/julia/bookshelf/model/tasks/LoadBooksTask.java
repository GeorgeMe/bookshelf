package com.julia.bookshelf.model.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.julia.bookshelf.model.data.Book;
import com.julia.bookshelf.model.parsers.JSONParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class LoadBooksTask extends AsyncTask<Void, Void, List<Book>> {
    private Context context;

    public LoadBooksTask(Context context) {
        this.context = context;
    }

    @Override
    protected List<Book> doInBackground(Void... params) {
        String json = loadJSONFromAsset("json/books.json");

        return JSONParser.parseBooks(json);
    }

    public String loadJSONFromAsset(String path) {
        String json;
        try {
            InputStream is = context.getAssets().open(path);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}