package io.github.tlaabs.toast_sy.Alarm;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.github.tlaabs.toast_sy.AddHistoryBucketActivity;
import io.github.tlaabs.toast_sy.BucketItem;
import io.github.tlaabs.toast_sy.BucketListActivity;
import io.github.tlaabs.toast_sy.ExtendBucketActivity;
import io.github.tlaabs.toast_sy.R;
import io.github.tlaabs.toast_sy.dbhelper.DBmanager;

public class startNow extends AppCompatActivity {
    Button start;
    Button keep;
    int itemId;
    BucketItem item;
    TextView message;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_now);

        start=(Button)findViewById(R.id.startB);
        keep=(Button)findViewById(R.id.cancel);
        message=(TextView)findViewById(R.id.message);
        db = new DBmanager(getApplicationContext()).getWDB();
        Intent i = getIntent();
        itemId=i.getIntExtra("nid",-1);

        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.cancel(itemId);

        Cursor cursor = db.rawQuery("SELECT * FROM "+DBmanager.TABLE_ITEM+" WHERE "+DBmanager.KEY_ID+" = "+itemId,null);
        cursor.moveToFirst();

        item = new BucketItem();

        item.setId(itemId);
        item.setTitle(cursor.getString(cursor.getColumnIndex(DBmanager.KEY_TITLE)));
        item.setUsageTime(cursor.getString(cursor.getColumnIndex(DBmanager.KEY_USAGE_TIME)));

        message.setText(item.getTitle() + " 한번 시작해 볼까요?");

        keep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!item.getUsageTime().equals("-")) {
                    //todo : -일때 구분...
                    //-----------------초기 설정
                    int howlong = Integer.parseInt(item.getUsageTime());
                    Intent notiAlarm = new Intent("toast.AlarmNoti.ALARM_START");//리시버 호출

                    notiAlarm.putExtra("item", item); //item
                    notiAlarm.putExtra("type", 1); // 타입 0은 bukcet, 1은 inpro



                    int notiId = item.getId(); //서로 다른 id 부여
                    PendingIntent notiIntent = PendingIntent.getBroadcast(
                            getApplicationContext(), notiId, notiAlarm, PendingIntent.FLAG_IMMUTABLE);
                    //------------------------------
                    Log.v("tt","초기설정 완료 : "+item.getTitle());
                    ContentValues recordValues = new ContentValues();

                    recordValues.put(DBmanager.KEY_STATE, 1);
                    String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    recordValues.put(DBmanager.KEY_START_TIME, now);

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new Date());

                    cal.add(Calendar.HOUR, +howlong); //toDo : 테스트 용으로 chfmf eksdnl
                    String end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
                    recordValues.put(DBmanager.KEY_END_TIME, end);


                    db.update(DBmanager.TABLE_ITEM, recordValues, DBmanager.KEY_ID + " = " + item.getId(), null);

                    Toast.makeText(getApplicationContext(), "진행중!", Toast.LENGTH_SHORT).show();

                    //알람 설정하는 부분-----------------------------------------------------------


                    AlarmManager notiManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                    notiManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), notiIntent);
                    //----------------------------------------------------------------------------
                    Log.v("tt","알람 설정 완료 : "+item.getTitle());
                }else{
                    Intent i = new Intent(getApplicationContext(), AddHistoryBucketActivity.class);
                    i.putExtra("item",item);
                    startActivityForResult(i,1);
                }
                db.close();
                finish();
            }
        });

    }
}
