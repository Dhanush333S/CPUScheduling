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

public class SRTFactivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_srtfactivity);

        floatingActionButton=findViewById(R.id.floatingAdd);
        tableLayout =findViewById(R.id.tableLayout);
        name=findViewById(R.id.processName);
        arrival=findViewById(R.id.Arrival);
        burst=findViewById(R.id.Burst);
        button=findViewById(R.id.button);

        TableRow row=new TableRow(SRTFactivity.this);

        TextView newName= new TextView(SRTFactivity.this);
        newName.setText("Name");
        newName.setGravity(Gravity.CENTER);
        TextView newArrival=new TextView(SRTFactivity.this);
        newArrival.setText("Arrival");
        newArrival.setGravity(Gravity.CENTER);
        TextView newBurst =new TextView(SRTFactivity.this);
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
                    Toast.makeText(SRTFactivity.this, "Fill in the Credentials", Toast.LENGTH_SHORT).show();
                    return;
                }

                TableRow row=new TableRow(SRTFactivity.this);

                TextView newName= new TextView(SRTFactivity.this);
                newName.setText(uploadName);
                newName.setGravity(Gravity.CENTER);
                strName.add(uploadName);
                TextView newArrival=new TextView(SRTFactivity.this);
                newArrival.setText(uploadArrival);
                newArrival.setGravity(Gravity.CENTER);
                arrivalInt.add(Integer.parseInt(uploadArrival));
                TextView newBurst =new TextView(SRTFactivity.this);
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
                String[] processes = strName.toArray(new String[0]);
                int n = processes.length;
                int at[] = new int[n]; // at means arrival time
                int bt[] = new int[n]; // bt means burst time
                int ct[] = new int[n]; // ct means complete time
                int ta[] = new int[n];// ta means turn around time
                int wt[] = new int[n];  // wt means waiting time
                int f[] = new int[n];  // f means it is flag it checks process is completed or not
                int k[] = new int[n];   // it is also stores brust time
                int i, st = 0, tot = 0;

                for(i=0;i<n;i++) {
                    at[i] = arrivalInt.get(i);
                    bt[i] = burstInt.get(i);
                    k[i]=bt[i];
                    f[i]=0;
                }

                while (true) {
                    int min = 99, c = n;
                    if (tot == n)
                        break;

                    for (i = 0; i < n; i++) {
                        if ((at[i] <= st) && (f[i] == 0) && (bt[i] < min)) {
                            min = bt[i];
                            c = i;
                        }
                    }

                    if (c == n)
                        st++;
                    else {
                        bt[c]--;
                        st++;
                        if (bt[c] == 0) {
                            ct[c] = st;
                            f[c] = 1;
                            tot++;
                        }
                    }
                }
                for(i=0;i<n;i++)
                {
                    ta[i] = ct[i] - at[i];
                    wt[i] = ta[i] - k[i];
                }
                for (i = 0; i < n; i++) {
                    int compl_time = ta[i] + at[i];
                    waiting.add(wt[i]);
                    turnAround.add(ta[i]);
                    completion.add(compl_time);
//                    System.out.println("\t" + at[i] + "\t" + k[i] + "\t" + ct[i] + "\t" + ta[i] + "\t" + wt[i]);
                }

                Intent intent=new Intent(SRTFactivity.this,AnswerActivity.class);
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
}