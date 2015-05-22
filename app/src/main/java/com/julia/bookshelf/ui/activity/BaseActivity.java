package com.julia.bookshelf.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;


import com.julia.bookshelf.model.pref.PreferencesManager;

public class BaseActivity extends AppCompatActivity {

    private PreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferencesManager = new PreferencesManager(getApplicationContext());
    }

    @NonNull
    public PreferencesManager getPreferences() {
        return preferencesManager;
    }
}
