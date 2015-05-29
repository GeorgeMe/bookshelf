package com.julia.bookshelf.model.tasks;

import android.os.AsyncTask;

import com.julia.bookshelf.model.data.FavouriteBook;
import com.julia.bookshelf.model.http.HTTPClient;
import com.julia.bookshelf.model.http.URLCreator;

public class DeleteFavouriteBookTask extends AsyncTask<Void, Void, Void> {
    private FavouriteBook favouriteBook;

    public DeleteFavouriteBookTask(FavouriteBook favouriteBook) {
        this.favouriteBook = favouriteBook;
    }

    @Override
    protected Void doInBackground(Void... params) {
        HTTPClient.delete(URLCreator.deleteFavouriteBook(favouriteBook.getObjectId()));
        return null;
    }
}
