package com.soldiersofmobile.todoekspert.activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.soldiersofmobile.todoekspert.App;
import com.soldiersofmobile.todoekspert.BuildConfig;
import com.soldiersofmobile.todoekspert.LoginManager;
import com.soldiersofmobile.todoekspert.R;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginManager.LoginCallback {


    private static final String LOG_TAG = LoginActivity.class.getSimpleName();

    @Bind(R.id.usernameEditText)
    EditText usernameEditText;
    @Bind(R.id.passwordEditText)
    EditText passwordEditText;
    @Bind(R.id.loginButton)
    Button loginButton;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Inject
    LoginManager loginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        App.getTodoComponent().inject(this);

        if(BuildConfig.DEBUG) {
            usernameEditText.setText("test");
            passwordEditText.setText("test");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loginManager.setLoginCallback(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        loginManager.setLoginCallback(null);
    }

    @OnClick(R.id.loginButton)
    public void tryToLogin() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        boolean hasErrors = false;

        if (username.isEmpty()) {
            usernameEditText.setError(getString(R.string.cannot_be_empty));
            hasErrors = true;
        }

        if (password.isEmpty()) {
            passwordEditText.setError(getString(R.string.cannot_be_empty));
            hasErrors = true;
        }

        if (!hasErrors) {
            loginManager.login(username, password);
        }
    }



    @Override
    public void loginDone() {
        Intent intent = new Intent(LoginActivity.this, TodoListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginError(String message) {

        loginButton.setEnabled(true);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void beforeLogin() {

        loginButton.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
    }
}
