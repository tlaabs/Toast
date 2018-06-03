package io.github.tlaabs.toast_sy;

import android.app.Service;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.github.tlaabs.toast_sy.dbhelper.DBmanager;

public class LockScreenService extends Service {

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
        Log.v("LCOKT","----서비스 실행----");

        /*
        //하나의 랜덤 버킷 가져오기 n년이 지난...
        String today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        SQLiteDatabase db = new DBmanager(getApplicationContext()).getRDB();

         //todo 년은 다르게 하기 추가
         String selectQuery="SELECT * FROM "+DBmanager.TABLE_ITEM
                  +" WHERE STATE = 2 AND SUBSTR("
                  +DBmanager.KEY_COMPLETE_TIME+",6,5) = "+today.substring(5,10)+ " ORDER BY RANDOM() LIMIT 1"; // 월,일만 같은 하나만...
         Log.v("LOCKT", "지정된 시간 : " + today.substring(5,10));

         String test="SELECT * FROM " + DBmanager.TABLE_ITEM + " WHERE STATE = 2";
         Cursor c1 = db.rawQuery(test,null);
         c1.moveToFirst();
         Log.v("LOCKT", "지정된 잠금화면 title : " + c1.getColumnIndex(DBmanager.KEY_TITLE));

         Cursor cursor = db.rawQuery(selectQuery,null);
         if(cursor.moveToFirst()){
                Log.v("LOCKT", "지정된 잠금화면 title : " + cursor.getColumnIndex(DBmanager.KEY_TITLE));
                Log.v("LCOKT", "지정된 잠금화면 날짜 : " + cursor.getColumnIndex(DBmanager.KEY_COMPLETE_TIME));
                Uri img = Uri.parse(cursor.getString(9));
             InputStream inputStream = null;
             try {
                 inputStream = getContentResolver().openInputStream(img);
             } catch (FileNotFoundException e) {
                 e.printStackTrace();
             }

             WallpaperManager wpm = WallpaperManager.getInstance(context);
             try {
                 wpm.setStream(inputStream);
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }*/
            if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
                Intent i = new Intent(context, LockScreenActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }


        }
    };

    ////////////////////////////////////////////////////////// 하드웨어 back 버튼 누르기 금지//
    public void onBackPressed(){
     //
    }



    @Override

    public IBinder onBind(Intent intent) {

        return null;

    }



    @Override

    public void onCreate() {

        super.onCreate();

        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);

        registerReceiver(receiver, filter);

    }

    @Override
    public void onDestroy() {

        super.onDestroy();

        unregisterReceiver(receiver);

    }
}
