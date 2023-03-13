package com.example.scheduling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> arr=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=findViewById(R.id.listview);
        arr.add("First Come First Serve");
        arr.add("Shortest Job First");
        arr.add("Shortest Remaining Job First");
        arr.add("Round Robin");
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arr);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        startActivity(new Intent(MainActivity.this,FCFSactivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this,SJFactivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this,SRTFactivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this,RRactivity.class));
                }
            }
        });
    }
}