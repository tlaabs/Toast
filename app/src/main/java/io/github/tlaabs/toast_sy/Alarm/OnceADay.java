package io.github.tlaabs.toast_sy.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

import io.github.tlaabs.toast_sy.BucketItem;
import io.github.tlaabs.toast_sy.dbhelper.DBmanager;

import static android.content.Context.MODE_PRIVATE;

public class OnceADay{
    private SQLiteDatabase db;
    private String selectQuery;
    private Context context;


    public OnceADay(Context context){
        db=new DBmanager(context).getWDB();
        this.context=context;
        selectQuery="SELECT * FROM "+DBmanager.TABLE_ITEM+" WHERE STATE = 0 ORDER BY RANDOM() LIMIT 1"; //state 가 0인 애들중 랜덤으로 하나
    }


    public int execute(int day, int hour, int minute) {
        Calendar cal = Calendar.getInstance();
        int currentDate = cal.get(Calendar.DAY_OF_MONTH);

        if (currentDate != day && db != null) {//하루에 한번만
            Cursor c = db.rawQuery(selectQuery, null);

            //아이템이 있을시에만...
            if (c.moveToFirst()) {

                BucketItem item = new BucketItem();
                item.setTitle(c.getString(c.getColumnIndex(DBmanager.KEY_TITLE)));
                item.setId(c.getInt(c.getColumnIndex(DBmanager.KEY_ID)));
                item.setRegTime(c.getString(c.getColumnIndex(DBmanager.KEY_REGISTER_TIME)));
                item.setUsageTime(c.getString(c.getColumnIndex(DBmanager.KEY_USAGE_TIME)));

                cal.set(Calendar.HOUR_OF_DAY,hour); //24시간... 알람 띄울 시간
                cal.set(Calendar.MINUTE,minute);
                cal.set(Calendar.SECOND,0);
                //알림 띄우기
                Intent notiAlarm = new Intent("toast.AlarmNoti.ALARM_START");//리시버 호출
                notiAlarm.putExtra("Title",item.getTitle());
                notiAlarm.putExtra("Id",item.getId());
                notiAlarm.putExtra("type", 0); // 타입 0은 bukcet, 1은 inpro


                Log.v("tt","BUCKET 알람 설정 title "+ item.getTitle());
                Log.v("tt","BUCKET 알람 설정 정보"+ cal.get(Calendar.HOUR_OF_DAY)+"|"+cal.get(Calendar.MINUTE));

                PendingIntent notiIntent = PendingIntent.getBroadcast(
                        context, -1, notiAlarm, PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager notiManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                notiManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), notiIntent);

                return cal.get(Calendar.DAY_OF_MONTH); //day 업데이트
            }
        }
        return -1; //아무 것도 안할시
    }
}
