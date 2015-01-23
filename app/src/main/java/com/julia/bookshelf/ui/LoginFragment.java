package com.julia.bookshelf.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.julia.bookshelf.R;
import com.julia.bookshelf.model.data.User;
import com.julia.bookshelf.model.http.InternetAccess;
import com.julia.bookshelf.model.pref.PreferencesManager;
import com.julia.bookshelf.model.tasks.LoginUserTask;

/**
 * Created by Julia on 21.01.2015.
 */
public class LoginFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(
                R.layout.login, container, false);
        Button btnLogin = (Button) view.findViewById(R.id.btn_log_in);
        TextView txtRegister = (TextView) view.findViewById(R.id.txt_sign_up);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndLoginUser(view);
            }
        });
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRegisterFragment();
            }
        });
        return view;
    }

    private void createRegisterFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        RegisterFragment registerFragment = new RegisterFragment();
        fragmentTransaction.replace(android.R.id.content, registerFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void checkAndLoginUser(View view) {
        EditText txtUsername = (EditText) view.findViewById(R.id.txt_username);
        EditText txtPassword = (EditText) view.findViewById(R.id.txt_password);

        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();

        boolean isEmptyUsername = username.isEmpty();
        boolean isEnoughPasswordLength = password.length() > getResources().getInteger(R.integer.min_password_length);
        if (isEnoughPasswordLength & !isEmptyUsername) {
            if (InternetAccess.isInternetConnection(getActivity().getApplicationContext())) {
                loginUser(username, password);
            } else {
                InternetAccess.showNoInternetConnection(getActivity().getApplicationContext());
            }
        } else {
            if (!isEnoughPasswordLength) {
                txtPassword.setError(getString(R.string.too_short_password));
            }
            if (isEmptyUsername) {
                txtUsername.setError(getString(R.string.empty_username));
            }
        }
    }

    private void loginUser(final String username, final String password) {
        LoginUserTask loginUserTask = new LoginUserTask(username, password) {
            @Override
            protected void onPostExecute(User user) {
                if (user == null) {
                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.incorrect_username_or_password), Toast.LENGTH_SHORT).show();
                } else {
                    PreferencesManager.saveUser(getActivity().getApplicationContext(), user);
                    createBookListActivity();
                }
            }
        };
        loginUserTask.execute();
    }

    private void createBookListActivity() {
        Intent intent = new Intent(getActivity().getApplicationContext(), BookListActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
