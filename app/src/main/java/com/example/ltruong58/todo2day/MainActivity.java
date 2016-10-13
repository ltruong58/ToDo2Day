package com.example.ltruong58.todo2day;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBHelper database;
    private List<Task> taskList;
    private TaskListAdapter taskListAdapter;

    private EditText taskEditText;
    private ListView taskListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FOr now, delete the old db
      //  this.deleteDatabase(DBHelper.DATABASE_TABLE);

        // Make a DBHelper
        database = new DBHelper(this);

       // database.askTask(new Task("Testing task", 0));
        //database.askTask(new Task("Testing task 2", 1));

        taskList = database.getAllTask();


        // Create custom task list adapter
        // Associate the adapter with context, the layout and the List

        taskListAdapter = new TaskListAdapter(this, R.layout.task_item, taskList);

        // Connect the list view with layout

        taskListView = (ListView) findViewById(R.id.taskListView);
        taskEditText = (EditText) findViewById(R.id.taskEditText);
        // Associate the adapter w list view

        taskListView.setAdapter(taskListAdapter);


    }

    public void askTask(View view)
    {
        String description = taskEditText.getText().toString();
        if(description.isEmpty() ){
            Toast.makeText(this, "Task description can't be empty.", Toast.LENGTH_SHORT).show();
        }
        else {
            Task newTask = new Task(description, 0);
            database.askTask(newTask);
            taskListAdapter.add(newTask);
            taskEditText.setText("");
        }

    }

    public void changeTaskStatus(View view)
    {
        if(view instanceof CheckBox) {
            CheckBox selectedCheckBox = (CheckBox) view;
            Task selectedTask = (Task) selectedCheckBox.getTag();
            selectedTask.setIsDone(selectedCheckBox.isChecked()? 1 : 0);
            database.updateTask(selectedTask);
        }
    }

    public void clearAllTasks(View view)
    {
        // Clear the list
        taskList.clear();
        database.deleteAllTasks();
        taskListAdapter.notifyDataSetChanged();
    }
}
