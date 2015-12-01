package com.soldiersofmobile.todoekspert;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddTodoActivity extends AppCompatActivity {

    public static final int RESULT_ADD = RESULT_FIRST_USER;
    public static final int RESULT_EDIT = RESULT_FIRST_USER + 1;
    public static final String CONTENT = "content";
    public static final String DONE = "done";
    public static final String TODO = "todo";

    @Bind(R.id.contentEditText)
    EditText contentEditText;
    @Bind(R.id.doneCheckBox)
    CheckBox doneCheckBox;
    @Bind(R.id.addButton)
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todoctivity);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.addButton)
    public void createTodo() {

        Intent intent = new Intent();

        Todo todo = new Todo();

        todo.content = contentEditText.getText().toString();
        todo.done = doneCheckBox.isChecked();

        intent.putExtra(TODO, todo);


        setResult(RESULT_OK, intent);
        finish();

    }
}
