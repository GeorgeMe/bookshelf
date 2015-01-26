package com.julia.bookshelf.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.julia.bookshelf.model.data.User;
import com.julia.bookshelf.model.pref.PreferencesManager;

/**
 * Created by Julia on 21.01.2015.
 */
public class LoginActivity extends Activity implements LoginFragment.OnRegisterClickedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User user = PreferencesManager.loadUser(getApplicationContext());
        if (user != null) {
            startBookListActivity();
        } else {
            showFragment(LoginFragment.newInstance(), false);
        }
    }

    private void startBookListActivity() {
        Intent intent = new Intent(this, BookListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRegisterClicked() {
        showFragment(new RegisterFragment(), true);
    }

    private void showFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }
}
