package com.julia.bookshelf.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.julia.bookshelf.R;
import com.julia.bookshelf.model.data.User;
import com.julia.bookshelf.model.http.InternetAccess;
import com.julia.bookshelf.model.tasks.LoginUserTask;

/**
 * Created by Julia on 12.01.2015.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initView();
    }

    private void initView() {
        Button btnLogin = (Button) findViewById(R.id.btn_log_in);
        TextView txtRegister = (TextView) findViewById(R.id.txt_sign_up);
        btnLogin.setOnClickListener(this);
        txtRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_sign_up: {
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
            }
            case R.id.btn_log_in: {
                EditText txtUsername = (EditText) findViewById(R.id.txt_username);
                EditText txtPassword = (EditText) findViewById(R.id.txt_password);

                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();

                boolean isEmptyUsername = username.isEmpty();
                boolean isEnoughPasswordLength = password.length() > getResources().getInteger(R.integer.min_password_length);

                if (isEnoughPasswordLength & !isEmptyUsername) {
                    if (InternetAccess.isInternetConnection(getApplicationContext())) {
                        loginUser(username, password);
                    } else {
                        InternetAccess.showNoInternetConnection(getApplicationContext());
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
        }
    }

    private void loginUser(final String username, final String password) {
        LoginUserTask loginUserTask = new LoginUserTask(username, password) {
            @Override
            protected void onPostExecute(User user) {
                if (user == null) {
                    Toast.makeText(getApplicationContext(), getString(R.string.incorrect_username_or_password), Toast.LENGTH_SHORT).show();
                } else {
                    createBookListActivity();
                    Log.i("BOOKSHELF", "User is not null");
                }
            }
        };
        loginUserTask.execute();
    }

    private void createBookListActivity() {
        Intent intent = new Intent(this, BookListActivity.class);
        startActivity(intent);
    }
}
