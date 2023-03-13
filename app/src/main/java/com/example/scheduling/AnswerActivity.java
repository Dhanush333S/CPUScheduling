package com.example.scheduling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class AnswerActivity extends AppCompatActivity {

    TableLayout table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        ArrayList<String> name=bundle.getStringArrayList("name");
        ArrayList<Integer> burst=bundle.getIntegerArrayList("burst");
        ArrayList<Integer> arrival=bundle.getIntegerArrayList("arrival");
        ArrayList<Integer> waiting=bundle.getIntegerArrayList("waiting");
        ArrayList<Integer> turnAround=bundle.getIntegerArrayList("turnAround");
        ArrayList<Integer> completion=bundle.getIntegerArrayList("completion");


        Log.d("clash", completion.toString());

        table=findViewById(R.id.table);

        for(int i=0;i<name.size();i++) {
            TableRow row = new TableRow(AnswerActivity.this);
            TextView newName = new TextView(AnswerActivity.this);
            newName.setText(name.get(i));
            newName.setBackgroundColor(Color.WHITE);
            newName.setPadding(3,3,3,3);
            newName.setTextSize(16);

            newName.setGravity(Gravity.CENTER);
            row.addView(newName);

            TextView newArrival = new TextView(AnswerActivity.this);
            newArrival.setText(arrival.get(i).toString());
            newArrival.setGravity(Gravity.CENTER);
            newArrival.setBackgroundColor(Color.WHITE);
            newArrival.setPadding(3,3,3,3);
            newArrival.setTextSize(16);
            row.addView(newArrival);

            TextView newBurst = new TextView(AnswerActivity.this);
            newBurst.setText(burst.get(i).toString());
            newBurst.setGravity(Gravity.CENTER);
            newBurst.setBackgroundColor(Color.WHITE);
            newBurst.setPadding(3,3,3,3);
            newBurst.setTextSize(16);
            row.addView(newBurst);

            TextView newWaiting = new TextView(AnswerActivity.this);
            newWaiting.setText(waiting.get(i).toString());
            newWaiting.setGravity(Gravity.CENTER);
            newWaiting.setBackgroundColor(Color.WHITE);
            newWaiting.setPadding(3,3,3,3);
            newWaiting.setTextSize(16);
            row.addView(newWaiting);

            TextView newTurn=new TextView(AnswerActivity.this);
            newTurn.setText(turnAround.get(i).toString());
            newTurn.setGravity(Gravity.CENTER);
            newTurn.setBackgroundColor(Color.WHITE);
            newTurn.setPadding(3,3,3,3);
            newTurn.setTextSize(16);
            row.addView(newTurn);

            TextView newCompletion = new TextView(AnswerActivity.this);
            newCompletion.setText(completion.get(i).toString());
            newCompletion.setGravity(Gravity.CENTER);
            newCompletion.setBackgroundColor(Color.WHITE);
            newCompletion.setPadding(3,3,3,3);
            newCompletion.setTextSize(16);
            row.addView(newCompletion);

            table.addView(row);
        }
    }
}