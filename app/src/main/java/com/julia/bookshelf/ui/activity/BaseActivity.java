package com.julia.bookshelf.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.julia.bookshelf.model.pref.PreferencesManager;

/**
 * Created by Julia on 01.02.2015.
 */
public class BaseActivity extends Activity {

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
