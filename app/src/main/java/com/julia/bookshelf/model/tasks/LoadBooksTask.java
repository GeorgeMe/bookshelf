package com.julia.bookshelf.model.tasks;

import android.os.AsyncTask;

import com.julia.bookshelf.model.data.Book;
import com.julia.bookshelf.model.http.HTTPClient;
import com.julia.bookshelf.model.http.HTTPResponse;
import com.julia.bookshelf.model.http.URLCreator;
import com.julia.bookshelf.model.parsers.JSONParser;

import java.util.List;

public class LoadBooksTask extends AsyncTask<Void, Void, List<Book>> {

    @Override
    protected List<Book> doInBackground(Void... params) {
        //String json = loadJSONFromAsset("json/books.json");
        HTTPResponse response = HTTPClient.get(URLCreator.loadBook());
        return JSONParser.parseBooks(response.getJson());
    }

//    public String loadJSONFromAsset(String path) {
//        String json;
//        try {
//            InputStream is = context.getAssets().open(path);
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, "UTF-8");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//        return json;
//
//    }
}