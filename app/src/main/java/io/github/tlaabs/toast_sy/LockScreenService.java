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
import android.os.Bundle;
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


        //하나의 랜덤 버킷 가져오기 n년이 지난...
        String today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        SQLiteDatabase db = new DBmanager(getApplicationContext()).getRDB();

        /*
        //todo 년은 다르게 하기 추가
        String selectQuery = "SELECT * FROM " + DBmanager.TABLE_ITEM
              + " WHERE STATE = 2 AND INSTR( " + DBmanager.KEY_COMPLETE_TIME + " , '" + today.substring(5, 10) + "')=6 AND "+DBmanager.KEY_COMPLETE_TIME+
              " NOT LIKE '"+today.substring(0,5)+"%' ORDER BY RANDOM() LIMIT 1"; // 월,일만 같은 하나만...*/

        String selectQuery = "SELECT * FROM " + DBmanager.TABLE_ITEM
              + " WHERE STATE = 2 AND INSTR( " + DBmanager.KEY_COMPLETE_TIME + " , '" + today.substring(5, 10) + "')=6 ORDER BY RANDOM() LIMIT 1"; //테스트용 완료 리스트에서 아무거나

        Log.v("LOCKT", "쿼리문 : "+selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())&& cursor.moveToFirst()) {
             Intent i = new Intent(context, LockScreenActivity.class);
             i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

             i.putExtra("title",cursor.getString(1));
             i.putExtra("date",cursor.getString(8));
             i.putExtra("img",cursor.getString(9));
            Log.v("LCOKT","----잠금화면 실행----");
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
