package com.example.leopardo.taskorganizer;

import com.example.leopardo.taskorganizer.TaskDataBase.TaskEntry;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.Context;
import android.database.sqlite.*;
import android.content.ContentValues;
import android.widget.EditText;
import java.util.UUID;

public class add_task extends AppCompatActivity {

    EditText mEditTitle;
    EditText mEditDate;
    EditText mEditDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button fab = (Button) findViewById(R.id.addTaskBut);
        mEditTitle = (EditText)findViewById(R.id.editTitle);
        mEditDate = (EditText)findViewById(R.id.editDate);
        mEditDetails = (EditText)findViewById(R.id.editDetails);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context context = getApplicationContext();

                String id = UUID.randomUUID().toString();

                TaskDataBaseHelper mDbHelper = new TaskDataBaseHelper(context);
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(TaskEntry.COLUMN_NAME_ENTRY_ID, id);
                values.put(TaskEntry.COLUMN_NAME_TITLE, mEditTitle.getText().toString());
                values.put(TaskEntry.COLUMN_NAME_DATE, mEditDate.getText().toString());
                values.put(TaskEntry.COLUMN_NAME_DETAILS, mEditDetails.getText().toString());

                long newRowId;
                newRowId = db.insert(
                        TaskEntry.TABLE_NAME,
                        null,
                        values);

                if(newRowId != -1){
                    CharSequence text = "Task Saved";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }else{
                    CharSequence text = "Error saving task";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
