package io.github.tlaabs.toast_sy;


import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

public class LockScreenActivity extends AppCompatActivity {

    ImageButton btn_unlock;
    ImageView image;
    TextView DateDisplay,TimeDisplay;
    LinearLayout linear;

    private int Year, Month,Day,Hour, Minute;
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

        btn_unlock = (ImageButton)findViewById(R.id.btn_unlock);
        image=(ImageView)findViewById(R.id.btn_unlock);
        linear = (LinearLayout)findViewById(R.id.linear1);

        image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(
                        getApplicationContext(), R.anim.translate);
                image.startAnimation(anim);


                anim.setAnimationListener(new AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        finish();
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                });

            }
        });

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
    }
    ////////////////////////////////////////////////////////// 하드웨어 back 버튼 누르기 무시//
    @Override
    public void onBackPressed() {
        //
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



}