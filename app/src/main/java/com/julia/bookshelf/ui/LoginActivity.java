package com.julia.bookshelf.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.julia.bookshelf.R;

/**
 * Created by Julia on 12.01.2015.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    public static final int MIN_PASSWORD_LENGTH = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Button btnLogin = (Button) findViewById(R.id.btn_log_in);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        EditText txtEmail = (EditText) findViewById(R.id.txt_email);
        EditText txtPassword = (EditText) findViewById(R.id.txt_password);
        boolean isCorrectEmail = Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText()).matches();
        boolean isCorrectPassword = txtPassword.getText().length() > MIN_PASSWORD_LENGTH;
        if (isCorrectEmail & isCorrectPassword) {
            Log.i("bookshelf", "to send");
        } else {
            if (!isCorrectEmail) {
                txtEmail.setError(getString(R.string.incorrect_email));
            }
            if (!isCorrectPassword) {
                txtPassword.setError(getString(R.string.too_short_password));
            }
        }
    }
}
