package com.julia.bookshelf.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.julia.bookshelf.R;
import com.julia.bookshelf.model.data.User;
import com.julia.bookshelf.model.http.InternetAccess;
import com.julia.bookshelf.model.pref.PreferencesManager;
import com.julia.bookshelf.model.tasks.RegisterUserTask;

/**
 * Created by Julia on 21.01.2015.
 */
public class RegisterFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.register, container, false);
        Button btnRegister = (Button) view.findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndRegisterUser(view);
            }
        });
        return view;
    }

    private void checkAndRegisterUser(View view) {
        EditText txtUsername = (EditText) view.findViewById(R.id.txt_username);
        EditText txtPassword = (EditText) view.findViewById(R.id.txt_password);
        EditText txtConfirmPassword = (EditText) view.findViewById(R.id.txt_confirm_password);
        EditText txtEmail = (EditText) view.findViewById(R.id.txt_email);

        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        String confirmPassword = txtConfirmPassword.getText().toString();
        String email = txtEmail.getText().toString();

        boolean isEmptyUsername = username.isEmpty();
        boolean isCorrectEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        boolean isEnoughPasswordLength = txtPassword.getText().length() > getResources().getInteger(R.integer.min_password_length);
        boolean isTheSamePassword = password.equals(confirmPassword);

        if (isCorrectEmail & isEnoughPasswordLength & isTheSamePassword & !isEmptyUsername) {

            if (InternetAccess.isInternetConnection(getActivity().getApplicationContext())) {
                registerUser(username, password, email);
            } else {
                InternetAccess.showNoInternetConnection(getActivity().getApplicationContext());
            }
        } else {
            if (isEmptyUsername) {
                txtUsername.setError(getString(R.string.empty_username));
            }
            if (!isCorrectEmail) {
                txtEmail.setError(getString(R.string.incorrect_email));
            }
            if (!isEnoughPasswordLength) {
                txtPassword.setError(getString(R.string.too_short_password));
            }
            if (!isTheSamePassword) {
                txtConfirmPassword.setError(getString(R.string.not_confirmed_password));
            }
        }
    }

    private void registerUser(String username, String password, String email) {
        RegisterUserTask registerUserTask = new RegisterUserTask() {
            @Override
            protected void onPostExecute(User user) {
                if (user != null) {
                    PreferencesManager.saveUser(getActivity().getApplicationContext(), user);
                    createBookListActivity();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.user_already_exists), Toast.LENGTH_SHORT).show();
                }
            }
        };
        registerUserTask.execute(username, password, email);
    }

    private void createBookListActivity() {
        Intent intent = new Intent(getActivity().getApplicationContext(), BookListActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
