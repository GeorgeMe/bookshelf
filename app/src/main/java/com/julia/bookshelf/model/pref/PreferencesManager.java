package com.julia.bookshelf.model.pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.julia.bookshelf.model.data.Book;
import com.julia.bookshelf.model.data.FavouriteBook;
import com.julia.bookshelf.model.data.User;
import com.julia.bookshelf.model.parsers.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PreferencesManager {
    private static final String PREFS_NAME = "book-shelf-pref";
    private static final String KEY_USER = "user";
    public static final String KEY_FAVOURITE_BOOKS = "favourite_books";
    private Context context;

    public PreferencesManager(Context context) {
        this.context = context;
    }

    public void saveUser(User user) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", user.getUsername());
            jsonObject.put("email", user.getEmail());
            jsonObject.put("sessionToken", user.getSessionToken());
            jsonObject.put("objectId", user.getId());
            getSharedPreferences().edit().putString(KEY_USER, jsonObject.toString()).commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public User loadUser() {
        SharedPreferences account = getSharedPreferences();
        String result = account.getString(KEY_USER, null);
        User user = null;
        if (result != null) {
            user = JSONParser.parseLogedUser(result);
        }
        return user;
    }

    public void clearUser() {
        getSharedPreferences().edit().remove(KEY_USER).commit();
    }

    public void saveFavouriteBooks(List<FavouriteBook> favouriteBooks) {
        JSONObject jsonObject;
        JSONArray jsonArray = new JSONArray();
        FavouriteBook book;
        try {
            for (int i = 0; i < favouriteBooks.size(); i++) {
                jsonObject = new JSONObject();
                book = favouriteBooks.get(i);
                jsonObject.put("objectId", book.getObjectId());
                jsonObject.put("bookId", book.getBookId());
                jsonArray.put(jsonObject);
            }
            getSharedPreferences().edit().putString(KEY_FAVOURITE_BOOKS, jsonArray.toString()).commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addFavouriteBook(Book book) {
        SharedPreferences preferences = getSharedPreferences();
        String result = preferences.getString(KEY_FAVOURITE_BOOKS, null);
        if (result != null) {
            ArrayList<FavouriteBook> favouriteBooksList = JSONParser.parseFavouriteBooksFromPreferences(result);

            favouriteBooksList.add(new FavouriteBook(null, book.getId()));
            saveFavouriteBooks(favouriteBooksList);
        }
    }

    public void updateFavouriteBook(FavouriteBook favouriteBook) {
        ArrayList<FavouriteBook> favouriteBookArrayList = loadFavouriteBooks();
        if (favouriteBookArrayList != null) {
            FavouriteBook currentBook;
            for (int i = 0; i < favouriteBookArrayList.size(); i++) {
                currentBook = favouriteBookArrayList.get(i);
                if (currentBook.getObjectId() == null && favouriteBook.getBookId().equals(currentBook.getBookId())) {
                    currentBook.setObjectId(favouriteBook.getObjectId());
                    break;
                }
            }
            saveFavouriteBooks(favouriteBookArrayList);
        }
    }

    @NonNull
    public ArrayList<String> getFavouriteBooksId() {
        ArrayList<FavouriteBook> favouriteBookArrayList = loadFavouriteBooks();
        ArrayList<String> idArrayList = new ArrayList<>();
        if (favouriteBookArrayList != null) {
            for (int i = 0; i < favouriteBookArrayList.size(); i++) {
                idArrayList.add(favouriteBookArrayList.get(i).getBookId());
            }
        }
        return idArrayList;
    }

    @Nullable
    public ArrayList<FavouriteBook> loadFavouriteBooks() {
        SharedPreferences preferences = getSharedPreferences();
        String result = preferences.getString(KEY_FAVOURITE_BOOKS, null);
        ArrayList<FavouriteBook> favouriteBooksList = null;
        if (result != null) {
            favouriteBooksList = JSONParser.parseFavouriteBooksFromPreferences(result);
        }
        return favouriteBooksList;
    }

    public void clearFavouriteBooks() {
        getSharedPreferences().edit().remove(KEY_FAVOURITE_BOOKS).commit();
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }


    public void deleteFavouriteBook(Book book) {
        ArrayList<FavouriteBook> favouriteBookArrayList = loadFavouriteBooks();
        if (favouriteBookArrayList != null) {
            for (int i = 0; i < favouriteBookArrayList.size(); i++) {
                if (favouriteBookArrayList.get(i).getBookId().equals(book.getId())) {
                    favouriteBookArrayList.remove(i);
                    break;
                }
            }
            saveFavouriteBooks(favouriteBookArrayList);
        }
    }

    public FavouriteBook getFavouriteBook(Book book) {
        ArrayList<FavouriteBook> favouriteBookArrayList = loadFavouriteBooks();
        FavouriteBook favouriteBook = null;
        if (favouriteBookArrayList != null) {
            for (int i = 0; i < favouriteBookArrayList.size(); i++) {
                if (favouriteBookArrayList.get(i).getBookId().equals(book.getId())) {
                    favouriteBook = favouriteBookArrayList.get(i);
                }
            }
        }
        return favouriteBook;
    }
}
