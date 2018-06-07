package io.github.tlaabs.toast_sy;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.github.tlaabs.toast_sy.dbhelper.DBmanager;


public class ExtendBucketActivity extends AppCompatActivity {
    SQLiteDatabase db;
    Spinner hourSpinner;

    Button extendBtn;
    Button backBtn;

    String[] hours = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};


    String selectedHour = "1";


    BucketItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend_bucket);

        db = new DBmanager(getApplicationContext()).getWDB();
        init();

        //취소
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.cancel(item.getId());


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, hours);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hourSpinner.setAdapter(adapter1);
        hourSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedHour = hours[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        extendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues recordValues = new ContentValues();
                //Log.i("ppp",item.getEndTime());

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date=null;
                Calendar cal = Calendar.getInstance();
                try {
                    date = sdf.parse(item.getEndTime());
                    cal.setTime(date);
                }catch (Exception e){}


                int howlong = Integer.parseInt(selectedHour);

//                cal.add(Calendar.SECOND, +howlong); //toDO 테스트용
                cal.add(Calendar.HOUR, +howlong); //toDO 테스트용

                String end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
                recordValues.put("END_TIME",end);

//                Log.i("date","date : " + today);
//                recordValues.put("REG_TIME", today);

                db.update(DBmanager.TABLE_ITEM,recordValues,"ID=" + item.getId(),null);
                Toast.makeText(getApplicationContext(),"연장 완료!",Toast.LENGTH_SHORT).show();


                //알람 설정하는 부분----------------------------------------------
                Intent notiAlarm = new Intent("toast.AlarmNoti.ALARM_START");//리시버 호출
                notiAlarm.putExtra("item", item); //물어보는 버킷 이름
                notiAlarm.putExtra("type",1); // 타입 0은 bukcet, 1은 inpro
                int notiId=item.getId(); //서로 다른 id 부여

                PendingIntent notiIntent = PendingIntent.getBroadcast(
                        getApplicationContext(), notiId, notiAlarm,PendingIntent.FLAG_IMMUTABLE);

                AlarmManager notiManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                notiManager.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),notiIntent);
                //----------------------------------------------------------------------------
                db.close();
                finish();
            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }

    public int getHoursIdxFromItem(BucketItem item){
        String h = item.getUsageTime();

        for(int i = 0 ; i < hours.length; i++)
            if(h.equals(hours[i])) return i;

        return -1;
    }



    public void init() {
//        titleEdit = findViewById(R.id.titleEdit);
        hourSpinner = findViewById(R.id.hour_spinner);
        extendBtn = findViewById(R.id.extendBtn);
//        deleteBtn = findViewById(R.id.deleteBtn);
        backBtn = findViewById(R.id.backBtn);

        //getIntent

        Intent i = getIntent();
        item = (BucketItem)i.getSerializableExtra("item");

//        titleEdit.setText(item.getTitle());
    }
}
