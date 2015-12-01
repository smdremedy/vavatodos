package com.soldiersofmobile.todoekspert;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.converter.GsonConverter;

public class LoginActivity extends AppCompatActivity {


    private static final String LOG_TAG = LoginActivity.class.getSimpleName();
    public static final String USER_ID = "userId";
    public static final String TOKEN = "token";

    @Bind(R.id.usernameEditText)
    EditText usernameEditText;
    @Bind(R.id.passwordEditText)
    EditText passwordEditText;
    @Bind(R.id.loginButton)
    Button loginButton;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        if(BuildConfig.DEBUG) {
            usernameEditText.setText("test");
            passwordEditText.setText("test");
        }
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
            login(username, password);
        }
    }

    private void login(String username, final String password) {

        AsyncTask<String, Integer, LoginResponse> asyncTask = new AsyncTask<String, Integer, LoginResponse>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loginButton.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected LoginResponse doInBackground(String... params) {

                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                        .create();

                RestAdapter.Builder builder = new RestAdapter.Builder();
                builder.setEndpoint("https://api.parse.com/1");
                builder.setConverter(new GsonConverter(gson));
                builder.setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL
                        : RestAdapter.LogLevel.NONE);
                RestAdapter adapter = builder.build();
                TodoApi todoApi = adapter.create(TodoApi.class);
                try {
                    return todoApi.login(params[0], params[1]);
                } catch (RetrofitError error) {
                    return null;
                }


            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                progressBar.setProgress(values[0]);
            }

            @Override
            protected void onPostExecute(LoginResponse result) {
                super.onPostExecute(result);
                if (result != null) {

                    SharedPreferences preferences
                            = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(USER_ID, result.getObjectId());
                    editor.putString(TOKEN, result.getSessionToken());
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, TodoListActivity.class);
                    startActivity(intent);
                    finish();
                }
                loginButton.setEnabled(true);
                progressBar.setVisibility(View.GONE);

            }
        };

        asyncTask.execute(username, password);

    }

}
