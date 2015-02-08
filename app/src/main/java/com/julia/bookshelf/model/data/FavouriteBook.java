package com.julia.bookshelf.model.data;

import android.support.annotation.Nullable;

/**
 * Created by Julia on 04.02.2015.
 */
public class FavouriteBook {
    private String objectId;
    private String bookId;

    public FavouriteBook(String objectId, String bookId) {
        this.objectId = objectId;
        this.bookId = bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Nullable
    public String getObjectId() {
        return objectId;
    }

    public String getBookId() {
        return bookId;
    }
}
