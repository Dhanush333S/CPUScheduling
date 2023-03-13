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

public class RRactivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    EditText name,arrival,burst,time;
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
        setContentView(R.layout.activity_rractivity);

        floatingActionButton=findViewById(R.id.floatingAdd);
        tableLayout =findViewById(R.id.tableLayout);
        name=findViewById(R.id.processName);
        arrival=findViewById(R.id.Arrival);
        burst=findViewById(R.id.Burst);
        button=findViewById(R.id.button);
        time=findViewById(R.id.time);

        TableRow row=new TableRow(RRactivity.this);

        TextView newName= new TextView(RRactivity.this);
        newName.setText("Name");
        newName.setGravity(Gravity.CENTER);
        TextView newArrival=new TextView(RRactivity.this);
        newArrival.setText("Arrival");
        newArrival.setGravity(Gravity.CENTER);
        TextView newBurst =new TextView(RRactivity.this);
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
                    Toast.makeText(RRactivity.this, "Fill in the Credentials", Toast.LENGTH_SHORT).show();
                    return;
                }

                TableRow row=new TableRow(RRactivity.this);

                TextView newName= new TextView(RRactivity.this);
                newName.setText(uploadName);
                newName.setGravity(Gravity.CENTER);
                strName.add(uploadName);
                TextView newArrival=new TextView(RRactivity.this);
                newArrival.setText(uploadArrival);
                newArrival.setGravity(Gravity.CENTER);
                arrivalInt.add(Integer.parseInt(uploadArrival));
                TextView newBurst =new TextView(RRactivity.this);
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
                if (time.getText().toString().isEmpty()) {
                    Toast.makeText(RRactivity.this, "Enter Time Quantum", Toast.LENGTH_SHORT).show();
                    return;
                }

                int n,tq, timer = 0, maxProccessIndex = 0;
                float avgWait = 0, avgTT = 0;
                tq = Integer.parseInt(time.getText().toString());
                n = strName.size();
                int arrival[] = new int[n];
                int burst[] = new int[n];
                int wait[] = new int[n];
                int turn[] = new int[n];
                int queue[] = new int[n];
                int temp_burst[] = new int[n];
                boolean complete[] = new boolean[n];


                for(int i = 0; i < n; i++) {
                    arrival[i] = arrivalInt.get(i);
                    burst[i] = burstInt.get(i);
                    temp_burst[i] = burst[i];
                }

                for(int i = 0; i < n; i++){    //Initializing the queue and complete array
                    complete[i] = false;
                    queue[i] = 0;
                }
                while(timer < arrival[0])    //Incrementing Timer until the first process arrives
                    timer++;
                queue[0] = 1;

                while(true){
                    boolean flag = true;
                    for(int i = 0; i < n; i++){
                        if(temp_burst[i] != 0){
                            flag = false;
                            break;
                        }
                    }
                    if(flag)
                        break;

                    for(int i = 0; (i < n) && (queue[i] != 0); i++){
                        int ctr = 0;
                        while((ctr < tq) && (temp_burst[queue[0]-1] > 0)){
                            temp_burst[queue[0]-1] -= 1;
                            timer += 1;
                            ctr++;

                            //Updating the ready queue until all the processes arrive
                            checkNewArrival(timer, arrival, n, maxProccessIndex, queue);
                        }
                        if((temp_burst[queue[0]-1] == 0) && (complete[queue[0]-1] == false)){
                            turn[queue[0]-1] = timer;        //turn currently stores exit times
                            complete[queue[0]-1] = true;
                        }

                        //checks whether or not CPU is idle
                        boolean idle = true;
                        if(queue[n-1] == 0){
                            for(int k = 0; k < n && queue[k] != 0; k++){
                                if(!complete[queue[k] - 1]){
                                    idle = false;
                                }
                            }
                        }
                        else
                            idle = false;

                        if(idle){
                            timer++;
                            checkNewArrival(timer, arrival, n, maxProccessIndex, queue);
                        }

                        //Maintaining the entries of processes after each premption in the ready Queue
                        queueMaintainence(queue,n);
                    }
                }

                for(int i = 0; i < n; i++){
                    turn[i] = turn[i] - arrival[i];
                    wait[i] = turn[i] - burst[i];
                }
                for(int i = 0; i < n; i++){
                    int compl_time = turn[i] + arrival[i];
                    waiting.add(wait[i]);
                    turnAround.add(turn[i]);
                    completion.add(compl_time);
//                    System.out.print(i+1+"\t\t"+arrival[i]+"\t\t"+burst[i]
//                            +"\t\t"+wait[i]+"\t\t"+turn[i]+ "\n");
                }

                Intent intent=new Intent(RRactivity.this,AnswerActivity.class);
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
    public void queueUpdation(int queue[],int timer,int arrival[],int n, int maxProccessIndex){
        int zeroIndex = -1;
        for(int i = 0; i < n; i++){
            if(queue[i] == 0){
                zeroIndex = i;
                break;
            }
        }
        if(zeroIndex == -1)
            return;
        queue[zeroIndex] = maxProccessIndex + 1;
    }

    public void checkNewArrival(int timer, int arrival[], int n, int maxProccessIndex,int queue[]){
        if(timer <= arrival[n-1]){
            boolean newArrival = false;
            for(int j = (maxProccessIndex+1); j < n; j++){
                if(arrival[j] <= timer){
                    if(maxProccessIndex < j){
                        maxProccessIndex = j;
                        newArrival = true;
                    }
                }
            }
            if(newArrival)    //adds the index of the arriving process(if any)
                queueUpdation(queue,timer,arrival,n, maxProccessIndex);
        }
    }

    public static void queueMaintainence(int queue[], int n){

        for(int i = 0; (i < n-1) && (queue[i+1] != 0) ; i++){
            int temp = queue[i];
            queue[i] = queue[i+1];
            queue[i+1] = temp;
        }
    }

}