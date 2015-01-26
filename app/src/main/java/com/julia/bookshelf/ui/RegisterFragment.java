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
    private EditText txtUsername;
    private EditText txtPassword;
    private EditText txtConfirmPassword;
    private EditText txtEmail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        txtUsername = (EditText) view.findViewById(R.id.txt_username);
        txtPassword = (EditText) view.findViewById(R.id.txt_password);
        txtConfirmPassword = (EditText) view.findViewById(R.id.txt_confirm_password);
        txtEmail = (EditText) view.findViewById(R.id.txt_email);

        Button btnRegister = (Button) view.findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegisterClicked();
            }
        });
    }

    private void onRegisterClicked() {
        if (InternetAccess.isInternetConnection(getActivity().getApplicationContext())) {
            if (isDataValid()) {
            }
            registerUser(getUsername(), getPassword(), getEmail());
        } else {
            InternetAccess.showNoInternetConnection(getActivity().getApplicationContext());
        }
    }

    private boolean isDataValid() {
        boolean isValidUsername = !getUsername().isEmpty();
        boolean isValidEmail = Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches();
        boolean isValidPassword = txtPassword.getText().length() > getResources().getInteger(R.integer.min_password_length);
        boolean isConfirmedPassword = getPassword().equals(getConfirmedPassword());

        if (!isValidUsername) {
            txtUsername.setError(getString(R.string.empty_username));
        }
        if (!isValidEmail) {
            txtEmail.setError(getString(R.string.incorrect_email));
        }
        if (!isValidPassword) {
            txtPassword.setError(getString(R.string.too_short_password));
        }
        if (!isConfirmedPassword) {
            txtConfirmPassword.setError(getString(R.string.not_confirmed_password));
        }
        return isValidEmail && isValidPassword && isConfirmedPassword && isValidUsername;
    }

    private String getEmail() {
        return txtEmail.getText().toString();
    }

    private String getConfirmedPassword() {
        return txtConfirmPassword.getText().toString();
    }

    private String getPassword() {
        return txtPassword.getText().toString();
    }

    private String getUsername() {
        return txtUsername.getText().toString();
    }

    private void registerUser(String username, String password, String email) {
        RegisterUserTask registerUserTask = new RegisterUserTask() {
            @Override
            protected void onPostExecute(User user) {
                if (user != null) {
                    PreferencesManager.saveUser(getActivity().getApplicationContext(), user);
                    startBookListActivity();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.user_already_exists), Toast.LENGTH_SHORT).show();
                }
            }
        };
        registerUserTask.execute(username, password, email);
    }

    private void startBookListActivity() {
        Intent intent = new Intent(getActivity().getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
