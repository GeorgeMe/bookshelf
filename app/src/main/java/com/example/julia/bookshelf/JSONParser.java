package com.example.julia.bookshelf;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Julia on 02.01.2015.
 */
public class JSONParser {
    public static ArrayList<Book> parseBooks(String json) {
        ArrayList<Book> books = new ArrayList<>();
        try {
            JSONObject reader = new JSONObject(json);
            books = new ArrayList<>();
            JSONArray jsonArray = reader.getJSONArray("books");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Book book = new Book();
                book.setTitle(jsonObject.getString("title"));
                book.setAuthor(jsonObject.getString("author"));
                book.setCover(jsonObject.getString("cover"));
                book.setGenre(jsonObject.getString("genre"));
                book.setAnnotation(jsonObject.getString("annotation"));
                book.setFavourite(jsonObject.getBoolean("isFavourite"));
                books.add(book);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return books;
    }

}
