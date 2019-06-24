package com.example.dblocal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {

    ListView mListView;
    FloatingActionButton fabButton;

    DataBase myDataBadse;
    ArrayList<Student> studentList;
    StudentListAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        fabButton = findViewById(R.id.fabButton);
        mListView = findViewById(R.id.mListView);
        studentList = new ArrayList<>();
        myDataBadse = new DataBase(this);
        loadData();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(DisplayActivity.this, MainActivity.class);
                i.putExtra("index",position);
                i.putExtra("Tag",999);
                startActivity(i);
                finish();
            }
        });



        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DisplayActivity.this,MainActivity.class));
            }
        });



    }

    private void loadData(){
        studentList = myDataBadse.getAllData();
        myAdapter = new StudentListAdapter(this, studentList);
        mListView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

    }

}
