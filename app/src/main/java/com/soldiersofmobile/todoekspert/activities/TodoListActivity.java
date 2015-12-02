package com.soldiersofmobile.todoekspert.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.soldiersofmobile.todoekspert.App;
import com.soldiersofmobile.todoekspert.LoginManager;
import com.soldiersofmobile.todoekspert.R;
import com.soldiersofmobile.todoekspert.Todo;
import com.soldiersofmobile.todoekspert.TodoApi;
import com.soldiersofmobile.todoekspert.TodosResponse;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TodoListActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 123;
    private static final String LOG_TAG = TodoListActivity.class.getSimpleName();

    @Inject
    LoginManager loginManager;
    @Inject
    TodoApi todoApi;

    @Bind(R.id.todosListView)
    ListView todosListView;
    private ArrayAdapter<Todo> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.getTodoComponent().inject(this);

        if (loginManager.hasToLogin()) {
            //not logged
            goToLogin();
            return;
        }
        setContentView(R.layout.activity_todo_list);
        ButterKnife.bind(this);

        adapter = new ArrayAdapter<Todo>(this, R.layout.todo_item, R.id.itemCheckBox) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                CheckBox itemCheckBox = (CheckBox) view.findViewById(R.id.itemCheckBox);
                Button itemButton = (Button) view.findViewById(R.id.itemButton);


                Todo todo = getItem(position);

                itemCheckBox.setChecked(todo.isDone());
                itemCheckBox.setText(todo.getContent());
                itemButton.setEnabled(!todo.isDone());

                return view;
            }
        };
        todosListView.setAdapter(adapter);

    }

    private void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(getApplicationContext(), AddTodoActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.action_refresh:
                todoApi.getTodos(loginManager.getToken(), new Callback<TodosResponse>() {
                    @Override
                    public void success(TodosResponse todosResponse, Response response) {

                        adapter.clear();
                        adapter.addAll(todosResponse.results);

                        for (Todo todo : todosResponse.results) {
                            Log.d(LOG_TAG, todo.toString());

                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
                break;
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.logout_dialog);
                builder.setMessage(R.string.dialog_are_you_sure);
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        loginManager.logout();


                        goToLogin();
                    }
                });
                builder.setNegativeButton(android.R.string.no, null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Todo todo = (Todo) data.getSerializableExtra(AddTodoActivity.TODO);
            Log.d(LOG_TAG, "Result:" + resultCode + " data:" + todo);
        }

    }
}
