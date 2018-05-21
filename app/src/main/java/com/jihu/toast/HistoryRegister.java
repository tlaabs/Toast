package com.jihu.toast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Date;

public class HistoryRegister extends AppCompatActivity {

    EditText name;
    DatePicker date;
    TimePicker time;
    Button picinsert;
    Button pictake;
    EditText memo;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_register);


        name = (EditText)findViewById(R.id.name);
        date = (DatePicker)findViewById(R.id.date);
        time = (TimePicker)findViewById(R.id.time);
        picinsert = (Button)findViewById(R.id.picinsert);
        pictake = (Button)findViewById(R.id.pictake);
        memo = (EditText)findViewById(R.id.memo);
        save = (Button)findViewById(R.id.save);

        //사진 찍기 버튼을 눌렀을때
        pictake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //사진 가져오기 버튼을 눌렀을때
        picinsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //저장 버튼을 눌렀을때
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){


            }
        });
    }
}
