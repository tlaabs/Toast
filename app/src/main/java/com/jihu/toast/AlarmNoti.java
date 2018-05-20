package com.jihu.toast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

public class ArlarmNoti extends AppCompatActivity {
    EditText hour;
    Button service;
    Button service2;
    int howlong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        hour=(EditText)findViewById(R.id.hours);
        service=(Button)findViewById(R.id.alarmservice);
        service2=(Button)findViewById(R.id.alarmservice2);

        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howlong=Integer.parseInt(hour.getText().toString());
                //언제 알람뜰지 정하는 곳
                Calendar mCalendar = Calendar.getInstance();
                mCalendar.add(Calendar.MINUTE, + howlong); //howlong 만큼 분을 더함


                Intent notiAlarm = new Intent("toast.AlarmNoti.ALARM_START");//리시버 호출
                notiAlarm.putExtra("title","test");
                PendingIntent notiIntent = PendingIntent.getBroadcast(
                        getApplicationContext(), 1, notiAlarm,PendingIntent.FLAG_IMMUTABLE);

                AlarmManager notiManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                notiManager.set(AlarmManager.RTC_WAKEUP,mCalendar.getTimeInMillis(),notiIntent);
            }
        });

        service2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howlong=Integer.parseInt(hour.getText().toString());
                //언제 알람뜰지 정하는 곳
                Calendar mCalendar2 = Calendar.getInstance();
                mCalendar2.add(Calendar.MINUTE, + howlong); //howlong 만큼 분을 더함


                Intent notiAlarm2 = new Intent("toast.AlarmNoti.ALARM_START");//리시버 호출
                notiAlarm2.putExtra("title","test");
                PendingIntent notiIntent2 = PendingIntent.getBroadcast(
                        getApplicationContext(), 1, notiAlarm2,PendingIntent.FLAG_IMMUTABLE);

                AlarmManager notiManager2 = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                notiManager2.set(AlarmManager.RTC_WAKEUP,mCalendar2.getTimeInMillis(),notiIntent2);
            }
        });

    }
}
