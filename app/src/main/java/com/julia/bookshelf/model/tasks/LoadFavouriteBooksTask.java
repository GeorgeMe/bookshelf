package com.julia.bookshelf.model.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.julia.bookshelf.model.data.Book;
import com.julia.bookshelf.model.data.User;
import com.julia.bookshelf.model.http.HTTPClient;
import com.julia.bookshelf.model.http.HTTPResponse;
import com.julia.bookshelf.model.http.URLCreator;
import com.julia.bookshelf.model.parsers.JSONParser;

import java.util.List;

/**
 * Created by Julia on 30.01.2015.
 */
public class LoadFavouriteBooksTask extends AsyncTask<Void, Void, List<Book>> {
    private User user;

    public LoadFavouriteBooksTask(User user) {
        this.user = user;
    }

    @Override
    protected List<Book> doInBackground(Void... params) {
        HTTPResponse httpResponse = HTTPClient.get(URLCreator.getFavouriteBooksId(user.getId()));
        List<String> arr=JSONParser.parseFavouriteBooksId(httpResponse.getJson());
        HTTPResponse response = HTTPClient.get(URLCreator.getFavouriteBooks(arr));
        Log.i("bookshelf",response.getJson());
        return JSONParser.parseBooks(response.getJson());
    }
}
