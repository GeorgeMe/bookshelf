package com.julia.bookshelf.model.http;

import android.util.Log;

import java.util.List;

/**
 * Created by Julia on 19.01.2015.
 */
public class URLCreator {
    public static String loadBook() {
        return "https://api.parse.com/1/classes/Book";
    }

    public static String createUser(String username, String password) {
        return String.format("https://api.parse.com/1/login?username=%s&password=%s", username, password);
    }

    public static String registerUser() {
        return "https://api.parse.com/1/users";
    }

    public static String addFavouriteBook() {
        return "https://api.parse.com/1/classes/FavouriteBooks";
    }

    public static String getFavouriteBooksId(String userId) {
        return "https://api.parse.com/1/classes/FavouriteBooks?where={\"userId\":\"" + userId + "\"}";
    }

    public static String getFavouriteBooks(List<String> bookIdArray) {
        Log.i("bookshelf", arrayToString(bookIdArray));
        return "https://api.parse.com/1/classes/Book?where={\"objectId\":{\"$in\":[" + arrayToString(bookIdArray) + "]}}";
    }

    private static String arrayToString(List<String> array) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < array.size(); i++) {
            stringBuilder.append("\"").append(array.get(i)).append("\"");
            if (i != array.size() - 1) {
                stringBuilder.append(",");
            }
        }
        return stringBuilder.toString();
    }
}
