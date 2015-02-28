package com.julia.bookshelf.ui.fragments;

import android.app.Fragment;
import android.content.Context;
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
import com.julia.bookshelf.model.tasks.LoginUserTask;
import com.julia.bookshelf.ui.activity.HomeActivity;

/**
 * Created by Julia on 21.01.2015.
 */
public class LoginFragment extends BaseFragment {

    public interface OnRegisterClickedListener {
        public void onRegisterClicked();
    }

    private EditText txtUsername;
    private EditText txtPassword;

    public static Fragment newInstance() {
        return new LoginFragment();
    }

    public static Fragment newInstance(int value) {
        LoginFragment fragment = new LoginFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("key", value);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        txtUsername = (EditText) view.findViewById(R.id.txt_username);
        txtPassword = (EditText) view.findViewById(R.id.txt_password);

        Button btnLogin = (Button) view.findViewById(R.id.btn_log_in);
        TextView txtRegister = (TextView) view.findViewById(R.id.txt_sign_up);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginClicked();
            }
        });
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().onRegisterClicked();
            }
        });
    }

    private void onLoginClicked() {
        if (InternetAccess.isInternetConnection(getContext())) {
            if (isDataValid()) {
                loginUser(getUsername(), getPassword());
            }
        } else {
            InternetAccess.showNoInternetConnection(getContext());
        }
    }

    private boolean isDataValid() {
        boolean isUsernameValid = !getUsername().isEmpty();
        boolean isPasswordValid =
                getPassword().length() > getResources().getInteger(R.integer.min_password_length);

        if (!isPasswordValid) {
            txtPassword.setError(getString(R.string.too_short_password));
        }
        if (!isUsernameValid) {
            txtUsername.setError(getString(R.string.empty_username));
        }

        return isUsernameValid && isPasswordValid;
    }



    private String getPassword() {
        return txtPassword.getText().toString();
    }

    private String getUsername() {
        return txtUsername.getText().toString();
    }

    private void loginUser(final String username, final String password) {
        LoginUserTask loginUserTask = new LoginUserTask(username, password) {
            @Override
            protected void onPostExecute(User user) {
                if (user == null) {
                    Toast.makeText(getContext(), getString(R.string.incorrect_username_or_password), Toast.LENGTH_SHORT).show();
                } else {
                    getPreferences().saveUser(user);
                    startBookListActivity();
                }
            }
        };
        loginUserTask.execute();
    }

    private void startBookListActivity() {
        Intent intent = new Intent(getContext(), HomeActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private OnRegisterClickedListener getListener() {
        return (OnRegisterClickedListener) getActivity();
    }
}
