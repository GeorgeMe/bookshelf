package com.julia.bookshelf.model.tasks;

import android.os.AsyncTask;

import com.julia.bookshelf.model.data.User;
import com.julia.bookshelf.model.http.HTTPClient;
import com.julia.bookshelf.model.http.HTTPResponse;
import com.julia.bookshelf.model.http.URLCreator;
import com.julia.bookshelf.model.parsers.JSONParser;

/**
 * Created by Julia on 17.01.2015.
 */
public class LoginUserTask extends AsyncTask<Void, Void, User> {

    private final String username;
    private final String password;

    public LoginUserTask(String username, String password) {

        this.username = username;
        this.password = password;
    }

    @Override
    protected User doInBackground(Void... params) {
        User user = null;
        HTTPResponse response = HTTPClient.get(URLCreator.createUser(username, password));
        if (response.isSuccessLogin()) {
            user = JSONParser.parseLogedUser(response.getJson());
        }
        return user;
    }

}
