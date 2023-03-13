package com.example.scheduling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FCFSactivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    EditText name,arrival,burst;
    ArrayList<String> strName=new ArrayList<>();
    ArrayList<Integer> arrivalInt=new ArrayList<>();
    ArrayList<Integer> burstInt=new ArrayList<>();

    ArrayList<Integer> waiting=new ArrayList<>();
    ArrayList<Integer> turnAround=new ArrayList<>();
    ArrayList<Integer> completion=new ArrayList<>();
    TableLayout tableLayout;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcfsactivity);

        floatingActionButton=findViewById(R.id.floatingAdd);
        tableLayout =findViewById(R.id.tableLayout);
        name=findViewById(R.id.processName);
        arrival=findViewById(R.id.Arrival);
        burst=findViewById(R.id.Burst);
        button=findViewById(R.id.button);

        TableRow row=new TableRow(FCFSactivity.this);

        TextView newName= new TextView(FCFSactivity.this);
        newName.setText("Name");
        newName.setGravity(Gravity.CENTER);
        TextView newArrival=new TextView(FCFSactivity.this);
        newArrival.setText("Arrival");
        newArrival.setGravity(Gravity.CENTER);
        TextView newBurst =new TextView(FCFSactivity.this);
        newBurst.setText("Burst");
        newBurst.setGravity(Gravity.CENTER);
        row.addView(newName);
        row.addView(newArrival);
        row.addView(newBurst);

        tableLayout.addView(row);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uploadName=name.getText().toString();
                String uploadArrival=arrival.getText().toString();
                String uploadBurst=burst.getText().toString();
                if(uploadName.isEmpty() || uploadArrival.isEmpty() || uploadBurst.isEmpty()) {
                    Toast.makeText(FCFSactivity.this, "Fill in the Credentials", Toast.LENGTH_SHORT).show();
                    return;
                }

                TableRow row=new TableRow(FCFSactivity.this);

                TextView newName= new TextView(FCFSactivity.this);
                newName.setText(uploadName);
                newName.setGravity(Gravity.CENTER);
                strName.add(uploadName);
                TextView newArrival=new TextView(FCFSactivity.this);
                newArrival.setText(uploadArrival);
                newArrival.setGravity(Gravity.CENTER);
                arrivalInt.add(Integer.parseInt(uploadArrival));
                TextView newBurst =new TextView(FCFSactivity.this);
                newBurst.setText(uploadBurst);
                newBurst.setGravity(Gravity.CENTER);
                burstInt.add(Integer.parseInt(uploadBurst));
                row.addView(newName);
                row.addView(newArrival);
                row.addView(newBurst);

                tableLayout.addView(row);

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] processes=strName.toArray(new String[0]);
                int n = processes.length;
                // Burst time of all processes
                int[] burst_time = new int[burstInt.size()];
                for (int i = 0; i < burst_time.length; i++) {
                    burst_time[i] = burstInt.get(i);
                }
                // Arrival time of all processes
                int[] arrival_time = new int[arrivalInt.size()];
                for (int i = 0; i < arrival_time.length; i++) {
                    arrival_time[i] = arrivalInt.get(i);
                }

                findavgTime(processes, n, burst_time, arrival_time);

                Intent intent=new Intent(FCFSactivity.this,AnswerActivity.class);
                intent.putStringArrayListExtra("name",strName);
                intent.putIntegerArrayListExtra("burst",burstInt);
                intent.putIntegerArrayListExtra("arrival",arrivalInt);
                intent.putIntegerArrayListExtra("waiting",waiting);
                intent.putIntegerArrayListExtra("turnAround",turnAround);
                intent.putIntegerArrayListExtra("completion",completion);

                Log.d("burst",turnAround.toString());

                startActivity(intent);

            }
        });

    }

     void findWaitingTime(String processes[], int n, int bt[], int wt[], int at[])
    {
        int service_time[] = new int[n];
        service_time[0] = at[0];
        wt[0] = 0;

        // calculating waiting time
        for (int i = 1; i < n ; i++)
        {
            //representing wasted time in queue
            int wasted=0;
            // Add burst time of previous processes
            service_time[i] = service_time[i-1] + bt[i-1];

            // Find waiting time for current process =
            // sum - at[i]
            wt[i] = service_time[i] - at[i];

            // If waiting time for a process is in negative
            // that means it is already in the ready queue
            // before CPU becomes idle so its waiting time is 0
            // wasted time is basically time for process to wait after a process is over
            if (wt[i] < 0) {
                wasted = Math.abs(wt[i]);
                wt[i] = 0;
            }
            //Add wasted time
            service_time[i] = service_time[i] + wasted;
        }
    }

    // Function to calculate turn around time
     void findTurnAroundTime(String processes[], int n, int bt[],
                                   int wt[], int tat[])
    {
        // Calculating turnaround time by adding bt[i] + wt[i]
        for (int i = 0; i < n ; i++)
            tat[i] = bt[i] + wt[i];
    }

    // Function to calculate average waiting and turn-around
// times.
     void findavgTime(String  processes[], int n, int bt[], int at[])
    {
        int wt[] = new int[n], tat[] = new int[n];

        // Function to find waiting time of all processes
        findWaitingTime(processes, n, bt, wt, at);

        // Function to find turn around time for all processes
        findTurnAroundTime(processes, n, bt, wt, tat);
        int total_wt = 0, total_tat = 0;
        for (int i = 0 ; i < n ; i++)
        {
            total_wt = total_wt + wt[i];
            total_tat = total_tat + tat[i];
            int compl_time = tat[i] + at[i];
            waiting.add(wt[i]);
            turnAround.add(tat[i]);
            completion.add(compl_time);
        }
    }

}