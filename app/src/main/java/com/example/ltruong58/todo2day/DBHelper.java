package com.example.ltruong58.todo2day;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

class DBHelper extends SQLiteOpenHelper {

    //TASK 1: DEFINE THE DATABASE VERSION, NAME AND TABLE NAME
    private static final String DATABASE_NAME = "ToDo2Day";
    static final String DATABASE_TABLE = "Tasks";
    private static final int DATABASE_VERSION = 1;


    //TASK 2: DEFINE THE FIELDS (COLUMN NAMES) FOR THE TABLE
    private static final String KEY_FIELD_ID = "id";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_IS_DONE = "is_done";


    public DBHelper (Context context){
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase database){
        String table = "CREATE TABLE " + DATABASE_TABLE + "("
                + KEY_FIELD_ID + " INTEGER PRIMARY KEY, "
                + FIELD_DESCRIPTION + " TEXT, "
                + FIELD_IS_DONE + " INTEGER" + ")";
        database.execSQL (table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database,
                          int oldVersion,
                          int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(database);
    }

    // Create a method to add a brand new task to the database:
    public void askTask(Task newTask)
    {
        // Step 1: Create a reference to our database
        SQLiteDatabase db = this.getWritableDatabase();

        // Step 2: make a key-value pair for each value you want to insert
        ContentValues values = new ContentValues();
        values.put(KEY_FIELD_ID, newTask.getId());
        values.put(FIELD_DESCRIPTION, newTask.getDescription());
        values.put(FIELD_IS_DONE, newTask.getIsDone());

        // Step 3: Insert value into db
        db.insert(DATABASE_TABLE, null, values);

        db.close();
    }

    // Create a method to get all the tasks in the database
    public ArrayList<Task> getAllTask()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        // New empty arrayList
        ArrayList<Task> newTask = new ArrayList<>();

        // Query all records
        // The return type of query is Cursor
       // Cursor results = db.query(DATABASE_TABLE, new String[] {KEY_FIELD_ID, FIELD_DESCRIPTION, FIELD_IS_DONE});
        Cursor results = db.query(DATABASE_TABLE, null, null, null, null, null, null);
        // Loop through the results , create Task object
        if(results.moveToFirst())
        {
            do{
                int id = results.getInt(0);
                String description = results.getString(1);
                int isDone = results.getInt(2);
                newTask.add(new Task(id, description, isDone));

            } while (results.moveToNext());
        }
        db.close();
        return newTask;
    }

}
