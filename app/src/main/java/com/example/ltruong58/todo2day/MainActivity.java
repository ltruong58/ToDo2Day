package com.example.ltruong58.todo2day;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FOr now, delete the old db
        this.deleteDatabase(DBHelper.DATABASE_TABLE);
        // Make a DBHelper
        DBHelper db = new DBHelper(this);
        // Let's make a new task and add to database
        db.askTask(new Task(1, "Go home", 0));
        db.askTask(new Task(2, "Sleep", 0));
        db.askTask(new Task(3, "Sleep 2", 0));
        db.askTask(new Task(4, "Sleep 3", 0));

        ArrayList<Task> allTasks = db.getAllTask();
        for(Task singleTask : allTasks)
            Log.i("DATABASE TASK", singleTask.toString());

    }
}
