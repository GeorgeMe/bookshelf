package com.julia.bookshelf.model.pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.julia.bookshelf.model.data.User;
import com.julia.bookshelf.model.parsers.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Julia on 20.01.2015.
 */
public class PreferencesManager {
    private static final String PREFS_NAME = "Account";
    private static final String KEY = "user";

    public static void saveUser(Context context, User user) {
        SharedPreferences account = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = account.edit();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", user.getUsername());
            jsonObject.put("email", user.getEmail());
            jsonObject.put("sessionToken", user.getSessionToken());
            editor.putString(KEY, jsonObject.toString());
            editor.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public static User loadUser(Context context) {
        SharedPreferences account = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);
        String result = account.getString(KEY, null);
        User user = null;
        if (result != null) {
            user = JSONParser.parseLogedUser(result);
        }
        return user;
    }
}
