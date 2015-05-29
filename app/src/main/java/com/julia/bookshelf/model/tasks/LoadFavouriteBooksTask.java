package com.julia.bookshelf.model.tasks;

import android.os.AsyncTask;

import com.julia.bookshelf.model.data.FavouriteBook;
import com.julia.bookshelf.model.data.User;
import com.julia.bookshelf.model.http.HTTPClient;
import com.julia.bookshelf.model.http.HTTPResponse;
import com.julia.bookshelf.model.http.URLCreator;
import com.julia.bookshelf.model.parsers.JSONParser;

import java.util.List;

public class LoadFavouriteBooksTask extends AsyncTask<Void, Void, List<FavouriteBook>> {
    private User user;

    public LoadFavouriteBooksTask(User user) {
        this.user = user;
    }

    @Override
    protected List<FavouriteBook> doInBackground(Void... params) {
        HTTPResponse response = HTTPClient.get(URLCreator.getFavouriteBooks(user.getId()));
        return JSONParser.parseFavouriteBooksFromServer(response.getJson());
    }
}
