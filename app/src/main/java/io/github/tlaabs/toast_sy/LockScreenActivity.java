package io.github.tlaabs.toast_sy;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.github.tlaabs.toast_sy.dbhelper.DBmanager;

public class LockScreenActivity extends AppCompatActivity {

    //ImageButton btn_unlock;
    //ImageView image;
    TextView DateDisplay,TimeDisplay;
    TextView msg;
    View background;

    GestureDetector detector;

    private int Year, Month,Day,Hour, Minute;
    private static String provider ="io.github.tlaabs.toast_sy";
    public int mYear=2018, mMonth=5, mDay=20, mHour=12, mMinute=10;

    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID = 0;

    private Runnable r;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        // 기본 잠금화면보다 우선출력,기본 잠금화면 해제시키기 - 최상위 우선순위로.
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);


        // Activity FullScreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_lock_screen);


        //btn_unlock = (ImageButton) findViewById(R.id.btn_unlock);
        //image = (ImageView) findViewById(R.id.btn_unlock);
        background = findViewById(R.id.backgruond);
        msg = (TextView)findViewById(R.id.msg);

        DateDisplay = (TextView) findViewById(R.id.dateDisplay);
        TimeDisplay = (TextView) findViewById(R.id.timeDisplay);

        // final Calendar c = Calendar.getInstance();

        mHandler = new Handler();

        r = new Runnable() {
            @Override
            public void run() {
                updateDisplay();

                if(Minute==47) {

                }
            }
        };
        mHandler.postDelayed(r, 1000);


        // 손으로 화면을 미는 제스처를 취하면 잠금화면 꺼지도록.
        detector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                finish();
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }
        });


        background.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                detector.onTouchEvent(motionEvent);

                return true;
            }
        });
    }


    @Override
    public void onResume(){
        super.onResume();

        //하나의 랜덤 버킷 가져오기 n년이 지난...
        String today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        SQLiteDatabase db = new DBmanager(getApplicationContext()).getRDB();

        //todo 년은 다르게 하기 추가
        String selectQuery = "SELECT * FROM " + DBmanager.TABLE_ITEM
                + " WHERE STATE = 2 AND INSTR( " + DBmanager.KEY_COMPLETE_TIME + " , '" + today.substring(5, 10) + "')=6 AND "+DBmanager.KEY_COMPLETE_TIME+
                " NOT LIKE '"+today.substring(0,5)+"%' ORDER BY RANDOM() LIMIT 1"; // 월,일만 같은 하나만...
        Log.v("LOCKT", "쿼리문 : "+selectQuery);

        /*
        //----------------------        String test="SELECT * FROM " + DBmanager.TABLE_ITEM + " WHERE "+DBmanager.KEY_COMPLETE_TIME+" LIKE '%06-04%'=6";
        String test="SELECT INSTR( "+DBmanager.KEY_COMPLETE_TIME+" , '06-04') FROM " + DBmanager.TABLE_ITEM + " WHERE STATE = 2";
        Cursor c1 = db.rawQuery(test,null);
        c1.moveToFirst();
        Log.v("LOCKT", "쿼리문 : " + test);
        Log.v("LOCKT", "테스트 : " + c1.getString(0));
        //Log.v("LOCKT", "잠금화면 날짜 : " + c1.getString(8));
        //-----------------------*/

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {  //선택된 item을 가지고....
            Log.v("LOCKT", "지정된 잠금화면 title : " + cursor.getString(1));
            Log.v("LCOKT", "지정된 잠금화면 날짜 : " + cursor.getString(8));

            Uri img = Uri.parse(cursor.getString(9));
            grantUriPermission(provider,img, Intent.FLAG_GRANT_READ_URI_PERMISSION);

            File file = new File(getRealPathFromUri(img));
            int interval = Integer.parseInt(cursor.getString(8).substring(0,3))-Integer.parseInt(today.substring(0,3));
            msg.setText(interval+" 년 전에는 "+cursor.getString(1)+" 했어요!");
            if (file.exists()) {
                Drawable d = Drawable.createFromPath(file.getAbsolutePath());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    background.setBackground(d);

                }else{
                    Toast.makeText(this,"젤리빈 이상만 지원되는 기능입니다.",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private String getRealPathFromUri(Uri img){

        grantUriPermission(provider,img, Intent.FLAG_GRANT_READ_URI_PERMISSION);

        Cursor c1 = getContentResolver().query(img,null,null,null,null);
        if(c1==null){
            return img.getPath();
        }else{
            c1.moveToFirst();
            int idx = c1.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return c1.getString(idx);
        }
    }

    private void updateDisplay() {

        final Calendar c = Calendar.getInstance();
        Year = c.get(Calendar.YEAR);
        Month = c.get(Calendar.MONTH);
        Day = c.get(Calendar.DAY_OF_MONTH);

        Hour = c.get(Calendar.HOUR_OF_DAY);
        Minute = c.get(Calendar.MINUTE);

        DateDisplay.setText(Year+"년 "+(Month+1) +"월 "+ Day+ "일 ");
        TimeDisplay.setText(pad(Hour)+":"+ pad(Minute));

        mHandler.postDelayed(r, 1000);
    }
    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }




/*
    ////////////////////////////////////////////////////////// 하드웨어 back 버튼 누르기 무시//
    @Override
    public void onBackPressed() {
        //
    }

    //키 이벤트 무시
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if((keyCode==KeyEvent.KEYCODE_HOME)
                ||(keyCode==KeyEvent.KEYCODE_DPAD_LEFT)
                ||(keyCode==KeyEvent.KEYCODE_DPAD_RIGHT)
                ||(keyCode==KeyEvent.KEYCODE_DPAD_RIGHT)
                ||(keyCode==KeyEvent.KEYCODE_CALL)
                ||(keyCode==KeyEvent.KEYCODE_ENDCALL))
        {
            return true;
        }

        // return super.onKeyDown(keyCode, event);
        return false;
    }*/


}