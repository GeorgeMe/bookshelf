package com.julia.bookshelf.model.tasks;

import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.julia.bookshelf.model.data.Book;
import com.julia.bookshelf.model.data.FavouriteBook;
import com.julia.bookshelf.model.data.User;
import com.julia.bookshelf.model.http.HTTPClient;
import com.julia.bookshelf.model.http.HTTPResponse;
import com.julia.bookshelf.model.http.URLCreator;
import com.julia.bookshelf.model.parsers.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

public class AddFavouriteBookTask extends AsyncTask<Void, Void, FavouriteBook> {
    private User user;
    private Book book;

    public AddFavouriteBookTask(User user, Book book) {
        this.user = user;
        this.book = book;
    }

    @Nullable
    @Override
    protected FavouriteBook doInBackground(Void... params) {
        JSONObject jsonObject = new JSONObject();
        FavouriteBook favouriteBook = null;
        try {
            jsonObject.put("bookId", book.getId());
            jsonObject.put("userId", user.getId());
            HTTPResponse response = HTTPClient.post(URLCreator.addFavouriteBook(), jsonObject);
            favouriteBook = JSONParser.parseFavouriteBook(response.getJson());
            favouriteBook.setBookId(book.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return favouriteBook;
    }
}
