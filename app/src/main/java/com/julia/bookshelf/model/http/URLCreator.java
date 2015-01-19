package com.julia.bookshelf.model.http;

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
}
