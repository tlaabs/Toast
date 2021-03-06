package io.github.tlaabs.toast_sy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

import io.github.tlaabs.toast_sy.Alarm.OnceADay;

import static com.kakao.util.helper.Utility.getPackageInfo;

public class MainActivity extends AppCompatActivity {
    boolean securityCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.btn_bucket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), BucketListActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.btn_ongoing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), OnGoingActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.btn_complete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.btn_recommend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RecommendActivity.class);
                startActivity(i);
            }
        });

        DBThread th = new DBThread();
        th.start();

        Log.i("haaha",getKeyHash(this));



    }

    public static String getKeyHash(final Context context) {
        PackageInfo packageInfo = getPackageInfo(context, PackageManager.GET_SIGNATURES);
        if (packageInfo == null)
            return null;

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
            } catch (NoSuchAlgorithmException e) {
            }
        }
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences pref = getSharedPreferences("security", MODE_PRIVATE);
        String set = pref.getString("securityType","FREE");
        String pw = pref.getString("pw","0000");

        if(securityCheck == false && set.equals("PW")){
            Intent i = new Intent(this,CheckingPW.class);
            i.putExtra("pw",pw);
            startActivityForResult(i,1);
        }
        else if(securityCheck == false && set.equals("FP")){
            Intent i = new Intent(this,CheckingFP.class);
            startActivityForResult(i,2);
        }

        //매일 하나씩 알람 하는 부분
        SQLiteDatabase db = openOrCreateDatabase("sim.db", MODE_PRIVATE, null);
        OnceADay dailyTask = new OnceADay(db,this);

        Calendar cal = Calendar.getInstance();

        SharedPreferences pref2 = getSharedPreferences("alarm", MODE_PRIVATE);

        int d =pref2.getInt("D",0);
        int h = pref2.getInt("H",3);
        int m = pref2.getInt("M",0);
        Log.i("tt",h + "|"+m);

        int result = dailyTask.execute(d,h,m); // toDO 테스트 환경 수정
        if(result>0){ //알람 설정이 완료 되었을때
            SharedPreferences.Editor pref2Editor = pref2.edit();
            Log.v("tt","Bucket 알람이 설정 되었습니다. :" + d + " " + h + " " + m + " " + result);
            pref2Editor.putInt("D",result);
            pref2Editor.commit();
        }
        Log.v("tt","Result는 이와 같습니다 : " + d + " = " + result);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                securityCheck = true;
            }else{
                finish();
            }
        }
        //지문 인식 ok
        else if(requestCode == 2){

                securityCheck = true;

        }
    }

    /**
     *  main_menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int curId = item.getItemId();
        switch (curId){
            case R.id.menu_alarm:
                return true;

            case R.id.menu_setting:
                Intent menu_intent2 = new Intent(getApplicationContext(),SettingActivity.class);
                startActivity(menu_intent2);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class DBThread extends Thread {
        @Override
        public void run() {
            SQLiteDatabase db = openOrCreateDatabase("sim.db", MODE_PRIVATE, null);
//            db.execSQL("DROP TABLE simDB");
            db.execSQL("CREATE TABLE IF NOT EXISTS " + "simDB" + "("
                    + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "TITLE TEXT, "
                    + "CATEGORY TEXT, "
                    + "USAGE_TIME STRING, "
                    + "REG_TIME DATE, "
                    + "START_TIME DATE, "
                    + "STATE INTEGER, " //0 , 1 , 2
                    + "END_TIME DATE, "
                    + "COMPLETE_TIME DATE, " //8
                    + "IMG_SRC STRING, "
                    + "REVIEW STRING);");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + "category" + "("
                    + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "CATEGORY TEXT);");

//            db.execSQL("DROP TABLE category;");

            db.close();


        }
    }

}
