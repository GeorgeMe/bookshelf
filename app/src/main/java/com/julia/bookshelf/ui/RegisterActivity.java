package com.julia.bookshelf.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.julia.bookshelf.R;
import com.julia.bookshelf.model.data.User;
import com.julia.bookshelf.model.http.InternetAccess;
import com.julia.bookshelf.model.tasks.RegisterUserTask;

/**
 * Created by Julia on 16.01.2015.
 */
public class RegisterActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Button btnRegister = (Button) findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        EditText txtUsername = (EditText) findViewById(R.id.txt_username);
        EditText txtPassword = (EditText) findViewById(R.id.txt_password);
        EditText txtConfirmPassword = (EditText) findViewById(R.id.txt_confirm_password);
        EditText txtEmail = (EditText) findViewById(R.id.txt_email);

        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        String confirmPassword = txtConfirmPassword.getText().toString();
        String email = txtEmail.getText().toString();

        boolean isEmptyUsername = username.isEmpty();
        boolean isCorrectEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        boolean isEnoughPasswordLength = txtPassword.getText().length() > getResources().getInteger(R.integer.min_password_length);
        boolean isTheSamePassword = password.equals(confirmPassword);
        if (isCorrectEmail & isEnoughPasswordLength & isTheSamePassword & !isEmptyUsername) {

            if (InternetAccess.isInternetConnection(getApplicationContext())) {
                RegisterUserTask registerUserTask = new RegisterUserTask() {
                    @Override
                    protected void onPostExecute(User user) {
                        if (user != null) {
                            createBookListActivity();
                        } else {
                            Toast.makeText(getApplicationContext(), getString(R.string.user_already_exists), Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                registerUserTask.execute(username, password, email);
            } else {
                InternetAccess.showNoInternetConnection(getApplicationContext());
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

    private void createBookListActivity() {
        Intent intent = new Intent(this, BookListActivity.class);
        startActivity(intent);
    }
}
