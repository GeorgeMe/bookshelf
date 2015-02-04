package com.julia.bookshelf.model.parsers;

import android.support.annotation.Nullable;
import android.util.Log;

import com.julia.bookshelf.model.data.Book;
import com.julia.bookshelf.model.data.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Julia on 02.01.2015.
 */
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

    public static ArrayList<String> parseFavouriteBooksId(String json) {
        ArrayList<String> idArray = new ArrayList<>();
        Log.i("bookshelf", json);
        try {
            JSONObject reader = new JSONObject(json);
            JSONArray jsonArray = reader.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                idArray.add(jsonObject.getString("bookId"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return idArray;
    }

}
