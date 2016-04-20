package com.example.leopardo.taskorganizer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.database.sqlite.*;
import android.database.Cursor;
import android.widget.ListView;

import java.util.List;
import java.util.ArrayList;
import android.media.MediaPlayer;

public class ViewTasks extends AppCompatActivity {
    MediaPlayer song;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tasks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button audioStopBut = (Button) findViewById(R.id.stopAudioBut);
        Button backAddBut = (Button) findViewById(R.id.backToAddBut);
        Button backHomeBut = (Button) findViewById(R.id.backToStartBut);

        ListView listView = (ListView) findViewById(R.id.taskList);

        song = MediaPlayer.create(ViewTasks.this, R.raw.snoopy);
        song.setLooping(true);
        song.start();

        Context context = getApplicationContext();
        TaskDataBaseHelper mDbHelper = new TaskDataBaseHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String selectQuery = "SELECT  * FROM Task";
        Cursor cursor      = db.rawQuery(selectQuery, null);
        List<String> tasks = new ArrayList<String>();

        if (cursor.moveToFirst()) {
            do {
            tasks.add(cursor.getString(2) + "       " + cursor.getString(3));
            } while (cursor.moveToNext());
        }

        cursor.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, tasks);
        listView.setAdapter(adapter);

        audioStopBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (song !=null) {
                    song.stop();
                    song.release();
                    song = null;
                }
            }
        });

        backAddBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        ViewTasks.this,
                        add_task.class);
                startActivity(i);
            }
        });

        backHomeBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        ViewTasks.this,
                        Home.class);
                startActivity(i);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
