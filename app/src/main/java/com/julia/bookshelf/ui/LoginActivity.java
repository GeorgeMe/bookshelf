package com.julia.bookshelf.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.julia.bookshelf.model.data.User;
import com.julia.bookshelf.model.pref.PreferencesManager;

/**
 * Created by Julia on 21.01.2015.
 */
public class LoginActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User user = PreferencesManager.loadUser(getApplicationContext());
        if (user != null) {
            createBookListActivity();
        } else {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            LoginFragment loginFragment = new LoginFragment();
            fragmentTransaction.replace(android.R.id.content, loginFragment);
            fragmentTransaction.commit();
        }
    }

    private void createBookListActivity() {
        Intent intent = new Intent(this, BookListActivity.class);
        startActivity(intent);
        finish();
    }
}
