package com.julia.bookshelf.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.julia.bookshelf.model.data.User;
import com.julia.bookshelf.ui.fragments.LoginFragment;
import com.julia.bookshelf.ui.fragments.RegisterFragment;

public class LoginActivity extends BaseActivity implements LoginFragment.OnRegisterClickedListener, RegisterFragment.OnLoginClickedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User user = getPreferences().loadUser();
        if (user != null) {
            startBookListActivity();
        } else {
            showFragment(LoginFragment.newInstance(), false);
        }
    }

    private void startBookListActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
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

    @Override
    public void onLoginClicked() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
