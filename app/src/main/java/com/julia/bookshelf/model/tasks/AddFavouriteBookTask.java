package com.julia.bookshelf.model.tasks;

import android.os.AsyncTask;

import com.julia.bookshelf.model.data.Book;
import com.julia.bookshelf.model.data.User;
import com.julia.bookshelf.model.http.HTTPClient;
import com.julia.bookshelf.model.http.URLCreator;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Julia on 28.01.2015.
 */
public class AddFavouriteBookTask extends AsyncTask<Void, Void, Void> {
    private User user;
    private Book book;

    public AddFavouriteBookTask(User user, Book book) {
        this.user = user;
        this.book = book;
    }

    @Override
    protected Void doInBackground(Void... params) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("bookId", book.getId());
            jsonObject.put("userId", user.getId());
            HTTPClient.post(URLCreator.addFavouriteBook(), jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
