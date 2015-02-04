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
    private static final String PREFS_NAME = "book-shelf-pref";
    private static final String KEY_USER = "user";
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

    public void clearUser(){
        getSharedPreferences().edit().remove(KEY_USER).commit();
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
}
