package com.julia.bookshelf.model.parsers;

import android.support.annotation.Nullable;
import android.util.Log;

import com.julia.bookshelf.model.data.Book;
import com.julia.bookshelf.model.data.FavouriteBook;
import com.julia.bookshelf.model.data.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONParser {
    public static ArrayList<Book> parseBooks(String json) {
        ArrayList<Book> bookList = new ArrayList<>();
        try {
            JSONObject reader = new JSONObject(json);
            JSONArray jsonArray = reader.getJSONArray("results");
            Book book;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                book = new Book();
                book.setId(jsonObject.getString("objectId"));
                book.setTitle(jsonObject.getString("title"));
                book.setAuthor(jsonObject.getString("author"));
                book.setCover(jsonObject.getString("cover"));
                book.setGenre(jsonObject.getString("genre"));
                book.setAnnotation(jsonObject.getString("annotation"));

                bookList.add(book);
            }
        } catch (JSONException e) {
            Log.w("BOOKSHELF", e.toString());
        }
        return bookList;
    }

    @Nullable
    public static User parseLogedUser(String json) {
        User user = null;
        try {
            user = new User();
            JSONObject jsonObject = new JSONObject(json);
            user.setUsername(jsonObject.getString("username"));
            user.setEmail(jsonObject.getString("email"));
            user.setSessionToken(jsonObject.getString("sessionToken"));
            user.setId(jsonObject.getString("objectId"));
        } catch (JSONException e) {
            Log.w("BOOKSHELF", e.toString());
        }
        return user;
    }

    public static User parseRegisteredUser(String json) {
        User user = new User();
        try {
            JSONObject jsonObject = new JSONObject(json);
            user.setSessionToken(jsonObject.getString("sessionToken"));
            user.setId(jsonObject.getString("objectId"));
        } catch (JSONException e) {
            Log.w("BOOKSHELF", e.toString());
        }
        return user;
    }

    public static ArrayList<FavouriteBook> parseFavouriteBooksFromServer(String json) {
        ArrayList<FavouriteBook> favouriteBooksList = new ArrayList<>();
        FavouriteBook favouriteBook;
        try {
            JSONObject reader = new JSONObject(json);
            JSONArray jsonArray = reader.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                favouriteBook = new FavouriteBook(jsonObject.getString("objectId"), jsonObject.getString("bookId"));
                favouriteBooksList.add(favouriteBook);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return favouriteBooksList;
    }

    public static ArrayList<FavouriteBook> parseFavouriteBooksFromPreferences(String json) {
        ArrayList<FavouriteBook> favouriteBooksList = new ArrayList<>();
        FavouriteBook favouriteBook;
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String objectId = jsonObject.optString("objectId",null);
                String bookId = jsonObject.getString("bookId");
                favouriteBook = new FavouriteBook(objectId, bookId);
                favouriteBooksList.add(favouriteBook);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return favouriteBooksList;
    }

    public static FavouriteBook parseFavouriteBook(String json) {
        FavouriteBook favouriteBook = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            favouriteBook = new FavouriteBook(jsonObject.getString("objectId"), null);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return favouriteBook;
    }

}
