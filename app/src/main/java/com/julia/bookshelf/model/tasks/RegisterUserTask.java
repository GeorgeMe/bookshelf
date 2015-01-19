package com.julia.bookshelf.model.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.julia.bookshelf.model.data.User;
import com.julia.bookshelf.model.http.HTTPClient;
import com.julia.bookshelf.model.http.HTTPResponse;
import com.julia.bookshelf.model.http.URLCreator;
import com.julia.bookshelf.model.parsers.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Julia on 16.01.2015.
 */
public class RegisterUserTask extends AsyncTask<String, Void, User> {
    @Override
    protected User doInBackground(String... params) {
        JSONObject jsonObject = new JSONObject();
        String username = params[0];
        String password = params[1];
        String email = params[2];
        User user = null;
        try {
            jsonObject.put("username", username);
            jsonObject.put("password", password);
            jsonObject.put("email", email);
            HTTPResponse httpResponse = HTTPClient.post(URLCreator.registerUser(), jsonObject);
            if (httpResponse.isSuccessRegister()) {
                user = JSONParser.parseRegisteredUser(httpResponse.getJson());
                user.setUsername(username);
                user.setEmail(email);
            }
        } catch (JSONException e) {
            Log.w("BOOKSHELF", e.toString());
        }
        return user;
    }
}
